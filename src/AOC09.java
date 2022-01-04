import java.io.InputStream;
import java.util.*;

public class AOC09 {



    public static void main(String[] args) {
        // read input, fill grid
        List<String> input = readFile("inputs/aoc09.txt");
        Map<ArrayList<Integer>, Integer> grid = populateGrid(input);

        // find low points
        ArrayList<ArrayList<Integer>> lowPoints = findLowPoints(grid);
        int totalRiskLevel = sumLowPointRiskLevel(grid, lowPoints);

        // find largest bassins
        int bassinArea = findLargestBassins(grid, lowPoints);

        // print solution
        System.out.println("PART 1: " + totalRiskLevel); // 532
        System.out.println("PART 2: " + bassinArea); // 1110780
    }

    public static List<String> readFile(String filepath) {
        InputStream is = AOC09.class.getClassLoader().getResourceAsStream(filepath);
        List<String> input = new ArrayList<>();

        try (Scanner scanner = new Scanner(is)) {
            while (scanner.hasNextLine()) {
                input.add(scanner.nextLine());
            }
        }

        return input;
    }

    public static Map<ArrayList<Integer>, Integer> populateGrid(List<String> input){
        Map<ArrayList<Integer>, Integer> grid = new HashMap<>();

        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);

            for (int x = 0; x < line.length(); x++) {
                Integer height = Integer.parseInt(String.valueOf(line.charAt(x)));
                ArrayList<Integer> pos =  new ArrayList<>();
                pos.add(x);
                pos.add(y);

                grid.put(pos, height);
            }
        }

        return grid;
    }

    public static ArrayList<ArrayList<Integer>> getNeighbors(Map<ArrayList<Integer>, Integer> grid, ArrayList<Integer> position){
        int x = position.get(0);
        int y = position.get(1);
        int[] moveSet = {-1, 1};

        ArrayList<ArrayList<Integer>> neighbors = new ArrayList<>();

        for (int dx: moveSet){
            ArrayList<Integer> neighborPosition = new ArrayList<>();
            neighborPosition.add(x + dx);
            neighborPosition.add(y);

            if (grid.containsKey(neighborPosition)){
                neighbors.add(neighborPosition);
            }
        }

        for (int dy: moveSet){
            ArrayList<Integer> neighborPosition = new ArrayList<>();
            neighborPosition.add(x);
            neighborPosition.add(y + dy);

            if (grid.containsKey(neighborPosition)){
                neighbors.add(neighborPosition);
            }
        }

        return neighbors;
    }

    public static ArrayList<ArrayList<Integer>> findLowPoints(Map<ArrayList<Integer>, Integer> grid){
        ArrayList<ArrayList<Integer>> lowPoints = new ArrayList<>(); // position of lowPoints, of unknown length

        // iterate over grid
        for (Map.Entry<ArrayList<Integer>, Integer> entry : grid.entrySet()) {
            // get position, height
            ArrayList<Integer> position = entry.getKey();
            Integer height = entry.getValue();

            // get neighbors
            ArrayList<ArrayList<Integer>> neighbors = getNeighbors(grid, position);

            // verify whether current point is local minimum
            int counter = 0;
            int numNeighbors = neighbors.size();

            for (ArrayList<Integer> neighbor: neighbors){
                if (grid.containsKey(neighbor)){
                    Integer neighborHeight = grid.get(neighbor);
                    if (height < neighborHeight){
                        counter++;
                    }
                }

            }

            // add to lowPoints if is local minimum
            if (counter == numNeighbors){
                lowPoints.add(position);
            }
        }

        return lowPoints;
    }

    public static int BFS(Map<ArrayList<Integer>, Integer> grid, ArrayList<Integer> start){
        // initialize variables
        ArrayList<ArrayList<Integer>> queue = new ArrayList<>();
        HashSet<ArrayList<Integer>> visited = new HashSet<>();
        int steps = 0;

        // add first node to queue
        queue.add(start);

        // visit all nodes from this queue
        while (queue.size() > 0){
            // pop queue
            ArrayList<Integer> position = queue.get(0);
            queue.remove(0);

            // skip if visited
            if (visited.contains(position)) {
                continue;
            }
            // if not, add to set, increment steps, add eligible neighbors to queue
            visited.add(position);
            steps++;
            ArrayList<ArrayList<Integer>> neighbors = getNeighbors(grid, position);

            for (ArrayList<Integer> neighbor: neighbors){
                if (grid.get(neighbor) < 9){
                    queue.add(neighbor);
                }
            }
        }

        return steps;
    }

    public static int sumLowPointRiskLevel(Map<ArrayList<Integer>, Integer> grid, ArrayList<ArrayList<Integer>> lowPoints){
        int total = 0;

        for (ArrayList<Integer> position: lowPoints){
            Integer height = grid.get(position);
            total += 1 + height;
        }
        return total;
    }


    public static int findLargestBassins(Map<ArrayList<Integer>, Integer> grid, ArrayList<ArrayList<Integer>> lowPoints){
        ArrayList<Integer> bassins = new ArrayList<>();

        // find all bassin sizes
        for (ArrayList<Integer> position: lowPoints){
            Integer bassinSize = BFS(grid, position);
            bassins.add(bassinSize);
        }

        // multiply three biggest
        bassins.sort(Collections.reverseOrder());
        return bassins.get(0) * bassins.get(1) * bassins.get(2);
    }
}

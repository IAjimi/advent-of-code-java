import java.io.InputStream;
import java.util.*;

public class AOC25 {

    private static final char EAST = '>';
    private static final char SOUTH = 'v';

    public static void main(String[] args) {
        // read input file
        List<String> input = readFile("inputs/aoc25.txt");


        int height = input.size();
        int width = input.get(0).length();

        // add sea cucumbers to horizontal and vertical sets
        ArrayList<HashSet<List<Integer>>> bundledSets = processInput(input);
        HashSet<List<Integer>> horizontal = bundledSets.get(0);
        HashSet<List<Integer>> vertical = bundledSets.get(1);

        // create herds
        SeaCucumberHerd eastCucumbers = new SeaCucumberHerd(horizontal, EAST, width, height);
        SeaCucumberHerd southCucumbers = new SeaCucumberHerd(vertical, SOUTH, width, height);

        // get solution
        int part1Solution = part1(eastCucumbers, southCucumbers);
        System.out.println("PART 1: " + part1Solution);
    }

    public static List<String> readFile(String filepath) {
        InputStream is = AOC25.class.getClassLoader().getResourceAsStream(filepath);
        List<String> input = new ArrayList<>();

        try (Scanner scanner = new Scanner(is)) {
            while (scanner.hasNextLine()) {
                input.add(scanner.nextLine());
            }
        }

        return input;
    }

    public static ArrayList<HashSet<List<Integer>>> processInput(List<String> input) {
        HashSet<List<Integer>> horizontal = new HashSet<>();
        HashSet<List<Integer>> vertical = new HashSet<>();

        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);

            for (int x = 0; x < line.length(); x++) {
                char direction = line.charAt(x);
                List<Integer> pos =  new ArrayList<>();
                pos.add(x);
                pos.add(y);

                if (direction == EAST){
                    horizontal.add(pos);
                } else if (direction == SOUTH){
                    vertical.add(pos);
                }
            }
        }
        ArrayList<HashSet<List<Integer>>> bundledSets = new ArrayList<>();
        bundledSets.add(horizontal);
        bundledSets.add(vertical);
        return bundledSets;
    }

    public static int part1(SeaCucumberHerd eastCucumbers, SeaCucumberHerd southCucumbers) {
        // iterate over set
        int steps = 0;
        HashSet<List<Integer>> horizontal = eastCucumbers.positions;
        HashSet<List<Integer>> vertical = southCucumbers.positions;

        while (steps < 500){
            // update steps
            steps ++;

            // move herds
            boolean eastChanged = eastCucumbers.moveHerd(vertical);
            horizontal = eastCucumbers.positions;

            boolean southChanged = southCucumbers.moveHerd(horizontal);
            vertical = southCucumbers.positions;

            boolean changed = southChanged || eastChanged;

            // if reached static state, stop
            if (!changed){
                return steps;
            }
        }

        return -1;
    }

}
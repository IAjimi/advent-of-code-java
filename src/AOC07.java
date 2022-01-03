import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

// better way to get MAX RANGE
// better way to select cost function (small classes?)

public class AOC07 {

    static int MAX_RANGE;
    static int[] crabPositions;

    public static void main(String[] args) {
        // read input, initialize crabPositions, MAX_RANGE
        processFile("src/inputs/aoc07.txt");

        // move to any position in range, with least fuel
        int part1_solution = findLeastCostlyMove(1);
        int part2_solution = findLeastCostlyMove(2);

        // print solution
        System.out.println("PART 1: " + part1_solution); // 356992
        System.out.println("PART 2: " + part2_solution); // 101268110
    }

    public static int findMaxRange(){
        int maxRange = -100000;

        for (int pos: crabPositions){
            if (pos > maxRange){
                maxRange = pos;
            }
        }

        return maxRange;
    }

    public static void processFile(String filepath) {
        // open reader
        Scanner reader = null;
        try {
            reader = new Scanner(new File(filepath));
        } catch (Exception e) {
            System.out.println("File not found.");
        }

        // get crabPositions
        crabPositions = Arrays.stream(reader.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();

        // get max range
        MAX_RANGE = findMaxRange();
    }

    public static int linearCost(int start, int end){
        return Math.abs(start - end);
    }

    public static int otherCost(int start, int end){
        int totalDist = Math.abs(1 + start - end);
        return totalDist * (totalDist + 1) /  2;
    }

    public static int calculateMoveCost(int end, int part){
        int sum = 0;

        for (int start: crabPositions){
            if (part == 1) {
                sum += linearCost(start, end);
            }
            else if (part == 2){
                sum += otherCost(start, end);
            }
        }

        return sum;
    }

    public static int findLeastCostlyMove(int part){
        int minCost = 1000000000;

        for (int dest = 0; dest < MAX_RANGE; dest++){
            int newCost = calculateMoveCost(dest, part);

            if (newCost < minCost){
                minCost = newCost;
            }
        }

        return minCost;
    }
}

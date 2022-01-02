import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class AOC06 {
    static final int MAX_AGE = 8 + 1; // max lanternfish age
    static int[] lanternfishList; // input
    static long[] lanternfishAges = new long[MAX_AGE]; // lantership counter

    public static void main(String[] args) {
        // read input
        processFile("src/inputs/aoc06.txt");

        // simulate lanternfish growth
        long part1_solution = simulateLanternfishGrowth(80);
        long part2_solution = simulateLanternfishGrowth(256);

        // print solution
        System.out.println("PART 1: " + part1_solution); // 365862
        System.out.println("PART 2: " + part2_solution); // 1653250886439
    }

    public static void processFile(String filepath) {
        // open reader
        Scanner reader = null;
        try {
            reader = new Scanner(new File(filepath));
        } catch (Exception e) {
            System.out.println("File not found.");
        }

        // get lanternfishList
        lanternfishList = Arrays.stream(reader.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
    }

    public static void countLanternfish(){
        for (int age: lanternfishList){
            lanternfishAges[age]++;
        }
    }

    public static void growLanternfish(){
        long[] newLanternfishAges = new long[MAX_AGE];

        for (int age = 0; age < lanternfishAges.length; age++) {
            long count = lanternfishAges[age];

            if (age == 0){
                newLanternfishAges[8] = count;
                newLanternfishAges[6] += count;
            }
            else {
                newLanternfishAges[age - 1] += count;
            }
        }

        lanternfishAges = newLanternfishAges;
    }

    public static long simulateLanternfishGrowth(int days){
        // create array that holds # of fish of age ix at pos ix
        // (need to reset this since running it 2x)
        countLanternfish();

        // simulate growth for days
        for (int i = 0; i < days; i ++){
            growLanternfish();
        }

        // return total number of lanternfish
        return Arrays.stream(lanternfishAges).sum();
    }
}

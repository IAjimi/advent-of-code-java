import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AOC01 {

    public static void main(String[] args) {
        List<Integer> input = readFile("inputs/aoc1.txt");
        int part1_solution = countIncrease(input);
        int part2_solution = countSlidingWindowIncrease(input);
        System.out.println("PART 1: " + part1_solution); // 1233
        System.out.println("PART 2: " + part2_solution); // 1275
    }

    public static List<Integer> readFile(String filepath) {
        InputStream is = AOC01.class.getClassLoader().getResourceAsStream(filepath);
        List<Integer> input = new ArrayList<>();
        try (Scanner scanner = new Scanner(is)) {
            while (scanner.hasNextInt()) {
                input.add(scanner.nextInt());
            }
        }
        return input;
    }

    public static int countIncrease(List<Integer> input) {
        int counter = 0;

        for (int i = 1; i < input.size(); i++) {
            int current = input.get(i);
            int previous = input.get(i - 1);

            if (current > previous) {
                counter++;
            }
        }
        return counter;
    }

    public static int countSlidingWindowIncrease(List<Integer> input) {
        int counter = 0;

        for (int i = 1; i < input.size() - 2; i++) {
            int current = input.get(i + 2);
            int previous = input.get(i - 1);

            if (current > previous) {
                counter++;
            }
        }
        return counter;
    }

}

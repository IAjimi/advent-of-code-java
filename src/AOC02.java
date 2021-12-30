import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class AOC02 {

    public static void main(String[] args) {
        // read input file
        List<String> input = readFile("inputs/aoc2.txt");

        // parse input, here creating 2 arrays
        int inputLength = input.size();

        int[] horizontalChange = new int[inputLength];
        int[] verticalChange = new int[inputLength];

        for (int i = 0; i < inputLength; i++){
            int[] moveArray = parseInstruction(input.get(i));
            horizontalChange[i] = moveArray[0];
            verticalChange[i] = moveArray[1];
        }

        // compute & print solutions
        int part1_solution = part1(horizontalChange, verticalChange);  // 1746616
        int part2_solution = part2(inputLength, horizontalChange, verticalChange); // 1741971043

        System.out.println("PART 1: " + part1_solution);
        System.out.println("PART 2: " + part2_solution);
    }

    public static List<String> readFile(String filepath) {
        InputStream is = AOC02.class.getClassLoader().getResourceAsStream(filepath);
        List<String> input = new ArrayList<>();
        try (Scanner scanner = new Scanner(is)) {
            while (scanner.hasNextLine()) {
                input.add(scanner.nextLine());
            }
        }
        return input;
    }

    public static int[] parseInstruction(String fullInstruction)  {
        String[] arrOfStr = fullInstruction.split(" ", 2);
        String instruction = arrOfStr[0];
        int value = Integer.parseInt(arrOfStr[1]);

        switch (instruction) {
            case "forward" -> {
                return new int[]{value, 0};
            }
            case "down" -> {
                return new int[]{0, value};
            }
            case "up" -> {
                return new int[]{0, -value};
            }
            default -> {
                return new int[]{0, 0};
            }
        }
    }

    public static int part1(int[] horizontalChange, int[] verticalChange)  {
        int totalHorizontalChange = IntStream.of(horizontalChange).sum();
        int totalDepthChange  = IntStream.of(verticalChange).sum();
        return totalHorizontalChange * totalDepthChange;
    }

    public static int part2(int inputLength, int[] horizontalChange, int[] verticalChange)  {
        int horizontalPos = 0;
        int depth = 0;
        int aim = 0;

        for (int i = 0; i < inputLength; i++){
            int forwardValue = horizontalChange[i];
            int aimChange = verticalChange[i];

            if (aimChange != 0){
                aim += aimChange;
            }
            else{
                horizontalPos += forwardValue;
                depth += aim * forwardValue;
            }
        }

        return horizontalPos * depth;
    }

}

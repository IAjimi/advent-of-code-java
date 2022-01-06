import java.io.InputStream;
import java.util.*;

public class AOC10 {

    final static Map<String, String> matchingChar = Map.of(
            "(", ")",
            "{", "}",
            "[", "]",
            "<", ">"
    );

    final static Map<String, Integer> corruptPoints = Map.of(
            ")", 3,
            "}", 1197,
            "]", 57 ,
            ">", 25137
    );

    final static Map<String, Integer> incompletePoints = Map.of(
            "(", 1,
            "{", 3,
            "[", 2,
            "<", 4
    );

    public static int totalCorruptScore = 0;
    public static ArrayList<Long> allIncompleteScores = new ArrayList<>();

    public static void main(String[] args) {
        List<String> input = readFile("inputs/aoc10.txt");

        // iterate over each line - adds to totalCorruptScore, allIncompleteScores
        for (String line: input){
            spellChecker(line); // increments totalCorruptScore
        }

        // print solution
        long part2_solution = getMedian(allIncompleteScores);
        System.out.println("PART 1: " + totalCorruptScore); // 367227
        System.out.println("PART 2: " + part2_solution); // 3583341858
    }

    public static List<String> readFile(String filepath) {
        InputStream is = AOC10.class.getClassLoader().getResourceAsStream(filepath);
        List<String> input = new ArrayList<>();

        try (Scanner scanner = new Scanner(is)) {
            while (scanner.hasNextLine()) {
                input.add(scanner.nextLine());
            }
        }

        return input;
    }

    public static void spellChecker(String line){
        ArrayList<String> stack = new ArrayList<>();

        for (int i = 0; i < line.length(); i++) {

            String s = String.valueOf(line.charAt(i));

            if (matchingChar.containsKey(s)){
                stack.add(s);
            }
            else{
                int lastIndex = stack.size() - 1;
                String openingChar = stack.get(lastIndex);
                stack.remove(lastIndex);

                String expectedChar = matchingChar.get(openingChar);

                if (!Objects.equals(expectedChar, s)){
                    totalCorruptScore += corruptPoints.get(s);
                    return;
                }
            }
        }

        // now go through stack in reverse
        long score = 0;
        for (int i = stack.size() - 1; i >= 0; i--){
            score *= 5;
            String s = stack.get(i);
            score += incompletePoints.get(s);
        }

        allIncompleteScores.add(score);
    }

    public static long getMedian(ArrayList<Long> arrayOfLongs){
        int mid = arrayOfLongs.size() / 2;
        arrayOfLongs.sort(Collections.reverseOrder());
        return arrayOfLongs.get(mid);
    }
}

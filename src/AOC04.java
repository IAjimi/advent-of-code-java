import java.io.File;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class AOC04 {
    static int boardCount;
    static int[] bingoNums;
    static Map<Integer, Board> allBoards = new HashMap<>();
    static List<Integer> allScores = new ArrayList<>();

    public static void main(String[] args) {
        // read input file
        processFile("src/inputs/aoc04.txt");

        // callNumber for every number in the list (also updates allScores)
        for (int num: bingoNums){
            callNumber(num);
        }

        // print solutions
        int part1_score = allScores.get(0); // score of 1st board to win
        int part2_score = allScores.get(boardCount - 1); // score of last board to win
        System.out.println("PART 1: " + part1_score); // 87456
        System.out.println("PART 2: " + part2_score); // 15561
    }

    public static void processFile(String filepath){
        // open reader
        Scanner reader = null;
        try {
            reader = new Scanner(new File(filepath));
        } catch (Exception e) {
            System.out.println("File not found.");
        }

        // get bingoNums
        bingoNums = Arrays.stream(reader.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        reader.nextLine(); // eat up blank line

        // then get all bingo boards
        int row = 0, col = 0, card = 0;
        int[][] currentGrid = new int[5][5];
        Map<Integer, int[]> numMap = new HashMap<>();

        while (reader.hasNextInt()) {
            // get next num
            int num = reader.nextInt();

            // add to grid, numMap
            currentGrid[row][col] = num;
            int[] pos = new int[] {row, col};
            numMap.put(num, pos);

            // move right
            col++;

            // if hit end of line, move down one row
            if (col == 5) {
                col = 0;
                row++;
            }
            // if row == 5, we've reached a new bingo board
            if (row == 5) {
                row = 0;
                card++;

                // add completed board to allBoards
                allBoards.put(card - 1, new Board(numMap, currentGrid));

                // reset grid, map
                currentGrid = new int[5][5];
                numMap = new HashMap<>();
            }

        }

        // update count of boards
        boardCount = card;
    }

    public static void callNumber(int num){
        for (Map.Entry<Integer,Board> entry : allBoards.entrySet()) {
            // get boardId, board class
            int boardId = entry.getKey();
            Board currentBoard = entry.getValue();

            // update score if board won
            updateScores(boardId, currentBoard, num);
        }
    }

    public static void updateScores(int boardId, Board currentBoard, int num){
        // check if already won
        if (!currentBoard.won){
            boolean hasWon = currentBoard.updateBoard(num);

            // if just won w/ latest num, get score
            if (hasWon){
                int score = currentBoard.computeScore(num);
                allScores.add(score);
            }
        }
    }

}
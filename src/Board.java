import java.util.Map;


public class Board {
    private final int CALLED_PLACEHOLDER = -1; // value stored in grid if number was called

    boolean won = false; // true if board won
    Map<Integer, int[]> numMap; // key: number, value: position in grid
    int[][] grid; // grid, num if number not called, else CALLED_PLACEHOLDER

    public Board(Map<Integer, int[]> numMap, int[][] thisGrid) {
        this.numMap = numMap;
        this.grid = thisGrid;
    }

    public boolean updateBoard(int num) {
        // check if board has number
        if (hasNum(num)) {
            // if so, get position
            int[] pos = this.numMap.get(num);

            int i = pos[0];
            int j = pos[1];

            // update grid
            this.grid[i][j] = CALLED_PLACEHOLDER;

            // check if has won
            this.won = isWon(i, j);
            return this.won;
        }
        else {
            return false;
        }
    }

    public boolean hasNum(int num){
        return this.numMap.containsKey(num);
    }

    public boolean checkRow(int y){
        int counter = 0;
        for (int i = 0; i < 5; i++){
            if (this.grid[i][y] == CALLED_PLACEHOLDER){
                counter++;
            }
        }
        return counter == 5;
    }

    public boolean checkCol(int x){
        int counter = 0;
        for (int i = 0; i < 5; i++){
            if (this.grid[x][i] == CALLED_PLACEHOLDER){
                counter++;
            }
        }
        return counter == 5;
    }

    public boolean isWon(int x, int y){
        // checks if board has won after adding a specific number
        boolean wonRow = checkRow(y);
        boolean wonCol = checkCol(x);
        return wonRow || wonCol;
    }

    public int computeScore(int num){
        int score = 0;

        // sum of all unmarked numbers on that board
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                // if unmarked
                if (this.grid[i][j] != CALLED_PLACEHOLDER){
                    score += this.grid[i][j];
                }
            }
        }

        // multiply that sum by the number that was just called
        score *= num;

        return score;
    }

}

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SeaCucumberHerd {

    private static final char EAST = '>';
    private static final char SOUTH = 'v';

    HashSet<List<Integer>> positions;
    char type;
    int width;
    int height;

    public SeaCucumberHerd(HashSet<List<Integer>> initialPositions, char cucumberType, int width, int height) {
        this.positions = initialPositions;
        this.type = cucumberType;
        this.width = width;
        this.height = height;
    }

    private List<Integer> nextPosition(List<Integer> pos, HashSet<List<Integer>> otherPositions){
        // create copy of pos
        List<Integer> newPos = new ArrayList<>(pos);

        // get newPos
        if (this.type == EAST){
            newPos.set(0, (newPos.get(0) + 1) % this.width);
        } else if (this.type == SOUTH) {
            newPos.set(1, (newPos.get(1) + 1) % this.height);
        }

        // if space is free, move to space
        if (!this.positions.contains(newPos) && !otherPositions.contains(newPos)){
            return newPos;
        }
        // otherwise stay put
        else{
            return pos;
        }
    }

    public boolean moveHerd(HashSet<List<Integer>> otherPositions){
        boolean changed = false;
        HashSet<List<Integer>> newPositions = new HashSet<>();

        for (List<Integer> pos: this.positions){

            // move to next position (sea cucumber may or may not move)
            List<Integer> newPos = nextPosition(pos, otherPositions);
            newPositions.add(newPos);

            // check if position has changed
            if (pos != newPos){
                changed = true;
            }
        }

        // update herd positions
        this.positions = newPositions;

        // return bool
        return changed;
    }

}

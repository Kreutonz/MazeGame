package ca.cmpt213.a2.model;

/**
 * Stores adjacent cells for reference to current ca.cmpt213.a2.model.Cell
 * as well as the value of this object in the maze and if
 * they are visible to the player or not
 *
 * @author Mike Kreutz
 */
class Cell {
    final private int CELL_NUMBER;
    private Cell adjacentUp;
    private Cell adjacentDown;
    private Cell adjacentLeft;
    private Cell adjacentRight;
    private char contents;
    private boolean visibleToPlayer;

    //constructor
    public Cell(int location){
        CELL_NUMBER = location;
    }

    public Cell getAdjacentUp() {
        return adjacentUp;
    }

    public void setAdjacentUp(Cell up) {
        adjacentUp = up;
    }

    public Cell getAdjacentDown() {
        return adjacentDown;
    }

    public void setAdjacentDown(Cell down) {
        adjacentDown = down;
    }

    public Cell getAdjacentLeft() {
        return adjacentLeft;
    }

    public void setAdjacentLeft(Cell left) {
        adjacentLeft = left;
    }

    public Cell getAdjacentRight() {
        return adjacentRight;
    }

    public void setAdjacentRight(Cell right) {
        adjacentRight = right;
    }

    public int getCellNumber() {
        return CELL_NUMBER;
    }

    public char getContents() {
        return contents;
    }

    public void setContents(char value) {
        contents = value;
    }

    public boolean isVisibleToPlayer() {
        return visibleToPlayer;
    }

    public void setVisibleToPlayer(boolean visible) {
        visibleToPlayer = visible;
    }
}//ca.cmpt213.a2.model.Cell Class

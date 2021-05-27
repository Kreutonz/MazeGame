package ca.cmpt213.a2.model;

import java.util.ArrayList;
import java.util.List;


/**
 * A class that creates a maze by determining its size (including borders) by
 * first creating a inner maze using Prim's Algorithm, checks for validity
 * and if valid, creates a maze with the borders, if not runs Prim's
 * Algorithm again until valid maze is found
 *
 * @author Mike Kreutz
 */
public class Maze {
    final private int ROWS = 15;
    final private int COLUMNS = 20;

    final private int INNER_MAZE_ROWS = ROWS - 2;
    final private int INNER_MAZE_COLUMNS = COLUMNS - 2;
    final private int INNER_MAZE_SIZE = INNER_MAZE_COLUMNS * INNER_MAZE_ROWS;
    final private int UP = 0;
    final private int LEFT = 1;
    final private int DOWN = 2;
    final private int RIGHT = 3;
    final private int NUMBER_CYCLES = 8;            //number of cycles to be created in maze
    final private RandomNumberGenerator randomNumber;

    private final int heroSpawnLocation = (COLUMNS - 1) + 2;
    private final int monster1SpawnLocation =  2 * (COLUMNS - 1);
    private final int monster2SpawnLocation = (COLUMNS * ROWS - 1) - (2 * COLUMNS) + 2;
    private final int monster3SpawnLocation = (COLUMNS * ROWS - 1) - COLUMNS - 1;

    final private List<Cell> mazeArr;
    private List<Cell> innerMazeArr;


    // constructor
    public Maze(){
        mazeArr = new ArrayList<>();
        randomNumber = new RandomNumberGenerator();
        constructMaze();
        createMazeWithBorders();
    }

    //***********
    //GETTERS
    //***********
    public List<Cell> getMaze(){
        return mazeArr;
    }

    public int getColumns(){
        return COLUMNS;
    }

    public int getRows(){
        return ROWS;
    }

    public char getContents(int location){
        return mazeArr.get(location).getContents();
    }

    public int getHeroSpawn(){
        return heroSpawnLocation;
    }

    public int getFirstMonsterSpawn(){
        return monster1SpawnLocation;
    }

    public int getSecondMonsterSpawn(){
        return monster2SpawnLocation;
    }

    public int getThirdMonsterSpawn(){
        return monster3SpawnLocation;
    }


    //**********
    //SETTERS
    //**********

    public void revealAllCells(){
        for(int i = 0; i < mazeArr.size(); i++){
            mazeArr.get(i).setVisibleToPlayer(true);
        }
    }

    public void setContents(int location, char value){
        mazeArr.get(location).setContents(value);
    }

    //******************
    //PUBLIC-METHODS
    //******************

    public void printMaze(Hero hero, Monster monster1, Monster monster2, Monster monster3, SuperPower superPower){
        for(int i = 0; i < mazeArr.size(); i++){
            if(i % COLUMNS == 0){
                if(i > 0){
                    System.out.println();
                }
            }
            if(i == hero.getHeroLocation() ||
                    i == monster1.getMonsterLocation() ||
                    i == monster2.getMonsterLocation() ||
                    i == monster3.getMonsterLocation() ||
                    i == superPower.getSuperPowerLocation()){
                System.out.print(mazeArr.get(i).getContents());
            }
            else if(!mazeArr.get(i).isVisibleToPlayer()){
                System.out.print('.');
            }else{
                System.out.print(mazeArr.get(i).getContents());
            }

        }
        System.out.println();
    }//printMaze()


    //*****************
    //PRIVATE-METHODS
    //*****************

    //calls the methods that help create a maze within the borders
    private void constructMaze() {
        while(true){
            createInnerMazeOfWalls();
            setAdjacents();
            createMazePath();

            boolean isValid = validInnerMaze();
            if(isValid){
                break;
            }
        }
    }//constructMaze()


    private void createMazeWithBorders() {
        //adds the top row of walls
        for(int i = 0; i < COLUMNS; i++){
            Cell topRow = new Cell(i);
            topRow.setContents('#');
            topRow.setVisibleToPlayer(true);
            mazeArr.add(topRow);
        }
        //loops through inner maze and copies its contents, if first or last column, a wall is added
        for(int i = 0; i < INNER_MAZE_SIZE; i++){
            if(i % INNER_MAZE_COLUMNS == 0){        //if first column
                Cell firstColumn = new Cell(i);
                firstColumn.setContents('#');
                firstColumn.setVisibleToPlayer(true);
                mazeArr.add(firstColumn);
            }

            mazeArr.add(innerMazeArr.get(i));       //adds contents between

            if(i % INNER_MAZE_COLUMNS == INNER_MAZE_COLUMNS - 1){   //if last column
                Cell lastColumn = new Cell(i);
                lastColumn.setContents('#');
                lastColumn.setVisibleToPlayer(true);
                mazeArr.add(lastColumn);
            }
        }
        //adds the bottom row of columns
        for(int i = 0; i < COLUMNS; i++){
            Cell bottomRow = new Cell(i);
            bottomRow.setContents('#');
            bottomRow.setVisibleToPlayer(true);
            mazeArr.add(bottomRow);
        }
    }//createMazeWithBorders()


    //creates a maze full of walls
    private void createInnerMazeOfWalls(){
        innerMazeArr = new ArrayList<>();
        for(int i = 0; i < INNER_MAZE_SIZE; i++){
            Cell currentCell = new Cell(i);
            currentCell.setContents('#');
            innerMazeArr.add(currentCell);
        }
    }//createInnerMazeOfWalls


    //checks if innerMaze created is valid
    private boolean validInnerMaze(){
        int topLeftCornerRight = 1;
        int topLeftCornerBelow = COLUMNS - 2;
        int topRightCornerLeft = COLUMNS - 4;
        int topRightCornerBelow = 2 * (INNER_MAZE_COLUMNS) - 1;
        int bottomLeftCornerAbove = (INNER_MAZE_SIZE - 1) - (2 * (INNER_MAZE_COLUMNS) - 1);
        int bottomLeftCornerRight = (INNER_MAZE_SIZE - 1) - (INNER_MAZE_COLUMNS - 2);
        int bottomRightCornerAbove = (INNER_MAZE_SIZE - 1) - (INNER_MAZE_COLUMNS);
        int bottomRightCornerLeft = (INNER_MAZE_SIZE - 1) - 1;

        //check to make sure spawn points are not closed in
        if(innerMazeArr.get(topLeftCornerRight).getContents() == '#' &&          //invalid Top Left corner
                innerMazeArr.get(topLeftCornerBelow).getContents() == '#') {
            return false;
        }else if(innerMazeArr.get(topRightCornerLeft).getContents() == '#' &&    //invalid Top Right corner
                innerMazeArr.get(topRightCornerBelow).getContents() == '#'){
            return false;
        }else if(innerMazeArr.get(bottomLeftCornerAbove).getContents() == '#' &&     //invalid Bottom Left corner
                innerMazeArr.get(bottomLeftCornerRight).getContents() == '#'){
            return false;
        }else if(innerMazeArr.get(bottomRightCornerAbove).getContents() == '#' &&    //invalid Bottom Right corner
                innerMazeArr.get(bottomRightCornerLeft).getContents() == '#'){
            return false;
        }
        //traces through maze to check 2x2 constraint
        for(int i = 0; i < INNER_MAZE_SIZE - (INNER_MAZE_COLUMNS); i++){    //loops through maze array to get a value
            if(i % (INNER_MAZE_COLUMNS) == INNER_MAZE_COLUMNS - 1) {        //if value == far right column, will not check
                continue;
            }
            char currentCell = innerMazeArr.get(i).getContents();               //gets current cells value
            if(innerMazeArr.get(i + 1).getContents() == currentCell &&          //gets cell to right of current cell's value
                    innerMazeArr.get(i + INNER_MAZE_COLUMNS).getContents() == currentCell &&        //gets cell below's value
                    innerMazeArr.get(i + INNER_MAZE_COLUMNS + 1).getContents() == currentCell)  {   //gets the cell to bottom right value
                return false;
            }
        }
        return true;        //will only return true if all tests above passed
    }//validInnerMaze()


    //loops through recently created maze and sets their adjacent (neighbouring cells)
    //neighbouring cells are those located two cells away from currentCell
    private void setAdjacents(){
        for(int i = 0; i < innerMazeArr.size(); i++){
            if(i >= (2 * INNER_MAZE_COLUMNS)){                          //if not top 2 rows, set adjacentUp
                innerMazeArr.get(i).setAdjacentUp(innerMazeArr.get(i - (2 * INNER_MAZE_COLUMNS)));
            }else{
                innerMazeArr.get(i).setAdjacentUp(null);
            }
            if((i % INNER_MAZE_COLUMNS != 0) && (i % INNER_MAZE_COLUMNS != 1)){       //if not far 2 left columns, set adjacentLeft
                innerMazeArr.get(i).setAdjacentLeft(innerMazeArr.get(i - 2));
            }else{
                innerMazeArr.get(i).setAdjacentLeft(null);
            }
            if((i % INNER_MAZE_COLUMNS != (INNER_MAZE_COLUMNS - 1)) && (i % (INNER_MAZE_COLUMNS) != (INNER_MAZE_COLUMNS - 2))) {
                innerMazeArr.get(i).setAdjacentRight(innerMazeArr.get(i + 2));     // if not 2 far right columns, set adjacentRight
            }else{
                innerMazeArr.get(i).setAdjacentRight(null);
            }
            if(i <= (innerMazeArr.size()- 1)  - (2 * (INNER_MAZE_COLUMNS))){            //if not bottom two rows, set adjacentDown
                innerMazeArr.get(i).setAdjacentDown(innerMazeArr.get(i + 2 * (INNER_MAZE_COLUMNS)));
            }else{
                innerMazeArr.get(i).setAdjacentDown(null);
            }
        }
    }//setAdjacents()

    /*
        PRIM'S MAZE ALGORITHM:  (source: http://weblog.jamisbuck.org/2011/1/10/maze-generation-prim-s-algorithm)
        Takes a maze full of walls. To start, it chooses a wall randomly and
        makes it empty creating a path to traverse through the maze. The cells adjacent
        to current cell are stored in a list. The algorithm then chooses a random value
        from the adjacent list, makes that cell empty and stores that cells adjacent values
        in the adjacent list if they are not already empty. The process is repeated until
        no cells are in the adjacent list. The result will be a linear maze.
     */
    private void primsMazeAlgorithm(List<Cell>adjacentCells, List<Cell> blankCells){
        int randomAdjacentCell = randomNumber.randomNumber(adjacentCells.size());   //chooses a random adjacent cell
        Cell currentCell = adjacentCells.get(randomAdjacentCell);
        int cellLocation = currentCell.getCellNumber();
        innerMazeArr.get(cellLocation).setContents(' ');

        while(true) {
            //choose a random direction - 0 (up), 1 (left), 2 (down), 3(right)
            int direction = randomNumber.randomNumber(4);

            if (direction == UP && currentCell.getAdjacentUp() != null) {
                if (currentCell.getAdjacentUp().getContents() == ' ') {
                    clearCellBetweenPathAndAdjacent(currentCell, UP);
                    break;
                }
            } else if (direction == LEFT && currentCell.getAdjacentLeft() != null) {
                if (currentCell.getAdjacentLeft().getContents() == ' ') {
                    clearCellBetweenPathAndAdjacent(currentCell, LEFT);
                    break;
                }
            } else if (direction == DOWN && currentCell.getAdjacentDown() != null) {
                if (currentCell.getAdjacentDown().getContents() == ' ') {
                    clearCellBetweenPathAndAdjacent(currentCell, DOWN);
                    break;
                }
            } else if (direction == RIGHT && currentCell.getAdjacentRight() != null) {
                if (currentCell.getAdjacentRight().getContents() == ' ') {
                    clearCellBetweenPathAndAdjacent(currentCell, RIGHT);
                    break;
                }
            }
        }
        blankCells.add(currentCell);                        //add cell to blankCells list
        addToAdjacentList(currentCell, adjacentCells);      //add new adjacents based on currentCell
        removeFromAdjacentList(adjacentCells, blankCells);  //remove currentCell from adjacentCells
    }//primsMazeAlgorithm()


    private void addToAdjacentList(Cell currentCell, List<Cell> adjacentCells){
        if(currentCell.getAdjacentUp() != null){
            adjacentCells.add(currentCell.getAdjacentUp());
        }
        if(currentCell.getAdjacentLeft() != null){
            adjacentCells.add(currentCell.getAdjacentLeft());
        }
        if(currentCell.getAdjacentDown() != null){
            adjacentCells.add(currentCell.getAdjacentDown());
        }
        if(currentCell.getAdjacentRight() != null){
            adjacentCells.add(currentCell.getAdjacentRight());
        }
    }//addToAdjacentList()


    private void removeFromAdjacentList(List<Cell> adjacentCells, List<Cell> blankCells){
        for(int i = 0; i < blankCells.size(); i++){
            for(int j = 0; j < adjacentCells.size(); j++){
                if(blankCells.get(i).getCellNumber() == adjacentCells.get(j).getCellNumber()){
                    int location = blankCells.get(i).getCellNumber();
                    innerMazeArr.get(location).setContents(' ');
                    adjacentCells.remove(j);
                }
            }
        }
    }//removeFromAdjacentList()


    //changes the contents of the cell between the adjacent cell and the maze path
    private void clearCellBetweenPathAndAdjacent(Cell adjacentCell, int direction){
        if(direction == UP){
            innerMazeArr.get(adjacentCell.getCellNumber() - INNER_MAZE_COLUMNS).setContents(' ');
        }else if(direction == LEFT){
            innerMazeArr.get(adjacentCell.getCellNumber() - 1).setContents(' ');
        }else if(direction == DOWN){
            innerMazeArr.get(adjacentCell.getCellNumber() + INNER_MAZE_COLUMNS).setContents(' ');
        }else if(direction == RIGHT){
            innerMazeArr.get(adjacentCell.getCellNumber() + 1).setContents(' ');
        }
    }//clearCellBetweenPathAndAdjacent


    private void clearSpawnPositions(){
        int topLeftCorner = 0;                                                      //top left corner
        int topRightCorner = INNER_MAZE_COLUMNS - 1;                                //top right corner
        int bottomLeftCorner = (INNER_MAZE_SIZE - 1) - (INNER_MAZE_COLUMNS - 1);    //bottom left corner
        int bottomRightCorner = INNER_MAZE_SIZE - 1;                                //bottom right corner

        innerMazeArr.get(topLeftCorner).setContents(' ');
        innerMazeArr.get(topRightCorner).setContents(' ');
        innerMazeArr.get(bottomLeftCorner).setContents(' ');
        innerMazeArr.get(bottomRightCorner).setContents(' ');
    }//clearSpawnPositions()


    private void createCyclesInMaze(){
        for(int i = 0; i < NUMBER_CYCLES; i++){
            int location = randomNumber.randomNumber(INNER_MAZE_SIZE);
            while(true){
                if(innerMazeArr.get(location).getContents() == '#'){
                    break;
                }
                location = randomNumber.randomNumber(INNER_MAZE_SIZE);
            }
            innerMazeArr.get(location).setContents(' ');
        }
    }//createCyclesInMaze()


    private void createMazePath(){
        int position = randomNumber.randomNumber(INNER_MAZE_SIZE);
        Cell currentCell = innerMazeArr.get(position);
        List<Cell> adjacentCells = new ArrayList<>();
        List<Cell> blankCells = new ArrayList<>();

        currentCell.setContents(' ');                   //chooses a cell at random to start to Prim Algorithm
        addToAdjacentList(currentCell, adjacentCells);
        blankCells.add(currentCell);

        while(adjacentCells.size() != 0){
            primsMazeAlgorithm(adjacentCells, blankCells);
        }

        createCyclesInMaze();
        clearSpawnPositions();
    }//createMazePath()
}//ca.cmpt213.a2.model.Maze Class

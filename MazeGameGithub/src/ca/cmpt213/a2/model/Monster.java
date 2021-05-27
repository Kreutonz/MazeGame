package ca.cmpt213.a2.model;

/**
 * creates an object that is used to traverse through the
 * maze pseudo-randomly
 *
 * @author Mike Kreutz
 */
class Monster{
    final private int UP = 0;
    final private int LEFT = 1;
    final private int DOWN = 2;
    final private int RIGHT = 3;
    final private Maze maze;
    final private int columns;
    final private RandomNumberGenerator randomNumber;

    private boolean monsterAlive;
    private int currentCellLocation;
    private int northCellLocation;
    private int westCellLocation;
    private int southCellLocation;
    private int eastCellLocation;
    private int lastCellLocation;



    //constructor
    public Monster(int spawnLocation, Maze maze){
        randomNumber = new RandomNumberGenerator();
        monsterAlive = true;
        currentCellLocation = spawnLocation;
        lastCellLocation = currentCellLocation;
        columns = maze.getColumns();
        this.maze = maze;
        setNeighbourLocations(currentCellLocation);
    }


    //**********
    //GETTERS
    //**********
    public boolean isMonsterAlive(){
        return monsterAlive;
    }


    public int getMonsterLocation(){
        return currentCellLocation;
    }


    //**********
    //SETTERS
    //**********

    public void monsterDefeated(){
        monsterAlive = false;
    }


    //*****************
    //PUBLIC-METHODS
    //*****************

    public void move(){
        int possibleDirections = 4;                  //4 choices: 0 (up), 1 (left), 2 (down), 3 (right)
        int directionToMove = randomNumber.randomNumber(possibleDirections);

        boolean isValidMove = validMove(directionToMove);
        while(!isValidMove){
            directionToMove = randomNumber.randomNumber(possibleDirections);
            isValidMove = validMove(directionToMove);
        }

        lastCellLocation = currentCellLocation;
        if(directionToMove == UP){
            currentCellLocation = northCellLocation;
        }else if(directionToMove == LEFT){
            currentCellLocation = westCellLocation;
        }else if(directionToMove == DOWN){
            currentCellLocation = southCellLocation;
        }else{
            currentCellLocation = eastCellLocation;
        }
        setNeighbourLocations(currentCellLocation);
    }

    //******************
    //PRIVATE_METHODS
    //******************

    private void setNeighbourLocations(int currentCellLocation){
        northCellLocation = currentCellLocation - columns;
        westCellLocation = currentCellLocation - 1;
        southCellLocation  = currentCellLocation + columns;
        eastCellLocation = currentCellLocation + 1;
    }


    private boolean validMove(int directionToMove){
        int cellToMoveTo = 0;                       //checks and assigns the cell value to move to
        if(directionToMove == UP){
            cellToMoveTo = northCellLocation;
        }else if(directionToMove == LEFT){
            cellToMoveTo = westCellLocation;
        }else if(directionToMove == DOWN){
            cellToMoveTo = southCellLocation;
        }else{
            cellToMoveTo = eastCellLocation;
        }
        //if the cell chosen at random is the last cell visited, checks to make sure
        //there are no other possible moves
        if(lastCellLocation == cellToMoveTo){
            int countPossibleMoves = 4;
            if(maze.getContents(northCellLocation) == '#'){
                countPossibleMoves--;
            }
            if(maze.getContents(westCellLocation) == '#'){
                countPossibleMoves--;
            }
            if(maze.getContents(southCellLocation) == '#'){
                countPossibleMoves--;
            }
            if(maze.getContents(eastCellLocation) == '#'){
                countPossibleMoves--;
            }
            if(countPossibleMoves > 1){
                return false;
            }
        }
        return maze.getContents(cellToMoveTo) != '#';
    }
}//ca.cmpt213.a2.model.Monster Class

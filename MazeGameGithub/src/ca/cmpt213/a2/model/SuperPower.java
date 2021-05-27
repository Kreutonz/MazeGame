package ca.cmpt213.a2.model;

/**
 * creates an object that is stored in a random location when initialized,
 * if hero lands on cell, the object will move to another random location
 * by calling its chooseRandomLocation() method
 *
 * @author Mike Kreutz
 */
class SuperPower{
    final private RandomNumberGenerator randomNumber = new RandomNumberGenerator();
    final private Maze maze;
    private int superPowerLocation;

    //constructor
    public SuperPower(Maze mazeObject)
    {
        maze = mazeObject;
        chooseRandomLocation();
    }

    //**********
    //GETTERS
    //**********

    public int getSuperPowerLocation(){
        return superPowerLocation;
    }

    //*****************
    //PUBLIC-METHODS
    //*****************

    public void chooseRandomLocation(){
        while(true){
            int location = randomNumber.randomNumber(maze.getMaze().size());
            if(maze.getContents(location) != '#'){
                if(location != maze.getHeroSpawn() &&
                    location != maze.getFirstMonsterSpawn() &&
                    location != maze.getSecondMonsterSpawn() &&
                    location != maze.getThirdMonsterSpawn()){
                        superPowerLocation = location;
                        break;
                }
            }//if location != wall
        }//while-loop
    }
}//ca.cmpt213.a2.model.SuperPower Class

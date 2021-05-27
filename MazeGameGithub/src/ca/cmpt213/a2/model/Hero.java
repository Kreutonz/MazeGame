package ca.cmpt213.a2.model;

import java.util.List;

/**
 * The object that the user controls, depending on userChoice
 * for moves, it will move according, keeps track of how
 * many powers are in hero's possession
 *
 * @author Mike Kreutz
 */
public class Hero{
    private boolean heroAlive;
    private int powerInPossession;
    private int currentLocation;

    //constructor
    public Hero(int spawnLocation){
        heroAlive = true;
        powerInPossession = 0;
        currentLocation = spawnLocation;
    }

    //**********
    //GETTERS
    //**********

    public boolean isHeroAlive(){
        return heroAlive;
    }

    public int getHeroLocation(){
        return currentLocation;
    }

    public int getPowerInPossession(){
        return powerInPossession;
    }


    //***********
    //SETTERS
    //***********

    public void setHeroLocation(int location){
        currentLocation = location;
    }

    public void gainPower(){
        powerInPossession++;
    }

    public void usePower(){
        if(powerInPossession == 0){
            heroAlive = false;
        }else{
            powerInPossession--;
        }
    }

    //******************
    //PUBLIC-METHODS
    //******************


    //reveals cell in all 8 directions to hero
    public void revealCellsAroundHero(Maze maze){
        List<Cell> mazeArr = maze.getMaze();
        int current = getHeroLocation();
        int north = getHeroLocation() - maze.getColumns();
        int west = current - 1;
        int east = current + 1;
        int south = current + maze.getColumns();
        int northWest = north - 1;
        int northEast = north + 1;
        int southWest = south - 1;
        int southEast = south + 1;

        mazeArr.get(current).setVisibleToPlayer(true);
        mazeArr.get(north).setVisibleToPlayer(true);
        mazeArr.get(west).setVisibleToPlayer(true);
        mazeArr.get(east).setVisibleToPlayer(true);
        mazeArr.get(south).setVisibleToPlayer(true);
        mazeArr.get(northWest).setVisibleToPlayer(true);
        mazeArr.get(northEast).setVisibleToPlayer(true);
        mazeArr.get(southWest).setVisibleToPlayer(true);
        mazeArr.get(southEast).setVisibleToPlayer(true);
    }

    //moves hero in maze
    public void move(String heroMove, int columns){
        if(heroMove.equals("W") || heroMove.equals("w")){
            setHeroLocation(getHeroLocation() - columns);
        }else if(heroMove.equals("A") || heroMove.equals("a")){
            setHeroLocation(getHeroLocation() - 1);
        }else if(heroMove.equals("S") || heroMove.equals("s")){
            setHeroLocation(getHeroLocation() + columns);
        }else if(heroMove.equals("D") || heroMove.equals("d")){
            setHeroLocation(getHeroLocation() + 1);
        }
    }

    public boolean validMove(String heroMove, Maze maze){
        if(heroMove.equals("W") || heroMove.equals("w")){
            return maze.getContents(currentLocation - maze.getColumns()) != '#';
        }else if(heroMove.equals("A") || heroMove.equals("a")){
            return maze.getContents(currentLocation - 1) != '#';
        }else if(heroMove.equals("S") || heroMove.equals("s")){
            return maze.getContents(currentLocation + maze.getColumns()) != '#';
        }else if(heroMove.equals("D") || heroMove.equals("d")){
            return maze.getContents(currentLocation + 1) != '#';
        }
        return false;
    }
}//ca.cmpt213.a2.model.Hero Class

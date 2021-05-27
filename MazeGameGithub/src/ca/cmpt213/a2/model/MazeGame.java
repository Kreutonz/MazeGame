package ca.cmpt213.a2.model;

/**
 * A class that uses other class objects and methods
 * to execute gameplay implementation
 *
 * @author Mike Kreutz
 */

public class MazeGame {
    final private Maze maze;

    final private Hero hero;
    final private Monster monster1;
    final private Monster monster2;
    final private Monster monster3;
    final private SuperPower superPower;

    private int monstersToBeKilled;
    private int monstersAlive;


    //constructor
    public MazeGame() {
        monstersToBeKilled = 3;
        monstersAlive = 3;
        maze = new Maze();
        hero = new Hero(maze.getHeroSpawn());
        monster1 = new Monster(maze.getFirstMonsterSpawn(), maze);
        monster2 = new Monster(maze.getSecondMonsterSpawn(), maze);
        monster3 = new Monster(maze.getThirdMonsterSpawn(), maze);
        superPower = new SuperPower(maze);
        startingLocations();
    }


    //**********
    //GETTERS
    //**********

    public int getMonstersToBeKilled(){
        return monstersToBeKilled;
    }

    public int getMonstersAlive(){
        return monstersAlive;
    }

    public boolean heroStatus(){
        return hero.isHeroAlive();
    }

    public Hero getHero(){
        return hero;
    }

    public Maze getMaze(){
        return maze;
    }

    public int getPowersInPossession() {
        return hero.getPowerInPossession();
    }

    //*********
    //SETTERS
    //*********

    private void monstersToBeKilledCheat(){
        monstersToBeKilled = 1;
    }

    public void monsterDefeated(){
        monstersToBeKilled--;
        monstersAlive--;
    }


    //******************
    //PUBLIC-METHODS
    //******************

    public void printMaze(){
        maze.printMaze(hero, monster1, monster2, monster3, superPower);
    }

    public void revealAllCells(){
        maze.revealAllCells();
    }

    public void play(String userMove){
            turn(userMove);
            hero.revealCellsAroundHero(maze);
            if(hero.getHeroLocation() == superPower.getSuperPowerLocation()){
                hero.gainPower();
                superPower.chooseRandomLocation();
                maze.setContents(superPower.getSuperPowerLocation(), '$');
            }
            if(hero.isHeroAlive() && !userMove.equals("?")){
                printMaze();
            }
    }


    //*******************
    //PRIVATE-METHODS
    //*******************

    private void startingLocations(){
        maze.setContents(hero.getHeroLocation(), '@');
        maze.setContents(monster1.getMonsterLocation(), '!');
        maze.setContents(monster2.getMonsterLocation(), '!');
        maze.setContents(monster3.getMonsterLocation(), '!');
        maze.setContents(superPower.getSuperPowerLocation(), '$');
        hero.revealCellsAroundHero(maze);
    }

    private void turn(String heroMove) {
        if(heroMove.equals("M") || heroMove.equals("m")){       //reveal map
            maze.revealAllCells();
            return;
        }
        if(heroMove.equals("C") || heroMove.equals("c")){       //sets kill one monster to win
            monstersToBeKilledCheat();
            return;
        }
        if(heroMove.equals("?")){
            return;
        }

        maze.setContents(superPower.getSuperPowerLocation(), '$');       //writes contents of superpower

        clearMonstersCell(monster1, superPower, maze);
        clearMonstersCell(monster2, superPower, maze);
        clearMonstersCell(monster3, superPower, maze);

        maze.setContents(hero.getHeroLocation(), ' ');

        //hero moves before monsters, ie if hero moves into cell occupied
        //by monster, they will battle
        hero.move(heroMove, maze.getColumns());
        moveThroughMaze(hero, monster1, monster2, monster3, maze);
    }


    private void moveThroughMaze(Hero hero, Monster monster1, Monster monster2, Monster monster3, Maze maze){
        checkHeroLocation(hero, monster1);
        checkHeroLocation(hero, monster2);
        checkHeroLocation(hero, monster3);

        //if hero survives moving first, then monsters move
        if (!hero.isHeroAlive()) {
            maze.setContents(hero.getHeroLocation(), 'X');       //display X on map at location of death
            return;
        } else {
            monsterTurn(monster1, maze);
            monsterTurn(monster2, maze);
            monsterTurn(monster3, maze);
            checkHeroLocation(hero, monster1);
            checkHeroLocation(hero, monster2);
            checkHeroLocation(hero, monster3);
            maze.setContents(hero.getHeroLocation(), '@');
        }
        //if hero does not survive monster moves
        if(!hero.isHeroAlive()) {
            maze.setContents(hero.getHeroLocation(), 'X');      //display X on map at location of death
        }
    }

    private void checkHeroLocation(Hero hero, Monster monster){
        if (hero.getHeroLocation() == monster.getMonsterLocation() && monster.isMonsterAlive()) {
            hero.usePower();
            if(hero.isHeroAlive()) {
                monster.monsterDefeated();
                monsterDefeated();
            }
        }
    }

    private void monsterTurn(Monster monster, Maze maze){
        if(monster.isMonsterAlive()){
            monster.move();
            maze.setContents(monster.getMonsterLocation(), '!');
        }
    }

    private void clearMonstersCell(Monster monster, SuperPower superPower, Maze maze){
        //if monster is alive and location is not superpower, makes the cell blank
        if(monster.isMonsterAlive()){
            if (superPower.getSuperPowerLocation() != monster.getMonsterLocation()) {
                maze.setContents(monster.getMonsterLocation(), ' ');
            }
        }
    }
}//ca.cmpt213.a2.model.MazeGame Class

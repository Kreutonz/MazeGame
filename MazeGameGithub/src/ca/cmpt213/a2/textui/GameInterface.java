package ca.cmpt213.a2.textui;

import ca.cmpt213.a2.model.*;

/**
 * defines the gameplay in the user interface
 * by combining the Menu and MazeGame classes
 *
 * @author Mike Kreutz
 */
class GameInterface {
    final private Menu menu;
    final private MazeGame mazeGame;


    //constructor
    GameInterface() {
        menu = new Menu();
        mazeGame = new MazeGame();
    }


    public static void main(String[] args) {
        GameInterface game = new GameInterface();

        game.menu.displayMenu(game.mazeGame.getMonstersToBeKilled());
        game.mazeGame.printMaze();
        game.menu.summary(  game.mazeGame.getMonstersToBeKilled(),
                            game.mazeGame.getPowersInPossession(),
                            game.mazeGame.getMonstersAlive()
        );

        game.play();

        game.mazeGame.revealAllCells();
        game.mazeGame.printMaze();
        game.menu.endGame(game.mazeGame.heroStatus(), game.mazeGame.getMonstersToBeKilled());
    }


    public void play() {
        while(mazeGame.heroStatus() && mazeGame.getMonstersToBeKilled() != 0){
            String userMove = menu.getUserMove(mazeGame);
            mazeGame.play(userMove);
            if(!userMove.equals("?")){
                menu.summary(mazeGame.getMonstersToBeKilled(), mazeGame.getPowersInPossession(), mazeGame.getMonstersAlive());
            }
        }
    }
}//GameInterface Class

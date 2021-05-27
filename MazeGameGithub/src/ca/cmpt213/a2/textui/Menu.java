package ca.cmpt213.a2.textui;

import java.util.Scanner;
import ca.cmpt213.a2.model.*;

/**
 * defines what the user interacts with, responsible for getting input
 * from the user and displaying certain information to screen
 *
 * @author Mike Kreutz
 */
class Menu{
    final private Scanner console;

    //constructor
    public Menu()
    {
        console = new Scanner(System.in);
    }

    //*****************
    //PRIVATE-METHODS
    //*****************

    private boolean validInput(String userMove){
        if(userMove.equals("W") || userMove.equals("w")){         //up
            return true;
        }else if(userMove.equals("A") || userMove.equals("a")){   //left
            return true;
        }else if(userMove.equals("S") || userMove.equals("s")){   //down
            return true;
        }else if(userMove.equals("D") || userMove.equals("d")){   //right
            return true;
        }else if(userMove.equals("?")){                           //displays help menu
            return true;
        }else //defeat 1 monster to win game
            if(userMove.equals("M") || userMove.equals("m")) {   //displays unhidden map
            return true;
        }else return userMove.equals("C") || userMove.equals("c");
    }

    private String getUserChoice(){
        String userChoice;
        while(true){
            System.out.println("Enter your move [WASD?]: ");
            userChoice = console.nextLine();
            boolean isValid = validInput(userChoice);
            if(isValid){
                break;
            }else{
                System.out.println("Invalid move. Please enter just A (left), S (down), D (right), W (up).");
            }
        }
        return userChoice;
    }


    //****************
    //PUBLIC_METHODS
    //****************

    public void displayMenu(int monstersToDefeat){
        System.out.println("DIRECTIONS:");
        System.out.println("\t\tKill " + monstersToDefeat + " Monsters!");

        System.out.println("LEGEND:");
        System.out.println("\t\t#: Wall");
        System.out.println("\t\t@: You (a hero)");
        System.out.println("\t\t!: Monster");
        System.out.println("\t\t$: Power");
        System.out.println("\t\t.: Unexplored space");

        System.out.println("MOVES");
        System.out.println("\t\tUse W (up), A (left), S (down), and D (right) to move.");
        System.out.println("\t\t(You must press enter after each move).");
    }

    public void endGame(boolean heroAlive, int monsterCount){
        if(!heroAlive){
            System.out.println("You, were eaten by a monster, YOU LOSE!");
        }else if(monsterCount == 0){
            System.out.println("All monsters were defeated, YOU WIN!");
        }
    }

    public void summary(int monstersToDefeat, int powersInPossession, int monstersAlive){
        System.out.println("Total number of monsters to be killed: " + monstersToDefeat);
        System.out.println("Number of powers currently in possession: " + powersInPossession);
        System.out.println("Number of monsters alive: " + monstersAlive);
    }


    public String getUserMove(MazeGame mazeGame){
        String heroMove;
        while(true){
            heroMove = getUserChoice();
            boolean validMove = mazeGame.getHero().validMove(heroMove, mazeGame.getMaze());
            if(validMove){
                break;
            }else if(heroMove.equals("M") || heroMove.equals("m")){     //cheat code for map reveal
                System.out.println("CHEAT CODE (REVEAL MAP) ACTIVATED!");
                break;
            }else if(heroMove.equals("C") || heroMove.equals("c")){     //cheat code for 1 monster to defeat
                System.out.println("CHEAT CODE (1 MONSTER TO WIN) ACTIVATED!");
                break;
            }else if(heroMove.equals("?")){     //display help menu
                displayMenu(mazeGame.getMonstersToBeKilled());
                break;
            }else{
                System.out.println("There is a wall there, please try again...");
            }
        }
        return heroMove;
    }
}//Menu Class

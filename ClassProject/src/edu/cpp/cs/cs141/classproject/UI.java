/**
 * 
 */
package edu.cpp.cs.cs141.classproject;

import java.util.Scanner;

/**
 * The UI class represents the interface through which the player will interact with the game world. It exists as an object
 * within the {@link GameEngine}, and is queried whenever input/output to the player is needed. The class is able to pass
 * any necessary input from the player to interact with their {@link Player} character.
 * 
 * @author Aidan Novobilski
 */
public class UI {

	/**
	 * This field represents all possible directions that a player could possibly input. 
	 * 
	 * @author Aidan Novobilski
	 */
	public static enum direction {UP, DOWN, LEFT, RIGHT};
	
	/**
	 * This field represents all possible actions that a player can take on a turn. On a given turn, the player can look,
	 * and then can move or shoot. 
	 * 
	 * @author Aidan Novobilski
	 */
	public static enum action {LOOK, MOVE, SHOOT};
	
	/**
	 * This field represents all possible modes that the player can input. 
	 * 
	 * @author Aidan Novobilski
	 */
	public static enum mode {DEBUG, NORMAL};
	
	/**
	 * This field represents the Scanner object that will read all player input into the console. It is initialized during
	 * the constructor and used whenever the player's input is needed.
	 */
	private Scanner sc;
	
	/**
	 * This field represents the stored input of the player. Initialized within any method that reads input, since String
	 * is immutable.
	 */
	private String input;
	
	/**
	 * The default constructor. Initializes the Scanner object.
	 */
	public UI(){
		sc = new Scanner(System.in);
	}
	
	/**
	 * This method prints the "title screen" of the game, as well as ask the player if they would like to play the game.
	 */
	public void printTitle(){
		
	}
	
	/**
	 * This method will ask the player whether they would like to play in normal mode or debug mode, and return their choice.
	 * 
	 * @return Returns {@code DEBUG} or {@code NORMAL}
	 */
	public mode selectMode(){
		return null;
	}
	
	/**
	 * This method will print the rules of the game. It will also explain to the player how to interpret the map screen.
	 * 
	 */
	public void printRules(){
		
	}
	
	/**
	 * This method will query the player for input on what action they wish to take. It will then interpret their input and
	 * return either {@code LOOK}, {@code MOVE}, or {@code SHOOT}. If their input is invalid, it will print {@code "Invalid input."}
	 * and query the player again.
	 * 
	 * @return Returns {@code LOOK}, {@code MOVE}, or {@code SHOOT}.
	 */
	public action readAction(){
		return null;
	}
	
	/**
	 * This method will query the player for input on what direction they wish to act towards. It will interpret their input
	 * and return one of the four cardinal directions. If their input is invalid, it will print {@code "Invalid input."}
	 * and query the player again.
	 * 
	 * @return Returns {@code UP}, {@code DOWN}, {@code LEFT}, or {@code RIGHT}.
	 */
	public direction readDirection(){
		return null;
	}
	
	/**
	 * This method will draw the map within the console. The player character will be represented with {@code "@"}, blank
	 * space will be represented with {@code "."}, and the rooms will be represented with {@code "R"}. If debug mode is
	 * enabled, the game will also show the location of the ninjas with {@code "N"} and the briefcase with {@code "B"} and
	 * powerups with various symbols ({@code b}, {@code i}, and {@code r}).
	 * The entire map will be surrounded with walls represented by {@code "#"}.
	 * 
	 * @param entities The entities 
	 * @param debug If {@code true}, the map will draw ninjas and the briefcase. If {@code false}, it will not.
	 */
	public void printMap(Entity[] entities, boolean debug){
		
	}
	
	/**
	 * This method will inform the player that they were killed by a ninja. In addition, it will tell them how many lives
	 * they have remaining.
	 * 
	 * @param lives The number of lives the player has remaining.
	 */
	public void printPlayerDied(int lives){
		
	}
	
	/**
	 * This method will inform the player that they have just picked up a power up. In addition, it will take the name
	 * as an argument to tell them which power up it was and what they gained. 
	 * 
	 * @param powerUpName The name of the power up the player picked up.
	 */
	public void printPowerUp(String powerUpName){
		
	}
	
	/**
	 * This method will inform the player that they have successfully found the briefcase and print the map showing the
	 * locations of all ninjas and power ups.
	 */
	public void printVictory(){
		
	}
	
	/**
	 * This method will inform the player that they were killed by a ninja and have lost their final life, and print the
	 * map showing the locations of all ninjas and power ups.
	 */
	public void printGameOver(){
		
	}
	
	/**
	 * This method will ask the player whether they would like to play again. If they answer some variation of {@code "No"}
	 * or {@code "Quit"}, the UI will call {@link #goodbye()} and quit the game.
	 */
	public void askIfPlayingAgain(){
		
	}
	
	/**
	 * This method will ask the player where their save file is located to load.
	 * 
	 * @return The name/location of the file to load.
	 */
	public String queryLoad(){
		return null;
	}
	
	/**
	 * This method will ask the player where to save their save file and what they would like to name it.
	 * 
	 * @return The name/location of the file to save.
	 */
	public String querySave(){
		return null;
	}

	/**
	 * This method prints a "goodbye" message to the player and quits the game safely.
	 */
	public void goodbye(){
		System.out.println("Goodbye!");
		System.exit(0);
	}
}

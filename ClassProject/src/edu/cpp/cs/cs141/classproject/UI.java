/**
 * 
 */
package edu.cpp.cs.cs141.classproject;

import java.util.Scanner;

/**
 * The UI class represents the interface through which the player will interact
 * with the game world. It exists as an object within the {@link GameEngine},
 * and is queried whenever input/output to the player is needed. The class is
 * able to pass any necessary input from the player to interact with their
 * {@link Player} character.
 * 
 * @author Aidan Novobilski
 */
public class UI {

	/**
	 * This field represents all possible directions that a player could
	 * possibly input.
	 * 
	 * @author Aidan Novobilski
	 */
	public static enum direction {
		UP, DOWN, LEFT, RIGHT
	};

	/**
	 * This field represents all possible actions that a player can take on a
	 * turn. On a given turn, the player can look or save, and then can move, shoot.
	 * 
	 * @author Aidan Novobilski
	 */
	public static enum action {
		LOOK, MOVE, SHOOT, SAVE
	};

	/**
	 * This field represents all possible modes that the player can input.
	 * 
	 * @author Aidan Novobilski
	 */
	public static enum mode {
		DEBUG, NORMAL
	};

	/**
	 * This field represents the Scanner object that will read all player input
	 * into the console. It is initialized during the constructor and used
	 * whenever the player's input is needed.
	 */
	private Scanner sc;

	/**
	 * This field represents the stored input of the player. Initialized within
	 * any method that reads input, since String is immutable.
	 */
	private String input;

	/**
	 * The default constructor. Initializes the Scanner object.
	 */
	public UI() {
		sc = new Scanner(System.in);
	}

	/**
	 * This method prints the "title screen" of the game, as well as ask the
	 * player if they would like to play the game.
	 */
	public void printTitle() {
		System.out.println();
		System.out.println("  iiii                      ffffffffffffffff    iiii  lllllll         tttt                                                     tttt            iiii                                     ");
		System.out.println(" i::::i                    f::::::::::::::::f  i::::i l:::::l      ttt:::t                                                  ttt:::t           i::::i                                    ");
		System.out.println("  iiii                    f::::::::::::::::::f  iiii  l:::::l      t:::::t                                                  t:::::t            iiii                                     ");
		System.out.println("                          f::::::fffffff:::::f        l:::::l      t:::::t                                                  t:::::t                                                     ");
		System.out.println("iiiiiiinnnn  nnnnnnnn     f:::::f       ffffffiiiiiii  l::::lttttttt:::::ttttttt   rrrrr   rrrrrrrrr   aaaaaaaaaaaaa  ttttttt:::::ttttttt    iiiiiii    ooooooooooo   nnnn  nnnnnnnn    ");
		System.out.println("i:::::in:::nn::::::::nn   f:::::f             i:::::i  l::::lt:::::::::::::::::t   r::::rrr:::::::::r  a::::::::::::a t:::::::::::::::::t    i:::::i  oo:::::::::::oo n:::nn::::::::nn  ");
		System.out.println(" i::::in::::::::::::::nn f:::::::ffffff        i::::i  l::::lt:::::::::::::::::t   r:::::::::::::::::r aaaaaaaaa:::::at:::::::::::::::::t     i::::i o:::::::::::::::on::::::::::::::nn ");
		System.out.println(" i::::inn:::::::::::::::nf::::::::::::f        i::::i  l::::ltttttt:::::::tttttt   rr::::::rrrrr::::::r         a::::atttttt:::::::tttttt     i::::i o:::::ooooo:::::onn:::::::::::::::n");
		System.out.println(" i::::i  n:::::nnnn:::::nf::::::::::::f        i::::i  l::::l      t:::::t          r:::::r     r:::::r  aaaaaaa:::::a      t:::::t           i::::i o::::o     o::::o  n:::::nnnn:::::n");
		System.out.println(" i::::i  n::::n    n::::nf:::::::ffffff        i::::i  l::::l      t:::::t          r:::::r     rrrrrrraa::::::::::::a      t:::::t           i::::i o::::o     o::::o  n::::n    n::::n");
		System.out.println(" i::::i  n::::n    n::::n f:::::f              i::::i  l::::l      t:::::t          r:::::r           a::::aaaa::::::a      t:::::t           i::::i o::::o     o::::o  n::::n    n::::n");
		System.out.println(" i::::i  n::::n    n::::n f:::::f              i::::i  l::::l      t:::::t    ttttttr:::::r          a::::a    a:::::a      t:::::t    tttttt i::::i o::::o     o::::o  n::::n    n::::n");
		System.out.println("i::::::i n::::n    n::::nf:::::::f            i::::::il::::::l     t::::::tttt:::::tr:::::r          a::::a    a:::::a      t::::::tttt:::::ti::::::io:::::ooooo:::::o  n::::n    n::::n");
		System.out.println("i::::::i n::::n    n::::nf:::::::f            i::::::il::::::l     tt::::::::::::::tr:::::r          a:::::aaaa::::::a      tt::::::::::::::ti::::::io:::::::::::::::o  n::::n    n::::n");
		System.out.println("i::::::i n::::n    n::::nf:::::::f            i::::::il::::::l       tt:::::::::::ttr:::::r           a::::::::::aa:::a       tt:::::::::::tti::::::i oo:::::::::::oo   n::::n    n::::n");
		System.out.println("iiiiiiii nnnnnn    nnnnnnfffffffff            iiiiiiiillllllll         ttttttttttt  rrrrrrr            aaaaaaaaaa  aaaa         ttttttttttt  iiiiiiii   ooooooooooo     nnnnnn    nnnnnn");
		System.out.println("\n\n");
		
		System.out.println("Press ENTER to play the game or \"q\" to quit.");

		input = sc.nextLine();
		if (input.equals("q") || input.equals("Q"))
			goodbye();
	}

	/**
	 * This method will ask the player whether they would like to play in normal
	 * mode or debug mode, and return their choice.
	 * 
	 * @return Returns {@code DEBUG} or {@code NORMAL}
	 */
	public mode selectMode() {
		while(true){
			System.out.println("Select a mode:");
			System.out.println("[1] - Normal");
			System.out.println("[2] - Debug");
			
			input = sc.nextLine();
			if(input.equals("1") || input.equals("N") || input.equals("n"))
				return mode.NORMAL;
			if(input.equals("2") || input.equals("D") || input.equals("d"))
				return mode.DEBUG;
			
			System.out.println("Invalid input.");
		}
	}

	/**
	 * This method will print the rules of the game.
	 */
	public void printRules() {
		System.out.println("You are a spy, infiltrating an enemy base to steal a briefcase full of intel.");
		System.out.println("Unfortunately, the base is pitch black, filled with ninjas, and your equipment sucks...\n");
		
		System.out.println("Each turn, you will have three options: look, move, and shoot.");
		System.out.println("Looking allows you to detect ninjas within two tiles in a single direction.");
		System.out.println("Looking does not end your turn, but can only be performed once a turn.");
		System.out.println("Shooting will fire your gun in a chosen direction, killing the first ninja struck.");
		System.out.println("You start with one bullet, and can only ever hold one.");
		System.out.println("There are nine rooms to check, one of which has the briefcase in it.");
		System.out.println("To check a room, move into it from the north side. If it contains the briefcase, you win.");
		System.out.println("Your turn will end after you have either moved or shot.");
		System.out.println("After your turn, all six ninjas will move around.");
		System.out.println("If you ended your turn next to a ninja, they will catch you, and you will lose a life.");
		System.out.println("If you run out of lives, you lose the game.");
		System.out.println("You might get lucky and find some items that can help you along the way.");
		
		System.out.println("Press ENTER to continue.");
		input = sc.nextLine();
	}
	
	/**
	 * This method will ask the player whether they would like to start a new game or load a save. 
	 * 
	 * @return If they select loading, {@link #queryLoad()} is called and the result is returned. Otherwise, returns {@code null}
	 */
	public String newGame(){
		System.out.println("Would you like to start a new game or load a save? (N/L)");
		input = sc.nextLine();
		
		if(input.equals("L") || input.equals("l") || input.equals("Load") || input.equals("load"))
			return queryLoad();
		
		return null;
	}

	/**
	 * This method will query the player for input on what action they wish to
	 * take. It will then interpret their input and return either {@code LOOK},
	 * {@code MOVE}, or {@code SHOOT}. If their input is invalid, it will print
	 * {@code "Invalid input."} and query the player again.
	 * 
	 * @return Returns {@code LOOK}, {@code MOVE}, or {@code SHOOT}.
	 * @param looked If the player has already looked this turn.
	 */
	public action readAction(boolean looked, boolean hasBullet) {
		while(true){
			System.out.println("Select an action:");
			if(looked)
				System.out.println("[1] - You have already looked this turn");
			else
				System.out.println("[1] - Look");
			System.out.println("[2] - Move");
			if(hasBullet)
				System.out.println("[3] - Shoot");
			else
				System.out.println("[3] - You do not have a bullet");
			if(looked)
				System.out.println("[4] - You can only save as the first action of your turn");
			else
				System.out.println("[4] - Save");
			
			input = sc.nextLine();
			if (input.equals("1") || input.equals("L") || input.equals("l")){
				if(!looked)
					return action.LOOK;
				else {
					System.out.println("You already looked this turn.");
					continue;
				}
			}
			
			if(input.equals("2") || input.equals("M") || input.equals("m"))
				return action.MOVE;
			
			if(input.equals("3") || input.equals("S") || input.equals("s")){
				if(hasBullet){
					return action.SHOOT;
				} else {
					System.out.println("You cannot shoot without a bullet.");
					continue;
				}
			}
			
			if((input.equals("4") || input.equals("Save") || input.equals("save"))){
				if(!looked)
					return action.SAVE;
				else{
					System.out.println("You can only save at the start of your turn.");
					continue;
				}
			}
			
			if(input.equals("quit") || input.equals("Quit") || input.equals("Q") || input.equals("q"))
					goodbye();
			
			System.out.println("Invalid input.");
		}
	}

	/**
	 * This method will query the player for input on what direction they wish
	 * to act towards. It will interpret their input and return one of the four
	 * cardinal directions. If their input is invalid, it will print
	 * {@code "Invalid input."} and query the player again.
	 * 
	 * @return Returns {@code UP}, {@code DOWN}, {@code LEFT}, or {@code RIGHT}.
	 */
	public direction readDirection() {
		while(true){
			System.out.println("In what direction? (W/A/S/D for UP/LEFT/DOWN/RIGHT)");
			input = sc.nextLine();
			
			if(input.equals("W") || input.equals("w"))
				return direction.UP;
			else if(input.equals("A") || input.equals("a"))
				return direction.LEFT;
			else if(input.equals("S") || input.equals("s"))
				return direction.DOWN;
			else if(input.equals("D") || input.equals("d"))
				return direction.RIGHT;
			
			System.out.println("Invalid input.");
		}
	}

	/**
	 * This method will draw the map within the console. The player character
	 * will be represented with {@code "@"}, blank space will be represented
	 * with {@code "."}, and the rooms will be represented with {@code "R"}. If
	 * debug mode is enabled, the game will also show the location of the ninjas
	 * with {@code "N"} and the briefcase with {@code "B"} and powerups with
	 * various symbols ({@code b}, {@code i}, and {@code r}). The entire map
	 * will be surrounded with walls represented by {@code "#"}.
	 * 
	 * @param entities
	 *            The map of entities
	 */
	public void printMap(Entity[][] entities) {
		System.out.println("#############################");
		
		for (int i = 0; i < 9; ++i) {
			System.out.print("#");
			for (int j = 0; j < 9; ++j) {
				System.out.print("[");
				if (entities[i][j] == null) {
					System.out.print(" ");
				} else
					System.out.print(entities[i][j]);
				System.out.print("]");
			}
			System.out.println("#");
		}
		
		System.out.println("#############################");
	}
	
	public void printLookResults(boolean ninjaFound){
		System.out.print("You concentrated and looked, finding... ");
		
		if(ninjaFound)
			System.out.println("A NINJA!");
		else
			System.out.println("nothing...");
	}

	/**
	 * This method will inform the player what the characters on the map stand
	 * for.
	 * 
	 * @param debug
	 *            Whether the game is in debug mode or not.
	 */
	public void printLegend(boolean debug) {
		System.out.println("[@] - Player");
		System.out.println("[R] - Room");
		System.out.println("[X] - Checked Room");
		if (debug) {
			System.out.println("[B] - Briefcase Room (only visible in debug mode)");
			System.out.println("[N] - Ninja (only visible in debug mode)");
			System.out.println("[!] - Extra Bullet (only visible in debug mode)");
			System.out.println("[*] - Invincibility (only visible in debug mode)");
			System.out.println("[%] - Radar (only visible in debug mode)");
		} else
			System.out.println("[B] - Briefcase Room (only visible with radar)");
		System.out.println();
	}

	/**
	 * This method will tell the player that they could not move because they collided with something.
	 */
	public void printPlayerBumped(){
		System.out.println("Whoops! You bumped into something!\n");
	}
	
	public void printPlayerBumpedWall(){
		System.out.println("You attempted to walk through a wall. Let's just pretend that never happened.\n");
	}
	
	/**
	 * This method will tell the player that they have checked a room and found nothing. For the successful room check, use {@link #printVictory()}.
	 */
	public void printCheckedRoom(){
		System.out.println("You checked the room and found nothing.");
	}
	
	/**
	 * This method will inform the player that they were killed by a ninja. In
	 * addition, it will tell them how many lives they have remaining.
	 * 
	 * @param lives
	 *            The number of lives the player has remaining.
	 */
	public void printPlayerDied(int lives) {
		System.out.println("Oh no! You were caught.");
		System.out.println("You have " + lives + " lives remaining.");
	}

	/**
	 * This method will inform the player that they have just picked up a power
	 * up. In addition, it will take the name as an argument to tell them which
	 * power up it was and what they gained.
	 * 
	 * @param powerUpName
	 *            The name of the power up the player picked up.
	 */
	public void printPowerUp(Item.itemType type, boolean hasBullet) {
		System.out.print("You picked up ");
		
		switch(type){
		case BULLET:
			System.out.println("a bullet!");
			if(hasBullet){
				System.out.println("Unfortunately, you're carrying too much, and throw the bullet away.");
			} else
				System.out.println("You put the bullet into your gun and chamber it.");
			break;
		case INVINCIBILITY:
			System.out.println("an invincibility potion!");
			System.out.println("You cannot be caught for the next five turns.");
			break;
		case RADAR:
			System.out.println("a radar chip!");
			System.out.println("The location of the briefcase has been revealed!");
		}
	}
	
	/**
	 * This method will tell the player whether or not their shot successfully hit an enemy.
	 * 
	 * @param hit Whether the shot hit or not
	 */
	public void printShoot(boolean hit){
		System.out.println("You fire into the darkness...");
		if(hit)
			System.out.println("You hear a pained groan in the distance. Sounds like you hit someone.\n");
		else
			System.out.println("You hear a small *ping* sound. Must have hit a wall.\n");
	}

	/**
	 * This method will inform the player that they have successfully found the
	 * briefcase and print the map showing the locations of all ninjas and power
	 * ups.
	 */
	public void printVictory() {
		System.out.println("You checked the room and found... wait a minute, that's...");
		System.out.println("You found the briefcase! You win!");
	}

	/**
	 * This method will inform the player that they were killed by a ninja and
	 * have lost their final life, and print the map showing the locations of
	 * all ninjas and power ups.
	 */
	public void printGameOver() {
		System.out.println("You were caught and killed by the ninjas! Too bad!");
	}

	/**
	 * This method will ask the player whether they would like to play again. If
	 * they answer some variation of {@code "No"} or {@code "Quit"}, the UI will
	 * call {@link #goodbye()} and quit the game.
	 */
	public void askIfPlayingAgain() {
		
		System.out.println("Would you like to play again? (Y/N)");

		input = sc.nextLine();
		if (input.equals("q") || input.equals("Q") || input.equals("No") || input.equals("no") || input.equals("N") || input.equals("n"))
			goodbye();
	}

	/**
	 * This method will ask the player where their save file is located to load.
	 * 
	 * @return The name/location of the file to load.
	 */
	public String queryLoad() {
		System.out.println("Please specify the location to load from:");
		return sc.nextLine();
	}
	
	/**
	 * This method will tell the player that they have successfully loaded a save.
	 */
	public void loadSuccess(){
		System.out.println("Successfully loaded your save.");
	}

	/**
	 * This method will ask the player where to save their save file and what
	 * they would like to name it.
	 * 
	 * @return The name/location of the file to save.
	 */
	public String querySave() {
		System.out.println("Please specify the location to save to:");
		return sc.nextLine();
	}
	
	/**
	 * This method will tell the player that they have successfully saved the game.
	 */
	public void saveSuccess(){
		System.out.println("Successfully saved your game.");
	}

	/**
	 * This method prints a "goodbye" message to the player and quits the game
	 * safely.
	 */
	public void goodbye() {
		System.out.println("Goodbye!");
		System.exit(0);
	}
}

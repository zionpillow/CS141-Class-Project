/**
 * 
 */
package edu.cpp.cs.cs141.classproject;

import java.io.File;
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
	 * turn. On a given turn, the player can look or save, and then can move,
	 * shoot.
	 * 
	 * @author Aidan Novobilski
	 */
	public static enum action {
		LOOK, MOVE, SHOOT, SAVE, QUIT
	};

	/**
	 * This field represents all possible modes that the player can input.
	 * 
	 * @author Aidan Novobilski
	 */
	public static enum mode {
		DEBUG, NORMAL, HARD, DEBUGHARD
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
		System.out.println("\n");
		System.out.println("8888888 888b    888 8888888888 8888888 888      88888888888 8888888b.         d8888 88888888888 8888888  .d88888b.  888b    888");
		System.out.println("  888   8888b   888 888          888   888          888     888   Y88b       d88888     888       888   d88P\" \"Y88b 8888b   888");
		System.out.println("  888   88888b  888 888          888   888          888     888    888      d88P888     888       888   888     888 88888b  888");
		System.out.println("  888   888Y88b 888 8888888      888   888          888     888   d88P     d88P 888     888       888   888     888 888Y88b 888");
		System.out.println("  888   888 Y88b888 888          888   888          888     8888888P\"     d88P  888     888       888   888     888 888 Y88b888");
		System.out.println("  888   888  Y88888 888          888   888          888     888 T88b     d88P   888     888       888   888     888 888  Y88888");
		System.out.println("  888   888   Y8888 888          888   888          888     888  T88b   d8888888888     888       888   Y88b. .d88P 888   Y8888");
		System.out.println("8888888 888    Y888 888        8888888 88888888     888     888   T88b d88P     888     888     8888888  \"Y88888P\"  888    Y888");
		System.out.println("\n\n");
	}
	
	/**
	 * This method will ask the player whether they would like to start a new
	 * game or load a save.
	 * 
	 * @return If they select loading, {@link #queryLoad()} is called and the
	 *         result is returned. Otherwise, returns {@code null}
	 */
	public String mainMenu(String[] names, int[] scores) {
		while (true) {
			printTitle();
			System.out.println("Main Menu:");
			System.out.println("[1] - New Game");
			System.out.println("[2] - Load Game");
			System.out.println("[3] - How to Play");
			System.out.println("[4] - Highscores");
			System.out.println("[5] - Quit");
			System.out.println();
			input = sc.nextLine();
			System.out.println();

			if (input.equals("1"))
				return null;
			
			else if (input.equals("2")) {
				String load = queryLoad();
				if (load == null)
					return "no saves";
				else
					return load;
			}

			else if (input.equals("3")) {
				printRules();
				continue;
			}
			
			else if (input.equals("4")) {
				printHighscores(names, scores);
				continue;
			}

			else if (input.equals("5")) {
				while (true) {
					System.out.println("Are you sure you want to quit? (Y/N)");
					System.out.println();
					input = sc.nextLine();
					System.out.println();

					if (input.equalsIgnoreCase("y"))
						goodbye();
					else if (input.equalsIgnoreCase("n"))
						break;
					
					System.out.println("Invalid input.\n");
				}

				continue;
			}

			System.out.println("Invalid input.\n");
		}
	}

	/**
	 * This method will ask the player whether they would like to play in normal
	 * mode or debug mode, and return their choice.
	 * 
	 * @return Returns {@code DEBUG} or {@code NORMAL}
	 */
	public mode selectMode() {
		while (true) {
			System.out.println("Select a mode:");
			System.out.println("[1] - Normal (1.0x score)");
			System.out.println("[2] - Debug (0.5x score)");
			System.out.println("[3] - Hard Mode (2.5x score)");
			System.out.println("[4] - Hard Debug Mode (0.5x score)");
			System.out.println();
			input = sc.nextLine();
			System.out.println();
			
			if (input.equalsIgnoreCase("1"))
				return mode.NORMAL;
			else if (input.equalsIgnoreCase("2"))
				return mode.DEBUG;
			else if (input.equalsIgnoreCase("3"))
				return mode.HARD;
			else if (input.equalsIgnoreCase("4"))
				return mode.DEBUGHARD;
			
			System.out.println("Invalid input.\n");
		}
	}

	/**
	 * This method will print the rules of the game.
	 */
	public void printRules() {
		System.out.println();
		System.out.println();
		System.out.println("888    888                            888                 8888888b.  888                  ");
		System.out.println("888    888                            888                 888   Y88b 888                  ");
		System.out.println("888    888                            888                 888    888 888                  ");
		System.out.println("8888888888  .d88b.  888  888  888     888888  .d88b.      888   d88P 888  8888b.  888  888");
		System.out.println("888    888 d88\"\"88b 888  888  888     888    d88\"\"88b     8888888P\"  888     \"88b 888  888");
		System.out.println("888    888 888  888 888  888  888     888    888  888     888        888 .d888888 888  888");
		System.out.println("888    888 Y88..88P Y88b 888 d88P     Y88b.  Y88..88P     888        888 888  888 Y88b 888");
		System.out.println("888    888  \"Y88P\"   \"Y8888888P\"       \"Y888  \"Y88P\"      888        888 \"Y888888  \"Y88888");
		System.out.println("                                                                                       888");
		System.out.println("                                                                                  Y8b d88P");
		System.out.println("                                                                                   \"Y88P\" ");
		System.out.println();
		System.out.println();
		System.out.println("-INTRODUCTION-");
		System.out.println("The U.S. government has hired you as a secret agent to infiltrate the enemy base and steal");
		System.out.println("a briefcase filled with intel of a planned Russian nuclear launch against the U.S. Several");
		System.out.println("pieces of intel have been scattered throughout different briefcases, each highly guarded");
		System.out.println("by ninjas several ninjas hired by the Russian government. The U.S. government has agreed");
		System.out.println("to pay you in large sums to complete the task. Each base is pitch black and the enemy has");
		System.out.println("been equipped with high quality night vision goggles. Unfortunately, the U.S. government");
		System.out.println("has been a little short on money recently and can only provide you with defective night");
		System.out.println("vision goggles. Nevertheless, you decide to accept the mission anyway.");
		System.out.println();
		System.out.println("-BASICS-");
		System.out.println("Each turn, you will have three options: look, move, and shoot. Looking allows you to detect");
		System.out.println("ninjas within two tiles in a single direction. Looking does not end your turn, but can only");
		System.out.println("be performed once a turn. Shooting will fire your gun in a chosen direction, killing the");
		System.out.println("first ninja struck. You will start with one bullet, and can only hold one. There are nine");
		System.out.println("rooms to check, one of which has the briefcase in it. To check a room, move into it from");
		System.out.println("the north side. If it contains the briefcase, you will win the level. Your turn will end");
		System.out.println("after you have either moved or shot. After your turn, all of the ninjas will move around.");
		System.out.println("If you ended your turn next to a ninja, they will catch you, and you will lose a life.");
		System.out.println("If you run out of lives, you lose the game. If you find the briefcase, you have the option");
		System.out.println("to move on to the next level. Just a little something extra, in each level, there may be");
		System.out.println("items that can help you along the way. The following are tiles you may see in the game.");
		System.out.println();
		System.out.println("-TILES-");
		System.out.println("[@] - Player");
		System.out.println("[R] - Room");
		System.out.println("[X] - Checked Room");
		System.out.println("[B] - Briefcase Room (only visible with radar or in debug mode)");
		System.out.println("[N] - Ninja (only visible in debug mode)");
		System.out.println("[!] - Extra Bullet (only visible in debug mode)");
		System.out.println("[*] - Invincibility (only visible in debug mode)");
		System.out.println("[%] - Radar (only visible in debug mode)");
		System.out.println();
		System.out.println("-GOAL-");
		System.out.println("The goal of the game is to find the briefcase and complete each level. Try to find as many");
		System.out.println("briefcases as you can because you will be scored based on your performance and several");
		System.out.println("other criterias including: the number of rooms checked, the number of enemies killed,");
		System.out.println("and the number of items picked up. You will also score higher if you defeat levels in ");
		System.out.println("less number of turns.");
		System.out.println();
		System.out.println("Good luck and have fun!");
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
	}
	
	/**
	 * @param level
	 * @param lives
	 * @param ammo
	 */
	public void printStats(int level, int lives, int ammo) {
		System.out.println();
		System.out.printf("Level %d\n", level);
		System.out.printf("Lives: %d\n", lives);
		System.out.printf("Ammo: %d/1\n", ammo);
		System.out.println();
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
		System.out.println();
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
	 * This method will query the player for input on what action they wish to
	 * take. It will then interpret their input and return either {@code LOOK},
	 * {@code MOVE}, or {@code SHOOT}. If their input is invalid, it will print
	 * {@code "Invalid input."} and query the player again.
	 * 
	 * @return Returns {@code LOOK}, {@code MOVE}, or {@code SHOOT}.
	 * @param looked
	 *            If the player has already looked this turn.
	 */
	public action readAction(boolean looked, boolean hasBullet) {
		while (true) {
			System.out.println("Select an action:");
			if (looked)
				System.out.println("[1] - You have already looked this turn");
			else
				System.out.println("[1] - Look");
			System.out.println("[2] - Move");
			if (hasBullet)
				System.out.println("[3] - Shoot");
			else
				System.out.println("[3] - You do not have a bullet");
			if (looked)
				System.out.println("[4] - You can only save as the first action of your turn");
			else
				System.out.println("[4] - Save");
			System.out.println("[5] - Quit to Menu");
			System.out.println();
			input = sc.nextLine();
			System.out.println();
			
			if (input.equals("1")) {
				if (!looked)
					return action.LOOK;
				else {
					System.out.println("You already looked this turn.\n");
					System.out.println();
					System.out.println("Press ENTER to continue...");
					sc.nextLine();
					continue;
				}
			}

			else if (input.equals("2"))
				return action.MOVE;

			else if (input.equals("3")) {
				if (hasBullet) {
					return action.SHOOT;
				} else {
					System.out.println("You cannot shoot without a bullet.\n");
					System.out.println();
					System.out.println("Press ENTER to continue...");
					sc.nextLine();
					continue;
				}
			}

			else if (input.equals("4")) {
				if (!looked)
					return action.SAVE;
				else {
					System.out.println("You can only save at the start of your turn.\n");
					System.out.println();
					System.out.println("Press ENTER to continue...");
					sc.nextLine();
					continue;
				}
			}

			else if (input.equalsIgnoreCase("5")) {
				while (true) {
					System.out.println("Are you sure you want to quit? (Y/N)");
					System.out.println();
					input = sc.nextLine();
					System.out.println();

					if (input.equalsIgnoreCase("y"))
						return action.QUIT;
					else if (input.equalsIgnoreCase("n"))
						break;
					
					System.out.println("Invalid input.\n");
				}
				
				continue;
			}

			System.out.println("Invalid input.\n");
		}
	}

	public void printLookResults(boolean ninjaFound) {
		System.out.print("You concentrated and looked, finding... ");
	
		if (ninjaFound)
			System.out.println("A NINJA!");
		else
			System.out.println("nothing...");
	
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
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
		while (true) {
			System.out.println("In what direction? (W/A/S/D for UP/LEFT/DOWN/RIGHT)");
			System.out.println();
			input = sc.nextLine();
			System.out.println();

			if (input.equalsIgnoreCase("w"))
				return direction.UP;
			else if (input.equalsIgnoreCase("a"))
				return direction.LEFT;
			else if (input.equalsIgnoreCase("s"))
				return direction.DOWN;
			else if (input.equalsIgnoreCase("d"))
				return direction.RIGHT;

			System.out.println("Invalid input.\n");
		}
	}

	/**
	 * This method will tell the player that they could not move because they
	 * collided with something.
	 */
	public void printPlayerBumped() {
		System.out.println("Whoops! You bumped into something!\n");
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
	}

	public void printPlayerBumpedWall() {
		System.out.println("You attempted to walk through a wall. Let's just pretend that never happened.");
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
	}

	/**
	 * This method will tell the player that they have checked a room and found
	 * nothing. For the successful room check, use {@link #printVictory()}.
	 */
	public void printCheckedRoom() {
		System.out.println("You checked the room and found nothing.");
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
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
	
		switch (type) {
		case BULLET:
			System.out.println("a bullet!");
			if (hasBullet) {
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
	
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
	}

	public void printInvincibility(int turns) {
		if (turns > 0)
			System.out.println("You have " + turns + " turns of invincibility remaining.");
		else
			System.out.println("Your invincibility has worn off.");
	
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
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
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
	}

	/**
	 * This method will tell the player whether or not their shot successfully
	 * hit an enemy.
	 * 
	 * @param hit
	 *            Whether the shot hit or not
	 */
	public void printShoot(boolean hit) {
		System.out.println("You fire into the darkness...");
		if (hit)
			System.out.println("You hear a pained groan in the distance. Sounds like you hit someone.");
		else
			System.out.println("You hear a small *ping* sound. Must have hit a wall.");
	
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
	}

	/**
	 * This method will inform the player that they have successfully found the
	 * briefcase and print the map showing the locations of all ninjas and power
	 * ups.
	 */
	public void printVictory() {
		System.out.println("You checked the room and found... wait a minute, that's...");
		System.out.println("You found the briefcase!");
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
	}
	
	public boolean printNextLevel() {
		System.out.println("HQ has sent you the following message:");
		System.out.println("\"Infiltrator, we have a new mission for you. Your mission,");
		System.out.println("should you choose to accept it, is to infiltrate a more highly");
		System.out.println("guarded compound. If you choose to accept, rendezvous at the");
		System.out.println("specified location.\"");
		System.out.println();
		
		while (true) {
			System.out.println("Do you accept the mission? (Y/N)");
			System.out.println();
			input = sc.nextLine();
			System.out.println();
			
			if (input.equalsIgnoreCase("y"))
				return true;
			else if (input.equalsIgnoreCase("n"))
				return false;
			
			System.out.println("Invalid input.\n");
		}
	}

	/**
	 * This method will inform the player that they were killed by a ninja and
	 * have lost their final life, and print the map showing the locations of
	 * all ninjas and power ups.
	 */
	public void printGameOver() {
		System.out.println("You were caught and killed by the ninjas! Too bad!");
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
	}
	
	public void printResults(int level, int numOfTurns, int livesRemaining, int roomsChecked, int itemPickups, int enemiesKilled, int score) {
		System.out.println("============================================================================================================");
		System.out.println("|                                                                                                          |");
		System.out.println("|     .d8888b.         d8888 888b     d888 8888888888      .d88888b.  888     888 8888888888 8888888b.     |");
		System.out.println("|    d88P  Y88b       d88888 8888b   d8888 888            d88P\" \"Y88b 888     888 888        888   Y88b    |");
		System.out.println("|    888    888      d88P888 88888b.d88888 888            888     888 888     888 888        888    888    |");
		System.out.println("|    888            d88P 888 888Y88888P888 8888888        888     888 Y88b   d88P 8888888    888   d88P    |");
		System.out.println("|    888  88888    d88P  888 888 Y888P 888 888            888     888  Y88b d88P  888        8888888P\"     |");
		System.out.println("|    888    888   d88P   888 888  Y8P  888 888            888     888   Y88o88P   888        888 T88b      |");
		System.out.println("|    Y88b  d88P  d8888888888 888   \"   888 888            Y88b. .d88P    Y888P    888        888  T88b     |");
		System.out.println("|     \"Y8888P88 d88P     888 888       888 8888888888      \"Y88888P\"      Y8P     8888888888 888   T88b    |");
		System.out.println("|                                                                                                          |");
		System.out.println("|                                                                                                          |");
		System.out.println("|                                                                                                          |");
		System.out.println("|                                                                                                          |");
		System.out.printf("|                                                 %08d                                                 |\n",  score);
		System.out.println("|                                                                                                          |");
		System.out.println("|                                                                                                          |");
		System.out.println("|                                                                                                          |");
		System.out.printf("|                 Highest Level Reached                                        %2d                          |\n", level);
		System.out.println("|                                                                                                          |");
		System.out.println("|                                                                                                          |");
		System.out.printf("|                 Lives Remaining                                              %2d                          |\n", livesRemaining);
		System.out.println("|                                                                                                          |");
		System.out.println("|                                                                                                          |");
		System.out.printf("|                 Number of Turns Survived                                    %3d                          |\n", numOfTurns);
		System.out.println("|                                                                                                          |");
		System.out.println("|                                                                                                          |");
		System.out.printf("|                 Number of Rooms Checked                                      %2d                          |\n", roomsChecked);
		System.out.println("|                                                                                                          |");
		System.out.println("|                                                                                                          |");
		System.out.printf("|                 Number of Items Obtained                                     %2d                          |\n", itemPickups);
		System.out.println("|                                                                                                          |");
		System.out.println("|                                                                                                          |");
		System.out.printf("|                 Number of Enemies Killed                                     %2d                          |\n", enemiesKilled);
		System.out.println("|                                                                                                          |");
		System.out.println("|                                                                                                          |");
		System.out.println("|                                                                                                          |");
		System.out.println("|                                          Better luck next time!                                          |");
		System.out.println("|                                                                                                          |");
		System.out.println("============================================================================================================");
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
	}
	
	/**
	 * @return
	 */
	public String askName() {
		String name;
		while (true) {
			System.out.println("Please enter your name (maximum 30 characters):");
			System.out.println();
			name = sc.nextLine();
			System.out.println();
			
			while (name.length() > 0 && name.length() <= 30) {
				System.out.println("Would you like to record your score under this name? (Y/N)");
				System.out.println();
				input = sc.nextLine();
				System.out.println();
				
				if (input.equalsIgnoreCase("y"))
					return name;
				else if (input.equalsIgnoreCase("n"))
					break;
				else
					System.out.println("Invalid input.\n");
			}
			
			System.out.println("Invalid input.\n");
		}
	}
	
	public void printHighscores(String[] names, int[] scores) {
		System.out.println("===============================================================================================================");
		System.out.println("|                                                                                                             |");
		System.out.println("| 888    888 8888888  .d8888b.  888    888  .d8888b.   .d8888b.   .d88888b.  8888888b.  8888888888  .d8888b.  |");
		System.out.println("| 888    888   888   d88P  Y88b 888    888 d88P  Y88b d88P  Y88b d88P\" \"Y88b 888   Y88b 888        d88P  Y88b |");
		System.out.println("| 888    888   888   888    888 888    888 Y88b.      888    888 888     888 888    888 888        Y88b.      |");
		System.out.println("| 8888888888   888   888        8888888888  \"Y888b.   888        888     888 888   d88P 8888888     \"Y888b.   |");
		System.out.println("| 888    888   888   888  88888 888    888     \"Y88b. 888        888     888 8888888P\"  888            \"Y88b. |");
		System.out.println("| 888    888   888   888    888 888    888       \"888 888    888 888     888 888 T88b   888              \"888 |");
		System.out.println("| 888    888   888   Y88b  d88P 888    888 Y88b  d88P Y88b  d88P Y88b. .d88P 888  T88b  888        Y88b  d88P |");
		System.out.println("| 888    888 8888888  \"Y8888P88 888    888  \"Y8888P\"   \"Y8888P\"   \"Y88888P\"  888   T88b 8888888888  \"Y8888P\"  |");
		System.out.println("|                                                                                                             |");
		System.out.println("|                                                                                                             |");
		for (int i = 0 ; i < names.length ; ++i) {
			System.out.println("|                                                                                                             |");
			System.out.println("|                                                                                                             |");
			System.out.printf("|               %-30s                               %8d                         |\n", names[i], scores[i]);
		}
		System.out.println("|                                                                                                             |");
		System.out.println("===============================================================================================================");
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
	}

	/**
	 * This method will ask the player where to save their save file and what
	 * they would like to name it.
	 * 
	 * @return The name/location of the file to save.
	 */
	public String querySave() {
		while (true) {
			String path;
			
			System.out.println("Please name your save file.");
			System.out.println();
			path = "saves" + GameEngine.fileSep + sc.nextLine() + ".dat";
			System.out.println();

			if (new File(path).exists()) {
				do {
					System.out.println("Save file already exists, would you like to overwrite the save file? (Y/N)");
					System.out.println();
					input = sc.nextLine();
					System.out.println();
				} while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));

				if (input.equalsIgnoreCase("y"))
					return path;
			} else
				return path;
		}
	}

	/**
	 * This method will tell the player that they have successfully saved the
	 * game.
	 */
	public void saveSuccess() {
		System.out.println("Successfully saved your game.");
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
	}
	
	/**
	 * This method will ask the player where their save file is located to load.
	 * 
	 * @return The name/location of the file to load.
	 */
	public String queryLoad() {
		String path;
		String[] dir = new File("saves").list();
		
		if (dir.length == 0) {
			System.out.println("You have no saved files.");
			System.out.println();
			System.out.println("Press ENTER to continue...");
			sc.nextLine();
			return null;
		}
		
		System.out.println("Here is a list of your saved files:\n");
		for (int i = 0; i < dir.length; ++i) {
			System.out.print("(" + (i + 1) + ") ");
			System.out.println(dir[i].replace(".dat", ""));
			System.out.println();
		}

		System.out.println("Please input the name of the save file you would like to load.");
		System.out.println();
		path = "saves" + GameEngine.fileSep + sc.nextLine() + ".dat";
		System.out.println();

		while (!new File(path).exists()) {
			System.out.println("Here is a list of your saved files:\n");

			for (int i = 0; i < dir.length; ++i) {
				System.out.print("(" + (i + 1) + ") ");
				System.out.println(dir[i].replace(".dat", ""));
				System.out.println();
			}
			
			System.out.println("Please input an existing save file.");
			System.out.println();
			path = "saves" + GameEngine.fileSep + sc.nextLine() + ".dat";
			System.out.println();
		}

		return path;
	}

	/**
	 * This method will tell the player that they have successfully loaded a
	 * save.
	 * 
	 * @param debug
	 *            Whether the save is in debug mode
	 * @param hard
	 *            Whether the save is in hard mode
	 */
	public void loadSuccess(boolean debug, boolean hard) {
		System.out.println("Successfully loaded your save.");
		System.out.println("Just so you remember...");
		if (debug)
			System.out.println("Your game is in debug mode.");

		if (hard)
			System.out.println("Your game is in hard mode.");
		else
			System.out.println("Your game is in normal mode.");
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
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

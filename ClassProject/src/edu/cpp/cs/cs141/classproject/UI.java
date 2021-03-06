/**
 * CS 141: Intro to Programming and Problem Solving
 * Professor: Edwin Rodr&iacute;guez
 * 
 * Final Project
 * 
 * This assignment is final class project involving four team members.
 * In this assignment, students are to create a small, yet interesting,
 * text-based game involving a grid of 81 squares in which the player
 * tries to find a briefcase in 1 of 9 different rooms while avoiding or
 * fighting existing ninjas in the grid. This assignment incorporates
 * all of the knowledge learned throughout the course.
 * 
 * Team Recycle Bin
 * 		<Natanael Ariawan, David Hau, Miguel Menjivar, Aidan Novobilsky>
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
 * @author Natanael Ariawan
 * @author David Hau
 * @author Miguel Menjivar
 * @author Aidan Novobilski
 */
public class UI {

	/**
	 * This field represents all possible directions that a player could
	 * possibly input.
	 * 
	 * @author Natanael Ariawan
	 * @author David Hau
	 * @author Miguel Menjivar
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
	 * @author Natanael Ariawan
	 * @author David Hau
	 * @author Miguel Menjivar
	 * @author Aidan Novobilski
	 */
	public static enum action {
		LOOK, MOVE, SHOOT, SAVE, QUIT
	};

	/**
	 * This field represents all possible modes that the player can input.
	 * 
	 * @author Natanael Ariawan
	 * @author David Hau
	 * @author Miguel Menjivar
	 * @author Aidan Novobilski
	 */
	public static enum mode {
		DEBUG, NORMAL, HARD, DEBUGHARD, GODMODE
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
	 * This method prints the "title screen" of the game.
	 */
	public void printTitle() {
		System.out.println("\n");
		System.out.println(
				"8888888 888b    888 8888888888 8888888 888      88888888888 8888888b.         d8888 88888888888 8888888  .d88888b.  888b    888");
		System.out.println(
				"  888   8888b   888 888          888   888          888     888   Y88b       d88888     888       888   d88P\" \"Y88b 8888b   888");
		System.out.println(
				"  888   88888b  888 888          888   888          888     888    888      d88P888     888       888   888     888 88888b  888");
		System.out.println(
				"  888   888Y88b 888 8888888      888   888          888     888   d88P     d88P 888     888       888   888     888 888Y88b 888");
		System.out.println(
				"  888   888 Y88b888 888          888   888          888     8888888P\"     d88P  888     888       888   888     888 888 Y88b888");
		System.out.println(
				"  888   888  Y88888 888          888   888          888     888 T88b     d88P   888     888       888   888     888 888  Y88888");
		System.out.println(
				"  888   888   Y8888 888          888   888          888     888  T88b   d8888888888     888       888   Y88b. .d88P 888   Y8888");
		System.out.println(
				"8888888 888    Y888 888        8888888 88888888     888     888   T88b d88P     888     888     8888888  \"Y88888P\"  888    Y888");
		System.out.println("\n\n");
	}

	/**
	 * This method directs the player to the main menu, which has a variety of
	 * options, including new game, load game, how to play, and other extraneous
	 * functions.
	 * 
	 * @return If they select loading, {@link #queryLoad()} is called and the
	 *         result is returned. Otherwise, returns {@code null}
	 */
	/**
	 * This method directs the player to the main menu, which has a variety of
	 * options, including new game, load game, how to play, and other extraneous
	 * functions.
	 * 
	 * @param highscores
	 *            the array of highscores taken from the {@link GameEngine} to
	 *            be sent to {@link #printHighscores(Highscore[])} if the player
	 *            wishes to view the highscores page
	 * @param achievements
	 *            the array of achievements taken from the {@link GameEngine} to
	 *            be sent to {@link #printAchievements(boolean[])} if the player
	 *            wishes to view the achievements page
	 * @param records
	 *            the array of records taken from the {@link GameEngine} to be
	 *            sent to {@link #printRecords(int[])} if the player wishes to
	 *            view the records page
	 * @return a string indicating an action needed to be completed within the
	 *         {@link GameEngine}
	 */
	public String mainMenu(Highscore[] highscores, boolean[] achievements, int[] records) {
		boolean running = true;
		String result = null;

		while (running) {
			printTitle();
			System.out.println("Main Menu:");
			System.out.println("[1] - New Game");
			System.out.println("[2] - Load Game");
			System.out.println("[3] - How to Play");
			System.out.println("[4] - Store");
			System.out.println("[5] - Highscores");
			System.out.println("[6] - Achievements");
			System.out.println("[7] - Records");
			System.out.println("[8] - Reset Game");
			System.out.println("[9] - ?????");
			System.out.println("[10] - Quit");
			System.out.println();
			input = sc.nextLine();
			System.out.println();

			if (input.equals("1"))
				running = false;

			else if (input.equals("2")) {
				String load = queryLoad();
				if (load == null) {
					result = "no saves";
					running = false;
				} else {
					result = load;
					running = false;
				}
			}

			else if (input.equals("3")) {
				printRules();
				continue;
			}

			else if (input.equals("4")) {
				result = "store";
				running = false;
			}

			else if (input.equals("5")) {
				printHighscores(highscores);
				continue;
			}

			else if (input.equals("6")) {
				printAchievements(achievements);
				continue;
			}

			else if (input.equals("7")) {
				printRecords(records);
				continue;
			}

			else if (input.equals("8")) {
				String reset = printReset();
				if (reset == null)
					continue;
				else {
					result = reset;
					running = false;
				}

			}

			else if (input.equals("9")) {
				String cheat = printCheat(achievements[20]);
				if (cheat == null)
					continue;
				else {
					result = cheat;
					running = false;
				}
			}

			else if (input.equals("10")) {
				boolean stillPlaying = true;

				while (stillPlaying) {
					System.out.println("Are you sure you want to quit? (Y/N)");
					System.out.println();
					input = sc.nextLine();
					System.out.println();

					if (input.equalsIgnoreCase("y"))
						goodbye();
					else if (input.equalsIgnoreCase("n"))
						stillPlaying = false;

					if (stillPlaying)
						System.out.println("Invalid input.\n");
				}

				continue;
			}

			if (running)
				System.out.println("Invalid input.\n");
		}

		return result;
	}

	/**
	 * This method will ask the player the game mode they would like to play in
	 * and returns the type of game mode they selected. If God mode has been
	 * bought from the {@link Shop}, an extra option appears allowing the player
	 * to select God mode.
	 * 
	 * @param godMode
	 *            {@code true} if God mode has been bought from the {@link Shop}
	 *            , {@code false} otherwise
	 * @return one of the five possible game mode options listed in the
	 *         enumeration {@link mode}
	 */
	public mode selectMode(boolean godMode) {
		while (true) {
			System.out.println("Select a mode:");
			System.out.println("[1] - Normal (1.0x score)");
			System.out.println("[2] - Debug (0.5x score)");
			System.out.println("[3] - Hard Mode (2.5x score)");
			System.out.println("[4] - Hard Debug Mode (0.5x score)");
			if (godMode)
				System.out.println("[5] - God Mode (1.0x Score, Highscore not saved)");
			System.out.println();
			input = sc.nextLine();
			System.out.println();

			if (input.equals("1"))
				return mode.NORMAL;
			else if (input.equals("2"))
				return mode.DEBUG;
			else if (input.equals("3"))
				return mode.HARD;
			else if (input.equals("4"))
				return mode.DEBUGHARD;
			else if (godMode && input.equals("5"))
				return mode.GODMODE;

			System.out.println("Invalid input.\n");
		}
	}

	/**
	 * This method will print the rules of the game.
	 */
	public void printRules() {
		System.out.println();
		System.out.println();
		System.out.println(
				"888    888  .d88888b.  888       888     88888888888  .d88888b.      8888888b.  888             d8888 Y88b   d88P");
		System.out.println(
				"888    888 d88P\" \"Y88b 888   o   888         888     d88P\" \"Y88b     888   Y88b 888            d88888  Y88b d88P ");
		System.out.println(
				"888    888 888     888 888  d8b  888         888     888     888     888    888 888           d88P888   Y88o88P  ");
		System.out.println(
				"8888888888 888     888 888 d888b 888         888     888     888     888   d88P 888          d88P 888    Y888P   ");
		System.out.println(
				"888    888 888     888 888d88888b888         888     888     888     8888888P\"  888         d88P  888     888    ");
		System.out.println(
				"888    888 888     888 88888P Y88888         888     888     888     888        888        d88P   888     888    ");
		System.out.println(
				"888    888 Y88b. .d88P 8888P   Y8888         888     Y88b. .d88P     888        888       d8888888888     888    ");
		System.out.println(
				"888    888  \"Y88888P\"  888P     Y888         888      \"Y88888P\"      888        88888888 d88P     888     888    ");
		System.out.println();
		System.out.println();
		System.out.println("-INTRODUCTION-");
		System.out.println(
				"The U.S. government has hired you as a secret agent to infiltrate the enemy base and steal a briefcase filled");
		System.out.println(
				"with intel of a planned Russian nuclear launch against the U.S. Several pieces of intel have been scattered");
		System.out.println(
				"throughout different briefcases, each highly guarded by ninjas several ninjas hired by the Russian government.");
		System.out.println(
				"The U.S. government has agreed to pay you in large sums to complete the task. Each base is pitch black and the");
		System.out.println(
				"enemy has been equipped with high quality night vision goggles. Unfortunately, the U.S. government has been a");
		System.out.println(
				"little short on money recently and can only provide you with defective night vision goggles. Nevertheless, you");
		System.out.println("decide to accept the mission anyway, simply because you're just absolutely broke.");
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
		System.out.println();
		System.out.println("-BASICS-");
		System.out.println(
				"Each turn, you will have three options: look, move, and shoot. Looking allows you to detect ninjas within two");
		System.out.println(
				"tiles in a single direction. Looking does not end your turn, but can only be performed once a turn. Shooting will");
		System.out.println(
				"fire your gun in a chosen direction, killing the first ninja struck. You will start with one bullet, and can only");
		System.out.println(
				"hold one. There are nine rooms to check, one of which has the briefcase in it. To check a room, move into it from");
		System.out.println(
				"the north side. If it contains the briefcase, you will win the level. Your turn will end after you have either");
		System.out.println(
				"moved or shot. After your turn, all of the ninjas will move around. If you ended your turn next to a ninja, they");
		System.out.println(
				"will catch you, and you will lose a life. If you run out of lives, you lose the game. If you find the briefcase,");
		System.out.println(
				"you have the option to move on to the next level. Just a little something extra, in each level, there may be");
		System.out.println(
				"items that can help you along the way. In addition, HQ has decided to be generous and said that they will give");
		System.out.println(
				"you an extra health pack every time you move move onto the next mission (+1 health when you enter a new level).");
		System.out.println("The following are tiles you may encounter.");
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
		System.out.println();
		System.out.println("-TILES-");
		System.out.println("[@] - Player");
		System.out.println("[R] - Room");
		System.out.println("[X] - Checked Room");
		System.out.println("[B] - Briefcase Room (only visible with radar or in debug mode)");
		System.out.println("[N] - Ninja (only visible in debug mode)");
		System.out.println("[!] - Extra Magazine (only visible in debug mode)");
		System.out.println("[*] - Invincibility (only visible in debug mode)");
		System.out.println("[%] - Radar (only visible in debug mode)");
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
		System.out.println();
		System.out.println("-GOAL-");
		System.out.println(
				"The goal of the game is to find the briefcase and complete each level. Try to find as many briefcases as you can");
		System.out.println(
				"because you will be scored based on your performance and several other criterias including: the number of rooms");
		System.out.println(
				"checked, the number of enemies killed, and the number of items picked up. You will also score higher if you");
		System.out.println("defeat levels in less number of turns.");
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
		System.out.println();
		System.out.println("-RECORDS/ACHIEVEMENTS-");
		System.out.println(
				"You may be able to earn some ACTUAL dollars. Well, the government can't really give you any money personally, but");
		System.out.println(
				"they can give you some in-game currency. If you get some achievements worth noting, perhaps the government will");
		System.out.println(
				"give you some extra bucks. And who doesn't feel accomplished when they see the list of achievements that they've");
		System.out.println(
				"accomplished that's actually worth noting? If not, at least your accomplishments will be recorded for your own");
		System.out
				.println("personal viewing. (Note: Records/Achievements are only recorded/attained after a game ends)");
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
		System.out.println();
		System.out.println("-STORE-");
		System.out.println(
				"Now that you can earn some extra cash from getting achievements, why not go spend it on some more useless things?");
		System.out.println(
				"Well, the items in this store wouldn't necessarily be considered useless. You'll find some useful items in here.");
		System.out.println(
				"Just take some time and go browse the store. It's better than browsing on Amazon, I promise.");
		System.out.println();
		System.out.println("Good luck and have fun!");
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
	}

	/**
	 * This method prints the shop according to the parameters taken from the
	 * {@link Shop} through the {@link GameEngine}.
	 * 
	 * @param money
	 *            the amount of money the player currently has
	 * @param vision
	 *            the player's current vision upgrade level
	 * @param magazine
	 *            the player's current magazine upgrade level
	 * @param health
	 *            the player's current health magazine upgrade level
	 * @param visionCost
	 *            the cost to buy the next vision upgrade
	 * @param magazineCost
	 *            the cost to buy the next magazine upgrade
	 * @param healthCost
	 *            the cost to buy the next health upgrade
	 * @param godMode
	 *            {@code true} if the player has obtained God mode,
	 *            {@code false} otherwise
	 * @return the type of upgrade bought, if any, in order to be acted upon in
	 *         the {@link GameEngine}
	 */
	public Shop.upgrades printShop(int money, int vision, int magazine, int health, int visionCost, int magazineCost,
			int healthCost, boolean godMode) {
		System.out.println();
		System.out.println();
		System.out.println("                                .d8888b.  88888888888  .d88888b.  8888888b.  8888888888");
		System.out.println("                               d88P  Y88b     888     d88P\" \"Y88b 888   Y88b 888       ");
		System.out.println("                               Y88b.          888     888     888 888    888 888       ");
		System.out.println("                                \"Y888b.       888     888     888 888   d88P 8888888   ");
		System.out.println("                                   \"Y88b.     888     888     888 8888888P\"  888       ");
		System.out.println("                                     \"888     888     888     888 888 T88b   888       ");
		System.out.println("                               Y88b  d88P     888     Y88b. .d88P 888  T88b  888       ");
		System.out
				.println("                                \"Y8888P\"      888      \"Y88888P\"  888   T88b 8888888888");
		System.out.println();
		System.out.println();
		System.out.printf("                                                   Money: $%-7d\n", money);
		System.out.println();
		System.out.println(
				"======================================================================================================================");
		System.out.println(
				"|             |                                       |                  |                     |                     |");
		System.out.println(
				"|   Upgrade   |             Description               |   Current Stat   |   Bonus per level   |   Cost to Upgrade   |");
		System.out.println(
				"|             |                                       |                  |                     |                     |");
		System.out.println(
				"|-------------|---------------------------------------|------------------|---------------------|---------------------|");
		System.out.println(
				"|             |                                       |                  |                     |                     |");
		System.out.printf(
				"|   Vision    | Purchase better night vision goggles! | %1d Tiles in front | +1 Distance to look |        $%-4d        |\n",
				(vision + 1), visionCost);
		System.out.println(
				"|             |                                       |                  |                     |                     |");
		System.out.println(
				"|-------------|---------------------------------------|------------------|---------------------|---------------------|");
		System.out.println(
				"|             |                                       |                  |                     |                     |");
		System.out.printf(
				"|Magazine Size|  Store more than 1 bullet, amazing!   |   %1d Bullet(s)    |  +1 Ammo capacity   |        $%-4d        |\n",
				magazine, magazineCost);
		System.out.println(
				"|             |                                       |                  |                     |                     |");
		System.out.println(
				"|-------------|---------------------------------------|------------------|---------------------|---------------------|");
		System.out.println(
				"|             |                                       |                  |                     |                     |");
		System.out.printf(
				"| Health Pack |Donate to the government and be repaid!|%1d Health per level| +1 Health per level |        $%-4d        |\n",
				health, healthCost);
		System.out.println(
				"|             |                                       |                  |                     |                     |");
		if (!godMode) {
			System.out.println(
					"|-------------|---------------------------------------|------------------|---------------------|---------------------|");
			System.out.println(
					"|             |                                       |                  |                     |                     |");
			System.out.println(
					"|  God Mode   |  Tired of dying? Then get this mode!  |   Not Obtained   |  Obtain game mode   |        $4000        |");
			System.out.println(
					"|             |                                       |                  |                     |                     |");
		} else {
			System.out.println(
					"|-------------|---------------------------------------|------------------|---------------------|---------------------|");
			System.out.println(
					"|             |                                       |                  |                     |                     |");
			System.out.println(
					"|  God Mode   |  Tired of dying? Then get this mode!  |     Obtained     |  Obtain game mode   |        $0           |");
			System.out.println(
					"|             |                                       |                  |                     |                     |");
		}
		System.out.println(
				"======================================================================================================================");
		System.out.println();
		System.out.println();
		while (true) {
			System.out.println("Would you like to buy something?");
			System.out.println("[1] - Upgrade vision");
			System.out.println("[2] - Upgrade magazine size");
			System.out.println("[3] - Upgrade health pack");
			System.out.println("[4] - Buy God mode");
			System.out.println("[5] - Nothing, just window shopping");
			System.out.println();
			input = sc.nextLine();
			System.out.println();

			if (input.equals("1") && vision >= 5) {
				System.out.println("You can't upgrade this anymore!");
				System.out.println();
				System.out.println("Press ENTER to continue...");
				sc.nextLine();
			} else if (input.equals("1") && (money > visionCost)) {
				System.out.println("Thank you, come again!");
				System.out.println();
				System.out.println("Press ENTER to continue...");
				sc.nextLine();
				return Shop.upgrades.VISION;
			} else if (input.equals("1") && (money < visionCost)) {
				System.out.println("You don't have enough money!");
				System.out.println();
				System.out.println("Press ENTER to continue...");
				sc.nextLine();
			} else if (input.equals("2") && magazine >= 5) {
				System.out.println("You can't upgrade this anymore!");
				System.out.println();
				System.out.println("Press ENTER to continue...");
				sc.nextLine();
			} else if (input.equals("2") && (money > magazineCost)) {
				System.out.println("Thank you, come again!");
				System.out.println();
				System.out.println("Press ENTER to continue...");
				sc.nextLine();
				return Shop.upgrades.MAGAZINE;
			} else if (input.equals("2") && (money < magazineCost)) {
				System.out.println("You don't have enough money!");
				System.out.println();
				System.out.println("Press ENTER to continue...");
				sc.nextLine();
			} else if (input.equals("3") && health >= 5) {
				System.out.println("You can't upgrade this anymore!");
				System.out.println();
				System.out.println("Press ENTER to continue...");
				sc.nextLine();
			} else if (input.equals("3") && (money > healthCost)) {
				System.out.println("Thank you, come again!");
				System.out.println();
				System.out.println("Press ENTER to continue...");
				sc.nextLine();
				return Shop.upgrades.HEALTH;
			} else if (input.equals("3") && (money < healthCost)) {
				System.out.println("You don't have enough money!");
				System.out.println();
				System.out.println("Press ENTER to continue...");
				sc.nextLine();
			} else if (input.equals("4") && godMode) {
				System.out.println("You've already obtained God mode!");
				System.out.println();
				System.out.println("Press ENTER to continue...");
				sc.nextLine();
			} else if (input.equals("4") && (money > 4000)) {
				System.out.println("Thank you, come again!");
				System.out.println();
				System.out.println("Press ENTER to continue...");
				sc.nextLine();
				return Shop.upgrades.GODMODE;
			} else if (input.equals("4") && (money < 4000)) {
				System.out.println("You don't have enough money!");
				System.out.println();
				System.out.println("Press ENTER to continue...");
				sc.nextLine();
			} else if (input.equals("5")) {
				System.out.println("You're not buying anything?! Then, get out of here, you bugger!");
				System.out.println();
				System.out.println("Press ENTER to continue...");
				sc.nextLine();
				return null;
			} else {
				System.out.println("Invalid input.\n");
			}
		}
	}

	/**
	 * This method prints the list of highscores and corresponding names based
	 * on the highscore array parameter passed from the {@link GameEngine}.
	 * 
	 * @param highscores
	 *            the list of highscores to be printed
	 */
	public void printHighscores(Highscore[] highscores) {
		System.out.println();
		System.out.println();
		System.out.println(
				"===============================================================================================================");
		System.out.println(
				"|                                                                                                             |");
		System.out.println(
				"| 888    888 8888888  .d8888b.  888    888  .d8888b.   .d8888b.   .d88888b.  8888888b.  8888888888  .d8888b.  |");
		System.out.println(
				"| 888    888   888   d88P  Y88b 888    888 d88P  Y88b d88P  Y88b d88P\" \"Y88b 888   Y88b 888        d88P  Y88b |");
		System.out.println(
				"| 888    888   888   888    888 888    888 Y88b.      888    888 888     888 888    888 888        Y88b.      |");
		System.out.println(
				"| 8888888888   888   888        8888888888  \"Y888b.   888        888     888 888   d88P 8888888     \"Y888b.   |");
		System.out.println(
				"| 888    888   888   888  88888 888    888     \"Y88b. 888        888     888 8888888P\"  888            \"Y88b. |");
		System.out.println(
				"| 888    888   888   888    888 888    888       \"888 888    888 888     888 888 T88b   888              \"888 |");
		System.out.println(
				"| 888    888   888   Y88b  d88P 888    888 Y88b  d88P Y88b  d88P Y88b. .d88P 888  T88b  888        Y88b  d88P |");
		System.out.println(
				"| 888    888 8888888  \"Y8888P88 888    888  \"Y8888P\"   \"Y8888P\"   \"Y88888P\"  888   T88b 8888888888  \"Y8888P\"  |");
		System.out.println(
				"|                                                                                                             |");
		System.out.println(
				"|                                                                                                             |");
		for (int i = 0; i < highscores.length; ++i) {
			System.out.println(
					"|                                                                                                             |");
			System.out.println(
					"|                                                                                                             |");
			System.out.printf("|               %-30s                               %8d                         |\n",
					highscores[i].getName(), highscores[i].getScore());
		}
		System.out.println(
				"|                                                                                                             |");
		System.out.println(
				"===============================================================================================================");
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
	}

	/**
	 * This method prints the player's achievements, using the boolean array of
	 * achievements passed from the {@link GameEngine} as a guideline of which
	 * achievements have been obtained.
	 * 
	 * @param achievements
	 *            an array of booleans that represents whether each achievement
	 *            in the list of achievements has or has not been obtained
	 */
	public void printAchievements(boolean[] achievements) {
		System.out.println();
		System.out.println();
		System.out.println(
				"       d8888  .d8888b.  888    888 8888888 8888888888 888     888 8888888888 888b     d888 8888888888 888b    888 88888888888  .d8888b. ");
		System.out.println(
				"      d88888 d88P  Y88b 888    888   888   888        888     888 888        8888b   d8888 888        8888b   888     888     d88P  Y88b");
		System.out.println(
				"     d88P888 888    888 888    888   888   888        888     888 888        88888b.d88888 888        88888b  888     888     Y88b.     ");
		System.out.println(
				"    d88P 888 888        8888888888   888   8888888    Y88b   d88P 8888888    888Y88888P888 8888888    888Y88b 888     888      \"Y888b.  ");
		System.out.println(
				"   d88P  888 888        888    888   888   888         Y88b d88P  888        888 Y888P 888 888        888 Y88b888     888         \"Y88b.");
		System.out.println(
				"  d88P   888 888    888 888    888   888   888          Y88o88P   888        888  Y8P  888 888        888  Y88888     888           \"888");
		System.out.println(
				" d8888888888 Y88b  d88P 888    888   888   888           Y888P    888        888   \"   888 888        888   Y8888     888     Y88b  d88P");
		System.out.println(
				"d88P     888  \"Y8888P\"  888    888 8888888 8888888888     Y8P     8888888888 888       888 8888888888 888    Y888     888      \"Y8888P\" ");
		System.out.println();
		System.out.println();
		System.out.println(
				"========================================================================================================================================");
		System.out.println(
				"|                               |                                                                          |            |              |");
		System.out.println(
				"|          Achievement          |                               Description                                |   Reward   |    Status    |");
		System.out.println(
				"|                               |                                                                          |            |              |");
		if (achievements[0]) {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|         Just a Casual         |                    Clear 1 level in normal game mode.                    |    $100    |   Achieved   |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		} else {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|         Just a Casual         |                                   ???                                    |    $100    |              |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		}
		if (achievements[1]) {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|     Better Than a Casual      |                   Clear 10 levels in normal game mode.                   |    $200    |   Achieved   |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		} else {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|     Better Than a Casual      |                                   ???                                    |    $200    |              |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		}
		if (achievements[2]) {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|   Much Better Than a Casual   |                   Clear 50 levels in normal game mode.                   |    $500    |   Achieved   |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		} else {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|   Much Better Than a Casual   |                                   ???                                    |    $500    |              |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		}
		if (achievements[3]) {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|          Casual King          |                   Clear 100 levels in normal game mode.                  |    $1000   |   Achieved   |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		} else {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|          Casual King          |                                   ???                                    |    $1000   |              |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		}
		if (achievements[4]) {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|      No More Mr. Casual       |                     Clear 1 level in hard game mode.                     |    $100    |   Achieved   |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		} else {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|      No More Mr. Casual       |                                   ???                                    |    $100    |              |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		}
		if (achievements[5]) {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|    Slightly More Hardcore     |                    Clear 10 levels in hard game mode.                    |    $200    |   Achieved   |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		} else {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|    Slightly More Hardcore     |                                   ???                                    |    $200    |              |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		}
		if (achievements[6]) {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|       Hardcore Hardcore       |                    Clear 50 levels in hard game mode.                    |    $500    |   Achieved   |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		} else {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|       Hardcore Hardcore       |                                   ???                                    |    $500    |              |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		}
		if (achievements[7]) {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|         Hardcore King         |                    Clear 100 levels in hard game mode.                   |    $1000   |   Achieved   |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		} else {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|         Hardcore King         |                                   ???                                    |    $1000   |              |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		}
		if (achievements[8]) {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|        I Opened a Door        |                      Check 1 room in any game mode.                      |    $100    |   Achieved   |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		} else {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|        I Opened a Door        |                                   ???                                    |    $100    |              |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		}
		if (achievements[9]) {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|   I Didn't Sign Up for This   |                     Check 10 rooms in any game mode.                     |    $200    |   Achieved   |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		} else {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|   I Didn't Sign Up for This   |                                   ???                                    |    $200    |              |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		}
		if (achievements[10]) {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|     Can't Find Briefcases     |                     Check 50 rooms in any game mode.                     |    $500    |   Achieved   |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		} else {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|     Can't Find Briefcases     |                                   ???                                    |    $500    |              |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		}
		if (achievements[11]) {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|      Christmas Caroling       |                     Check 100 rooms in any game mode.                    |    $1000   |   Achieved   |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		} else {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|      Christmas Caroling       |                                   ???                                    |    $1000   |              |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		}
		if (achievements[12]) {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|        Casual Shopper         |                     Pick up 1 item in any game mode.                     |    $100    |   Achieved   |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		} else {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|        Casual Shopper         |                                   ???                                    |    $100    |              |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		}
		if (achievements[13]) {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|         Lucky Pennies         |                    Pick up 10 items in any game mode.                    |    $200    |   Achieved   |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		} else {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|         Lucky Pennies         |                                   ???                                    |    $200    |              |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		}
		if (achievements[14]) {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|          Shopahollic          |                    Pick up 50 items in any game mode.                    |    $500    |   Achieved   |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		} else {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|          Shopahollic          |                                   ???                                    |    $500    |              |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		}
		if (achievements[15]) {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|        Metal Detector?        |                    Pick up 100 items in any game mode.                   |    $1000   |   Achieved   |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		} else {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|        Metal Detector?        |                                   ???                                    |    $1000   |              |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		}
		if (achievements[16]) {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|          First Blood          |                      Kill 1 enemy in any game mode.                      |    $100    |   Achieved   |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		} else {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|          First Blood          |                                   ???                                    |    $100    |              |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		}
		if (achievements[17]) {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|          PTSD Begins          |                    Kill 10 enemies in any game mode.                     |    $200    |   Achieved   |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		} else {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|          PTSD Begins          |                                   ???                                    |    $200    |              |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		}
		if (achievements[18]) {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|       Ninja the Ninjas        |                    Kill 50 enemies in any game mode.                     |    $500    |   Achieved   |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		} else {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|       Ninja the Ninjas        |                                   ???                                    |    $500    |              |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		}
		if (achievements[19]) {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|           No Mercy            |                    Kill 100 enemies in any game mode.                    |    $1000   |   Achieved   |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		} else {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|           No Mercy            |                                   ???                                    |    $1000   |              |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		}
		if (achievements[20]) {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|              ???              |               You're a cheater, what else is there to say?               |   $99999   |   Achieved   |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		} else {
			System.out.println(
					"|-------------------------------|--------------------------------------------------------------------------|------------|--------------|");
			System.out.println(
					"|                               |                                                                          |            |              |");
			System.out.println(
					"|              ???              |                                   ???                                    |    ???     |              |");
			System.out.println(
					"|                               |                                                                          |            |              |");
		}
		System.out.println(
				"========================================================================================================================================");
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
	}

	/**
	 * This method prints all of the player's records based on the array of
	 * integers corresponding to each record sent as an array to this method.
	 * 
	 * @param records
	 *            an array of ints that represent each player record noted in
	 *            the {@link Record} class
	 */
	public void printRecords(int[] records) {
		System.out.println();
		System.out.println();
		System.out.println("8888888b.  8888888888  .d8888b.   .d88888b.  8888888b.  8888888b.   .d8888b. ");
		System.out.println("888   Y88b 888        d88P  Y88b d88P\" \"Y88b 888   Y88b 888  \"Y88b d88P  Y88b");
		System.out.println("888    888 888        888    888 888     888 888    888 888    888 Y88b.     ");
		System.out.println("888   d88P 8888888    888        888     888 888   d88P 888    888  \"Y888b.  ");
		System.out.println("8888888P\"  888        888        888     888 8888888P\"  888    888     \"Y88b.");
		System.out.println("888 T88b   888        888    888 888     888 888 T88b   888    888       \"888");
		System.out.println("888  T88b  888        Y88b  d88P Y88b. .d88P 888  T88b  888  .d88P Y88b  d88P");
		System.out.println("888   T88b 8888888888  \"Y8888P\"   \"Y88888P\"  888   T88b 8888888P\"   \"Y8888P\" ");
		System.out.println();
		System.out.println();
		System.out.println("=============================================================================");
		System.out.println("|                                                                           |");
		System.out.printf("|     Highest Normal Level Reached:                               %5d     |\n", records[0]);
		System.out.println("|                                                                           |");
		System.out.printf("|     Highest Hard Level Reached:                                 %5d     |\n", records[1]);
		System.out.println("|                                                                           |");
		System.out.printf("|     Most Lives Remaining in a Single Gameplay:                  %5d     |\n", records[2]);
		System.out.println("|                                                                           |");
		System.out.printf("|     Most Turns Survived in a Single Gameplay:                   %5d     |\n", records[3]);
		System.out.println("|                                                                           |");
		System.out.printf("|     Most Rooms Checked in a Single Gameplay:                    %5d     |\n", records[4]);
		System.out.println("|                                                                           |");
		System.out.printf("|     Most Items Obtained in a Single Gameplay:                   %5d     |\n", records[5]);
		System.out.println("|                                                                           |");
		System.out.printf("|     Most Enemies Killed in a Single Gameplay:                   %5d     |\n", records[6]);
		System.out.println("|                                                                           |");
		System.out.printf("|     Overall Normal Levels Cleared:                              %5d     |\n", records[7]);
		System.out.println("|                                                                           |");
		System.out.printf("|     Overall Hard Levels Cleared:                                %5d     |\n", records[8]);
		System.out.println("|                                                                           |");
		System.out.printf("|     Overall Rooms Checked:                                      %5d     |\n", records[9]);
		System.out.println("|                                                                           |");
		System.out.printf("|     Overall Items Obtained:                                     %5d     |\n", records[10]);
		System.out.println("|                                                                           |");
		System.out.printf("|     Overall Enemies Killed:                                     %5d     |\n", records[11]);
		System.out.println("|                                                                           |");
		System.out.println("=============================================================================");
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
	}

	/**
	 * This method prints the player's status corresponding to the current
	 * situation using the parameters given.
	 * 
	 * @param level
	 *            the level the player is currently on
	 * @param lives
	 *            the player's current lives
	 * @param ammo
	 *            the player's current ammo
	 * @param maxAmmo
	 *            the max ammo the player can have
	 */
	public void printStats(int level, int lives, int ammo, int maxAmmo) {
		System.out.println();
		System.out.printf("Level %d\n", level);
		System.out.printf("Lives: %d\n", lives);
		System.out.printf("Ammo: %d/%d\n", ammo, maxAmmo);
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
	 *            the map of entities
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
	 *            {@code true} if the game is in debug mode, {@code false}
	 *            otherwise
	 */
	public void printLegend(boolean debug) {
		System.out.println("[@] - Player");
		System.out.println("[R] - Room");
		System.out.println("[X] - Checked Room");
		if (debug) {
			System.out.println("[B] - Briefcase Room (only visible in debug mode)");
			System.out.println("[N] - Ninja (only visible in debug mode)");
			System.out.println("[!] - Extra Magazine (only visible in debug mode)");
			System.out.println("[*] - Invincibility (only visible in debug mode)");
			System.out.println("[%] - Radar (only visible in debug mode)");
		} else
			System.out.println("[B] - Briefcase Room (only visible with radar)");

		System.out.println();
	}

	/**
	 * This method will query the player for input on what action they wish to
	 * take. It will then interpret their input and return one of the five
	 * possible action types listed in the {@link action} enumeration. If their
	 * input is invalid, a proper message will be printed and the player will be
	 * queried again.
	 * 
	 * @param looked
	 *            {@code true} if the player has already looked this turn,
	 *            {@code false} otherwise
	 * @param hasBullet
	 *            {@code true} if the player still has ammo, {@code false}
	 *            otherwise
	 * @return one of five possible action types listed in the {@link action}
	 *         enumeration
	 */
	public action readAction(boolean looked, boolean hasBullet) {
		boolean running = true;
		action result = null;

		while (running) {
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
				if (!looked) {
					result = action.LOOK;
					running = false;
				} else {
					System.out.println("You already looked this turn.\n");
					System.out.println();
					System.out.println("Press ENTER to continue...");
					sc.nextLine();
					continue;
				}
			}

			else if (input.equals("2")) {
				result = action.MOVE;
				running = false;
			}

			else if (input.equals("3")) {
				if (hasBullet) {
					result = action.SHOOT;
					running = false;
				} else {
					System.out.println("You cannot shoot without a bullet.\n");
					System.out.println();
					System.out.println("Press ENTER to continue...");
					sc.nextLine();
					continue;
				}
			}

			else if (input.equals("4")) {
				if (!looked) {
					result = action.SAVE;
					running = false;
				} else {
					System.out.println("You can only save at the start of your turn.\n");
					System.out.println();
					System.out.println("Press ENTER to continue...");
					sc.nextLine();
					continue;
				}
			}

			else if (input.equalsIgnoreCase("5")) {
				boolean stillPlaying = true;
				while (stillPlaying) {
					System.out.println("Are you sure you want to quit? (Y/N)");
					System.out.println();
					input = sc.nextLine();
					System.out.println();

					if (input.equalsIgnoreCase("y")) {
						result = action.QUIT;
						running = false;
						stillPlaying = false;
					} else if (input.equalsIgnoreCase("n"))
						stillPlaying = false;

					if (stillPlaying)
						System.out.println("Invalid input.\n");
				}

				continue;
			}

			if (running)
				System.out.println("Invalid input.\n");
		}

		return result;
	}

	/**
	 * This method will print the results of looking based on the parameter
	 * passed. If a ninja was found within the limits of the player's vision in
	 * the direction that the player looked, then a value greater than 0 will be
	 * passed as the parameter to indicate the distance between the ninja and
	 * the player, in which case a proper message will be printed. On the other
	 * hand, if the player looks and the first thing that's found is a room or a
	 * wall or if the player cannot see anything within their vision limits,
	 * then 0 will be passed as the parameter to indicate that nothing was found
	 * from looking, in which case a proper message will be printed.
	 * 
	 * @param ninjaDistance
	 *            an int that represents the distance between the closest ninja
	 *            within the vision limitations and line of sight to the player
	 */
	public void printLookResults(int ninjaDistance) {
		System.out.print("You concentrated and looked, finding... ");

		if (ninjaDistance != 0) {
			System.out.println("A NINJA!");
			System.out.println();
			System.out.printf("It seems the ninja is approximately %d steps away.\n", ninjaDistance);
		} else
			System.out.println("nothing...");

		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
	}

	/**
	 * This method will query the player for input on what direction they wish
	 * to act towards. It will interpret their input and return one of the four
	 * cardinal directions. If their input is invalid, a proper message will be
	 * printed and the player will be queried again.
	 * 
	 * @return Returns {@code UP}, {@code DOWN}, {@code LEFT}, or {@code RIGHT}.
	 */
	public direction readDirection() {
		boolean running = true;
		direction result = null;

		while (running) {
			System.out.println("In what direction? (W/A/S/D for UP/LEFT/DOWN/RIGHT)");
			System.out.println();
			input = sc.nextLine();
			System.out.println();

			if (input.equalsIgnoreCase("w")) {
				result = direction.UP;
				running = false;
			} else if (input.equalsIgnoreCase("a")) {
				result = direction.LEFT;
				running = false;
			} else if (input.equalsIgnoreCase("s")) {
				result = direction.DOWN;
				running = false;
			} else if (input.equalsIgnoreCase("d")) {
				result = direction.RIGHT;
				running = false;
			}

			if (running)
				System.out.println("Invalid input.\n");
		}

		return result;
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

	/**
	 * This method will tell the player that they could not move because they
	 * attempted to move into a wall.
	 */
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
	 * This method will inform the player that they have just picked up an item.
	 * In addition, it will take the type of the item as a parameter to tell the
	 * player which item was picked up and what resulted from the item.
	 * 
	 * @param type
	 *            the type of the power up that the player picked up
	 * @param hasBullet
	 *            {@code true} if the player has a full magazine, {@code false}
	 *            otherwise
	 */
	public void printPowerUp(Item.itemType type, boolean hasBullet) {
		System.out.print("You picked up ");

		switch (type) {
		case BULLET:
			System.out.println("a magazine!");
			if (hasBullet) {
				System.out.println("You tried to reload your gun, but unfortunately it's full.");
			} else
				System.out.println("You reloaded your gun.");
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

	/**
	 * This method will print all messages regarding invincibility after each
	 * turn passes by while the player still has invincibility.
	 * 
	 * @param turns
	 *            an int that represents the number of turns the player has left
	 *            with invincibility
	 */
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
	 *            the number of lives the player has remaining
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
	 *            {@code true} if the shot hit, {@code false} otherwise
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

	/**
	 * This method will ask the player if they want to move onto the next level
	 * and return the player's answer as a boolean.
	 * 
	 * @return {@code true} if the player chooses to move onto the next level,
	 *         {@code false} otherwise
	 */
	public boolean printNextLevel() {
		System.out.println("HQ has sent you the following message:");
		System.out.println("\"Infiltrator, we have a new mission for you. Your mission,");
		System.out.println("should you choose to accept it, is to infiltrate a more highly");
		System.out.println("guarded compound. If you choose to accept, rendezvous at the");
		System.out.println("specified location.\"");
		System.out.println();

		boolean running = true;
		boolean output = false;
		while (running) {
			System.out.println("Do you accept the mission? (Y/N)");
			System.out.println();
			input = sc.nextLine();
			System.out.println();

			if (input.equalsIgnoreCase("y")) {
				output = true;
				running = false;
			} else if (input.equalsIgnoreCase("n"))
				running = false;

			if (running)
				System.out.println("Invalid input.\n");
		}

		return output;
	}

	/**
	 * This method will inform the player that they were killed by a ninja and
	 * have lost their final life.
	 */
	public void printLoss() {
		System.out.println("You were caught and killed by the ninjas! Too bad!");
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
	}

	/**
	 * This method prints out the level clear screen, which shows the scores
	 * given for each criteria and the total score given for clearing the level.
	 * 
	 * @param turnsScore
	 *            the score given based on the number of turns it took to
	 *            complete the level
	 * @param livesScore
	 *            the score given based on the number of lives the player has
	 *            remaining
	 * @param roomsScore
	 *            the score given based on the number of rooms the player
	 *            checked
	 * @param itemsScore
	 *            the score given based on the number of items the player picked
	 *            up
	 * @param killsScore
	 *            the score given based on the number of kills the player
	 *            obtained
	 * @param levelScore
	 *            the total score given for the level
	 */
	public void printLevelClear(int turnsScore, int livesScore, int roomsScore, int itemsScore, int killsScore,
			int levelScore) {
		System.out.println(
				"==================================================================================================================");
		System.out.println(
				"|                                                                                                                |");
		System.out.println(
				"| 888      8888888888 888     888 8888888888 888           .d8888b.  888      8888888888        d8888 8888888b.  |");
		System.out.println(
				"| 888      888        888     888 888        888          d88P  Y88b 888      888              d88888 888   Y88b |");
		System.out.println(
				"| 888      888        888     888 888        888          888    888 888      888             d88P888 888    888 |");
		System.out.println(
				"| 888      8888888    Y88b   d88P 8888888    888          888        888      8888888        d88P 888 888   d88P |");
		System.out.println(
				"| 888      888         Y88b d88P  888        888          888        888      888           d88P  888 8888888P\"  |");
		System.out.println(
				"| 888      888          Y88o88P   888        888          888    888 888      888          d88P   888 888 T88b   |");
		System.out.println(
				"| 888      888           Y888P    888        888          Y88b  d88P 888      888         d8888888888 888  T88b  |");
		System.out.println(
				"| 88888888 8888888888     Y8P     8888888888 88888888      \"Y8888P\"  88888888 8888888888 d88P     888 888   T88b |");
		System.out.println(
				"|                                                                                                                |");
		System.out.println(
				"|                                                                                                                |");
		System.out.println(
				"|                                                                                                                |");
		System.out.println(
				"|                                                                                                                |");
		System.out.printf(
				"|                   Turns Used                                                   %5d                           |\n",
				turnsScore);
		System.out.println(
				"|                                                                                                                |");
		System.out.println(
				"|                                                                                                                |");
		System.out.printf(
				"|                   Lives Remaining                                              %5d                           |\n",
				livesScore);
		System.out.println(
				"|                                                                                                                |");
		System.out.println(
				"|                                                                                                                |");
		System.out.printf(
				"|                   Rooms Checked                                                %5d                           |\n",
				roomsScore);
		System.out.println(
				"|                                                                                                                |");
		System.out.println(
				"|                                                                                                                |");
		System.out.printf(
				"|                   Items Obtained                                               %5d                           |\n",
				itemsScore);
		System.out.println(
				"|                                                                                                                |");
		System.out.println(
				"|                                                                                                                |");
		System.out.printf(
				"|                   Enemies Killed                                               %5d                           |\n",
				killsScore);
		System.out.println(
				"|                                                                                                                |");
		System.out.println(
				"|                                                                                                                |");
		System.out.println(
				"|                                                                                                                |");
		System.out.println(
				"|                                                                                                                |");
		System.out.printf(
				"|                   Level Score                                                 %08d                         |\n",
				levelScore);
		System.out.println(
				"|                                                                                                                |");
		System.out.println(
				"|                                                                                                                |");
		System.out.println(
				"|                                                                                                                |");
		System.out.println(
				"|                                                                                                                |");
		System.out.println(
				"|                                                   Great Job!                                                   |");
		System.out.println(
				"|                                                                                                                |");
		System.out.println(
				"==================================================================================================================");
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
	}

	/**
	 * This method prints the game over screen, which shows a number of stats
	 * accumulated throughout the single gameplay, the final score obtained in
	 * that gameplay, and the achievements that were unlocked from completing
	 * that gameplay.
	 * 
	 * @param level
	 *            the highest level achieved during the gameplay
	 * @param numOfTurns
	 *            the number of turns survived during the gameplay
	 * @param livesRemaining
	 *            the number of lives remaining during the gameplay
	 * @param roomsChecked
	 *            the number of rooms checked during the gameplay
	 * @param itemPickups
	 *            the number of items picked up during the gameplay
	 * @param enemiesKilled
	 *            the number of enemies killed during the gameplay
	 * @param score
	 *            the final score of the gameplay
	 * @param achievements
	 *            an array of strings containing the list of achievements that
	 *            were unlocked
	 */
	public void printGameOver(int level, int numOfTurns, int livesRemaining, int roomsChecked, int itemPickups,
			int enemiesKilled, int score, String[] achievements) {
		System.out.println(
				"============================================================================================================");
		System.out.println(
				"|                                                                                                          |");
		System.out.println(
				"|     .d8888b.         d8888 888b     d888 8888888888      .d88888b.  888     888 8888888888 8888888b.     |");
		System.out.println(
				"|    d88P  Y88b       d88888 8888b   d8888 888            d88P\" \"Y88b 888     888 888        888   Y88b    |");
		System.out.println(
				"|    888    888      d88P888 88888b.d88888 888            888     888 888     888 888        888    888    |");
		System.out.println(
				"|    888            d88P 888 888Y88888P888 8888888        888     888 Y88b   d88P 8888888    888   d88P    |");
		System.out.println(
				"|    888  88888    d88P  888 888 Y888P 888 888            888     888  Y88b d88P  888        8888888P\"     |");
		System.out.println(
				"|    888    888   d88P   888 888  Y8P  888 888            888     888   Y88o88P   888        888 T88b      |");
		System.out.println(
				"|    Y88b  d88P  d8888888888 888   \"   888 888            Y88b. .d88P    Y888P    888        888  T88b     |");
		System.out.println(
				"|     \"Y8888P88 d88P     888 888       888 8888888888      \"Y88888P\"      Y8P     8888888888 888   T88b    |");
		System.out.println(
				"|                                                                                                          |");
		System.out.println(
				"|                                                                                                          |");
		System.out.println(
				"|                                                                                                          |");
		System.out.println(
				"|                                                                                                          |");
		System.out.printf(
				"|                 Highest Level Reached                                        %2d                          |\n",
				level);
		System.out.println(
				"|                                                                                                          |");
		System.out.println(
				"|                                                                                                          |");
		System.out.printf(
				"|                 Lives Remaining                                              %2d                          |\n",
				livesRemaining);
		System.out.println(
				"|                                                                                                          |");
		System.out.println(
				"|                                                                                                          |");
		System.out.printf(
				"|                 Number of Turns Survived                                    %3d                          |\n",
				numOfTurns);
		System.out.println(
				"|                                                                                                          |");
		System.out.println(
				"|                                                                                                          |");
		System.out.printf(
				"|                 Number of Rooms Checked                                      %2d                          |\n",
				roomsChecked);
		System.out.println(
				"|                                                                                                          |");
		System.out.println(
				"|                                                                                                          |");
		System.out.printf(
				"|                 Number of Items Obtained                                     %2d                          |\n",
				itemPickups);
		System.out.println(
				"|                                                                                                          |");
		System.out.println(
				"|                                                                                                          |");
		System.out.printf(
				"|                 Number of Enemies Killed                                     %2d                          |\n",
				enemiesKilled);
		System.out.println(
				"|                                                                                                          |");
		System.out.println(
				"|                                                                                                          |");
		System.out.println(
				"|                                                                                                          |");
		System.out.println(
				"|                                                                                                          |");
		System.out.printf(
				"|                 Final Score                                               %08d                       |\n",
				score);
		System.out.println(
				"|                                                                                                          |");
		System.out.println(
				"|                                                                                                          |");
		System.out.println(
				"|                                                                                                          |");
		System.out.println(
				"|                                                                                                          |");
		if (achievements.length != 0) {
			System.out.println(
					"|                 Achievements Unlocked:                                                                   |");
			System.out.println(
					"|                                                                                                          |");
			for (int i = 0; i < achievements.length; ++i) {
				System.out.printf(
						"|                    %-25s                                                             |\n",
						achievements[i]);
				System.out.println(
						"|                                                                                                          |");
			}
		}
		System.out.println(
				"|                                          Better luck next time!                                          |");
		System.out.println(
				"|                                                                                                          |");
		System.out.println(
				"============================================================================================================");
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
	}

	/**
	 * This method prompts the user to enter their name in order to be entered
	 * into the highscore system.
	 * 
	 * @return the String correlating to the player's name
	 */
	public String askName() {
		boolean running = true;
		String name = null;
		while (running) {
			System.out.println("Please enter your name (maximum 30 characters):");
			System.out.println();
			name = sc.nextLine();
			System.out.println();

			while (name.length() > 0 && name.length() <= 30) {
				System.out.println("Would you like to record your score under this name? (Y/N)");
				System.out.println();
				input = sc.nextLine();
				System.out.println();

				if (input.equalsIgnoreCase("y")) {
					running = false;
					break;
				} else if (input.equalsIgnoreCase("n"))
					break;
				else
					System.out.println("Invalid input.\n");
			}

			if (running)
				System.out.println("Invalid input.\n");
		}

		return name;
	}

	/**
	 * This method prints the proper message that the player's score will not be
	 * saved, nor recorded, if they are playing in God mode.
	 */
	public void printNoHighscore() {
		System.out.println("You are playing on God Mode. Your score will not be saved.");
		System.out.println();
		System.out.println("Press ENTER to continue...");
		sc.nextLine();
	}

	/**
	 * This method prints a series of messages prompting the player whether or
	 * not they would like to reset the status of the game and returns a proper
	 * string value when the player wishes to reset the game.
	 * 
	 * @return a string of either "reset" to indicate that the player wishes to
	 *         reset the game, or {@code null} if the player does not want to
	 *         reset the game
	 */
	public String printReset() {
		boolean running = true;
		String output = null;
		while (running) {
			System.out.println("Are you sure you want to reset your game status? (Y/N)");
			System.out.println();
			input = sc.nextLine();
			System.out.println();

			if (input.equalsIgnoreCase("y")) {
				boolean stillRunning = true;

				while (stillRunning) {
					System.out.println("This will reset all of your purchases, achievements, records, and highscores.");
					System.out.println("Are you sure you want to reset? (Y/N)");
					System.out.println();
					input = sc.nextLine();
					System.out.println();

					if (input.equalsIgnoreCase("y")) {
						System.out.println("The game has been reset.");
						System.out.println();
						System.out.println("Press ENTER to continue...");
						sc.nextLine();
						output = "reset";
						running = false;
						stillRunning = false;
					} else if (input.equalsIgnoreCase("n")) {
						running = false;
						stillRunning = false;
					}

					if (stillRunning)
						System.out.println("Invalid input.\n");
				}
			} else if (input.equalsIgnoreCase("n"))
				running = false;

			if (running)
				System.out.println("Invalid input.\n");
		}

		return output;
	}

	/**
	 * This method prompts the user to enter any arbitrary string. If the string
	 * is the correct string corresponding to a correct cheat code, then this
	 * method will return a proper string value.
	 * 
	 * @param cheat
	 *            {@code true} if the player has already entered in a cheat code
	 *            (checked in the {@link Achievements} through the
	 *            {@link GameEngine}), {@code false} otherwise
	 * @return a string of either "cheat" to indicate a proper cheat code has
	 *         been entered, or {@code null} if the string entered does not
	 *         match any of the cheat codes
	 */
	public String printCheat(boolean cheat) {
		String output = null;

		if (cheat) {
			System.out.println("You've already entered a cheat code. Stop being greedy.");
			System.out.println();
			System.out.println("Press ENTER to continue...");
			sc.nextLine();
		} else {
			System.out.println("?????");
			System.out.println();
			input = sc.nextLine();
			System.out.println();
			if (input.equalsIgnoreCase("Professor Rodriguez is the best.")) {
				System.out.println("How did you know? Well, regardless Professor Rodriguez is definitely the best");
				System.out.println("professor. Just for that, I guess I'd better give you some money then, since");
				System.out.println("that's what you wanted, right?");
				System.out.println();
				System.out.println("You receieved $99999.");
				System.out.println();
				System.out.println("Press ENTER to continue...");
				sc.nextLine();
				output = "cheat";
			} else if (input.equalsIgnoreCase("This game belongs in the Recycle Bin.")) {
				System.out.println("Blasphemy! How could you possibly say that this game belongs in the Recycle");
				System.out.println("Bin? You do realize I have control over your progress in the game right? If");
				System.out.println("I wasn't forced to allow you to play this game, I would have deleted all of");
				System.out.println("your progress already. In fact, this game is probably the best game you'll");
				System.out.println("ever play. Be grateful! Since you did mention the developer's name and since");
				System.out.println("I'm an extremely nice game, I'll reward you with some money.");
				System.out.println();
				System.out.println("You receieved $99999.");
				System.out.println();
				System.out.println("Press ENTER to continue...");
				sc.nextLine();
				output = "cheat";
			} else {
				System.out.println("Turn back, you don't know what you're doing.");
				System.out.println();
				System.out.println("Press ENTER to continue...");
				sc.nextLine();
			}
		}

		return output;
	}

	/**
	 * This method will ask the player where to save their save file and what
	 * they would like to name it.
	 * 
	 * @return the path of the file to be saved
	 */
	public String querySave() {
		boolean running = true;
		String output = null;

		while (running) {
			String path;

			System.out.println("Please name your save file.");
			System.out.println();
			path = "resources" + GameEngine.fileSep + "saves" + GameEngine.fileSep + sc.nextLine() + ".dat";
			System.out.println();

			if (new File(path).exists()) {
				boolean stillRunning = true;
				while (stillRunning) {
					System.out.println("Save file already exists, would you like to overwrite the save file? (Y/N)");
					System.out.println();
					input = sc.nextLine();
					System.out.println();

					if (input.equalsIgnoreCase("y")) {
						output = path;
						running = false;
						stillRunning = false;
					} else if (input.equalsIgnoreCase("n")) {
						stillRunning = false;
					}
				}
			} else {
				output = path;
				running = false;
			}
		}

		return output;
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
	 * This method will print a list of available load files and ask the player
	 * to select an available file to load.
	 * 
	 * @return the path of the file to be loaded from
	 */
	public String queryLoad() {
		String path = null;
		String[] dir = new File("resources" + GameEngine.fileSep + "saves").list();

		if (dir.length == 0) {
			System.out.println("You have no saved files.");
			System.out.println();
			System.out.println("Press ENTER to continue...");
			sc.nextLine();
		} else {
			while (path == null) {

				System.out.println("Here is a list of your saved files:\n");
				for (int i = 0; i < dir.length; ++i) {
					System.out.print("(" + (i + 1) + ") ");
					System.out.println(dir[i].replace(".dat", ""));
					System.out.println();
				}

				System.out.println("Please input the name of the save file you would like to load.");
				System.out.println();
				path = "resources" + GameEngine.fileSep + "saves" + GameEngine.fileSep + sc.nextLine() + ".dat";
				System.out.println();
				if (!new File(path).exists()) {
					path = null;
					System.out.println("Please input an existing save file.\n");
				}
			}
		}

		return path;
	}

	/**
	 * This method will tell the player that they have successfully loaded a
	 * save.
	 * 
	 * @param debug
	 *            {@code true} if the save is in debug mode, {@code false}
	 *            otherwise
	 * @param hard
	 *            {@code true} if the save is in hard mode, {@code false}
	 *            otherwise
	 * @param god
	 *            {@code true} if the save is in God mode, {@code false}
	 *            otherwise
	 */
	public void loadSuccess(boolean debug, boolean hard, boolean god) {
		System.out.println("Successfully loaded your save.");
		System.out.println("Just so you remember...");
		if (debug)
			System.out.println("Your game is in debug mode.");

		if (god)
			System.out.println("Your game is in God mode.");
		else if (hard)
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

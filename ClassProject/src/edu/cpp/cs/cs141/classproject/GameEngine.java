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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.util.ArrayList;

/**
 * This class represents the Game Engine, where all game logic are executed.
 * 
 * @author Natanael Ariawan
 * @author David Hau
 * @author Miguel Menjivar
 * @author Aidan Novobilsky
 */
public class GameEngine {
	
	/**
	 * This enumeration represents all of the possible types of extra resource
	 * objects/files used in the game
	 * 
	 * @author Natanael Ariawan
	 * @author David Hau
	 * @author Miguel Menjivar
	 * @author Aidan Novobilsky
	 */
	public static enum resource {
		HIGHSCORES, RECORDS, ACHIEVEMENTS, SHOP
	};
	
	/**
	 * This field represents the {@link UI} the player will interact with.
	 * This field is initialized in the constructor method, {@link #GameEngine(UI)},
	 * and is called continously throughout this class.
	 */
	private UI ui;

	/**
	 * This field represents the game's {@link Map} or board, containing all
	 * {@link Entity} within the game world. A {@link Map} object can be saved
	 * from this field through the method {@link #saveGame(String)} and loaded
	 * to this field through the method {@link #loadGame(String)}.
	 */
	private Map map;
	
	/**
	 * This field represents the set of player {@link #highscores} that have been
	 * saved locally. An array of {@link Highscore} objects can be saved
	 * from this field through the method {@link #saveResource(resource)} and loaded
	 * to this field through the method {@link #loadResource(resource)}.
	 */
	private Highscore[] highscores;
	
	/**
	 * This field represents the set of player {@link #records} that have been
	 * saved locally. A {@link Record} object can be saved from this field through
	 * the method {@link #saveResource(resource)} and loaded to this field through
	 * the method {@link #loadResource(resource)}.
	 */
	private Record records;
	
	/**
	 * This field represents the set of player {@link #achievements} that have been
	 * saved locally. An {@link Achievements} object can be saved from this field
	 * through the method {@link #saveResource(resource)} and loaded to this field
	 * through the method {@link #loadResource(resource)}.
	 */
	private Achievements achievements;
	
	/**
	 * This field represents the player's {@link #shop} that has been saved locally.
	 * A {@link Shop} object can be saved from this field through the method
	 * {@link #saveResource(resource)} and loaded to this field through the method
	 * {@link #loadResource(resource)}.
	 */
	private Shop shop;

	/**
	 * This field represents whether the game is still being played, for example
	 * whether the player has lost, won, or quit the game.
	 */
	private boolean gamePlaying;
	
	/**
	 * This field represents whether or not the player quit to the menu, or rather
	 * whether or not they manually quit the game.
	 */
	private boolean toMenu;
	
	/**
	 * This field represents whether or not the player has decided to proceed to
	 * the next level.
	 */
	private boolean nextLevel;
	
	/**
	 * This field represents the separator character used in the OS to determine
	 * a folder or location.
	 */
	public static String fileSep = FileSystems.getDefault().getSeparator();

	/**
	 * This constructor method initializes the {@link #ui} and {@link #map} and
	 * decides whether to initialize a new local file of {@link #highscores},
	 * {@link #records}, {@link #achievements}, and {@link #shop} or load pre-existing
	 * resource files.
	 * 
	 * @param the game's desired UI
	 */
	public GameEngine(UI ui) {
		this.ui = ui;
		map = new Map();
		if (!new File("resources" + fileSep + "saves").exists())
			new File("resources" + fileSep + "saves").mkdirs();
		if (!new File("resources" + fileSep + "highscores.dat").exists()) {
			highscores = new Highscore[0];
			saveResource(resource.HIGHSCORES);
		}
		else
			loadResource(resource.HIGHSCORES);
		if (!new File("resources" + fileSep + "records.dat").exists()) {
			records = new Record();
			saveResource(resource.RECORDS);
		}
		else
			loadResource(resource.RECORDS);
		if (!new File("resources" + fileSep + "achievements.dat").exists()) {
			achievements = new Achievements();
			saveResource(resource.ACHIEVEMENTS);
		}
		else
			loadResource(resource.ACHIEVEMENTS);
		if (!new File("resources" + fileSep + "shop.dat").exists()) {
			shop = new Shop();
			saveResource(resource.SHOP);
		}
		else
			loadResource(resource.SHOP);
	}

	/**
	 * This method starts the game and creates the main loop of the entire game
	 * which encompasses the {@link UI#mainMenu(Highscore[], boolean[], int[])},
	 * initialization of a new game, loading a saved game, or proceeding to the
	 * next level of the game. This method also prints out all of the text needed
	 * at the end of the game, including the
	 * {@link UI#printGameOver(int, int, int, int, int, int, int, String[])} and
	 * {@link UI#printHighscores(Highscore[])}.
	 */
	public void start() {
		while (true) {
			String path = ui.mainMenu(highscores, achievements.getAchievements(), records.getRecords());
			if (path == null) {
				UI.mode mode = ui.selectMode(shop.getGodMode());
				switch (mode) {
				case DEBUG:
					map.initialize(true, false, false, shop.getMagazine());
					break;
				case NORMAL:
					map.initialize(false, false, false, shop.getMagazine());
					break;
				case HARD:
					map.initialize(false, true, false, shop.getMagazine());
					break;
				case DEBUGHARD:
					map.initialize(true, true, false, shop.getMagazine());
					break;
				case GODMODE:
					map.initialize(false, false, true, shop.getMagazine());
					break;
				}
				gamePlaying = true;
				toMenu = false;
				nextLevel = false;
				map.setMaxAmmo(shop.getMagazine());
			} else if (path.equalsIgnoreCase("store")) {
				Shop.upgrades upgrade = ui.printShop(shop.getMoney(), shop.getVision(), shop.getMagazine(), shop.getHealth(),
						shop.getVisionCost(), shop.getMagazineCost(), shop.getHealthCost(), shop.getGodMode());
				if (upgrade == Shop.upgrades.VISION)
					shop.buyVision();
				else if (upgrade == Shop.upgrades.MAGAZINE)
					shop.buyMagazine();
				else if (upgrade == Shop.upgrades.HEALTH)
					shop.buyHealth();
				else if (upgrade == Shop.upgrades.GODMODE)
					shop.buyGodMode();
				gamePlaying = false;
				toMenu = true;
				nextLevel = false;
				saveResource(resource.SHOP);
			} else if (path.equalsIgnoreCase("reset")) {
				reset();
				gamePlaying = false;
				toMenu = true;
				nextLevel = false;
			} else if (path.equalsIgnoreCase("cheat")) {
				cheat();
				gamePlaying = false;
				toMenu = true;
				nextLevel = false;
			} else {
				if (path.equalsIgnoreCase("no saves"))
					continue;
				else
					map = loadGame(path);
				gamePlaying = true;
				toMenu = false;
				nextLevel = false;
				map.setMaxAmmo(shop.getMagazine());
			}
			gameLoop();
			
			while (nextLevel) {
				map.nextLevel(shop.health);
				gamePlaying = true;
				toMenu = false;
				gameLoop();
			}
			
			if (!toMenu) { //if the player did not quit manually
				ui.printGameOver(map.getLevel(), map.getTotalNumOfTurns(), map.getPlayerLives(), map.getTotalRoomsChecked(),
						map.getTotalItemPickups(), map.getTotalEnemiesKilled(), map.getScore(), updateAchievements());
				if (map.getGodMode())
					ui.printNoHighscore();
				else {
					addHighscore();
					ui.printHighscores(highscores);
				}
			}
		}
	}

	/**
	 * This method goes through the game loop and allows the player to select
	 * options during gameplay. The game loop can be broken through several different
	 * means. When the {@link Enemy} kills the {@link Player} enough times so that
	 * the {@link Player} runs out of lives, the {@link Player} finds the briefcase,
	 * or the player quits to the main menu, the game loop will be broken based on
	 * different criterias and the fields, {@link #toMenu}, {@link #gamePlaying},
	 * and {@link #nextLevel} will be set accordingly.
	 */
	public void gameLoop() {
		boolean hasLooked = false;
		boolean turnEnded = false;

		while (gamePlaying) {
			turnEnded = false;
			hasLooked = false;

			while (!turnEnded) {
				ui.printStats(map.getLevel(), map.getPlayerLives(), map.getAmmo(), map.getMaxAmmo());
				ui.printMap(map.getMap());
				ui.printLegend(map.getDebug());

				UI.action action = ui.readAction(hasLooked, map.getAmmo()>0);
				switch (action) {
				case LOOK:
					ui.printLookResults(map.look(ui.readDirection(), (shop.getVision()+1)));
					hasLooked = true;
					break;
				case MOVE:
					turnEnded = playerMoved();
					break;
				case SHOOT:
					ui.printShoot(map.shoot(ui.readDirection()));
					turnEnded = true;
					break;
				case SAVE:
					saveGame(ui.querySave());
					break;
				case QUIT:
					gamePlaying = false;
					nextLevel = false;
					turnEnded = true;
					toMenu = true;
				}

			}
			
			if (gamePlaying) {
				if (!map.getGodMode() && map.enemyScan()) {
					if (map.getPlayerLives() > 1) {
						map.returnPlayerToStart();
						ui.printPlayerDied(map.getPlayerLives());
					} else {
						ui.printLoss();
						map.tallyScore(false);
						gamePlaying = false;
						nextLevel = false;
						updateRecords(false);
					}
				}
				
				if (gamePlaying) {
					if(map.getHardMode())
						map.moveAI();
					else map.enemyMove();
				}
			}
			
			if (!gamePlaying) {
				while (map.getTurnsInvincible() > 0)
					map.reduceTurnsInvincible();
			}
			
			if (map.getTurnsInvincible() > 0) {
				map.reduceTurnsInvincible();
				ui.printInvincibility(map.getTurnsInvincible());
			}
		}
	}

	/**
	 * This method moves the {@link Player} and resolves the move accordingly,
	 * the result and whether the player will move or not, is determined by this
	 * method.
	 * 
	 * @return {@code true} if the player found the briefcase {@code false}
	 * otherwise
	 */
	public boolean playerMoved() {
		Map.moveResult result = map.movePlayer(ui.readDirection());
		switch (result) {
		case COLLISION:
			ui.printPlayerBumped();
			break;
		case FOUNDBRIEFCASE:
			ui.printVictory();
			map.tallyScore(true);
			ui.printLevelClear(map.getNumOfTurnsScore(), map.getLivesRemainingScore(), map.getRoomsCheckedScore(),
					map.getItemPickupsScore(), map.getEnemiesKilledScore(), map.getLevelScore());
			nextLevel = ui.printNextLevel();
			gamePlaying = false;
			if (!map.getGodMode() && !nextLevel)
				updateRecords(true);
			break;
		case ITEM:
			Item.itemType type = map.getLastItem();
			ui.printPowerUp(type, (map.getAmmo()==map.getMaxAmmo()));
			map.resolveItem(type);
			map.resetLastItem(); // prevents item leakage
			break;
		case ROOMCHECKED:
			ui.printCheckedRoom();
			break;
		case WALL:
			ui.printPlayerBumpedWall();
			return false;
		case MOVED:
			break;
		}

		return true;
	}
	
	/**
	 * This method adds a newly created {@link Highscore} after the player finishes
	 * a game and the Game Over screen is printed. This method adds the the
	 * {@link Highscore} object to the field {@link #highscores} and sorts the
	 * objects using the method, {@link Comparable#compareTo(Object)}, that is
	 * implemented in the {@link Highscore} class.
	 */
	public void addHighscore() {
		Highscore highscore = new Highscore(ui.askName(), map.getScore());
		Highscore[] highscores = new Highscore[this.highscores.length+1];
		
		highscores[0] = highscore;
		for (int i = 1 ; i < highscores.length ; ++i) {
			highscores[i] = this.highscores[i-1];
		}
		
		for (int i = 0 ; i < (highscores.length-1) ; ++i) {
			Highscore tempHighscore = highscores[i];
			if (highscores[i].compareTo(highscores[i+1]) == -1) {
				highscores[i] = highscores[i+1];
				highscores[i+1] = tempHighscore;
			}
		}
		
		this.highscores = highscores;
		saveResource(resource.HIGHSCORES);
	}
	
	/**
	 * This method updates the player records after the player finishes a game Some
	 * records, such as the number of normal levels the player has cleared, will
	 * always be updated, while others, such as the highest normal level reached
	 * in a single gameplay, will only be updated if the number of levels cleared
	 * during that gameplay is higher than the existing record. In addition, this
	 * method will not run if the player is playing on God mode.
	 */
	public void updateRecords(boolean win) {
		if (win && map.getHardMode())
			records.increaseTotalLevelHard(map.getLevel());
		else if (win && !map.getHardMode())
			records.increaseTotalLevelNormal(map.getLevel());
		else if (!win && map.getHardMode())
			records.increaseTotalLevelHard(map.getLevel()-1);
		else
			records.increaseTotalLevelNormal(map.getLevel()-1);
		records.increaseTotalRoomsChecked(map.getTotalRoomsChecked());
		records.increaseTotalEnemiesKilled(map.getTotalEnemiesKilled());
		records.increaseTotalItemsObtained(map.getTotalItemPickups());
		if (win && map.getHardMode() && (map.getLevel() > records.getRecords()[1]))
			records.setHighestLevelHard(map.getLevel());
		else if (win && !map.getHardMode() && (map.getLevel() > records.getRecords()[0]))
			records.setHighestLevelNormal(map.getLevel());
		else if (!win && map.getHardMode() && ((map.getLevel()-1) > records.getRecords()[1]))
			records.setHighestLevelHard(map.getLevel());
		else if (!win && !map.getHardMode() && ((map.getLevel()-1) > records.getRecords()[0]))
			records.setHighestLevelNormal(map.getLevel());
		if (map.getPlayerLives() > records.getRecords()[2])
			records.setLivesRemaining(map.getPlayerLives());
		if (map.getTotalNumOfTurns() > records.getRecords()[3])
			records.setTurnsSurvived(map.getTotalNumOfTurns());
		if (map.getTotalRoomsChecked() > records.getRecords()[4])
			records.setRoomsChecked(map.getTotalRoomsChecked());
		if (map.getTotalItemPickups() > records.getRecords()[5])
			records.setItemsObtained(map.getTotalItemPickups());
		if (map.getTotalEnemiesKilled() > records.getRecords()[6])
			records.setEnemiesKilled(map.getTotalEnemiesKilled());
		saveResource(resource.RECORDS);
	}
	
	/**
	 * This method updates the list of achievements that have been accomplished by
	 * checking through each player record after each time the player record is
	 * updated. In addition, this method also gives the player money by adding
	 * money assigned to each achievement to the {@link Shop} object. In order to
	 * avoid giving the player achievements that have already been achieved, the
	 * first parameter checked before updating each achievement is whether or not
	 * the achievement has been achieved. Finally, this method returns an array of
	 * strings containing the names of the achievements that were achieved during
	 * the time the achievements is updated in order to be printed to the Game Over
	 * screen.
	 * 
	 * @return an array of strings containing all of the achievements that were
	 * updated by this method
	 */
	public String[] updateAchievements() {
		ArrayList<String> achievements = new ArrayList<String>();
		if (!this.achievements.getAchievements()[0] && records.getRecords()[7] >= 1) {
			this.achievements.setNormalLevelsCleared1();
			achievements.add("Just a Casual");
			shop.addMoney(100);
		}
		if (!this.achievements.getAchievements()[1] && records.getRecords()[7] >= 10) {
			this.achievements.setNormalLevelsCleared10();
			achievements.add("Better Than a Casual");
			shop.addMoney(200);
		}
		if (!this.achievements.getAchievements()[2] && records.getRecords()[7] >= 50) {
			this.achievements.setNormalLevelsCleared50();
			achievements.add("Much Better Than a Casual");
			shop.addMoney(500);
		}
		if (!this.achievements.getAchievements()[3] && records.getRecords()[7] >= 100) {
			this.achievements.setNormalLevelsCleared100();
			achievements.add("Casual King");
			shop.addMoney(1000);
		}
		if (!this.achievements.getAchievements()[4] && records.getRecords()[8] >= 1) {
			this.achievements.setHardLevelsCleared1();
			achievements.add("No More Mr. Casual");
			shop.addMoney(100);
		}
		if (!this.achievements.getAchievements()[5] && records.getRecords()[8] >= 10) {
			this.achievements.setHardLevelsCleared10();
			achievements.add("Slightly More Hardcore");
			shop.addMoney(200);
		}
		if (!this.achievements.getAchievements()[6] && records.getRecords()[8] >= 50) {
			this.achievements.setHardLevelsCleared50();
			achievements.add("Hardcore Hardcore");
			shop.addMoney(500);
		}
		if (!this.achievements.getAchievements()[7] && records.getRecords()[8] >= 100) {
			this.achievements.setHardLevelsCleared100();
			achievements.add("Hardcore King");
			shop.addMoney(1000);
		}
		if (!this.achievements.getAchievements()[8] && records.getRecords()[9] >= 1) {
			this.achievements.setRoomsChecked1();
			achievements.add("I Opened a Door");
			shop.addMoney(100);
		}
		if (!this.achievements.getAchievements()[9] && records.getRecords()[9] >= 10) {
			this.achievements.setRoomsChecked10();
			achievements.add("I Didn't Sign Up for This");
			shop.addMoney(200);
		}
		if (!this.achievements.getAchievements()[10] && records.getRecords()[9] >= 50) {
			this.achievements.setRoomsChecked50();
			achievements.add("Can't Find Briefcases");
			shop.addMoney(500);
		}
		if (!this.achievements.getAchievements()[11] && records.getRecords()[9] >= 100) {
			this.achievements.setRoomsChecked100();
			achievements.add("Christmas Caroling");
			shop.addMoney(1000);
		}
		if (!this.achievements.getAchievements()[12] && records.getRecords()[10] >= 1) {
			this.achievements.setItemsObtained1();
			achievements.add("Casual Shopper");
			shop.addMoney(100);
		}
		if (!this.achievements.getAchievements()[13] && records.getRecords()[10] >= 10) {
			this.achievements.setItemsObtained10();
			achievements.add("Lucky Pennies");
			shop.addMoney(200);
		}
		if (!this.achievements.getAchievements()[14] && records.getRecords()[10] >= 50) {
			this.achievements.setItemsObtained50();
			achievements.add("Shopahollic");
			shop.addMoney(500);
		}
		if (!this.achievements.getAchievements()[15] && records.getRecords()[10] >= 100) {
			this.achievements.setItemsObtained100();
			achievements.add("Metal Detector?");
			shop.addMoney(1000);
		}
		if (!this.achievements.getAchievements()[16] && records.getRecords()[10] >= 1) {
			this.achievements.setEnemiesKilled1();
			achievements.add("First Blood");
			shop.addMoney(100);
		}
		if (!this.achievements.getAchievements()[17] && records.getRecords()[10] >= 10) {
			this.achievements.setEnemiesKilled10();
			achievements.add("PTSD Begins");
			shop.addMoney(200);
		}
		if (!this.achievements.getAchievements()[18] && records.getRecords()[10] >= 50) {
			this.achievements.setEnemiesKilled50();
			achievements.add("Ninja the Ninjas");
			shop.addMoney(500);
		}
		if (!this.achievements.getAchievements()[19] && records.getRecords()[10] >= 100) {
			this.achievements.setEnemiesKilled100();
			achievements.add("No Mercy");
			shop.addMoney(1000);
		}
		saveResource(resource.ACHIEVEMENTS);
		saveResource(resource.SHOP);
		return achievements.toArray(new String[achievements.size()]);
	}
	
	/**
	 * This method allows the player to reset the status of the game, including all
	 * of the upgrades the player has, the money they own, the highscores that have
	 * been recorded, the achievements the player has gotten, and the player records
	 * that have been recorded.
	 */
	public void reset() {
		shop = new Shop();
		highscores = new Highscore[0];
		achievements = new Achievements();
		records = new Record();
		saveResource(resource.SHOP);
		saveResource(resource.HIGHSCORES);
		saveResource(resource.ACHIEVEMENTS);
		saveResource(resource.RECORDS);
	}
	
	/**
	 * This method is only invoked when the player knows the cheat code and enters
	 * it in properly when the {@link UI#printCheat(boolean)} is called. This method
	 * then adds a proper amount of money needed to buy every possible item in the
	 * shop.
	 */
	public void cheat() {
		achievements.setCheat();
		shop.addMoney(99999);
		saveResource(resource.ACHIEVEMENTS);
		saveResource(resource.SHOP);
	}

	/**
	 * This method will save the {@link Map}, and thus all associated objects
	 * within the game, to be reloaded and played later.
	 * 
	 * @param path the filename and location to be saved to
	 */
	public void saveGame(String path) {
		try {
			FileOutputStream dos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(dos);
			oos.writeObject(map);
			oos.close();
			ui.saveSuccess();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will load a {@link Map} from a saved file, and returns it for
	 * the {@link GameEngine} to use.
	 * 
	 * @param path the filename and location to be loaded from.
	 * @return the loaded {@link Map}
	 */
	public Map loadGame(String path) {
		Map map;
		try {
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);
			map = (Map) ois.readObject();
			ois.close();
			ui.loadSuccess(map.getDebug(), map.getHardMode());
			return map;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null; // only reached if the load fails
	}
	
	/**
	 * This method will save any of the four available resources as specified when
	 * the method is called to be later reloaded by the method,
	 * {@link #loadResource(resource)}, and reused by the {@link GameEngine}.
	 * 
	 * @param resource the specified resource type to be saved
	 */
	public void saveResource(GameEngine.resource resource) {
		String path = null;
		switch (resource) {
		case HIGHSCORES:
			path = "resources" + fileSep + "highscores.dat";
			break;
		case RECORDS:
			path = "resources" + fileSep + "records.dat";
			break;
		case ACHIEVEMENTS:
			path = "resources" + fileSep + "achievements.dat";
			break;
		case SHOP:
			path = "resources" + fileSep + "shop.dat";
			break;
		}
		try {
			FileOutputStream dos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(dos);
			switch (resource) {
			case HIGHSCORES:
				oos.writeObject(highscores);
				break;
			case RECORDS:
				oos.writeObject(records);
				break;
			case ACHIEVEMENTS:
				oos.writeObject(achievements);
				break;
			case SHOP:
				oos.writeObject(shop);
				break;
			}
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method loads one of the four resources that has been saved by the method,
	 * {@link #saveResource(resource)}, to be reused by the {@link GameEngine}.
	 * 
	 * @param resource the specified resource type to be loaded
	 */
	public void loadResource(GameEngine.resource resource) {
		String path = null;
		switch (resource) {
		case HIGHSCORES:
			path = "resources" + fileSep + "highscores.dat";
			break;
		case RECORDS:
			path = "resources" + fileSep + "records.dat";
			break;
		case ACHIEVEMENTS:
			path = "resources" + fileSep + "achievements.dat";
			break;
		case SHOP:
			path = "resources" + fileSep + "shop.dat";
			break;
		}
		try {
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);
			switch (resource) {
			case HIGHSCORES:
				highscores = (Highscore[]) ois.readObject();
				break;
			case RECORDS:
				records = (Record) ois.readObject();
				break;
			case ACHIEVEMENTS:
				achievements = (Achievements) ois.readObject();
				break;
			case SHOP:
				shop = (Shop) ois.readObject();
				break;
			}
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
/**
 * 
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
 */

public class GameEngine {
	
	/**
	 * @author Natanael
	 *
	 */
	public static enum resource {
		HIGHSCORES, RECORDS, ACHIEVEMENTS, SHOP
	};
	
	/**
	 * This field represents the {@link UI} the player will interface with.
	 * Called continuously.
	 */
	private UI ui;

	/**
	 * This field represents the game's {@link Map}, containing all
	 * {@link Entity} within the game world.
	 */
	private Map map;
	
	/**
	 * 
	 */
	private Highscore[] highscores;
	
	/**
	 * 
	 */
	private Record records;
	
	/**
	 * 
	 */
	private Achievements achievements;
	
	/**
	 * 
	 */
	private Shop shop;

	/**
	 * This field represents whether the game is still being played, I.E., whether the
	 * player has not lost or won.
	 */
	private boolean gamePlaying;
	
	/**
	 * This field represents whether or not the player quit to the menu, or rather
	 * whether or not they manually quit the game.
	 */
	private boolean toMenu;
	
	/**
	 * 
	 */
	private boolean nextLevel;
	
	/**
	 * This field represents the separator character used in the OS to determine folder/location.
	 */
	public static String fileSep = FileSystems.getDefault().getSeparator();

	/**
	 * The default constructor, which instantiates the {@link UI} and
	 * {@link Map}, prints the title screen, UNFINISHED GAME AAAAAHHHHHH
	 * 
	 * @param ui
	 *            The game's UI. If GUI is implemented and extends {@link UI},
	 *            it can be used in place of the regular UI
	 */
	public GameEngine(UI ui) {
		this.ui = ui;
		map = new Map();
		if (!new File("resources" + fileSep + "saves").exists())
			new File("resources" + fileSep + "saves").mkdirs();
		if (!new File("resources" + fileSep + "highscores.dat").exists())
			highscores = new Highscore[0];
		else
			loadResource(resource.HIGHSCORES);
		if (!new File("resources" + fileSep + "records.dat").exists())
			records = new Record();
		else
			loadResource(resource.RECORDS);
		if (!new File("resources" + fileSep + "achievements.dat").exists())
			achievements = new Achievements();
		else
			loadResource(resource.ACHIEVEMENTS);
		if (!new File("resources" + fileSep + "shop.dat").exists())
			shop = new Shop();
		else
			loadResource(resource.SHOP);
	}

	public void start() {
		while (true) {
			String path = ui.mainMenu(highscores, achievements.getAchievements(), records.getRecords());
			if (path == null) {
				UI.mode mode = ui.selectMode();
				switch (mode) {
				case DEBUG:
					map.initialize(true, false);
					break;
				case NORMAL:
					map.initialize(false, false);
					break;
				case HARD:
					map.initialize(false, true);
					break;
				case DEBUGHARD:
					map.initialize(true, true);
					break;
				}
				gamePlaying = true;
				toMenu = false;
				nextLevel = false;
				map.setMaxAmmo(shop.getMagazine());
			} else if (path.equalsIgnoreCase("store")) {
				Shop.upgrades upgrade = ui.printShop(shop.getMoney(), shop.getVision(), shop.getMagazine(),
						shop.getHealth(), shop.getVisionCost(), shop.getMagazineCost(), shop.getHealthCost());
				if (upgrade == Shop.upgrades.VISION)
					shop.buyVision();
				else if (upgrade == Shop.upgrades.MAGAZINE)
					shop.buyMagazine();
				else if (upgrade == Shop.upgrades.HEALTH)
					shop.buyHealth();
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
				addHighscore();
				ui.printHighscores(highscores);
			}
		}
	}

	/**
	 * 
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
					ui.printLookResults(map.look(ui.readDirection(), shop.getVision()+1));
					hasLooked = true;
					break;
				case MOVE:
					turnEnded = playerMoved();
					break;
				case SHOOT:
					boolean shot = map.shoot(ui.readDirection());
					ui.printShoot(shot);
					if (shot)
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
				if (map.enemyScan()) {
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
	 * @return Returns {@code true} if the player found the briefcase,
	 *         {@code false} otherwise
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
			if (!nextLevel)
				updateRecords(true);
			break;
		case ITEM:
			Item.itemType type = map.getLastItem();
			ui.printPowerUp(type, map.getAmmo()>0);
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * @param path
	 *            The filename and location to be saved to.
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
	 * @param path
	 *            The filename and location to be loaded from.
	 * @return Returns the loaded {@link Map}
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
	 * 
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
	 * 
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
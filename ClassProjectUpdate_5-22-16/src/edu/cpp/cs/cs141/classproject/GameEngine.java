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

/**
 * This class represents the Game Engine, where all game logic are executed.
 *
 */

public class GameEngine {

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
	private Highscores highscores;

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
		if (!new File("saves").exists())
			new File("saves").mkdir();
		if (!new File("highscores").exists())
			new File("highscores").mkdir();
		if (!new File("highscores" + fileSep + "highscores.dat").exists())
			highscores = new Highscores();
		else
			loadScores();
	}

	public void start() {
		while (true) {
			String path = ui.mainMenu(highscores.getNames(), highscores.getScores());
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
			} else {
				if (path.equalsIgnoreCase("no saves"))
					continue;
				else
					map = loadGame(path);
			}
				gamePlaying = true;
				toMenu = false;
				nextLevel = false;
				gameLoop();
			
			while (nextLevel) {
				map.nextLevel();
				gamePlaying = true;
				toMenu = false;
				gameLoop();
			}
			
			if (!toMenu) { //if the player did not quit manually
				ui.printResults(map.getLevel(), map.getTotalNumOfTurns(), map.getPlayerLives(), map.getTotalRoomsChecked(),
						map.getTotalItemPickups(), map.getTotalEnemiesKilled(), map.getScore());
				highscores.storeHighscore(ui.askName(), map.getScore());
				ui.printHighscores(highscores.getNames(), highscores.getScores());
				saveScores();
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
				if (map.getHasBullet())
					ui.printStats(map.getLevel(), map.getPlayerLives(), 1);
				else
					ui.printStats(map.getLevel(), map.getPlayerLives(), 0);
				ui.printMap(map.getMap());
				ui.printLegend(map.getDebug());

				UI.action action = ui.readAction(hasLooked, map.getHasBullet());
				switch (action) {
				case LOOK:
					ui.printLookResults(map.look(ui.readDirection()));
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
						ui.printGameOver();
						map.tallyScore(false);
						gamePlaying = false;
						nextLevel = false;
					}
				}

				if (gamePlaying) {
					if(map.getHardMode())
						map.moveAI();
					else map.enemyMove();
				}
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
			nextLevel = ui.printNextLevel();
			gamePlaying = false;
			break;
		case ITEM:
			Item.itemType type = map.getLastItem();
			ui.printPowerUp(type, map.getHasBullet());
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
	public void saveScores() {
		try {
			FileOutputStream dos = new FileOutputStream("highscores" + fileSep + "highscores.dat");
			ObjectOutputStream oos = new ObjectOutputStream(dos);
			oos.writeObject(highscores);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	public void loadScores() {
		try {
			FileInputStream fis = new FileInputStream("highscores" + fileSep + "highscores.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			highscores = (Highscores) ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
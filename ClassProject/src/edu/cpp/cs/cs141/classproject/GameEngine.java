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
	}

	public void start() {
		ui.printTitle();

		while (true) {
			String path = ui.mainMenu();
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
				map = loadGame(path);
			}
			gamePlaying = true;
			toMenu = false;
			gameLoop();
			
			if(!toMenu) //if the player did not quit manually
				ui.askIfPlayingAgain();
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
					ui.printShoot(map.shoot(ui.readDirection()));
					turnEnded = true;
					break;
				case SAVE:
					saveGame(ui.querySave());
					break;
				case QUIT:
					gamePlaying = false;
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
						gamePlaying = false;
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
			gamePlaying = false;
			break;
		case ITEM:
			Item.itemType type = map.getLastItem();
			ui.printPowerUp(type, map.getHasBullet());
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
	public void saveGame(String save) {
		if (!new File("Saves").exists())
			new File("Saves").mkdirs();
		try {
			FileOutputStream dos = new FileOutputStream("Saves\\" + save + ".dat");
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
	public Map loadGame(String save) {
		Map map;
		try {
			FileInputStream fis = new FileInputStream("Saves\\" + save + ".dat");
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
}
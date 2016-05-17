/**
 * 
 */

package edu.cpp.cs.cs141.classproject;

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
	 * 
	 */
	private boolean gamePlaying;

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
		this.ui.printTitle();
		map = new Map();

		

		// boolean running = true;
		// while(running){
		// map.initialize(true);
		// ui.printMap(map.getMap());
		// ui.printLegend(map.getDebug());
		// ui.askIfPlayingAgain();
		// } //Milestone 2 stuff

		// map.initialize(true);
		// ui.printMap(map.getMap());
		// saveGame("save.dat");

		// map = loadGame("save.dat");
		// ui.printMap(map.getMap());

		while(true){
			String path = ui.newGame();
			if (path == null) {
				UI.mode mode = ui.selectMode();
				switch (mode) {
				case DEBUG:
					map.initialize(true);
					break;
				case NORMAL:
					map.initialize(false);
				}
			} else {
				map = loadGame(path);
				ui.loadSuccess();
			}
			gamePlaying = true;
			gameLoop();
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
					// TODO
					turnEnded = true;
					break;
				}
			
			map.reduceTurnsInvincible();
			}
		
			if(gamePlaying){
				System.out.println("ENEMY TURN GOES HERE\n\n");
			}
		}
	}
	
	/**
	 * @return Returns {@code true} if the player found the briefcase, {@code false} otherwise
	 */
	public boolean playerMoved(){
		Map.moveResult result = map.movePlayer(ui.readDirection());
		switch (result) {
		case COLLISION:
			ui.printPlayerBumped();
			return false;
		case FOUNDBRIEFCASE:
			ui.printVictory();
			gamePlaying = false;
			break;
		case ITEM:
			Item.itemType type = map.getLastItem();
			String name = null;
			switch (type) {
			case BULLET:
				name = "a bullet";
				break;
			case INVINCIBILITY:
				name = "an invincibility potion";
				break;
			case RADAR:
				name = "a radar chip";
				break;
			}
			ui.printPowerUp(name);
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
			return map;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null; // only reached if the load fails
	}
}
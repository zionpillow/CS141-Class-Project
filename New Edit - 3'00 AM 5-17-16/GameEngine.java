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
	 * This field represents the {@link UI} the player will interface with. Called continuously.
	 */
	private UI ui;
	
	/**
	 * This field represents the game's {@link Map}, containing all {@link Entity} within the game world.
	 */
	private Map map;

	/**
	 * The default constructor, which instantiates the {@link UI} and {@link Map}, prints the title screen, UNFINISHED GAME AAAAAHHHHHH
	 * 
	 * @param ui The game's UI. If GUI is implemented and extends {@link UI}, it can be used in place of the regular UI
	 */
	public GameEngine(UI ui){
		this.ui = ui;
		this.ui.printTitle();
		map = new Map();
		
		boolean running = true;
		while(running){
			map.initialize(true);
			ui.printMap(map.getMap());
			ui.printLegend(map.getDebug());
			ui.askIfPlayingAgain();
		}
		
		map.initialize(true);
		ui.printMap(map.getMap());
		saveGame("save.dat");
		
		map = loadGame("save.dat");
		ui.printMap(map.getMap());
	}
	
	/**
	 * This method will save the {@link Map}, and thus all associated objects within the game, to be reloaded and played later.
	 * 
	 * @param path The filename and location to be saved to.
	 */
	public void saveGame(String path){
		try{
			FileOutputStream dos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(dos);
			oos.writeObject(map);
			oos.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will load a {@link Map} from a saved file, and returns it for the {@link GameEngine} to use.
	 * 
	 * @param path The filename and location to be loaded from.
	 * @return Returns the loaded {@link Map}
	 */
	public Map loadGame(String path){
		Map map;
		try{
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);
			map = (Map)ois.readObject();
			ois.close();
			return map;
		}catch (IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
		
		return null; //should never be reached but Eclipse flips out without this here
	}
}
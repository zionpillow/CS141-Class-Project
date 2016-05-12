/**
 * 
 */

package edu.cpp.cs.cs141.classproject;

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
	}
}
/**
 * 
 */
package edu.cpp.cs.cs141.classproject;

import java.io.Serializable;
import java.util.Random;

/**
 * The Map class represents the game's map and all the {@code Entity} objects contained within. 
 * 
 * @author Aidan Novobilski
 */
public class Map implements Serializable {

	/**
	 * This field represents the unique ID used for saving and loading via
	 * serialization.
	 */
	private static final long serialVersionUID = 521977643662282156L;
	
	public static enum moveResult {
		WALL, COLLISION, ITEM, ROOMCHECKED, FOUNDBRIEFCASE
	};

	/**
	 * This field represents the game's map, and the locations of every entity
	 * within the game world. (Re)set during the
	 */
	private Entity[][] gameMap;

	/**
	 * This field represents the random number generator used in determining
	 * random outcomes.
	 */
	private Random rand;
	
	/**
	 * 
	 */
	private int playerColumn;
	
	/**
	 * 
	 */
	private int playerRow;
	
	/**
	 * 
	 */
	private Item.itemType lastItem;

	/**
	 * This field represents whether or not the game is in "debug mode". Set
	 * during constructor.
	 */
	private boolean debug;

	/**
	 * The default constructor. Initializes the Random object.
	 */
	public Map() {
		rand = new Random();
		playerColumn = 0;
		playerRow = 8;
	}
	
	/**
	 * Returns the map of the game as a 2D array of entities.
	 * 
	 * @return The game map.
	 */
	public Entity[][] getMap(){
		return gameMap;
	}

	/**
	 * Initializes and places all {@code Entity} objects necessary within the
	 * game map. ({@code 0, 0} is the top left corner, and so on.) First it will
	 * 
	 * @param debug
	 *            {@code true} if in debug mode, {@code false} otherwise.
	 */
	public void initialize(boolean debug) {
		this.debug = debug;

		gameMap = new Entity[9][9];
		placeRooms();
		placeEnemies();
		placeItems();
		gameMap[8][0] = new Player();
	}

	/**
	 * This method will generate the nine rooms and randomly add the briefcase
	 * to one of them. It will then add the rooms into the map. If
	 * {@code #debug} is {@code true}, the room with the briefcase will be
	 * visible.
	 */
	private void placeRooms() {
		Room[] rooms = new Room[9];

		for (int i = 0; i < 9; ++i) {
			rooms[i] = new Room();
			if (debug)
				rooms[i].setVisible(); // If debug mode is on, the player will
										// see the briefcase.
		}

		int index = rand.nextInt(9);
		rooms[index].placeBriefcase();

		index = 0;
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {
				if ((i == 1 || i == 4 || i == 7) && (j == 1 || j == 4 || j == 7)) { // Predetermined
																					// positions
					gameMap[i][j] = rooms[index];
					++index;
				}
			}
		}
	}

	/**
	 * This method will generate the six {@link Enemy}, and place them within
	 * the game world. If {@code debug} is {@code true}, then the enemies will
	 * be visible
	 */
	private void placeEnemies() {
		Enemy[] enemies = new Enemy[6];

		for (int i = 0; i < 6; ++i) {
			enemies[i] = new Enemy();
			if (debug)
				enemies[i].setVisible(); // if debug mode is on, the player will see the enemies.
			placeRandomly(enemies[i]);
		}
	}
	
	private void placeItems() {
		Item[] items = new Item[3];
		items[0] = new Item(Item.itemType.INVINCIBILITY);
		items[1] = new Item(Item.itemType.BULLET);
		items[2] = new Item(Item.itemType.RADAR);
		
		for(int i = 0; i < 3; ++i){
			if(debug)
				items[i].setVisible();
			placeRandomly(items[i]);
		}
		
	}

	/**
	 * This method will generate a random position, and if it is unoccupied, it
	 * will place the given entity in that position.
	 * 
	 * @param entity The entity to place
	 */
	public void placeRandomly(Entity entity) {
		int row;
		int column;
		boolean loop = true;

		while (loop) {
			row = rand.nextInt(9); //generated a number from 0 to 8
			if (row >= 6) {
				column = rand.nextInt(6) + 3; // prevents from spawning within protected area near player
			} else {
				column = rand.nextInt(9);
			}
			
			if(gameMap[row][column] == null){ //trying to check with .equals throws a NullPointerException
				gameMap[row][column] = entity;
				loop = false;
			}
		}
	}
	
	/**
	 * @param dir
	 * @return
	 */
	public moveResult movePlayer(UI.direction dir) {
		int initialRow = playerRow;
		int initialColumn = playerColumn;
		Entity temp = gameMap[playerRow][playerColumn];
		gameMap[playerRow][playerColumn] = null;
		moveResult moveResult = null;
		
		switch(dir) {
		case UP:
			playerRow--;
		case DOWN:
			playerRow++;
		case LEFT:
			playerColumn--;
		case RIGHT:
			playerColumn++;
		}
		
		if(playerRow < 0 || playerRow > 8 || playerColumn < 0 || playerColumn > 8) {
			moveResult = moveResult.WALL;
			playerRow = initialRow;
			playerColumn = initialColumn;
		}
		else if(gameMap[playerRow][playerColumn] != null) {
			switch(gameMap[playerRow][playerColumn].getEntityType()) {
			case ITEM:
				Item item = (Item)gameMap[playerRow][playerColumn];
				moveResult = moveResult.ITEM;
				lastItem = item.getType();
				break;
			case ENEMY:
				moveResult = moveResult.COLLISION;
				playerRow = initialRow;
				playerColumn = initialColumn;
				break;
			case ROOM:
				Room room = (Room)gameMap[playerRow][playerColumn];
				if(initialRow < playerRow) {
					if(room.getHasBriefcase())
						moveResult = moveResult.FOUNDBRIEFCASE;
					else
						moveResult = moveResult.ROOMCHECKED;
					playerRow = initialRow;
					playerColumn = initialColumn;
				}
				else {
					moveResult = moveResult.WALL;
					playerRow = initialRow;
					playerColumn = initialColumn;
				}
				break;
			}
		}
		gameMap[playerRow][playerColumn] = temp;
		return moveResult;
	}
	
	/**
	 * This method will return whether the game is in debug mode. Useful for loading a save.
	 * 
	 * @return {@code true} if game is in debug mode, {@code false} otherwise
	 */
	public boolean getDebug(){
		return debug;
	}
}

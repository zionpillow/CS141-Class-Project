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

import java.io.Serializable;
import java.util.Random;

/**
 * This class represents the game's map and all of the {@code Entity} objects
 * contained within.
 * 
 * @author Natanael Ariawan
 * @author David Hau
 * @author Miguel Menjivar
 * @author Aidan Novobilski
 */
public class Map implements Serializable {

	/**
	 * This field represents the unique ID used for saving and loading via
	 * serialization.
	 */
	private static final long serialVersionUID = -4512803050114192493L;

	/**
	 * This enumeration represents all of the possible outcomes of the movement
	 * of the player.
	 * 
	 * @author Natanael Ariawan
	 * @author David Hau
	 * @author Miguel Menjivar
	 * @author Aidan Novobilski
	 */
	public static enum moveResult {
		MOVED, WALL, COLLISION, ITEM, ROOMCHECKED, FOUNDBRIEFCASE
	};

	/**
	 * This field represents the game's map, and the locations of every entity
	 * within the game world.
	 */
	private Entity[][] gameMap;

	/**
	 * This field represents the random number generator used in determining
	 * random outcomes.
	 */
	private Random rand;
	
	/**
	 * This field is used in keeping track of the player's row prior to moving.
	 * This field is used in order to replace the player back in their original
	 * position if the player is unable to move. In addition, this field is also
	 * partly used in determining the AI movement of the enemy in the method,
	 * {@link #moveAI()}.
	 */
	private int previousPlayerRow;
	
	/**
	 * This field is used in keeping track of the player's column prior to moving.
	 * This field is used in order to replace the player back in their original
	 * position if the player is unable to move. In addition, this field is also
	 * partly used in determining the AI movement of the enemy in the method,
	 * {@link #moveAI()}.
	 */
	private int previousPlayerColumn;

	/**
	 * This field represents the player's current row. This field can be altered
	 * when the player moves through the method,
	 * {@link #movePlayer(edu.cpp.cs.cs141.classproject.UI.direction)} and is used
	 * to determine where the player is at all times. This is useful for when the
	 * player {{@link #look(edu.cpp.cs.cs141.classproject.UI.direction, int)}s or
	 * {@link #shoot(edu.cpp.cs.cs141.classproject.UI.direction)}s.
	 */
	private int playerRow;

	/**
	 * This field represents the player's current column. This field can be altered
	 * when the player moves through the method,
	 * {@link #movePlayer(edu.cpp.cs.cs141.classproject.UI.direction)} and is used
	 * to determine where the player is at all times. This is useful for when the
	 * player {{@link #look(edu.cpp.cs.cs141.classproject.UI.direction, int)}s or
	 * {@link #shoot(edu.cpp.cs.cs141.classproject.UI.direction)}s.
	 */
	private int playerColumn;

	/**
	 * This field represents the type of the last item that the player picked up.
	 * If this field is not {@code null}, then the item will be resolved by the
	 * method, {@link #resolveItem(edu.cpp.cs.cs141.classproject.Item.itemType)},
	 * and be reset to null by the method, {@link #resetLastItem()}.
	 */
	private Item.itemType lastItem;

	/**
	 * This field represents the number of turns the player has remaining to be
	 * invincible. This item is set to a value of 6 in the method,
	 * {@link #resolveItem(edu.cpp.cs.cs141.classproject.Item.itemType)}, when
	 * the player picks up an invincibility item. This value is automatically
	 * reduced on the first turn, ensuring that the player only has 5 turns of
	 * invincibility. Then, the value of this field will be reduced once every
	 * turn. While this field contains a value greater than 0, the method,
	 * {{@link #enemyScan()}, will not be called, giving the player a "pseudo
	 * invincibility".
	 */
	private int turnsInvincible;

	/**
	 * This field represents whether or not the game is in "debug mode". This field
	 * is initialized when a game is first initialized by the {@link GameEngine}
	 * through the method, {@link #initialize(boolean, boolean, boolean, int)}} and
	 * can never be altered.
	 */
	private boolean debug;
	
	/**
	 * This field represents whether or not the game is in "hard mode". This field
	 * is initialized when a game is first initialized by the {@link GameEngine}
	 * through the method, {@link #initialize(boolean, boolean, boolean, int)}} and
	 * can never be altered.
	 */
	private boolean hardMode;
	
	/**
	 * This field represents whether or not the game is in "God mode". This field
	 * is initialized when a game is first initialized by the {@link GameEngine}
	 * through the method, {@link #initialize(boolean, boolean, boolean, int)}} and
	 * can never be altered.
	 */
	private boolean godMode;
	
	/**
	 * This field represents the current level the player is on. This field is
	 * initialized to 1 when a game is first initialized by the {@link GameEngine}
	 * through the method, {@link #initialize(boolean, boolean, boolean, int)} and
	 * is increased in increments of 1 when the method, {@link #nextLevel(int)},
	 * is called.
	 */
	private int level;
	
	/**
	 * This field represents the number of turns the player has taken on the
	 * specific level that the player is currently on. This field is only
	 * increased after the player moves through the method,
	 * {@link #movePlayer(edu.cpp.cs.cs141.classproject.UI.direction)}}.
	 */
	private int numOfTurns;
	
	/**
	 * This field represents the total number of turns the player has taken
	 * prior to the current level. The field {@link #numOfTurns} is only added
	 * to this field when the player decides to move onto the next level by calling
	 * the method, {@link #nextLevel(int)}.
	 */
	private int totalNumOfTurns;
	
	/**
	 * This field represents the number of rooms the player has checked on the
	 * specific level that the player is currently on. This field is only increased
	 * when the player checks a room in the method,
	 * {@link #movePlayer(edu.cpp.cs.cs141.classproject.UI.direction)}.
	 */
	private int roomsChecked;
	
	/**
	 * This field represents the total number of rooms the player has checked
	 * prior to the current level. The field {@link #roomsChecked} is only added
	 * to this field when the player decides to move onto the next level by calling
	 * the method, {@link #nextLevel(int)}.
	 */
	private int totalRoomsChecked;
	
	/**
	 * This field represents the number of items the player has obtained on the
	 * specific level that the player is currently on. This field is only increased
	 * when the player obtains an item in the method,
	 * {@link #movePlayer(edu.cpp.cs.cs141.classproject.UI.direction)}.
	 */
	private int itemPickups;
	
	/**
	 * This field represents the total number of items the player has obtained
	 * prior to the current level. The field {@link #itemPickups} is only added
	 * to this field when the player decides to move onto the next level by calling
	 * the method, {@link #nextLevel(int)}.
	 */
	private int totalItemPickups;
	
	/**
	 * This field represents the number of enemies the player has killed on the
	 * specific level that the player is currently on. This field is only increased
	 * when the player kills an enemy in the method,
	 * {@link #shoot(edu.cpp.cs.cs141.classproject.UI.direction)}.
	 */
	private int enemiesKilled;
	
	/**
	 * This field represents the total number of enemies the player has killed
	 * prior to the current level. The field {@link #enemiesKilled} is only added
	 * to this field when the player decides to move onto the next level by calling
	 * the method, {@link #nextLevel(int)}.
	 */
	private int totalEnemiesKilled;
	
	/**
	 * This field represents the player's current score. This field is first
	 * initialized as 0 in the method,
	 * {@link #initialize(boolean, boolean, boolean, int)} and is only updated
	 * when the {@link #tallyScore(boolean)} method is called based on the different
	 * circumstances.
	 */
	private int score;
	
	/**
	 * This field represents the score multiplier associated with each mode. This
	 * field is initialized in the method,
	 * {@link #initialize(boolean, boolean, boolean, int)}, based on the mode that
	 * the player is playing in and can never be changed.
	 */
	private double modeMultiplier;
	
	/**
	 * This field represents the score multiplier associated with the current level.
	 * This field is initialized as 1 in the method,
	 * {@link #initialize(boolean, boolean, boolean, int)}, and is increased by
	 * 0.5 for each level after level 1 through the method, {@link #nextLevel(int)}.
	 */
	private double levelMultiplier;

	/**
	 * This constructor method initializes the random object assigned to the field,
	 * {@link #rand}, in order to be used as a random number generated for random
	 * events.
	 */
	public Map() {
		rand = new Random();
	}

	/**
	 * This method first assigns 
	 * This method Initializes and places all {@code Entity} objects necessary within the
	 * game map ((0, 0) being the top left corner and (8, 8) being the bottom
	 * right corner). 
	 * 
	 * @param debug
	 *            {@code true} if in debug mode, {@code false} otherwise.
	 */
	/**
	 * This method first assigns proper values to the fields {@link #debug},
	 * {@link #hardMode}, and {@link #godMode} based on the parameters given.
	 * In addition, this method also sets proper values to each of the fields
	 * involving game stats, score, and multipliers. Then, this method
	 * initializes and places all {@code Entity} objects necessary within the
	 * game map ((0, 0) being the top left corner and (8, 8) being the bottom
	 * right corner), where the number of enemies placed is dictated by the level.
	 * 
	 * @param debug {@code true} if in debug mode, {@code false} otherwise
	 * @param hardMode {@code true} if in hard mode, {@code false} otherwise
	 * @param godMode {@code true} if in God mode, {@code false} otherwise
	 * @param maxAmmo an int representing the maximum ammo of the player based on
	 * the current magazine upgrade of the {@link Shop} in the {@link GameEngine}
	 */
	public void initialize(boolean debug, boolean hardMode, boolean godMode, int maxAmmo) {
		this.debug = debug;
		this.hardMode = hardMode;
		this.godMode = godMode;
		
		level = 1;
		levelMultiplier = 1;
		numOfTurns = 0;
		totalNumOfTurns = 0;
		roomsChecked = 0;
		totalRoomsChecked = 0;
		itemPickups = 0;
		totalItemPickups = 0;
		enemiesKilled = 0;
		totalEnemiesKilled = 0;
		score = 0;
		
		if (debug)
			modeMultiplier = 0.5;
		else if (hardMode)
			modeMultiplier = 2.5;
		else
			modeMultiplier = 1.0;
		
		gameMap = new Entity[9][9];
		placeRooms();
		placeEnemies(4 + level);
		placeItems();
		gameMap[8][0] = new Player(maxAmmo);
		playerRow = 8;
		playerColumn = 0;
	}
	
	/**
	 * This method first updates all of the fields involving game stats, scores,
	 * and multipliers accordingly. Then, this method creates a new game world with
	 * randomly placed entities and takes the player from the previous game world
	 * and re-places that same player object in the same starting position as the
	 * previous game world.
	 * 
	 * @param gainLife an int representing the number of lives gained when moving
	 * onto the next level, which is determined by the current health upgrade of
	 * the {@link Shop} through the {@link GameEngine}
	 */
	public void nextLevel(int gainLife) {
		++level;
		levelMultiplier += 0.5;
		totalNumOfTurns += numOfTurns;
		totalRoomsChecked += roomsChecked;
		totalItemPickups += itemPickups;
		totalEnemiesKilled += enemiesKilled;
		numOfTurns = 0;
		roomsChecked = 0;
		itemPickups = 0;
		enemiesKilled = 0;
		
		Player player = (Player)gameMap[playerRow][playerColumn];
		player.gainLife(gainLife);
		gameMap = new Entity[9][9];
		placeRooms();
		placeEnemies(4 + level);
		placeItems();
		gameMap[8][0] = player;
		playerRow = 8;
		playerColumn = 0;
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
				rooms[i].setVisible();
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
	 * This method will generate the six {@link Enemy} objects, and place them
	 * within the game world. If {@code debug} is {@code true}, then the enemies
	 * will be visible.
	 * 
	 * @param numOfEnemies the number of enemies to be randomly placed
	 */
	private void placeEnemies(int numOfEnemies) {
		Enemy[] enemies = new Enemy[numOfEnemies];

		for (int i = 0; i < enemies.length; ++i) {
			enemies[i] = new Enemy();
			if (debug)
				enemies[i].setVisible(); // if debug mode is on, the player will
											// see the enemies.
			placeRandomly(enemies[i]);
		}
	}

	/**
	 * This method will generate the three {@link Item} objects, and place them
	 * within the game world. If {@code debug} is {@code true}, then the items
	 * will be visible.
	 */
	private void placeItems() {
		Item[] items = new Item[3];
		items[0] = new Item(Item.itemType.INVINCIBILITY);
		items[1] = new Item(Item.itemType.BULLET);
		items[2] = new Item(Item.itemType.RADAR);

		for (int i = 0; i < 3; ++i) {
			if (debug)
				items[i].setVisible();
			placeRandomly(items[i]);
		}

	}

	/**
	 * This method will generate a random position, and if it is unoccupied, it
	 * will place the given entity in that position. This method also ensures that
	 * no entity, whether enemy or item, can be placed within the spawning area
	 * of the player, the 3 by 3 block area at the bottom left of the world.
	 * 
	 * @param entity the entity to be placed
	 */
	private void placeRandomly(Entity entity) {
		int row;
		int column;
		boolean loop = true;

		while (loop) {
			row = rand.nextInt(9); // generated a number from 0 to 8

			if (row >= 6)
				column = rand.nextInt(6) + 3; // prevents from spawning within
												// protected area near player
			else
				column = rand.nextInt(9);

			if (gameMap[row][column] == null) { // trying to check with .equals
												// throws a NullPointerException
				gameMap[row][column] = entity;
				loop = false;
			}
		}
	}

	/**
	 * This method allows the player to look in a certain direction to check
	 * whether or not there are enemies in the direction that the player looks.
	 * The player initially can only look 2 tiles in front of them. However, this
	 * feature can be upgraded in the {@link Shop} through the {@link GameEngine}.
	 * This method then returns an integer value representing the distance from the
	 * player to the first enemy in their line of sight that is within the vision
	 * limits. If the player does not see any enemies, is looking at a wall, or is
	 * looking at a room, this method will return 0.
	 * 
	 * @param dir the direction the player wants to look at
	 * @param distance an int representing how far the player's vision can see
	 * @return an int representing the distance between and the first enemy in
	 * their line of sight or 0 if no enemies were found
	 */
	public int look(UI.direction dir, int distance) {
		switch (dir) {
		case UP:
			for (int i = playerRow ; i >= (playerRow - distance) ; --i) {
				if (i >= 0 && gameMap[i][playerColumn] != null) {
					if (gameMap[i][playerColumn].getEntityType() == Entity.entityType.ROOM)
						return 0;
					else if (gameMap[i][playerColumn].getEntityType() == Entity.entityType.ENEMY)
						return (playerRow - i);
				}
			}
			break;
		case DOWN:
			for (int i = playerRow ; i <= (playerRow + distance) ; ++i) {
				if (i <= 8 && gameMap[i][playerColumn] != null) {
					if (gameMap[i][playerColumn].getEntityType() == Entity.entityType.ROOM)
						return 0;
					else if (gameMap[i][playerColumn].getEntityType() == Entity.entityType.ENEMY)
						return (i - playerRow);
				}
			}
			break;
		case LEFT:
			for (int i = playerColumn ; i >= (playerColumn - distance) ; --i) {
				if (i >= 0 && gameMap[playerRow][i] != null) {
					if (gameMap[playerRow][i].getEntityType() == Entity.entityType.ROOM)
						return 0;
					else if (gameMap[playerRow][i].getEntityType() == Entity.entityType.ENEMY)
						return (playerColumn - i);
				}
			}
			break;
		case RIGHT:
			for (int i = playerColumn ; i <= (playerColumn + distance) ; ++i) {
				if (i <= 8 && gameMap[playerRow][i] != null) {
					if (gameMap[playerRow][i].getEntityType() == Entity.entityType.ROOM)
						return 0;
					else if (gameMap[playerRow][i].getEntityType() == Entity.entityType.ENEMY)
						return (i - playerColumn);
				}
			}
			break;
		}
		return 0;
	}
	
	/**
	 * This method will "fire" a bullet from the player's position, and remove
	 * the first enemy it encounters from the map.
	 * 
	 * @param dir the direction the player wants to shoot
	 * @return {@code true} if the shot hits, {@code false} otherwise
	 */
	public boolean shoot(UI.direction dir) {
		Player player = (Player) gameMap[playerRow][playerColumn];
		player.shoot();
		gameMap[playerRow][playerColumn] = player; // Uses player's bullet, then
													// updates the array
		
		switch (dir) {
		case UP:
			for (int i = playerRow; i >= 0; --i) {
				if (gameMap[i][playerColumn] != null && gameMap[i][playerColumn].getEntityType() == Entity.entityType.ENEMY) {
					gameMap[i][playerColumn] = null;
					++enemiesKilled;
					return true;
				}
			}
			break;
		case DOWN:
			for (int i = playerRow; i <= 8; ++i) {
				if (gameMap[i][playerColumn] != null && gameMap[i][playerColumn].getEntityType() == Entity.entityType.ENEMY) {
					gameMap[i][playerColumn] = null;
					++enemiesKilled;
					return true;
				}
			}
			break;
		case LEFT:
			for (int i = playerColumn; i >= 0; --i) {
				if (gameMap[playerRow][i] != null && gameMap[playerRow][i].getEntityType() == Entity.entityType.ENEMY) {
					gameMap[playerRow][i] = null;
					++enemiesKilled;
					return true;
				}
			}
			break;
		case RIGHT:
			for (int i = playerColumn; i <= 8; ++i) {
				if (gameMap[playerRow][i] != null && gameMap[playerRow][i].getEntityType() == Entity.entityType.ENEMY) {
					gameMap[playerRow][i] = null;
					++enemiesKilled;
					return true;
				}
			}
			break;
		}
		
		return false;
	}

	/**
	 * This method attempts to move the player in the direction specified by the
	 * player. The attempt will only be successful if the space the player is
	 * trying move to is either {@code null} or is an item. Then, the type of
	 * the result from the attempt to move will be returned. From this information,
	 * the {@link GameEngine} will be able to tell whether the player has moved
	 * or hasn't moved and the reason for the action that's been made.
	 * 
	 * @param dir the direction the player wants to move
	 * @return the type of result from the attempt to move in the given direction
	 */
	public moveResult movePlayer(UI.direction dir) {
		previousPlayerRow = playerRow;
		previousPlayerColumn = playerColumn;
		Entity player = gameMap[playerRow][playerColumn];
		gameMap[playerRow][playerColumn] = null;
		moveResult moveResult = null;

		switch (dir) {
		case UP:
			--playerRow;
			break;
		case DOWN:
			++playerRow;
			break;
		case LEFT:
			--playerColumn;
			break;
		case RIGHT:
			++playerColumn;
			break;
		}

		if (playerRow < 0 || playerRow > 8 || playerColumn < 0 || playerColumn > 8) {
			moveResult = Map.moveResult.WALL;
			playerRow = previousPlayerRow;
			playerColumn = previousPlayerColumn;
		} else if (gameMap[playerRow][playerColumn] != null) {
			switch (gameMap[playerRow][playerColumn].getEntityType()) {
			case ITEM:
				Item item = (Item)gameMap[playerRow][playerColumn];
				moveResult = Map.moveResult.ITEM;
				lastItem = item.getType();
				++itemPickups;
				break;
			case ENEMY:
				moveResult = Map.moveResult.COLLISION;
				playerRow = previousPlayerRow;
				playerColumn = previousPlayerColumn;
				break;
			case ROOM:
				Room room = (Room) gameMap[playerRow][playerColumn];
				if (previousPlayerRow < playerRow) {
					if (room.getHasBriefcase())
						moveResult = Map.moveResult.FOUNDBRIEFCASE;
					else {
						room.check();
						moveResult = Map.moveResult.ROOMCHECKED;
					}
					gameMap[playerRow][playerColumn] = room;
					playerRow = previousPlayerRow;
					playerColumn = previousPlayerColumn;
					++roomsChecked;
				} else {
					moveResult = Map.moveResult.WALL;
					playerRow = previousPlayerRow;
					playerColumn = previousPlayerColumn;
				}
				break;
			case PLAYER:
				break;
			}
		} else
			moveResult = Map.moveResult.MOVED;

		gameMap[playerRow][playerColumn] = player;
		++numOfTurns;
		
		return moveResult;
	}
	
	/**
	 * This method returns the player to the coordinates (8, 0), which is the
	 * player's starting position, and calls the method, {@link #playerLostLife()},
	 * when the player is on a tile next to a ninja at the start of the enemies'
	 * turn.
	 */
	public void returnPlayerToStart() {
		Player temp = (Player) gameMap[playerRow][playerColumn];
		gameMap[playerRow][playerColumn] = null;

		gameMap[8][0] = temp;
		playerRow = 8;
		playerColumn = 0;
		playerLostLife();
	}

	/**
	 * This method reduces the player's life and also repositions enemies if there
	 * exists any number of enemies within the player's spawn area, the 3 by 3
	 * block area at the bottom left of the world.
	 */
	private void playerLostLife() {
		Player player = (Player) gameMap[playerRow][playerColumn];
		player.loseLife();
		
		int enemies = 0;
		for (int i = 6 ; i < 9 ; ++i) {
			for (int j = 0 ; j < 3 ; ++j) {
				if (gameMap[i][j] != null && gameMap[i][j].getEntityType() == Entity.entityType.ENEMY) {
					gameMap[i][j] = null;
					++enemies;
				}
			}
		}
		placeEnemies(enemies);
	}
	
	/**
	 * This method sets the player's max ammo when the game is initialized based on
	 * the current magazine upgrade in the {@link Shop}. This method is only called
	 * once in the method, {@link #initialize(boolean, boolean, boolean, int)},
	 * and the player's max ammo is never changed afterwards.
	 * 
	 * @param maxAmmo an int that represents the player's designated max ammo based
	 * on the magazine upgrade in the {@link Shop}
	 */
	public void setMaxAmmo(int maxAmmo) {
		Player player = (Player) gameMap[playerRow][playerColumn];
		player.setMaxAmmo(maxAmmo);
	}

	/**
	 * This method resolves the item effect of an item when an item is picked up
	 * by the player in the method,
	 * {@link #movePlayer(edu.cpp.cs.cs141.classproject.UI.direction)}, based on
	 * the type of item that was picked up by the player.
	 * 
	 * @param type the type of item picked up by the player
	 */
	public void resolveItem(Item.itemType type) {
		switch (type) {
		case BULLET:
			Player player = (Player) gameMap[playerRow][playerColumn];
			player.reload();
			break;
		case INVINCIBILITY:
			turnsInvincible = 6; //reduced on first turn.
			break;
		case RADAR:
			for (int i = 0; i < 8; ++i) {
				for (int j = 0; j < 8; ++j) {
					if ((i == 1 || i == 4 || i == 7) && (j == 1 || j == 4 || j == 7)) { // Predetermined
																						// positions
						Room room = (Room) gameMap[i][j];
						room.setVisible();
					}
				}
			}
			break;
		}
	}
	
	/**
	 * This method ensures that an item can only be used once, and is called after
	 * resolving an item's use.
	 */
	public void resetLastItem(){
		lastItem = null;
	}

	/**
	 * This method reduces the number of turns the player has left to be invincible
	 * when the player has picked up an invincibility item and has turns of
	 * invincibility remaining.
	 */
	public void reduceTurnsInvincible() {
		--turnsInvincible;
	}
	
	/**
	 * This method allows every enemy on the map to scan the four adjacent spaces
	 * around them. If a player is on a space adjacent to any ninja on the map,
	 * this method will return {@code true}, otherwise this method will return
	 * {@code false}.
	 * 
	 * @return {@code true} if the player is next to a ninja, {@code false}
	 * otherwise
	 */
	public boolean enemyScan() {

		if (turnsInvincible <= 0) {
			for (int i = 0; i < gameMap.length; i++) {
				for (int j = 0; j < gameMap[0].length; j++) {
					if (gameMap[i][j] != null && gameMap[i][j].getEntityType() == Entity.entityType.ENEMY) {
						if ((i - 1 >= 0 && i - 1 <= 8) && gameMap[i - 1][j] != null
								&& gameMap[i - 1][j].getEntityType() == Entity.entityType.PLAYER)
							return true;

						if ((i + 1 >= 0 && i + 1 <= 8) && gameMap[i + 1][j] != null
								&& gameMap[i + 1][j].getEntityType() == Entity.entityType.PLAYER)
							return true;

						if ((j - 1 >= 0 && j - 1 <= 8) && gameMap[i][j - 1] != null
								&& gameMap[i][j - 1].getEntityType() == Entity.entityType.PLAYER)
							return true;

						if ((j + 1 >= 0 && j + 1 <= 8) && gameMap[i][j + 1] != null
								&& gameMap[i][j + 1].getEntityType() == Entity.entityType.PLAYER)
							return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * This method moves the ninja in one of four random directions. This method
	 * first scans the spaces around the ninja to check which directions are
	 * available for the ninja to move to. Then, this method takes the available
	 * directions and equally chooses one of the available directions and moves
	 * the ninja in that direction. If no directions are available, then the ninja
	 * will remain in the same spot.
	 */
	public void enemyMove() {
		for (int i = 0; i < gameMap.length; i++) {
			for (int j = 0; j < gameMap[0].length; j++) {
				if (gameMap[i][j] != null && gameMap[i][j].getEntityType() == Entity.entityType.ENEMY) {
					int enemyRow = i;
					int enemyColumn = j;
					Enemy enemy = (Enemy) gameMap[enemyRow][enemyColumn];
					enemy.resetTurn();
					gameMap[enemyRow][enemyColumn] = (Entity) enemy;
				}
			}
		}

		for (int i = 0; i < gameMap.length; i++) {
			for (int j = 0; j < gameMap[0].length; j++) {
				if (gameMap[i][j] != null && gameMap[i][j].getEntityType() == Entity.entityType.ENEMY) {
					int enemyRow = i;
					int enemyColumn = j;
					Enemy enemy = (Enemy) gameMap[enemyRow][enemyColumn];
					if (!enemy.getHasMoved()) {
						gameMap[enemyRow][enemyColumn] = null;
						int rand;

						UI.direction dir[] = new UI.direction[4];
						int dirNumber = 0;

						if ((enemyRow - 1) >= 0 && gameMap[enemyRow - 1][enemyColumn] == null) {
							dir[dirNumber] = UI.direction.UP;
							++dirNumber;
						}
						if ((enemyRow + 1) <= 8 && gameMap[enemyRow + 1][enemyColumn] == null) {
							dir[dirNumber] = UI.direction.DOWN;
							++dirNumber;
						}
						if ((enemyColumn - 1) >= 0 && gameMap[enemyRow][enemyColumn - 1] == null) {
							dir[dirNumber] = UI.direction.LEFT;
							++dirNumber;
						}
						if ((enemyColumn + 1) <= 8 && gameMap[enemyRow][enemyColumn + 1] == null) {
							dir[dirNumber] = UI.direction.RIGHT;
							++dirNumber;
						}

						switch (dirNumber) {
						case 4:
							rand = this.rand.nextInt(100);
							if (rand < 25) {
								enemy.move();
								gameMap[enemyRow - 1][enemyColumn] = enemy;
							} else if (rand < 50) {
								enemy.move();
								gameMap[enemyRow + 1][enemyColumn] = enemy;
							} else if (rand < 75) {
								enemy.move();
								gameMap[enemyRow][enemyColumn - 1] = enemy;
							} else {
								enemy.move();
								gameMap[enemyRow][enemyColumn + 1] = enemy;
							}
							break;
						case 3:
							rand = this.rand.nextInt(75);
							if (dir[0] == UI.direction.UP) {
								if (dir[1] == UI.direction.DOWN) {
									if (dir[2] == UI.direction.LEFT) {
										if (rand < 25) {
											enemy.move();
											gameMap[enemyRow - 1][enemyColumn] = enemy;
										} else if (rand < 50) {
											enemy.move();
											gameMap[enemyRow + 1][enemyColumn] = enemy;
										} else {
											enemy.move();
											gameMap[enemyRow][enemyColumn - 1] = enemy;
										}
									} else {
										if (rand < 25) {
											enemy.move();
											gameMap[enemyRow - 1][enemyColumn] = enemy;
										} else if (rand < 50) {
											enemy.move();
											gameMap[enemyRow + 1][enemyColumn] = enemy;
										} else {
											enemy.move();
											gameMap[enemyRow][enemyColumn + 1] = enemy;
										}
									}
								} else {
									if (rand < 25) {
										enemy.move();
										gameMap[enemyRow - 1][enemyColumn] = enemy;
									} else if (rand < 50) {
										enemy.move();
										gameMap[enemyRow][enemyColumn - 1] = enemy;
									} else {
										enemy.move();
										gameMap[enemyRow][enemyColumn + 1] = enemy;
									}
								}
							} else {
								if (rand < 25) {
									enemy.move();
									gameMap[enemyRow + 1][enemyColumn] = enemy;
								} else if (rand < 50) {
									enemy.move();
									gameMap[enemyRow][enemyColumn - 1] = enemy;
								} else {
									enemy.move();
									gameMap[enemyRow][enemyColumn + 1] = enemy;
								}
							}
							break;
						case 2:
							rand = this.rand.nextInt(50);
							if (dir[0] == UI.direction.UP) {
								if (dir[1] == UI.direction.DOWN) {
									if (rand < 25) {
										enemy.move();
										gameMap[enemyRow - 1][enemyColumn] = enemy;
									} else {
										enemy.move();
										gameMap[enemyRow + 1][enemyColumn] = enemy;
									}
								} else if (dir[1] == UI.direction.LEFT) {
									if (rand < 25) {
										enemy.move();
										gameMap[enemyRow - 1][enemyColumn] = enemy;
									} else {
										enemy.move();
										gameMap[enemyRow][enemyColumn - 1] = enemy;
									}
								} else {
									if (rand < 25) {
										enemy.move();
										gameMap[enemyRow - 1][enemyColumn] = enemy;
									} else {
										enemy.move();
										gameMap[enemyRow][enemyColumn + 1] = enemy;
									}
								}
							} else if (dir[0] == UI.direction.DOWN) {
								if (dir[1] == UI.direction.LEFT) {
									if (rand < 25) {
										enemy.move();
										gameMap[enemyRow + 1][enemyColumn] = enemy;
									} else {
										enemy.move();
										gameMap[enemyRow][enemyColumn - 1] = enemy;
									}
								} else {
									if (rand < 25) {
										enemy.move();
										gameMap[enemyRow + 1][enemyColumn] = enemy;
									} else {
										enemy.move();
										gameMap[enemyRow][enemyColumn + 1] = enemy;
									}
								}
							} else {
								if (rand < 25) {
									enemy.move();
									gameMap[enemyRow][enemyColumn - 1] = enemy;
								} else {
									enemy.move();
									gameMap[enemyRow][enemyColumn + 1] = enemy;
								}
							}
							break;
						case 1:
							if (dir[0] == UI.direction.UP) {
								enemy.move();
								gameMap[enemyRow - 1][enemyColumn] = enemy;
							} else if (dir[0] == UI.direction.DOWN) {
								enemy.move();
								gameMap[enemyRow + 1][enemyColumn] = enemy;
							} else if (dir[0] == UI.direction.LEFT) {
								enemy.move();
								gameMap[enemyRow][enemyColumn - 1] = enemy;
							} else {
								enemy.move();
								gameMap[enemyRow][enemyColumn + 1] = enemy;
							}
							break;
						default:
							enemy.move();
							gameMap[enemyRow][enemyColumn] = enemy;
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * This method moves the ninja according to an AI. This method first checks
	 * the available directions, just as in the method, {@link #enemyMove()}.
	 * Then, this method checks which direction(s) are preferred by the ninja,
	 * if one or two directions were to be chosen so that the ninja approaches the
	 * player, and compares those directions to the available directions to see
	 * which possible directions the ninja would move in if it was to seek the
	 * player. Then, the choice between whether the ninja seeks the player or is
	 * moved randomly is randomized, with the chance of seeking increased the
	 * farther away the ninja is from the player. If seeking fails, then a
	 * direction is randomly chosen from the available directions. If seeking
	 * succeeds but there are no possible directions the ninja could move in order
	 * to seek the player, then the ninja is moved in whatever direction is
	 * available. If there are no directions available, then the ninja will remain
	 * in the same spot.
	 */
	public void moveAI() {
		for (int i = 0; i < gameMap.length; i++) {
			for (int j = 0; j < gameMap[0].length; j++) {
				if (gameMap[i][j] != null && gameMap[i][j].getEntityType() == Entity.entityType.ENEMY) {
					int enemyRow = i;
					int enemyColumn = j;
					Enemy enemy = (Enemy) gameMap[enemyRow][enemyColumn];
					enemy.resetTurn();
					gameMap[enemyRow][enemyColumn] = (Entity) enemy;
				}
			}
		}
	
		for (int i = 0; i < gameMap.length; i++) {
			for (int j = 0; j < gameMap[0].length; j++) {
				if (gameMap[i][j] != null && gameMap[i][j].getEntityType() == Entity.entityType.ENEMY) {
					int enemyRow = i;
					int enemyColumn = j;
					Enemy enemy = (Enemy) gameMap[enemyRow][enemyColumn];
					if (!enemy.getHasMoved()) {
						gameMap[enemyRow][enemyColumn] = null;
						int playerToEnemyDistance = (enemyRow - playerRow) + (enemyColumn - playerColumn);
						int seekChance = 33 + playerToEnemyDistance * 2;
						int rand;
	
						UI.direction dirAvailable[] = new UI.direction[4];
						int dirAvailableNumber = 0;
	
						if ((enemyRow - 1) >= 0 && gameMap[enemyRow - 1][enemyColumn] == null) {
							dirAvailable[dirAvailableNumber] = UI.direction.UP;
							++dirAvailableNumber;
						}
						if ((enemyRow + 1) <= 8 && gameMap[enemyRow + 1][enemyColumn] == null) {
							dirAvailable[dirAvailableNumber] = UI.direction.DOWN;
							++dirAvailableNumber;
						}
						if ((enemyColumn - 1) >= 0 && gameMap[enemyRow][enemyColumn - 1] == null) {
							dirAvailable[dirAvailableNumber] = UI.direction.LEFT;
							++dirAvailableNumber;
						}
						if ((enemyColumn + 1) <= 8 && gameMap[enemyRow][enemyColumn + 1] == null) {
							dirAvailable[dirAvailableNumber] = UI.direction.RIGHT;
							++dirAvailableNumber;
						}
	
						UI.direction dirPref[] = new UI.direction[2];
						int dirPrefNum = 0;
	
						if (enemyRow > playerRow) {
							dirPref[dirPrefNum] = UI.direction.UP;
							++dirPrefNum;
						}
						if (enemyRow < playerRow) {
							dirPref[dirPrefNum] = UI.direction.DOWN;
							++dirPrefNum;
						}
						if (enemyColumn > playerColumn) {
							dirPref[dirPrefNum] = UI.direction.LEFT;
							++dirPrefNum;
						}
						if (enemyColumn < playerColumn) {
							dirPref[dirPrefNum] = UI.direction.RIGHT;
							++dirPrefNum;
						}
	
						UI.direction dirPrefMove[] = new UI.direction[2];
						int dirPrefMoveNum = 0;
	
						for (int a = 0; a < dirAvailableNumber; ++a) {
							if (dirAvailable[a]== dirPref[0] || dirAvailable[a] == dirPref[1])
								dirPrefMove[dirPrefMoveNum] = dirAvailable[a];
						}
	
						if (dirPrefMoveNum > 0) {
							if (this.rand.nextInt(100) < seekChance) {
								switch (dirPrefMoveNum) {
								case 2:
									if (dirPrefMove[0] == UI.direction.UP) {
										if (dirPrefMove[1] == UI.direction.LEFT) {
											if (previousPlayerColumn != playerColumn) {
												enemy.move();
												gameMap[enemyRow - 1][enemyColumn] = enemy;
											} else {
												enemy.move();
												gameMap[enemyRow][enemyColumn - 1] = enemy;
											}
										} else {
											if (previousPlayerColumn != playerColumn) {
												enemy.move();
												gameMap[enemyRow - 1][enemyColumn] = enemy;
											} else {
												enemy.move();
												gameMap[enemyRow][enemyColumn + 1] = enemy;
											}
										}
									} else {
										if (dirPrefMove[1] == UI.direction.LEFT) {
											if (previousPlayerColumn != playerColumn) {
												enemy.move();
												gameMap[enemyRow + 1][enemyColumn] = enemy;
											} else {
												enemy.move();
												gameMap[enemyRow][enemyColumn - 1] = enemy;
											}
										} else {
											if (previousPlayerColumn != playerColumn) {
												enemy.move();
												gameMap[enemyRow + 1][enemyColumn] = enemy;
											} else {
												enemy.move();
												gameMap[enemyRow][enemyColumn + 1] = enemy;
											}
										}
									}
									break;
								default:
									if (dirPrefMove[0] == UI.direction.UP) {
										enemy.move();
										gameMap[enemyRow - 1][enemyColumn] = enemy;
									} else if (dirPrefMove[0] == UI.direction.DOWN) {
										enemy.move();
										gameMap[enemyRow + 1][enemyColumn] = enemy;
									} else if (dirPrefMove[0] == UI.direction.LEFT) {
										enemy.move();
										gameMap[enemyRow][enemyColumn - 1] = enemy;
									} else {
										enemy.move();
										gameMap[enemyRow][enemyColumn + 1] = enemy;
									}
									break;
								}
							} else
								dirPrefMoveNum = 0;
						}
						if (dirPrefMoveNum == 0) {
							switch (dirAvailableNumber) {
							case 4:
								rand = this.rand.nextInt(100);
								if (rand < 25) {
									enemy.move();
									gameMap[enemyRow - 1][enemyColumn] = enemy;
								} else if (rand < 50) {
									enemy.move();
									gameMap[enemyRow + 1][enemyColumn] = enemy;
								} else if (rand < 75) {
									enemy.move();
									gameMap[enemyRow][enemyColumn - 1] = enemy;
								} else {
									enemy.move();
									gameMap[enemyRow][enemyColumn + 1] = enemy;
								}
								break;
							case 3:
								rand = this.rand.nextInt(75);
								if (dirAvailable[0] == UI.direction.UP) {
									if (dirAvailable[1] == UI.direction.DOWN) {
										if (dirAvailable[2] == UI.direction.LEFT) {
											if (rand < 25) {
												enemy.move();
												gameMap[enemyRow - 1][enemyColumn] = enemy;
											} else if (rand < 50) {
												enemy.move();
												gameMap[enemyRow + 1][enemyColumn] = enemy;
											} else {
												enemy.move();
												gameMap[enemyRow][enemyColumn - 1] = enemy;
											}
										} else {
											if (rand < 25) {
												enemy.move();
												gameMap[enemyRow - 1][enemyColumn] = enemy;
											} else if (rand < 50) {
												enemy.move();
												gameMap[enemyRow + 1][enemyColumn] = enemy;
											} else {
												enemy.move();
												gameMap[enemyRow][enemyColumn + 1] = enemy;
											}
										}
									} else {
										if (rand < 25) {
											enemy.move();
											gameMap[enemyRow - 1][enemyColumn] = enemy;
										} else if (rand < 50) {
											enemy.move();
											gameMap[enemyRow][enemyColumn - 1] = enemy;
										} else {
											enemy.move();
											gameMap[enemyRow][enemyColumn + 1] = enemy;
										}
									}
								} else {
									if (rand < 25) {
										enemy.move();
										gameMap[enemyRow + 1][enemyColumn] = enemy;
									} else if (rand < 50) {
										enemy.move();
										gameMap[enemyRow][enemyColumn - 1] = enemy;
									} else {
										enemy.move();
										gameMap[enemyRow][enemyColumn + 1] = enemy;
									}
								}
								break;
							case 2:
								rand = this.rand.nextInt(50);
								if (dirAvailable[0] == UI.direction.UP) {
									if (dirAvailable[1] == UI.direction.DOWN) {
										if (rand < 25) {
											enemy.move();
											gameMap[enemyRow - 1][enemyColumn] = enemy;
										} else {
											enemy.move();
											gameMap[enemyRow + 1][enemyColumn] = enemy;
										}
									} else if (dirAvailable[1] == UI.direction.LEFT) {
										if (rand < 25) {
											enemy.move();
											gameMap[enemyRow - 1][enemyColumn] = enemy;
										} else {
											enemy.move();
											gameMap[enemyRow][enemyColumn - 1] = enemy;
										}
									} else {
										if (rand < 25) {
											enemy.move();
											gameMap[enemyRow - 1][enemyColumn] = enemy;
										} else {
											enemy.move();
											gameMap[enemyRow][enemyColumn + 1] = enemy;
										}
									}
								} else if (dirAvailable[0] == UI.direction.DOWN) {
									if (dirAvailable[1] == UI.direction.LEFT) {
										if (rand < 25) {
											enemy.move();
											gameMap[enemyRow + 1][enemyColumn] = enemy;
										} else {
											enemy.move();
											gameMap[enemyRow][enemyColumn - 1] = enemy;
										}
									} else {
										if (rand < 25) {
											enemy.move();
											gameMap[enemyRow + 1][enemyColumn] = enemy;
										} else {
											enemy.move();
											gameMap[enemyRow][enemyColumn + 1] = enemy;
										}
									}
								} else {
									if (rand < 25) {
										enemy.move();
										gameMap[enemyRow][enemyColumn - 1] = enemy;
									} else {
										enemy.move();
										gameMap[enemyRow][enemyColumn + 1] = enemy;
									}
								}
								break;
							case 1:
								if (dirAvailable[0] == UI.direction.UP) {
									enemy.move();
									gameMap[enemyRow - 1][enemyColumn] = enemy;
								} else if (dirAvailable[0] == UI.direction.DOWN) {
									enemy.move();
									gameMap[enemyRow + 1][enemyColumn] = enemy;
								} else if (dirAvailable[0] == UI.direction.LEFT) {
									enemy.move();
									gameMap[enemyRow][enemyColumn - 1] = enemy;
								} else {
									enemy.move();
									gameMap[enemyRow][enemyColumn + 1] = enemy;
								}
								break;
							default:
								enemy.move();
								gameMap[enemyRow][enemyColumn] = enemy;
								break;
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * This method adds the current level's score onto the overall score. The score
	 * calculated differs on whether the condition is that the player cleared the
	 * level, in which extra points are added, or whether the condition is that
	 * the player lost before the level was cleared, in which the level clear
	 * points are not added.
	 * 
	 * @param win {@code true} if the player cleared the level, {@code false}
	 * otherwise
	 */
	public void tallyScore(boolean win) {
		if (win)
			score = score + getLevelScore();
		else
			score = score + (getRoomsCheckedScore() + getItemPickupsScore() + getEnemiesKilledScore());
	}
	
	/**
	 * Returns the map of the game as a 2D array of entities.
	 * 
	 * @return the game map
	 */
	public Entity[][] getMap() {
		return gameMap;
	}
	
	/**
	 * This method retrieves the number of lives the player currently has.
	 * 
	 * @return an int that represents the player's lives
	 */
	public int getPlayerLives() {
		Player player = (Player) gameMap[playerRow][playerColumn];
		return player.getLives();
	}
	
	/**
	 * This method retrieves the number of bullets the player currently has.
	 * 
	 * @return an int that represents the player's ammo
	 */
	public int getAmmo() {
		Player player = (Player) gameMap[playerRow][playerColumn];
		return player.getAmmo();
	}
	
	/**
	 * This method retrieves the number of bullets the player's gun can hold.
	 * 
	 * @return an int that represents the player's max ammo
	 */
	public int getMaxAmmo() {
		Player player = (Player) gameMap[playerRow][playerColumn];
		return player.getMaxAmmo();
	}
	
	/**
	 * This method retrieves the number of turns that the player has to be
	 * invincible.
	 * 
	 * @return an int that represents the player's remaining number of turns to
	 * be invincible
	 */
	public int getTurnsInvincible() {
		return turnsInvincible;
	}

	/**
	 * This method retrieves the type of item that was last picked up by the player.
	 * 
	 * @return the type of item last picked up by the player
	 */
	public Item.itemType getLastItem() {
		return lastItem;
	}
	
	/**
	 * This method retrieves the current level the player is on.
	 * 
	 * @return an int that represents the current level
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * This method calculates the points given when a level is cleared based on
	 * how many turns have elapsed.
	 * 
	 * @return an int that represents the score for the level clear
	 */
	public int getNumOfTurnsScore() {
		if (numOfTurns >= 44)
			return (int)Math.round(2000*levelMultiplier*modeMultiplier);
		else
			return (int)Math.round(((10000 - ((numOfTurns - 4) * 200))*levelMultiplier*modeMultiplier));
	}
	
	/**
	 * This method calculates the points given for the number of lives the player
	 * has remaining.
	 * 
	 * @return an int that represents the score for the player's remaining lives
	 */
	public int getLivesRemainingScore() {
		Player player = (Player)gameMap[playerRow][playerColumn];
		return (int)Math.round((player.getLives() * 500)*levelMultiplier*modeMultiplier);
	}
	
	/**
	 * This method calculates the points given for the number of rooms the player
	 * checked.
	 * 
	 * @return an int that represents the score for the number of rooms the player
	 * checked
	 */
	public int getRoomsCheckedScore() {
		return (int)Math.round((roomsChecked * 300)*levelMultiplier*modeMultiplier);
	}
	
	/**
	 * This method calculates the points given for the number of items the player
	 * obtained.
	 * 
	 * @return an int that represents the score for the number of items the player
	 * obtained
	 */
	public int getItemPickupsScore() {
		return (int)Math.round((itemPickups * 200)*levelMultiplier*modeMultiplier);
	}
	
	/**
	 * This method calculates the points given for the number of enemies the player
	 * killed.
	 * 
	 * @return and int that represents the score for the number of enemies the player
	 * killed
	 */
	public int getEnemiesKilledScore() {
		return (int)Math.round((enemiesKilled * 500)*levelMultiplier*modeMultiplier);
	}
	
	/**
	 * This method calculates the total score for the current level only if the
	 * player has cleared the level.
	 * 
	 * @return an int that represents the player's total score for the level
	 */
	public int getLevelScore() {
		return (getNumOfTurnsScore() + getLivesRemainingScore() + getRoomsCheckedScore() + getItemPickupsScore() + getEnemiesKilledScore());
	}
	
	/**
	 * This method returns the value of the field {@link #totalNumOfTurns} plus the
	 * value of the field {@link #numOfTurns} in order to get the overall number
	 * of turns the player has taken.
	 * 
	 * @return an int that represents the total number of turns the player has
	 * taken
	 */
	public int getTotalNumOfTurns() {
		return (numOfTurns + totalNumOfTurns);
	}
	
	/**
	 * This method returns the value of the field {@link #totalRoomsChecked} plus
	 * the value of the field {@link #roomsChecked} in order to get the overall
	 * number of rooms the player has checked.
	 * 
	 * @return an int that represents the total number of rooms the player has
	 * checked
	 */
	public int getTotalRoomsChecked() {
		return (roomsChecked + totalRoomsChecked);
	}
	
	/**
	 * This method returns the value of the field {@link #getTotalItemPickups()}
	 * plus the value of the field {@link #itemPickups} in order to get the overall
	 * number of items the player has obtained.
	 * 
	 * @return an int that represents the total number of items the player has
	 * obtained
	 */
	public int getTotalItemPickups() {
		return (itemPickups + totalItemPickups);
	}
	
	/**
	 * This method returns the value of the field {@link #getTotalEnemiesKilled()}
	 * plus the value of the field {@link #enemiesKilled} in order to get the
	 * overall number of enemies the player has killed.
	 * 
	 * @return an int that represents the total number of enemies the player has
	 * killed
	 */
	public int getTotalEnemiesKilled() {
		return (enemiesKilled + totalEnemiesKilled);
	}
	
	/**
	 * This method retrieves the value of the final score to be printed in the
	 * method, {@link UI#printGameOver(int, int, int, int, int, int, int, String[])}.
	 * 
	 * @return an int that represents the player's final score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * This method will return whether or not the game is in debug mode.
	 * 
	 * @return {@code true} if game is in debug mode, {@code false} otherwise
	 */
	public boolean getDebug() {
		return debug;
	}
	
	/**
	 * This method will return whether or not the game is in hard mode.
	 * 
	 * @return {@code true} if game is in hard mode, {@code false} otherwise
	 */
	public boolean getHardMode(){
		return hardMode;
	}
	
	/**
	 * This method will return whether or not the game is in God mode.
	 * 
	 * @return {@code true} if game is in God mode, {@code false} otherwise
	 */
	public boolean getGodMode() {
		return godMode;
	}
	
}
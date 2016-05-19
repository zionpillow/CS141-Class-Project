/**
 * 
 */
package edu.cpp.cs.cs141.classproject;

import java.io.Serializable;
import java.util.Random;

/**
 * The Map class represents the game's map and all the {@code Entity} objects
 * contained within.
 * 
 * @author Aidan Novobilski
 */
public class Map implements Serializable {

	/**
	 * This field represents the unique ID used for saving and loading via
	 * serialization.
	 */
	private static final long serialVersionUID = 521977643662282156L;

	/**
	 * @author Natanael
	 *
	 */
	public static enum moveResult {
		MOVED, WALL, COLLISION, ITEM, ROOMCHECKED, FOUNDBRIEFCASE
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
	private int playerRow;

	/**
	 * 
	 */
	private int playerColumn;

	/**
	 * 
	 */
	private Item.itemType lastItem;

	/**
	 * 
	 */
	private int turnsInvincible;

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
	}

	/**
	 * Returns the map of the game as a 2D array of entities.
	 * 
	 * @return The game map.
	 */
	public Entity[][] getMap() {
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
				enemies[i].setVisible(); // if debug mode is on, the player will
											// see the enemies.
			placeRandomly(enemies[i]);
		}
	}

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
	 * will place the given entity in that position.
	 * 
	 * @param entity
	 *            The entity to place
	 */
	public void placeRandomly(Entity entity) {
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
	 * @param dir
	 * @return
	 */
	public boolean look(UI.direction dir) {
		boolean detectedEnemy = false;

		switch (dir) {
		case UP:
			for (int i = (playerRow - 2); i < playerRow; ++i) {
				if (i >= 0 && gameMap[i][playerColumn] != null
						&& gameMap[i][playerColumn].getEntityType() == Entity.entityType.ENEMY)
					detectedEnemy = true;
			}
			break;
		case DOWN:
			for (int i = (playerRow + 2); i > playerRow; --i) {
				if (i < gameMap.length && gameMap[i][playerColumn] != null
						&& gameMap[i][playerColumn].getEntityType() == Entity.entityType.ENEMY)
					detectedEnemy = true;
			}
			break;
		case LEFT:
			for (int i = (playerColumn - 2); i < playerColumn; ++i) {
				if (i >= 0 && gameMap[playerRow][i] != null
						&& gameMap[playerRow][i].getEntityType() == Entity.entityType.ENEMY)
					detectedEnemy = true;
			}
			break;
		case RIGHT:
			for (int i = (playerColumn + 2); i > playerColumn; --i) {
				if (i < gameMap[0].length && gameMap[playerRow][i] != null
						&& gameMap[playerRow][i].getEntityType() == Entity.entityType.ENEMY)
					detectedEnemy = true;
			}
			break;
		}

		return detectedEnemy;
	}

	/**
	 * @param dir
	 * @return
	 */
	public moveResult movePlayer(UI.direction dir) {
		int initialRow = playerRow;
		int initialColumn = playerColumn;
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
			playerRow = initialRow;
			playerColumn = initialColumn;
		} else if (gameMap[playerRow][playerColumn] != null) {
			switch (gameMap[playerRow][playerColumn].getEntityType()) {
			case ITEM:
				Item item = (Item) gameMap[playerRow][playerColumn];
				moveResult = Map.moveResult.ITEM;
				lastItem = item.getType();
				break;
			case ENEMY:
				moveResult = Map.moveResult.COLLISION;
				playerRow = initialRow;
				playerColumn = initialColumn;
				break;
			case ROOM:
				Room room = (Room) gameMap[playerRow][playerColumn];
				if (initialRow < playerRow) {
					if (room.getHasBriefcase())
						moveResult = Map.moveResult.FOUNDBRIEFCASE;
					else {
						room.check();
						moveResult = Map.moveResult.ROOMCHECKED;
					}
					gameMap[playerRow][playerColumn] = room;
					playerRow = initialRow;
					playerColumn = initialColumn;
				} else {
					moveResult = Map.moveResult.WALL;
					playerRow = initialRow;
					playerColumn = initialColumn;
				}
				break;
			case PLAYER:
				break;
			}
		} else
			moveResult = Map.moveResult.MOVED;

		gameMap[playerRow][playerColumn] = player;
		if (lastItem != null) {
			resolveItem(lastItem);
		}

		return moveResult;
	}

	/**
	 * @param type
	 */
	public void resolveItem(Item.itemType type) {
		switch (type) {
		case BULLET:
			Player temp = (Player) gameMap[playerRow][playerColumn];
			temp.gainBullet();
			gameMap[playerRow][playerColumn] = temp;
			break;
		case INVINCIBILITY:
			turnsInvincible = 6; //reduced on first turn.
			break;
		case RADAR:
			for (int i = 0; i < 8; ++i) {
				for (int j = 0; j < 8; ++j) {
					if ((i == 1 || i == 4 || i == 7) && (j == 1 || j == 4 || j == 7)) { // Predetermined
																						// positions
						Room tempRoom = (Room) gameMap[i][j];
						tempRoom.setVisible();
						gameMap[i][j] = tempRoom;
					}
				}
			}
			break;
		}
	}
	
	/**
	 * This method ensures that an item can only be used once, and is called after resolving an item's use.
	 */
	public void resetLastItem(){
		lastItem = null;
	}

	/**
	 * @return
	 */
	public int getTurnsInvincible() {
		return turnsInvincible;
	}

	/**
	 * 
	 */
	public void reduceTurnsInvincible() {
			--turnsInvincible;
	}

	/**
	 * @return
	 */
	public int getPlayerLives() {
		Player player = (Player) gameMap[playerRow][playerColumn];
		return player.getLives();
	}

	/**
	 * @return
	 */
	public boolean getHasBullet() {
		Player player = (Player) gameMap[playerRow][playerColumn];
		return player.getHasBullet();
	}

	/**
	 * @return
	 */
	public Item.itemType getLastItem() {
		return lastItem;
	}

	/**
	 * 
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
	 * 
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
						int playerToEnemyDistance = (int) Math.round(Math.sqrt(
								(double) ((enemyRow - playerRow) ^ 2) + (double) ((enemyColumn - playerColumn) ^ 2)));
						int seekChance = 25 + playerToEnemyDistance * 2;
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
							if (dirAvailable[a] == dirPref[0] || dirAvailable[a] == dirPref[1])
								dirPrefMove[dirPrefMoveNum] = dirAvailable[a];
						}

						if (dirPrefNum > 0) {
							if (this.rand.nextInt(100) < seekChance) {
								switch (dirPrefMoveNum) {
								case 2:
									rand = this.rand.nextInt(100);
									if (dirPrefMove[0] == UI.direction.UP) {
										if (dirPrefMove[1] == UI.direction.LEFT) {
											if (rand < 50) {
												enemy.move();
												gameMap[enemyRow - 1][enemyColumn] = enemy;
											} else {
												enemy.move();
												gameMap[enemyRow][enemyColumn - 1] = enemy;
											}
										} else {
											if (rand < 50) {
												enemy.move();
												gameMap[enemyRow - 1][enemyColumn] = enemy;
											} else {
												enemy.move();
												gameMap[enemyRow][enemyColumn + 1] = enemy;
											}
										}
									} else {
										if (dirPrefMove[1] == UI.direction.LEFT) {
											if (rand < 50) {
												enemy.move();
												gameMap[enemyRow + 1][enemyColumn] = enemy;
											} else {
												enemy.move();
												gameMap[enemyRow][enemyColumn - 1] = enemy;
											}
										} else {
											if (rand < 50) {
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
								dirPrefNum = 0;
						}
						if (dirPrefNum == 0) {
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
	 * @return
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
	 * This method will "fire" a bullet from the player's position, and remove
	 * the first enemy it encounters from the map.
	 * 
	 * @return {@code true} on a hit, {@code false} otherwise
	 */
	public boolean shoot(UI.direction dir) {
		Player player = (Player) gameMap[playerRow][playerColumn];
		player.useBullet();
		gameMap[playerRow][playerColumn] = player; // Uses player's bullet, then
													// updates the array

		switch (dir) {
		case UP:
			for (int i = playerRow - 1; i >= 0; --i)
				if (gameMap[i][playerColumn] != null) {
					if (gameMap[i][playerColumn].getEntityType() == Entity.entityType.ENEMY) {
						gameMap[i][playerColumn] = null;
						return true;
					} else if (gameMap[i][playerColumn].getEntityType() == Entity.entityType.ROOM) {
						return false;
					}
				}
			break;
		case DOWN:
			for (int i = playerRow + 1; i <= 8; ++i)
				if (gameMap[i][playerColumn] != null) {
					if (gameMap[i][playerColumn].getEntityType() == Entity.entityType.ENEMY) {
						gameMap[i][playerColumn] = null;
						return true;
					} else if (gameMap[i][playerColumn].getEntityType() == Entity.entityType.ROOM) {
						return false;
					}
				}
			break;
		case LEFT:
			for (int i = playerColumn - 1; i >= 0; --i)
				if (gameMap[playerRow][i] != null) {
					if (gameMap[playerRow][i].getEntityType() == Entity.entityType.ENEMY) {
						gameMap[playerRow][i] = null;
						return true;
					} else if (gameMap[i][playerColumn].getEntityType() == Entity.entityType.ROOM) {
						return false;
					}
				}
			break;
		case RIGHT:
			for (int i = playerColumn + 1; i <= 8; ++i)
				if (gameMap[playerRow][i] != null) {
					if (gameMap[playerRow][i].getEntityType() == Entity.entityType.ENEMY) {
						gameMap[playerRow][i] = null;
						return true;
					} else if (gameMap[i][playerColumn].getEntityType() == Entity.entityType.ROOM) {
						return false;
					}
				}
			break;
		}

		return false;
	}

	/**
	 * 
	 */
	public void turnOnDebug() {
		for (int i = 0; i < gameMap.length; i++) {
			for (int j = 0; j < gameMap[0].length; j++) {
				if (gameMap[i][j] != null) {
					switch (gameMap[i][j].getEntityType()) {
					case ITEM:
						Item item = (Item) gameMap[i][j];
						item.setVisible();
						gameMap[i][j] = item;
						break;
					case ENEMY:
						Enemy enemy = (Enemy) gameMap[i][j];
						enemy.setVisible();
						gameMap[i][j] = enemy;
						break;
					case ROOM:
						Room room = (Room) gameMap[i][j];
						room.setVisible();
						gameMap[i][j] = room;
					case PLAYER:
						break;
					}
				}
			}
		}
	}

	/**
	 * 
	 */
	public void turnOffDebug() {
		for (int i = 0; i < gameMap.length; i++) {
			for (int j = 0; j < gameMap[0].length; j++) {
				if (gameMap[i][j] != null) {
					switch (gameMap[i][j].getEntityType()) {
					case ITEM:
						Item item = (Item) gameMap[i][j];
						item.setInvisible();
						gameMap[i][j] = item;
						break;
					case ENEMY:
						Enemy enemy = (Enemy) gameMap[i][j];
						enemy.setInvisible();
						gameMap[i][j] = enemy;
						break;
					case ROOM:
						Room room = (Room) gameMap[i][j];
						room.setInvisible();
						gameMap[i][j] = room;
					case PLAYER:
						break;
					}
				}
			}
		}
	}

	/**
	 * This method will return whether the game is in debug mode. Useful for
	 * loading a save.
	 * 
	 * @return {@code true} if game is in debug mode, {@code false} otherwise
	 */
	public boolean getDebug() {
		return debug;
	}

	/**
	 * 
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
	 * 
	 */
	private void playerLostLife() {
		Player temp = (Player) gameMap[playerRow][playerColumn];
		temp.loseLife();
	}
}

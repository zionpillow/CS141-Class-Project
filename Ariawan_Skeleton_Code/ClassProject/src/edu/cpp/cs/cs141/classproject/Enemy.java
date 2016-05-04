/**
 * 
 */
package edu.cpp.cs.cs141.classproject;

import edu.cpp.cs.cs141.classproject.UI.direction;

/**
 * This class represents an enemy in the game. This class takes certain commands
 * but most actions from this class is randomized.
 * 
 * @author Natanael Ariawan
 */
public class Enemy extends Actives {
	
	/**
	 * This field represents the position of the enemy in the game map. All movement functions and "check-nearby" functions
	 * will operate using the entity's position. Set with the method {@link #spawn()}.
	 */
	private int position[];
	
	/**
	 * This field represents whether or not the enemy has been killed. While {@code true}, the enemy will be allowed to
	 * perform any function as usual. If it is {@code false}, the game will ignore the entity, and it will be
	 * prevented from interacting with the game. Set with the constructor method {@link #Enemy()}.
	 */
	private boolean isAlive;
	
	/**
	 * This constructor method sets the field {@link #isAlive} to {@code true} and activates
	 * the method {@link #spawn()}.
	 */
	public Enemy() {
		// TODO
	}
	
	/* (non-Javadoc)
	 * @see edu.cpp.cs.cs141.classproject.Actives#getPosition()
	 */
	@Override
	public int[] getPosition() {
		// TODO
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.cpp.cs.cs141.classproject.Actives#checkIfAlive()
	 */
	@Override
	public boolean checkIfAlive() {
		// TODO
		return false;
	}

	/* (non-Javadoc)
	 * @see edu.cpp.cs.cs141.classproject.Actives#kill()
	 */
	@Override
	public void kill() {
		// TODO
	}

	/* (non-Javadoc)
	 * @see edu.cpp.cs.cs141.classproject.Actives#move(edu.cpp.cs.cs141.classproject.UI.direction)
	 */
	@Override
	public void move(direction dir) {
		// TODO
	}
	
	/**
	 * This method will randomly spawn the enemy so that the enemy spawns in any
	 * random position throughout the map that is at least 3 squares away from
	 * the spy's initial location [0,0].
	 */
	public void spawn() {
		// TODO
	}
	
	/**
	 * This method will move the enemy in a certain manner so that movement is not
	 * randomized but directed to move the enemy closer towards the player.
	 * 
	 * @param playerPosition the position of the player retrieved from the game engine
	 */
	public void aiMove(int[] playerPosition) {
		// TODO
	}
}

/**
 * 
 */
package edu.cpp.cs.cs141.classproject;

/**
 * This interface outlines the main functions of every entity in the game.
 * 
 * @author Natanael Ariawan
 */
public interface Entity {
	
	/**
	 * This method will return the current position of the entity.
	 * 
	 * @return Returns the position
	 */
	int[] getPosition();
	
	/**
	 * This method will return whether the entity is currently alive.
	 * 
	 * @return Returns {@code true} if alive, {@code false} if dead.
	 */
	boolean checkIfAlive();
	
	/**
	 * This method will "kill" the object, effectively removing it from the game. This allows the game to ignore the entity
	 * after the point at which it has been "killed".
	 */
	void kill();
	
}
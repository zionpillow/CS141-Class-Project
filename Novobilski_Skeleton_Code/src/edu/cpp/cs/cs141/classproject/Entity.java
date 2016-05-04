/**
 * 
 */
package edu.cpp.cs.cs141.classproject;

/**
 * This class represents any entity within the game world.
 * 
 * @author Aidan Novobilski
 */
public abstract class Entity {

	/**
	 * This field represents the position of the entity in the game map. All movement functions and "check-nearby" functions
	 * will operate using the entity's position. Set during constructor, 
	 */
	private int[] position;
	
	/**
	 * This field represents whether or not the entity has been destroyed. While {@code true}, the entity will be allowed to
	 * perform any function as usual. If it is {@code false}, the game will more or less ignore the entity, and it will be
	 * prevented from interacting with the game. Set during constructor and,
	 */
	private boolean isAlive = true;
	
	/**
	 * This method will move the entity in the desired direction. It must be passed a direction from {@link UI.direction}.
	 * If either number in the position is less than {@code 0} or greater than {@code 9}, the move will fail and the
	 * entity will remain stationary.
	 * 
	 * @param dir The direction to move in.
	 */
	public void move(UI.direction dir){
		
	}
	
	/**
	 * This method will return the current position of the entity.
	 * 
	 * @return Returns the position
	 */
	public int[] getPosition(){
		return position;
	}
	
	/**
	 * This method will return whether the entity is currently alive.
	 * 
	 * @return Returns {@code true} if alive, {@code false} if dead.
	 */
	public boolean checkIfAlive(){
		return isAlive;
	}
	
	/**
	 * This method will "kill" the object, effectively removing it from the game. This allows the game to ignore the entity
	 * after the point at which it has been "killed".
	 */
	public void kill(){
		isAlive = false;
	}
}

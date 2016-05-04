/**
 * 
 */
package edu.cpp.cs.cs141.classproject;

/**
 * This abstract class implements the entity interface and also outlines
 * the main functions of all active entities.
 * 
 * @author Natanael Ariawan
 */
public abstract class Actives implements Entity {
	
	/* (non-Javadoc)
	 * @see edu.cpp.cs.cs141.classproject.Entity#getPosition()
	 */
	@Override
	public abstract int[] getPosition();

	/* (non-Javadoc)
	 * @see edu.cpp.cs.cs141.classproject.Entity#checkIfAlive()
	 */
	@Override
	public abstract boolean checkIfAlive();

	/* (non-Javadoc)
	 * @see edu.cpp.cs.cs141.classproject.Entity#kill()
	 */
	@Override
	public abstract void kill();
	
	/**
	 * This method will move the entity in the desired direction. It must be passed a direction from {@link UI.direction}.
	 * If either number in the position is less than {@code 0} or greater than {@code 9}, the move will fail and the
	 * entity will remain stationary.
	 * 
	 * @param dir The direction to move in.
	 */
	public abstract void move(UI.direction dir);
	
}
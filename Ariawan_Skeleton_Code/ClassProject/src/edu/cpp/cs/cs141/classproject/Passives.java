/**
 * 
 */
package edu.cpp.cs.cs141.classproject;

/**
 * This abstract class implements the entity interface and also outlines
 * the main functions of all passive entities.
 * 
 * @author Natanael Ariawan
 */
public abstract class Passives implements Entity {
	
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

}
/**
 * 
 */
package edu.cpp.cs.cs141.classproject;

import java.io.Serializable;

/**
 * This interface outlines the main functions of every entity in the game.
 * 
 * @author Natanael Ariawan
 */
public interface Entity extends Serializable {
	
	/**
	 * This field represents all possible types an entity can possibly have.
	 * 
	 * @author Aidan Novobilski
	 */
	public static enum entityType {PLAYER, ENEMY, ROOM, ITEM};
	
	/**
	 * This method will return the type of the entity.
	 * 
	 * @return Returns the entity's type.
	 */
	public entityType getEntityType();
}
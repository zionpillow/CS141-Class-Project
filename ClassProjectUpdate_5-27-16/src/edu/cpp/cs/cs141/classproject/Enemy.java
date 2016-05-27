/**
 * 
 */
package edu.cpp.cs.cs141.classproject;

import java.io.Serializable;

/**
 * This class represents an enemy in the game. This class takes certain commands
 * but most actions from this class is randomized.
 * 
 * @author Natanael Ariawan
 */
public class Enemy implements Entity, Serializable {

	/**
	 * This field represents the unique ID used for saving and loading via
	 * serialization.
	 */
	private static final long serialVersionUID = 348594919363127780L;

	/**
	 * This field represents whether the player can see the ninja (in case of debug mode)
	 */
	private boolean visible;
	
	/**
	 * 
	 */
	private boolean hasMoved;
	
	/**
	 * The default constructor. By default, the "hard mode" AI is disabled, so hasAI is initialized to {@code false}.
	 */
	public Enemy() {
		visible = false;
		hasMoved = false;
	}
	
	/**
	 * This method will set the room to be visible (the briefcase will show)
	 */
	public void setVisible(){
		visible = true;
	}
	
	/**
	 * 
	 */
	public void move() {
		hasMoved = true;
	}
	
	/**
	 * 
	 */
	public void resetTurn() {
		hasMoved = false;
	}
	
	/* (non-Javadoc)
	 * @see edu.cpp.cs.cs141.classproject.Entity#getEntityType()
	 */
	@Override
	public Entity.entityType getEntityType(){
		return Entity.entityType.ENEMY;
	}
	
	/**
	 * @return
	 */
	public boolean getHasMoved() {
		return hasMoved;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		if(visible)
			return "N";
		return " ";
	}
}

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
	 * This field represents the unique ID used for saving and loading via serialization.
	 */
	private static final long serialVersionUID = -2278136719387442891L;
	
	/**
	 * This field represents whether the AI has its "hard mode" AI enabled.
	 */
	private boolean hasAI;
	
	/**
	 * This field represents whether the player can see the ninja (in case of debug mode)
	 */
	private boolean visible;
	
	/**
	 * The default constructor. By default, the "hard mode" AI is disabled, so hasAI is initialized to {@code false}.
	 */
	public Enemy() {
		hasAI = false;
		visible = false;
	}
	
	/**
	 * This constructor allows the game to set whether the Enemy has its "hard mode" AI enabled. 
	 * 
	 * @param AI Whether the AI is enabled or not.
	 */
	public Enemy(boolean AI){
		hasAI = AI;
		visible = false;
	}
	
	/**
	 * This method will set the room to be visible (the briefcase will show)
	 */
	public void setVisible(){
		visible = true;
	}
	
	/* (non-Javadoc)
	 * @see edu.cpp.cs.cs141.classproject.Entity#getEntityType()
	 */
	public Entity.entityType getEntityType(){
		return Entity.entityType.ENEMY;
	}
	
	/**
	 * This method will return whether the enemy has its "hard mode" AI enabled.
	 * 
	 * @return {@code True} if AI is enabled, {@code false} if it is disabled.
	 */
	public boolean getHasAI(){
		return hasAI;
	}
	
	@Override
	public String toString(){
		if(visible)
			return "N";
		return " ";
	}
}

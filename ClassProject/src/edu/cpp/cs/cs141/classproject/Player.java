/**
 * 
 */
package edu.cpp.cs.cs141.classproject;

import java.io.Serializable;

/**
 * This class represents the player in the game. This class will take commands
 * from the UI to take certain actions such as moving and shooting.
 * 
 * @author Natanael Ariawan
 */
public class Player implements Entity, Serializable {
	
	/**
	 * This field represents the unique ID used for saving and loading via serialization.
	 */
	private static final long serialVersionUID = 8730488362165143607L;

	/**
	 * This field represents the number of lives the player has remaining.
	 */
	private int lives;
	
	/**
	 * This field represents the player's ammo. This field determines whether the player can
	 * shoot or not. This field can be altered by the method {@link } and
	 * is set with the constructor method {@link #Player()}.
	 */
	private boolean hasBullet;
	
	/**
	 * This constructor method sets the fields {@link #lives} to {@code 3}, and {@link #hasBullet} to {@code true}.
	 */
	public Player() {
		lives = 3;
		hasBullet = true;
	}
	
	/**
	 * This method gets whether the player has a bullet to shoot.
	 * 
	 * @return {@code True} if the player has a bullet, {@code false} if they do not.
	 */
	public boolean getHasBullet() {
		return hasBullet;
	}
	
	/**
	 * This method gets the current {@link #lives} of the player to be used in
	 * the game engine and UI, as well as to be interacted upon by other entities in the game.
	 * 
	 * @return the current {@link #lives} of the player
	 */
	public int getLives() {
		return lives;
	}
	
	/**
	 * 
	 */
	public void loseLife() {
		lives -= 1;
	}
	
	/**
	 * This method will trigger when the player wants to shoot. It will signify that their bullet has been used and cannot be used again.
	 */
	public void useBullet() {
		hasBullet = false;
	}
	
	/**
	 * This method allows the player to gain an additional bullet. If they pick up the bullet and they already have one, it is lost.
	 */
	public void gainBullet() {
		hasBullet = true;
	}
	
	/* (non-Javadoc)
	 * @see edu.cpp.cs.cs141.classproject.Entity#getEntityType()
	 */
	public Entity.entityType getEntityType(){
		return Entity.entityType.PLAYER;
	}
	
	@Override
	public String toString(){
		return "@";
	}
}
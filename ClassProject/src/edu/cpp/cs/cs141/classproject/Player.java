/**
 * CS 141: Intro to Programming and Problem Solving
 * Professor: Edwin Rodr&iacute;guez
 * 
 * Final Project
 * 
 * This assignment is final class project involving four team members.
 * In this assignment, students are to create a small, yet interesting,
 * text-based game involving a grid of 81 squares in which the player
 * tries to find a briefcase in 1 of 9 different rooms while avoiding or
 * fighting existing ninjas in the grid. This assignment incorporates
 * all of the knowledge learned throughout the course.
 * 
 * Team Recycle Bin
 * 		<Natanael Ariawan, David Hau, Miguel Menjivar, Aidan Novobilsky>
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
	 * This field represents the unique ID used for saving and loading via
	 * serialization.
	 */
	private static final long serialVersionUID = -5544078866457060795L;

	/**
	 * This field represents the number of lives the player has remaining.
	 */
	private int lives;
	
	/**
	 * This field represents the player's ammo. This field determines whether the player can
	 * shoot or not. This field can be altered by the method {@link } and
	 * is set with the constructor method {@link #Player()}.
	 */
	private int ammo;
	
	/**
	 * 
	 */
	private int maxAmmo;
	
	/**
	 * This constructor method sets the fields {@link #lives} to {@code 3}, and {@link #hasBullet} to {@code true}.
	 */
	public Player(int maxAmmo) {
		lives = 3;
		setMaxAmmo(maxAmmo);
		reload();
	}
	
	/**
	 * This method gets whether the player has a bullet to shoot.
	 * 
	 * @return {@code True} if the player has a bullet, {@code false} if they do not.
	 */
	public int getAmmo() {
		return ammo;
	}
	
	/**
	 * @return
	 */
	public int getMaxAmmo() {
		return maxAmmo;
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
	 * @param maxAmmo
	 */
	public void setMaxAmmo(int maxAmmo) {
		this.maxAmmo = maxAmmo;
	}
	
	/**
	 * 
	 */
	public void gainLife(int life) {
		lives += life;
	}
	
	/**
	 * 
	 */
	public void loseLife() {
		--lives;
	}
	
	/**
	 * This method will trigger when the player wants to shoot. It will signify that their bullet has been used and cannot be used again.
	 */
	public void shoot() {
		--ammo;
	}
	
	/**
	 * This method allows the player to gain an additional bullet. If they pick up the bullet and they already have one, it is lost.
	 */
	public void reload() {
		ammo = maxAmmo;
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

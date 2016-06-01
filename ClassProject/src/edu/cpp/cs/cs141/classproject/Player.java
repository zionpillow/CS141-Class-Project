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
 * @author David Hau
 * @author Miguel Menjivar
 * @author Aidan Novobilski
 */
public class Player implements Entity, Serializable {

	/**
	 * This field represents the unique ID used for saving and loading via
	 * serialization.
	 */
	private static final long serialVersionUID = -5544078866457060595L;

	/**
	 * This field represents the number of lives the player has remaining.
	 */
	private int lives;
	
	/**
	 * This field represents the player's current ammo. This field can be altered
	 * by the method {@link #shoot()} and {@link #reload()} and is set with the
	 * constructor method, {@link #Player(int)}.
	 */
	private int ammo;
	
	/**
	 * This field represents the number of bullets the player can hold. This field
	 * is initialized in the constructor method, {@link #Player(int)}, through the
	 * method {@link #setMaxAmmo(int)}.
	 */
	private int maxAmmo;
	
	/**
	 * This constructor method sets the fields {@link #lives} to 3, {@link #maxAmmo}
	 * to whatever the max ammo the player can hold, which is based on the magazine
	 * {@link Shop} upgrade, and reloads the gun using the method, {@link #reload()}.
	 * 
	 * @param maxAmmo an int that represents the max ammo the player can hold
	 */
	public Player(int maxAmmo) {
		lives = 3;
		setMaxAmmo(maxAmmo);
		reload();
	}
	
	/**
	 * This method retrieves the player's current ammo count.
	 * 
	 * @return an int representing the player's current ammo count
	 */
	public int getAmmo() {
		return ammo;
	}
	
	/**
	 * This method retrieves the max ammo that the player can hold.
	 * 
	 * @return an int representing the max ammo that the player can hold
	 */
	public int getMaxAmmo() {
		return maxAmmo;
	}
	
	/**
	 * This method gets the current {@link #lives} of the player to be used in
	 * the game engine and UI, as well as to be interacted upon by other entities
	 * in the game.
	 * 
	 * @return the current {@link #lives} of the player
	 */
	public int getLives() {
		return lives;
	}
	
	/**
	 * This method sets the player's max ammo to a specified max ammo. The parameter
	 * value will be dictated by the magazine upgrade in the {@link Shop}.
	 * 
	 * @param maxAmmo an int that represents what the player's max ammo is supposed
	 * to be
	 */
	public void setMaxAmmo(int maxAmmo) {
		this.maxAmmo = maxAmmo;
	}
	
	/**
	 * This method increases the player's life by the number specified when the
	 * player moves onto the next level. The number of lives the player gains will
	 * be dictated by the health upgrade in the {@link Shop}.
	 * 
	 * @param life the number of lives the player is gains
	 */
	public void gainLife(int life) {
		lives += life;
	}
	
	/**
	 * This method decreases the player's life by 1, which will be called when the
	 * player is killed by an adjacent ninja.
	 */
	public void loseLife() {
		--lives;
	}
	
	/**
	 * This method reduces the player's ammo by 1, which will be called when the
	 * player successfully shoots (which will only be possible if the player has
	 * one or more ammo).
	 */
	public void shoot() {
		--ammo;
	}
	
	/**
	 * This method allows the player to reload their gun, which sets the field
	 * {@link #ammo} equal to the player's {@link #maxAmmo}.
	 */
	public void reload() {
		ammo = maxAmmo;
	}
	
	/* (non-Javadoc)
	 * @see edu.cpp.cs.cs141.classproject.Entity#getEntityType()
	 */
	@Override
	public Entity.entityType getEntityType(){
		return Entity.entityType.PLAYER;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "@";
	}
	
}

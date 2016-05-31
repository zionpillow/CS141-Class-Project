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
 * This class represents an enemy in the game. This class takes certain commands
 * but most actions from this class is randomized.
 * 
 * @author Natanael Ariawan
 * @author David Hau
 * @author Miguel Menjivar
 * @author Aidan Novobilsky
 */
public class Enemy implements Entity, Serializable {

	/**
	 * This field represents the unique ID used for saving and loading via
	 * serialization.
	 */
	private static final long serialVersionUID = 348594319363127780L;

	/**
	 * This field represents whether the ninja is visible on the map.
	 * This field is set to {@code false} when the class is initialized and
	 * can only be set to {@code true} with the method {@link #setVisible()}
	 * when the game is started in debug mode.
	 */
	private boolean visible;
	
	/**
	 * This field represents whether the ninja has moved during the turn where
	 * each ninja is moved around. This field makes sure that each ninja only moves
	 * once per turn. This field is set to {@code false} when the class is initialized
	 * and can be set to {@code true} with the method {@link #move()} when the enemy
	 * has moved and {@code false} with the method {@link #resetTurn()} at the start
	 * of the enemies' turn.
	 */
	private boolean hasMoved;
	
	/**
	 * This constructor method initializes the class and defaults every ninja to
	 * be invisible by setting the field, {@link #visible}, to {@code false} and
	 * hasn't moved by setting the field, {@link #hasMoved}, to {@code false}.
	 */
	public Enemy() {
		visible = false;
		hasMoved = false;
	}
	
	/**
	 * This method sets the field, {@link #visible}, to true when the game is
	 * initialized to be in debug mode.
	 */
	public void setVisible(){
		visible = true;
	}
	
	/**
	 * This method sets the field, {@link #hasMoved}, to {@code true} after
	 * the ninja moves.
	 */
	public void move() {
		hasMoved = true;
	}
	
	/**
	 * This method sets the field, {@link #hasMoved}, to {@code false} at the
	 * beginning of the enemies' turn.
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
	 * This method returns the value of the field {@link #hasMoved} to check
	 * whether or not the ninja has moved.
	 * 
	 * @return {@code true} if the enemy has moved and {@code false} if the enemy
	 * hasn't moved
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

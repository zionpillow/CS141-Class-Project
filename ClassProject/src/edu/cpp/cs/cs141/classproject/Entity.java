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
 * This interface outlines the main functions of every entity in the game.
 * 
 * @author Natanael Ariawan
 * @author David Hau
 * @author Miguel Menjivar
 * @author Aidan Novobilski
 */
public interface Entity extends Serializable {
	
	/**
	 * This enumeration represents all of the possible types an entity can have.
	 * 
	 * @author Natanael Ariawan
	 * @author David Hau
	 * @author Miguel Menjivar
	 * @author Aidan Novobilski
	 */
	public static enum entityType {PLAYER, ENEMY, ROOM, ITEM};
	
	/**
	 * This method will return the type of the entity.
	 * 
	 * @return the entity's type.
	 */
	public entityType getEntityType();
	
	/**
	 * This method will return the symbol of the entity on the map as a string.
	 * 
	 * @return the entity's symbol
	 */
	public String toString();
}
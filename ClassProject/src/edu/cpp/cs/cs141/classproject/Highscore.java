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
 * This class represents a single score in the list of existing player highscores.
 * The player highscores will be saved and loaded as an array of {@link Highscore}
 * objects inside the {@link GameEngine}.
 * 
 * @author Natanael Ariawan
 * @author David Hau
 * @author Miguel Menjivar
 * @author Aidan Novobilski
 */
public class Highscore implements Serializable, Comparable<Highscore> {

	/**
	 * This field represents the unique ID used for saving and loading via
	 * serialization.
	 */
	private static final long serialVersionUID = -5987175536086085428L;

	/**
	 * This field represents the player's name associated with their {@link #score}.
	 * This field will be initialized in the constructor method,
	 * {@link #Highscore(String, int)} and can never be changed.
	 */
	private String name;
	
	/**
	 * This field represents the player's score associated with their {@link #name}.
	 * This field will be initialized in the constructor method,
	 * {@link #Highscore(String, int)} and can never be changed.
	 */
	private int score;
	
	/**
	 * This constructor method initializes the fields {@link #name} and {@link #score}
	 * based on the name and score parameters taken. The name and score will be obtained
	 * from the {@link UI} and {@link Map}, respectively.
	 * 
	 * @param name a string representing the player's name
	 * @param score an int representing the player's score
	 */
	public Highscore(String name, int score) {
		this.name = name;
		this.score = score;
	}

	/**
	 * This method allows the {@link UI} to get the {@link #name} associated with
	 * each {@link #score} through the {@link GameEngine} to be printed in the list
	 * of highscores.
	 * 
	 * @return a string from the field {@link #name} representing the player's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method allows the {@link UI} to get the {@link #score} associated with
	 * each {@link #name} through the {@link GameEngine} to be printed in the list
	 * of highscores.
	 * 
	 * @return an int from the field {@link #score} representing the player's score
	 */
	public int getScore() {
		return score;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Highscore highscore) {
		return highscore.getScore() - score;
	}
	
}
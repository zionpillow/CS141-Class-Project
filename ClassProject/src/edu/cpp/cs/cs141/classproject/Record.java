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
 * This class contains all of the player's records, including top records as well
 * as accumulative records.
 * 
 * @author Natanael Ariawan
 * @author David Hau
 * @author Miguel Menjivar
 * @author Aidan Novobilski
 */
public class Record implements Serializable {
	
	/**
	 * This field represents the unique ID used for saving and loading via
	 * serialization.
	 */
	private static final long serialVersionUID = -5542581930624597294L;

	/**
	 * This field records the highest level reached in normal game mode in a single
	 * gameplay.
	 */
	private int highestLevelNormal;

	/**
	 * This field records the highest level reached in hard game mode in a single
	 * gameplay.
	 */
	private int highestLevelHard;
	
	/**
	 * This field records the highest remaining player lives in any game mode in
	 * a single gameplay.
	 */
	private int livesRemaining;
	
	/**
	 * This field records the highest number of turns survived in any game mode in
	 * a single gameplay.
	 */
	private int turnsSurvived;
	
	/**
	 * This field records the highest number of rooms checked in any game mode in
	 * a single gameplay.
	 */
	private int roomsChecked;
	
	/**
	 * This field records the highest number of items obtained in any game mode in
	 * a single gameplay.
	 */
	private int itemsObtained;
	
	/**
	 * This field records the highest number of enemies killed in any game mode in
	 * a single gameplay.
	 */
	private int enemiesKilled;
	
	/**
	 * This field records the accumulative number of levels cleared in normal game
	 * mode.
	 */
	private int totalLevelNormal;
	
	/**
	 * This field records the accumulative number of levels cleared in hard game
	 * mode.
	 */
	private int totalLevelHard;
	
	/**
	 * This field records the accumulative number of rooms checked in any game mode.
	 */
	private int totalRoomsChecked;
	
	/**
	 * This field records the accumulative number of items obtained in any game mode.
	 */
	private int totalItemsObtained;
	
	/**
	 * This field records the accumulative number of enemies killed in any game mode.
	 */
	private int totalEnemiesKilled;
	
	/**
	 * This constructor method initializes every field contained in this class to 0.
	 */
	public Record() {
		highestLevelNormal = 0;
		highestLevelHard = 0;
		livesRemaining = 0;
		turnsSurvived = 0;
		roomsChecked = 0;
		itemsObtained = 0;
		enemiesKilled = 0;
		totalLevelNormal = 0;
		totalLevelHard = 0;
		totalRoomsChecked = 0;
		totalItemsObtained = 0;
		totalEnemiesKilled = 0;
	}
	
	/**
	 * This method retrieves the value of every field in this class and compiles the
	 * data into an array of integers, which is then returned to be used in the
	 * {@link GameEngine}.
	 * 
	 * @return an array of ints that represents the value of every field contained
	 * in this class
	 */
	public int[] getRecords() {
		int[] records = new int[12];
		records[0] = highestLevelNormal;
		records[1] = highestLevelHard;
		records[2] = livesRemaining;
		records[3] = turnsSurvived;
		records[4] = roomsChecked;
		records[5] = itemsObtained;
		records[6] = enemiesKilled;
		records[7] = totalLevelNormal;
		records[8] = totalLevelHard;
		records[9] = totalRoomsChecked;
		records[10] = totalItemsObtained;
		records[11] = totalEnemiesKilled;
		return records;
	}
	
	/**
	 * This method sets the field {@link #highestLevelNormal} using the given levels
	 * cleared passed as a parameter. This method will only be called if the
	 * parameter passed is higher than the current value of the field.
	 * 
	 * @param highestLevelNormal the number of levels the player cleared in that
	 * specific gameplay
	 */
	public void setHighestLevelNormal(int highestLevelNormal) {
		this.highestLevelNormal = highestLevelNormal;
	}

	/**
	 * This method sets the field {@link #highestLevelHard} using the given levels
	 * cleared passed as a parameter. This method will only be called if the
	 * parameter passed is higher than the current value of the field.
	 * 
	 * @param highestLevelHard the number of levels the player cleared in that
	 * specific gameplay
	 */
	public void setHighestLevelHard(int highestLevelHard) {
		this.highestLevelHard = highestLevelHard;
	}

	/**
	 * This method sets the field {@link #highestLevelHard} using the given number
	 * of lives remaining passed as a parameter. This method will only be called if
	 * the parameter passed is higher than the current value of the field.
	 * 
	 * @param livesRemaining the number of lives the player has remaining in that
	 * specific gameplay
	 */
	public void setLivesRemaining(int livesRemaining) {
		this.livesRemaining = livesRemaining;
	}

	/**
	 * This method sets the field {@link #turnsSurvived} using the given number
	 * of turns survived passed as a parameter. This method will only be called if
	 * the parameter passed is higher than the current value of the field.
	 * 
	 * @param turnsSurvived the number of turns the player survived in that specific
	 * gameplay
	 */
	public void setTurnsSurvived(int turnsSurvived) {
		this.turnsSurvived = turnsSurvived;
	}

	/**
	 * This method sets the field {@link #roomsChecked} using the given number
	 * of rooms checked passed as a parameter. This method will only be called if
	 * the parameter passed is higher than the current value of the field.
	 * 
	 * @param roomsChecked the number of rooms the player checked in that specific
	 * gameplay
	 */
	public void setRoomsChecked(int roomsChecked) {
		this.roomsChecked = roomsChecked;
	}

	/**
	 * This method sets the field {@link #itemsObtained} using the given number
	 * of items obtained passed as a parameter. This method will only be called if
	 * the parameter passed is higher than the current value of the field.
	 * 
	 * @param itemsObtained the number of items the player obtained in that specific
	 * gameplay
	 */
	public void setItemsObtained(int itemsObtained) {
		this.itemsObtained = itemsObtained;
	}
	
	/**
	 * This method sets the field {@link #enemiesKilled} using the given number
	 * of enemies killed passed as a parameter. This method will only be called if
	 * the parameter passed is higher than the current value of the field.
	 * 
	 * @param enemiesKilled the number of enemies the player killed in that specific
	 * gameplay
	 */
	public void setEnemiesKilled(int enemiesKilled) {
		this.enemiesKilled = enemiesKilled;
	}

	/**
	 * This method updates the field {@link #totalLevelNormal} by increasing it by
	 * the amount of levels cleared during this specific gameplay if the player is
	 * playing in normal mode. This method will always be called after a normal mode
	 * game is completed.
	 * 
	 * @param levelsCleared the number of normal levels cleared during this gameplay
	 */
	public void increaseTotalLevelNormal(int levelsCleared) {
		totalLevelNormal += levelsCleared;
	}

	/**
	 * This method updates the field {@link #totalLevelHard} by increasing it by
	 * the amount of levels cleared during this specific gameplay if the player is
	 * playing in hard mode. This method will always be called after a hard mode
	 * game is completed.
	 * 
	 * @param levelsCleared the number of hard levels cleared during this gameplay
	 */
	public void increaseTotalLevelHard(int levelsCleared) {
		totalLevelHard += levelsCleared;
	}

	/**
	 * This method updates the field {@link #totalRoomsChecked} by increasing it by
	 * the amount of rooms checked during this specific gameplay regardless of game
	 * mode. This method will always be called after a game in any mode is completed.
	 * 
	 * @param roomsChecked the number of rooms checked during this gameplay
	 */
	public void increaseTotalRoomsChecked(int roomsChecked) {
		totalRoomsChecked += roomsChecked;
	}

	/**
	 * This method updates the field {@link #totalItemsObtained} by increasing it by
	 * the amount of items obtained during this specific gameplay regardless of game
	 * mode. This method will always be called after a game in any mode is completed.
	 * 
	 * @param itemsObtained the number of items obtained during this gameplay
	 */
	public void increaseTotalItemsObtained(int itemsObtained) {
		totalItemsObtained += itemsObtained;
	}

	/**
	 * This method updates the field {@link #totalEnemiesKilled} by increasing it by
	 * the amount of enemies killed during this specific gameplay regardless of game
	 * mode. This method will always be called after a game in any mode is completed.
	 * 
	 * @param enemiesKilled the number of enemies killed during this gameplay
	 */
	public void increaseTotalEnemiesKilled(int enemiesKilled) {
		totalEnemiesKilled += enemiesKilled;
	}
	
}
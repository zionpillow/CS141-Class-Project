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
 * @author Natanael
 *
 */
public class Record implements Serializable {
	
	/**
	 * This field represents the unique ID used for saving and loading via
	 * serialization.
	 */
	private static final long serialVersionUID = -5542581930624597494L;

	private int highestLevelNormal;

	private int highestLevelHard;
	
	private int livesRemaining;
	
	private int turnsSurvived;
	
	private int roomsChecked;
	
	private int itemsObtained;
	
	private int enemiesKilled;
	
	private int totalLevelNormal;
	
	private int totalLevelHard;
	
	private int totalRoomsChecked;
	
	private int totalItemsObtained;
	
	private int totalEnemiesKilled;
	
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
	
	public void setHighestLevelNormal(int highestLevelNormal) {
		this.highestLevelNormal = highestLevelNormal;
	}

	public void setHighestLevelHard(int highestLevelHard) {
		this.highestLevelHard = highestLevelHard;
	}

	public void setLivesRemaining(int livesRemaining) {
		this.livesRemaining = livesRemaining;
	}

	public void setTurnsSurvived(int turnsSurvived) {
		this.turnsSurvived = turnsSurvived;
	}

	public void setRoomsChecked(int roomsChecked) {
		this.roomsChecked = roomsChecked;
	}

	public void setItemsObtained(int itemsObtained) {
		this.itemsObtained = itemsObtained;
	}

	public void setEnemiesKilled(int enemiesKilled) {
		this.enemiesKilled = enemiesKilled;
	}

	public void increaseTotalLevelNormal(int levelsCleared) {
		totalLevelNormal += levelsCleared;
	}

	public void increaseTotalLevelHard(int levelsCleared) {
		totalLevelHard += levelsCleared;
	}

	public void increaseTotalRoomsChecked(int roomsChecked) {
		totalRoomsChecked += roomsChecked;
	}

	public void increaseTotalItemsObtained(int itemsObtained) {
		totalItemsObtained += itemsObtained;
	}

	public void increaseTotalEnemiesKilled(int enemiesKilled) {
		totalEnemiesKilled += enemiesKilled;
	}
	
}
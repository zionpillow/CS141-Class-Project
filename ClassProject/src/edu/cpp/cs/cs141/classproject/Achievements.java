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
 * This class keeps track of all existing achievements that the player could
 * Receive and what the player has already achieved or not.
 * 
 * @author Natanael Ariawan
 * @author David Hau
 * @author Miguel Menjivar
 * @author Aidan Novobilski
 */
public class Achievements implements Serializable {

	/**
	 * This field represents the unique ID used for saving and loading via
	 * serialization.
	 */
	private static final long serialVersionUID = -1109622401625934316L;

	/**
	 * This field represents the achievement of clearing 1 normal level.
	 * This field is set to {@code false} when the class is initialized and
	 * can only be set to {@code true} with the method {@link #setNormalLevelsCleared1()}.
	 */
	private boolean normalLevelsCleared1;
	
	/**
	 * This field represents the achievement of clearing 10 normal levels.
	 * This field is set to {@code false} when the class is initialized and
	 * can only be set to {@code true} with the method {@link #setNormalLevelsCleared10()}.
	 */
	private boolean normalLevelsCleared10;
	
	/**
	 * This field represents the achievement of clearing 50 normal levels.
	 * This field is set to {@code false} when the class is initialized and
	 * can only be set to {@code true} with the method {@link #setNormalLevelsCleared50()}.
	 */
	private boolean normalLevelsCleared50;
	
	/**
	 * This field represents the achievement of clearing 100 normal levels.
	 * This field is set to {@code false} when the class is initialized and
	 * can only be set to {@code true} with the method {@link #setNormalLevelsCleared100()}.
	 */
	private boolean normalLevelsCleared100;
	
	/**
	 * This field represents the achievement of clearing 1 hard level.
	 * This field is set to {@code false} when the class is initialized and
	 * can only be set to {@code true} with the method {@link #setHardLevelsCleared1()}.
	 */
	private boolean hardLevelsCleared1;
	
	/**
	 * This field represents the achievement of clearing 10 hard levels.
	 * This field is set to {@code false} when the class is initialized and
	 * can only be set to {@code true} with the method {@link #setHardLevelsCleared10()}.
	 */
	private boolean hardLevelsCleared10;
	
	/**
	 * This field represents the achievement of clearing 50 hard levels.
	 * This field is set to {@code false} when the class is initialized and
	 * can only be set to {@code true} with the method {@link #setHardLevelsCleared50()}.
	 */
	private boolean hardLevelsCleared50;
	
	/**
	 * This field represents the achievement of clearing 100 hard levels.
	 * This field is set to {@code false} when the class is initialized and
	 * can only be set to {@code true} with the method {@link #setHardLevelsCleared100()}.
	 */
	private boolean hardLevelsCleared100;
	
	/**
	 * This field represents the achievement of checking 1 room in any game mode.
	 * This field is set to {@code false} when the class is initialized and
	 * can only be set to {@code true} with the method {@link #setRoomsChecked1()}.
	 */
	private boolean roomsChecked1;
	
	/**
	 * This field represents the achievement of checking 10 rooms in any game mode.
	 * This field is set to {@code false} when the class is initialized and
	 * can only be set to {@code true} with the method {@link #setRoomsChecked10()}.
	 */
	private boolean roomsChecked10;
	
	/**
	 * This field represents the achievement of checking 50 rooms in any game mode.
	 * This field is set to {@code false} when the class is initialized and
	 * can only be set to {@code true} with the method {@link #setRoomsChecked50()}.
	 */
	private boolean roomsChecked50;
	
	/**
	 * This field represents the achievement of checking 100 rooms in any game mode.
	 * This field is set to {@code false} when the class is initialized and
	 * can only be set to {@code true} with the method {@link #setRoomsChecked100()}.
	 */
	private boolean roomsChecked100;
	
	/**
	 * This field represents the achievement of obtaining 1 item in any game mode.
	 * This field is set to {@code false} when the class is initialized and
	 * can only be set to {@code true} with the method {@link #setItemsObtained1()}.
	 */
	private boolean itemsObtained1;
	
	/**
	 * This field represents the achievement of obtaining 10 items in any game mode.
	 * This field is set to {@code false} when the class is initialized and
	 * can only be set to {@code true} with the method {@link #setItemsObtained10()}.
	 */
	private boolean itemsObtained10;
	
	/**
	 * This field represents the achievement of obtaining 50 items in any game mode.
	 * This field is set to {@code false} when the class is initialized and
	 * can only be set to {@code true} with the method {@link #setItemsObtained50()}.
	 */
	private boolean itemsObtained50;
	
	/**
	 * This field represents the achievement of obtaining 100 items in any game mode.
	 * This field is set to {@code false} when the class is initialized and
	 * can only be set to {@code true} with the method {@link #setItemsObtained100()}.
	 */
	private boolean itemsObtained100;
	
	/**
	 * This field represents the achievement of killing 1 enemy in any game mode.
	 * This field is set to {@code false} when the class is initialized and
	 * can only be set to {@code true} with the method {@link #setEnemiesKilled1()}.
	 */
	private boolean enemiesKilled1;

	/**
	 * This field represents the achievement of killing 10 enemies in any game mode.
	 * This field is set to {@code false} when the class is initialized and
	 * can only be set to {@code true} with the method {@link #setEnemiesKilled10()}.
	 */
	private boolean enemiesKilled10;

	/**
	 * This field represents the achievement of killing 50 enemies in any game mode.
	 * This field is set to {@code false} when the class is initialized and
	 * can only be set to {@code true} with the method {@link #setEnemiesKilled50()}.
	 */
	private boolean enemiesKilled50;

	/**
	 * This field represents the achievement of killing 100 enemies in any game mode.
	 * This field is set to {@code false} when the class is initialized and
	 * can only be set to {@code true} with the method {@link #setEnemiesKilled100()}.
	 */
	private boolean enemiesKilled100;

	/**
	 * This field represents the achievement of activating the cheat code.
	 * This field is set to {@code false} when the class is initialized and
	 * can only be set to {@code true} with the method {@link #setCheat()}.
	 */
	private boolean cheat;
	
	/**
	 * This constructor method initializes the class and sets all fields to
	 * {@code false}.
	 */
	public Achievements() {
		normalLevelsCleared1 = false;
		normalLevelsCleared10 = false;
		normalLevelsCleared50 = false;
		normalLevelsCleared100 = false;
		hardLevelsCleared1 = false;
		hardLevelsCleared10 = false;
		hardLevelsCleared50 = false;
		hardLevelsCleared100 = false;
		roomsChecked1 = false;
		roomsChecked10 = false;
		roomsChecked50 = false;
		roomsChecked100 = false;
		enemiesKilled1 = false;
		enemiesKilled10 = false;
		enemiesKilled50 = false;
		enemiesKilled100 = false;
		itemsObtained1 = false;
		itemsObtained10 = false;
		itemsObtained50 = false;
		itemsObtained100 = false;
		cheat = false;
	}
	
	/**
	 * This method returns the status of all the fields in this class, meaning
	 * all 21 achievements, in the form of an array of booleans.
	 * 
	 * @return an array of 21 booleans each corresponding to the status of
	 * each achievement
	 */
	public boolean[] getAchievements() {
		boolean achievements[] = new boolean[21];
		achievements[0] = normalLevelsCleared1;
		achievements[1] = normalLevelsCleared10;
		achievements[2] = normalLevelsCleared50;
		achievements[3] = normalLevelsCleared100;
		achievements[4] = hardLevelsCleared1;
		achievements[5] = hardLevelsCleared10;
		achievements[6] = hardLevelsCleared50;
		achievements[7] = hardLevelsCleared100;
		achievements[8] = roomsChecked1;
		achievements[9] = roomsChecked10;
		achievements[10] = roomsChecked50;
		achievements[11] = roomsChecked100;
		achievements[12] = enemiesKilled1;
		achievements[13] = enemiesKilled10;
		achievements[14] = enemiesKilled50;
		achievements[15] = enemiesKilled100;
		achievements[16] = itemsObtained1;
		achievements[17] = itemsObtained10;
		achievements[18] = itemsObtained50;
		achievements[19] = itemsObtained100;
		achievements[20] = cheat;
		return achievements;
	}
	
	/**
	 * This method sets the field, {@link #normalLevelsCleared1}, to {@code true}
	 * once the achievement has been obtained.
	 */
	public void setNormalLevelsCleared1() {
		normalLevelsCleared1 = true;
	}
	
	/**
	 * This method sets the field, {@link #normalLevelsCleared10}, to {@code true}
	 * once the achievement has been obtained.
	 */
	public void setNormalLevelsCleared10() {
		normalLevelsCleared10 = true;
	}
	
	/**
	 * This method sets the field, {@link #normalLevelsCleared50}, to {@code true}
	 * once the achievement has been obtained.
	 */
	public void setNormalLevelsCleared50() {
		normalLevelsCleared50 = true;
	}
	
	/**
	 * This method sets the field, {@link #normalLevelsCleared100}, to {@code true}
	 * once the achievement has been obtained.
	 */
	public void setNormalLevelsCleared100() {
		normalLevelsCleared100 = true;
	}
	
	/**
	 * This method sets the field, {@link #hardLevelsCleared1}, to {@code true}
	 * once the achievement has been obtained.
	 */
	public void setHardLevelsCleared1() {
		hardLevelsCleared1 = true;
	}
	
	/**
	 * This method sets the field, {@link #hardLevelsCleared10}, to {@code true}
	 * once the achievement has been obtained.
	 */
	public void setHardLevelsCleared10() {
		hardLevelsCleared10 = true;
	}
	
	/**
	 * This method sets the field, {@link #hardLevelsCleared50}, to {@code true}
	 * once the achievement has been obtained.
	 */
	public void setHardLevelsCleared50() {
		hardLevelsCleared50 = true;
	}
	
	/**
	 * This method sets the field, {@link #hardLevelsCleared100}, to {@code true}
	 * once the achievement has been obtained.
	 */
	public void setHardLevelsCleared100() {
		hardLevelsCleared100 = true;
	}
	
	/**
	 * This method sets the field, {@link #roomsChecked1}, to {@code true}
	 * once the achievement has been obtained.
	 */
	public void setRoomsChecked1() {
		roomsChecked1 = true;
	}
	
	/**
	 * This method sets the field, {@link #roomsChecked10}, to {@code true}
	 * once the achievement has been obtained.
	 */
	public void setRoomsChecked10() {
		roomsChecked10 = true;
	}
	
	/**
	 * This method sets the field, {@link #roomsChecked50}, to {@code true}
	 * once the achievement has been obtained.
	 */
	public void setRoomsChecked50() {
		roomsChecked50 = true;
	}
	
	/**
	 * This method sets the field, {@link #roomsChecked100}, to {@code true}
	 * once the achievement has been obtained.
	 */
	public void setRoomsChecked100() {
		roomsChecked100 = true;
	}
	
	/**
	 * This method sets the field, {@link #itemsObtained1}, to {@code true}
	 * once the achievement has been obtained.
	 */
	public void setItemsObtained1() {
		itemsObtained1 = true;
	}
	
	/**
	 * This method sets the field, {@link #itemsObtained10}, to {@code true}
	 * once the achievement has been obtained.
	 */
	public void setItemsObtained10() {
		itemsObtained10 = true;
	}
	
	/**
	 * This method sets the field, {@link #itemsObtained50}, to {@code true}
	 * once the achievement has been obtained.
	 */
	public void setItemsObtained50() {
		itemsObtained50 = true;
	}
	
	/**
	 * This method sets the field, {@link #itemsObtained100}, to {@code true}
	 * once the achievement has been obtained.
	 */
	public void setItemsObtained100() {
		itemsObtained100 = true;
	}
	
	/**
	 * This method sets the field, {@link #enemiesKilled1}, to {@code true}
	 * once the achievement has been obtained.
	 */
	public void setEnemiesKilled1() {
		enemiesKilled1 = true;
	}

	/**
	 * This method sets the field, {@link #enemiesKilled10}, to {@code true}
	 * once the achievement has been obtained.
	 */
	public void setEnemiesKilled10() {
		enemiesKilled10 = true;
	}

	/**
	 * This method sets the field, {@link #enemiesKilled50}, to {@code true}
	 * once the achievement has been obtained.
	 */
	public void setEnemiesKilled50() {
		enemiesKilled50 = true;
	}

	/**
	 * This method sets the field, {@link #enemiesKilled100}, to {@code true}
	 * once the achievement has been obtained.
	 */
	public void setEnemiesKilled100() {
		enemiesKilled100 = true;
	}

	/**
	 * This method sets the field, {@link #cheat}, to {@code true}
	 * once the achievement has been obtained.
	 */
	public void setCheat() {
		cheat = true;
	}
	
}
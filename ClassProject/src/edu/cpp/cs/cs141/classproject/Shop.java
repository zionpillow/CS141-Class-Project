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
public class Shop implements Serializable {

	/**
	 * This field represents the unique ID used for saving and loading via
	 * serialization.
	 */
	private static final long serialVersionUID = -1270016899582463809L;

	public static enum upgrades {
		VISION, MAGAZINE, HEALTH, GODMODE
	};
	
	private int money;
	
	private int vision;
	
	private int magazine;
	
	private int health;
	
	private int visionCost;
	
	private int magazineCost;
	
	private int healthCost;
	
	private boolean godMode;
	
	public Shop() {
		money = 0;
		vision = 1;
		magazine = 1;
		health = 1;
		visionCost = 100;
		magazineCost = 100;
		healthCost = 100;
		godMode = false;
	}
	
	public void addMoney(int money) {
		this.money += money;
	}
	
	public void buyVision() {
		money -= visionCost;
		++vision;
		switch (vision) {
		case 2:
			visionCost = 300;
			break;
		case 3:
			visionCost = 600;
			break;
		case 4:
			visionCost = 1000;
			break;
		default:
			visionCost = 0;
			break;
		}
	}
	
	public void buyMagazine() {
		money -= magazineCost;
		++magazine;
		switch (magazine) {
		case 2:
			magazineCost = 300;
			break;
		case 3:
			magazineCost = 600;
			break;
		case 4:
			magazineCost = 1000;
			break;
		default:
			magazineCost = 0;
			break;
		}
	}
	
	public void buyHealth() {
		money -= healthCost;
		++health;
		switch (health) {
		case 2:
			healthCost = 300;
			break;
		case 3:
			healthCost = 600;
			break;
		case 4:
			healthCost = 1000;
			break;
		default:
			healthCost = 0;
			break;
		}
	}
	
	public void buyGodMode() {
		money -= 4000;
		godMode = true;
	}
	
	public int getMoney() {
		return money;
	}

	public int getVision() {
		return vision;
	}

	public int getMagazine() {
		return magazine;
	}

	public int getHealth() {
		return health;
	}
	
	public int getVisionCost() {
		return visionCost;
	}
	
	public int getMagazineCost() {
		return magazineCost;
	}
	
	public int getHealthCost() {
		return healthCost;
	}
	
	public boolean getGodMode() {
		return godMode;
	}
	
}
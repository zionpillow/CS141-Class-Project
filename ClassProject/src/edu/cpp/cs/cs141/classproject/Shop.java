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
 * This class keeps track of the upgrades that the player has bought and how much
 * money the player has. Money can be obtained through {@link Achievements} and
 * upgrades are directly used in the {@link GameEngine} to be sent as parameters to
 * the {@link Map}.
 * 
 * @author Natanael Ariawan
 * @author David Hau
 * @author Miguel Menjivar
 * @author Aidan Novobilski
 */
public class Shop implements Serializable {

	/**
	 * This field represents the unique ID used for saving and loading via
	 * serialization.
	 */
	private static final long serialVersionUID = -1270016899582463609L;

	/**
	 * This enumeration represents the four different types of possible items that
	 * can be bought in the shop.
	 * 
	 * @author Natanael Ariawan
	 * @author David Hau
	 * @author Miguel Menjivar
	 * @author Aidan Novobilski
	 */
	public static enum upgrades {
		VISION, MAGAZINE, HEALTH, GODMODE
	};
	
	/**
	 * This field represents the money the player currently has. This field can be
	 * reduced when items are bought through the methods, {@link #buyHealth()},
	 * {@link #buyMagazine()}, {@link #buyVision()}, and {@link #buyGodMode()}, and
	 * increased when achievements are obtained through the method,
	 * {@link #addMoney(int)}.
	 */
	private int money;
	
	/**
	 * This field represents the player's current night vision goggles's level.
	 * This field dictates how far the player can see using the
	 * {@link Map#look(edu.cpp.cs.cs141.classproject.UI.direction, int)} method.
	 */
	private int vision;
	
	/**
	 * This field represents the player's current magazine level. This field dictates
	 * the amount of ammo the player can hold and is used to set the player's max
	 * ammo using the {@link Map#setMaxAmmo(int)} method.
	 */
	private int magazine;
	
	/**
	 * This field represents the player's current health level. This field dictates
	 * how much health the player gets when moving onto the next level and is used
	 * in the {@link Map#nextLevel(int)} method.
	 */
	private int health;
	
	/**
	 * This field represents how much the vision upgrade currently costs. Every time
	 * an upgrade is bought the price is increased in the method,
	 * {@link #buyVision()}. Once the highest upgrade is bought, this field will be
	 * set to 0.
	 */
	private int visionCost;
	
	/**
	 * This field represents how much the magazine upgrade currently costs. Every
	 * time an upgrade is bought the price is increased in the method,
	 * {@link #buyMagazine()}. Once the highest upgrade is bought, this field will
	 * be set to 0.
	 */
	private int magazineCost;
	
	/**
	 * This field represents how much the health upgrade currently costs. Every
	 * time an upgrade is bought the price is increased in the method,
	 * {@link #buyHealth()}. Once the highest upgrade is bought, this field will
	 * be set to 0.
	 */
	private int healthCost;
	
	/**
	 * This field represents whether or not the player has obtained God mode or not.
	 * When God mode is obtained, the player can play in this new game mode.
	 */
	private boolean godMode;
	
	/**
	 * This constructor method sets the player's money to 0, each upgrade to level 1,
	 * each upgrade cost to 100, and {@link #godMode} to false.
	 */
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
	
	/**
	 * This method adds money based on the achievement that's been obtained.
	 * Different achievements will give different amounts of money, which will be
	 * passed as an integer parameter to this method from the {@link GameEngine}.
	 * 
	 * @param money the amount of money to be given to the player
	 */
	public void addMoney(int money) {
		this.money += money;
	}
	
	/**
	 * This method "buys" one vision upgrade, which increases the {@link #vision}
	 * upgrade level, decreases the player's {@link #money} based on the current
	 * {@link #visionCost}, and changes the {@link #visionCost} accordingly.
	 */
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
	
	/**
	 * This method "buys" one magazine upgrade, which increases the {@link #magazine}
	 * upgrade level, decreases the player's {@link #money} based on the current
	 * {@link #magazineCost}, and changes the {@link #magazineCost} accordingly.
	 */
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
	
	/**
	 * This method "buys" one health upgrade, which increases the {@link #health}
	 * upgrade level, decreases the player's {@link #money} based on the current
	 * {@link #healthCost}, and changes the {@link #healthCost} accordingly.
	 */
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
	
	/**
	 * This method "buys" God mode, which sets {@link #godMode} to {@code true} and
	 * decreases the player's {@link #money} by 4000.
	 */
	public void buyGodMode() {
		money -= 4000;
		godMode = true;
	}
	
	/**
	 * This method retrieves the player's current {@link #money} to be used in the
	 * method {@link UI#printShop(int, int, int, int, int, int, int, boolean)}.
	 * 
	 * @return the player's current {@link #money}
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * This method retrieves the player's current {@link #vision} level to be used
	 * in the game logic as well as in the method,
	 * {@link UI#printShop(int, int, int, int, int, int, int, boolean)}.
	 * 
	 * @return the player's current {@link #vision} level
	 */
	public int getVision() {
		return vision;
	}

	/**
	 * This method retrieves the player's current {@link #magazine} level to be used
	 * in the game logic as well as in the method,
	 * {@link UI#printShop(int, int, int, int, int, int, int, boolean)}.
	 * 
	 * @return the player's current {@link #magazine} level
	 */
	public int getMagazine() {
		return magazine;
	}

	/**
	 * This method retrieves the player's current {@link #health} level to be used
	 * in the game logic as well as in the method,
	 * {@link UI#printShop(int, int, int, int, int, int, int, boolean)}.
	 * 
	 * @return the player's current {@link #health} level
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * This method retrieves the current {@link #visionCost} of a {@link #vision}
	 * upgrade to be used in the method
	 * {@link UI#printShop(int, int, int, int, int, int, int, boolean)}.
	 * 
	 * @return the current cost of a vision upgrade
	 */
	public int getVisionCost() {
		return visionCost;
	}
	
	/**
	 * This method retrieves the current {@link #magazineCost} of a {@link #magazine}
	 * upgrade to be used in the method
	 * {@link UI#printShop(int, int, int, int, int, int, int, boolean)}.
	 * 
	 * @return the current cost of a magazine upgrade
	 */
	public int getMagazineCost() {
		return magazineCost;
	}
	
	/**
	 * This method retrieves the current {@link #healthCost} of a {@link #health}
	 * upgrade to be used in the method
	 * {@link UI#printShop(int, int, int, int, int, int, int, boolean)}.
	 * 
	 * @return the current cost of a health upgrade
	 */
	public int getHealthCost() {
		return healthCost;
	}
	
	/**
	 * This method retrieves the boolean value of {@link #godMode} to check whether
	 * or not the player has bought {@link #godMode}. If {@link #godMode} is bought,
	 * then the game mode option will be available once the player starts a new game.
	 * 
	 * @return {@code true} if {@link #godMode} has been bought, {@code false}
	 * otherwise
	 */
	public boolean getGodMode() {
		return godMode;
	}
	
}
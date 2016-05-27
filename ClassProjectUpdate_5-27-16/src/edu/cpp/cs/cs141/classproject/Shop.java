/**
 * 
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
	private static final long serialVersionUID = 8566003326349244863L;

	public static enum upgrades {
		VISION, MAGAZINE, HEALTH
	};
	
	int money;
	
	int vision;
	
	int magazine;
	
	int health;
	
	int visionCost;
	
	int magazineCost;
	
	int healthCost;
	
	public Shop() {
		money = 0;
		vision = 1;
		magazine = 1;
		health = 1;
		visionCost = 200;
		magazineCost = 200;
		healthCost = 200;
	}
	
	public void addMoney(int money) {
		this.money += money;
	}
	
	public void buyVision() {
		money -= visionCost;
		++vision;
		switch (vision) {
		case 2:
			visionCost = 500;
			break;
		case 3:
			visionCost = 900;
			break;
		case 4:
			visionCost = 1400;
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
			magazineCost = 500;
			break;
		case 3:
			magazineCost = 900;
			break;
		case 4:
			magazineCost = 1400;
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
			healthCost = 500;
			break;
		case 3:
			healthCost = 900;
			break;
		case 4:
			healthCost = 1400;
			break;
		default:
			healthCost = 0;
			break;
		}
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
	
}
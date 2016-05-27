/**
 * 
 */
package edu.cpp.cs.cs141.classproject;

import java.io.Serializable;

/**
 * @author Natanael
 *
 */
public class Achievements implements Serializable {

	/**
	 * This field represents the unique ID used for saving and loading via
	 * serialization.
	 */
	private static final long serialVersionUID = -1109622401625938516L;

	private boolean normalLevelsCleared1;
	
	private boolean normalLevelsCleared10;
	
	private boolean normalLevelsCleared50;
	
	private boolean normalLevelsCleared100;
	
	private boolean hardLevelsCleared1;
	
	private boolean hardLevelsCleared10;
	
	private boolean hardLevelsCleared50;
	
	private boolean hardLevelsCleared100;
	
	private boolean roomsChecked1;
	
	private boolean roomsChecked10;
	
	private boolean roomsChecked50;
	
	private boolean roomsChecked100;
	
	private boolean itemsObtained1;
	
	private boolean itemsObtained10;
	
	private boolean itemsObtained50;
	
	private boolean itemsObtained100;
	
	private boolean enemiesKilled1;

	private boolean enemiesKilled10;

	private boolean enemiesKilled50;

	private boolean enemiesKilled100;

	private boolean cheat;
	
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
	
	public void setNormalLevelsCleared1() {
		normalLevelsCleared1 = true;
	}
	
	public void setNormalLevelsCleared10() {
		normalLevelsCleared10 = true;
	}
	
	public void setNormalLevelsCleared50() {
		normalLevelsCleared50 = true;
	}
	
	public void setNormalLevelsCleared100() {
		normalLevelsCleared100 = true;
	}
	
	public void setHardLevelsCleared1() {
		hardLevelsCleared1 = true;
	}
	
	public void setHardLevelsCleared10() {
		hardLevelsCleared10 = true;
	}
	
	public void setHardLevelsCleared50() {
		hardLevelsCleared50 = true;
	}
	
	public void setHardLevelsCleared100() {
		hardLevelsCleared100 = true;
	}
	
	public void setRoomsChecked1() {
		roomsChecked1 = true;
	}
	
	public void setRoomsChecked10() {
		roomsChecked10 = true;
	}
	
	public void setRoomsChecked50() {
		roomsChecked50 = true;
	}
	
	public void setRoomsChecked100() {
		roomsChecked100 = true;
	}
	
	public void setItemsObtained1() {
		itemsObtained1 = true;
	}
	
	public void setItemsObtained10() {
		itemsObtained10 = true;
	}
	
	public void setItemsObtained50() {
		itemsObtained50 = true;
	}
	
	public void setItemsObtained100() {
		itemsObtained100 = true;
	}
	
	public void setEnemiesKilled1() {
		enemiesKilled1 = true;
	}

	public void setEnemiesKilled10() {
		enemiesKilled10 = true;
	}

	public void setEnemiesKilled50() {
		enemiesKilled50 = true;
	}

	public void setEnemiesKilled100() {
		enemiesKilled100 = true;
	}

	public void setCheat() {
		cheat = true;
	}
	
}
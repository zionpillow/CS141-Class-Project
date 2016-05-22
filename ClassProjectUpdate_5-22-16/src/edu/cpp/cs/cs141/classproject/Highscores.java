/**
 * 
 */
package edu.cpp.cs.cs141.classproject;

import java.io.Serializable;

/**
 * @author Natanael Ariawan
 *
 */
public class Highscores implements Serializable {

	/**
	 * This field represents the unique ID used for saving and loading via
	 * serialization.
	 */
	private static final long serialVersionUID = -2054266536877501527L;

	/**
	 * 
	 */
	private String[] names = new String[0];
	
	/**
	 * 
	 */
	private int[] scores = new int[0];
	
	/**
	 * @param name
	 * @param score
	 */
	public void storeHighscore(String name, int score) {
		String[] tempNames = names;
		int[] tempScores = scores;
		
		names = new String[tempNames.length+1];
		scores = new int[tempScores.length+1];
		
		for (int i = 0 ; i < tempNames.length ; ++i) {
			names[i] = tempNames[i];
			scores[i] = tempScores[i];
		}
		
		names[tempNames.length] = name;
		scores[tempScores.length] = score;
	}
	
	/**
	 * @return
	 */
	public String[] getNames() {
		return names;
	}
	
	/**
	 * @return
	 */
	public int[] getScores() {
		return scores;
	}
	
}
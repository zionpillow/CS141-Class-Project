/**
 * 
 */
package edu.cpp.cs.cs141.classproject;

import java.io.Serializable;

/**
 * @author Natanael Ariawan
 *
 */
public class Highscore implements Serializable, Comparable<Highscore> {

	/**
	 * This field represents the unique ID used for saving and loading via
	 * serialization.
	 */
	private static final long serialVersionUID = -5987375566086085628L;

	private String name;
	
	private int score;
	
	public Highscore(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	@Override
	public int compareTo(Highscore highscore) {
		if (score < highscore.getScore())
			return -1;
		else
			return 0;
	}
	
}
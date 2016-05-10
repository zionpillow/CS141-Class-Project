/**
 * 
 */
package edu.cpp.cs.cs141.classproject;

/**
 * This class represents the player in the game. This class will take commands
 * from the UI to take certain actions such as moving and shooting.
 * 
 * @author Natanael Ariawan
 */
public class Player extends Actives {
	
	/**
	 * This field represents the position of the player in the game map. All movement functions
	 * will operate using the entity's position. Set with the constructor method {@link #Player()}.
	 */
	private int position[];
	
	/**
	 * This field represents whether or not the player has been killed. While {@code true}, the player will be allowed to
	 * perform any function as usual. If it is {@code false}, the game will end.
	 * Set with the constructor method {@link #Player()}.
	 */
	private boolean isAlive;
	
	/**
	 * This field represents the player's health. This field determines whether the player is
	 * {@link #isAlive}. This field can be altered by the method {@link #alterHealth(int)} and
	 * is set with the constructor method {@link #Player()}.
	 */
	private int health;
	
	/**
	 * This field represents the player's ammo. This field determines whether the player can
	 * {@link #shoot()} or not. This field can be altered by the method {@link #alterAmmo(int)} and
	 * is set with the constructor method {@link #Player()}.
	 */
	private int ammo;
	
	/**
	 * This constructor method sets the fields {@link #position}, {@link #isAlive},
	 * {@link #health}, and {@link #ammo}.
	 */
	public Player() {
		// TODO
	}

	/* (non-Javadoc)
	 * @see edu.cpp.cs.cs141.classproject.Actives#getPosition()
	 */
	@Override
	public int[] getPosition() {
		return position;
	}

	/* (non-Javadoc)
	 * @see edu.cpp.cs.cs141.classproject.Actives#checkIfAlive()
	 */
	@Override
	public boolean checkIfAlive() {
		return isAlive;
	}
	
	/**
	 * This method gets the current {@link #health} of the player to be used in
	 * the game engine and UI, as well as to be interacted upon by other entities in the game.
	 * 
	 * @return the current {@link #health} of the player
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * This method gets the current {@link #ammo} of the player to be used in
	 * the game engine and UI, as well as to be interacted upon by other entities in the game.
	 * 
	 * @return the current {@link #ammo} of the player
	 */
	public int getAmmo() {
		return ammo;
	}
	
	/* (non-Javadoc)
	 * @see edu.cpp.cs.cs141.classproject.Actives#move(edu.cpp.cs.cs141.classproject.UI.direction)
	 */
	@Override
	public void move(UI.direction dir) {
		// TODO
	}
	
	/* (non-Javadoc)
	 * @see edu.cpp.cs.cs141.classproject.Actives#kill()
	 */
	@Override
	public void kill() {
		isAlive = false;
	}
	
	/**
	 * This method alters the player's {@link #health} when the player is hit or
	 * whenever the player restores health.
	 * 
	 * @param health amount of health lost or restored
	 */
	public void alterHealth(int health) {
		this.health += health;
		if(this.health <= 0)
			kill();
	}
	
	/**
	 * This method alters the player's {@link #ammo} when the player shoots or
	 * whenever the player regains ammo.
	 * 
	 * @param ammo amount of ammo lost or regained
	 */
	public void alterAmmo(int ammo) {
		this.ammo += ammo;
	}
	
	/**
	 * This method is invoked when the player wishes to shoot in a certain direction.
	 * The method returns a value of type direction initialized in the {@link UI} class
	 * in order to indicate which direction the player wishes to shoot.
	 * 
	 * @return the direction the player wishes to shoot
	 */
	public UI.direction shoot(){
		// TODO
		return null;
	}

}

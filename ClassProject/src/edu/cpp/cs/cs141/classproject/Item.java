/**
 * 
 */
package edu.cpp.cs.cs141.classproject;

import java.io.Serializable;

/**
 * The Item class represents an item that the player can pick up. It can be either an invincibility powerup, an extra bullet, or a
 * radar that determines where the briefcase is.
 * 
 * @author Aidan Novobilski
 */
public class Item implements Entity, Serializable {

	/**
	 * This field represents the unique ID used for saving and loading via
	 * serialization.
	 */
	private static final long serialVersionUID = 491499858124042794L;

	/**
	 * This field represents all possible types that the item can be.
	 * 
	 * @author Aidan Novobilski
	 */
	public static enum itemType {
		INVINCIBILITY, BULLET, RADAR
	};

	/**
	 * This field represents the type of item the item actually is.
	 */
	private itemType type;
	
	/**
	 * This field represents whether the player can see the item.
	 */
	private boolean visible;

	/**
	 * This constructor allows the game to set the type of the item to be
	 * created. Also sets visibility to {@code false}.
	 * 
	 * @param type The type.
	 */
	public Item(itemType type) {
		this.type = type;
		visible = false;
	}
	
	/**
	 * This method will set the room to be visible (the briefcase will show)
	 */
	public void setVisible(){
		visible = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cpp.cs.cs141.classproject.Entity#getEntityType()
	 */
	@Override
	public entityType getEntityType() {
		return Entity.entityType.ITEM;
	}

	/**
	 * This method will return the type of item the item is.
	 * 
	 * @return Either {@code INVINCIBILITY}, {@code BULLET}, or {@code RADAR}.
	 */
	public itemType getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (visible) {
			switch (type) {
			case BULLET:
				return "!";
			case INVINCIBILITY:
				return "*";
			case RADAR:
				return "%";
			}
		}
		return " ";
	}

}

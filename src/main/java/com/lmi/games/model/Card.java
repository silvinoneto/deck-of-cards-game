package com.lmi.games.model;

import java.io.Serializable;

/**
 * Class responsible for representing a Card.
 * 
 * @author silvinoneto
 */
public class Card implements Serializable {

	private static final long serialVersionUID = 1L;

	// Values for the non-numeric cards.
	public final static int ACE = 1;
	public final static int JACK = 11; 
	public final static int QUEEN = 12;
	public final static int KING = 13;

	private Suit suit;
	private int value;

	/**
	 * Creates a card using a specific suit and value.
	 * 
	 * @param suit
	 * @param value
	 */
	public Card(Suit suit, int value) {
		this.suit = suit;
		this.value = value;
	}

	public Suit getSuit() {
		return suit;
	}

	public int getValue() {
		return value;
	}
}

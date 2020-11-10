package com.lmi.games.resource;

import java.io.Serializable;

import com.lmi.games.model.Card;
import com.lmi.games.model.Suit;

/**
 * Resource/Transport class used to represent a Card.
 * 
 * @author silvinoneto
 */
public class CardResource implements Serializable {

	private static final long serialVersionUID = 1L;

	private Suit suit;
	private String faceValue;
	private int count;

	/**
	 * Constructor.
	 * 
	 * @param suit
	 * @param faceValue
	 * @param count
	 */
	public CardResource(Card card, int count) {
		super();
		this.suit = card.getSuit();
		this.faceValue = toFaceValue(card.getValue());
		this.count = count;
	}

	/*
	 * Utility method used to convert numeric face values.
	 */
	private String toFaceValue(int value) {
		String cardFace = null;
		if (value >= 2 && value <= 10) {
			cardFace = String.valueOf(value);
		}
		else if (value == 1) {
			cardFace = "Ace";
		}
		else if (value == 11) {
			cardFace =  "Jack";
		}
		else if (value == 12) {
			cardFace =  "Queen";
		}
		else if (value == 13) {
			cardFace = "King";
		}

        return cardFace;
	}

	public Suit getSuit() {
		return suit;
	}
	public String getFaceValue() {
		return faceValue;
	}
	public int getCount() {
		return count;
	}
}

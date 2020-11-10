package com.lmi.games.model;

import java.io.Serializable;
import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class responsible for representing a Card.
 * 
 * @author silvinoneto
 */
public class Card implements Serializable, Comparable<Card> {

	private static final long serialVersionUID = 1L;

	// Values for the non-numeric cards.
	public final static int ACE = 1;
	public final static int JACK = 11; 
	public final static int QUEEN = 12;
	public final static int KING = 13;

	private Suit suit;
	private int value;
	@JsonIgnore
	private Long deckId;

	/**
	 * Creates a card using a specific suit and value.
	 * 
	 * @param suit
	 * @param value
	 */
	public Card(Suit suit, int value, Long deckId) {
		this.suit = suit;
		this.value = value;
		this.deckId = deckId;
	}

	public Suit getSuit() {
		return suit;
	}

	public int getValue() {
		return value;
	}
	
	public Long getDeckId() {
		return deckId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (suit != other.suit)
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public int compareTo(Card o) {
		// Sorting is done using Suit enum ordinal value and face value descending (higher values should be displayed on top).
		return Comparator.comparing(Card::getSuit)
				.thenComparing(Comparator.comparingInt(Card::getValue).reversed())
	            .compare(this, o);
	}
}

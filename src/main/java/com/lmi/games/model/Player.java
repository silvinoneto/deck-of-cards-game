package com.lmi.games.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class used to represent a Player.
 * 
 * @author silvinoneto
 */
public class Player {

	private String login;
	private List<Card> cards = new ArrayList<Card>();
	private Integer totalValue = 0; // Holds total added value of all the cards each player holds

	public Player(String login) {
		this.login = login;
	}

	/**
	 * Add a card to the list of cards held by a player.
	 * 
	 * @param card
	 */
	public void addCard(Card card) {
		cards.add(card);
		totalValue += card.getValue();
	}

	public List<Card> getCards() {
		return cards;
	}

	public String getLogin() {
		return login;
	}

	public Integer getTotalValue() {
		return totalValue;
	}
}

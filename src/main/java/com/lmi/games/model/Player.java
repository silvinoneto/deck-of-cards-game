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
	private String name;
	private List<Card> cards = new ArrayList<Card>();

	public Player(String login, String name) {
		this.login = login;
		this.name = name;
	}
	
	public void addCard(Card card) {
		cards.add(card);
	}

	public List<Card> getCards() {
		return cards;
	}

	public String getLogin() {
		return login;
	}
	
	public String getName() {
		return name;
	}
}

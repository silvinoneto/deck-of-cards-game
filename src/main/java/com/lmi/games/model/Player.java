package com.lmi.games.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class used to represent a Player.
 * 
 * @author silvinoneto
 */
public class Player implements Identifiable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private List<Card> cards = new ArrayList<Card>();

	public Player(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public Long getId() {
		return id;
	}

	public void addCard(Card card) {
		cards.add(card);
	}

	public List<Card> getCards() {
		return cards;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}

package com.lmi.games.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class responsible for representing a Cards Game.
 * 
 * @author silvinoneto
 */
public class Game implements Identifiable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Map<String, Player> players = new ConcurrentHashMap<String, Player>();
	private Map<Long, Deck> decks = new ConcurrentHashMap<Long, Deck>();

	public void addPlayer(String playerLogin) {
		if (players.containsKey(playerLogin))
			throw new IllegalArgumentException("This login is already taken, please use a different one.");
		players.put(playerLogin, new Player(playerLogin));
	}

	public void removePlayer(String playerLogin) {
		Player player = players.remove(playerLogin);
		if (player == null)
			throw new IllegalArgumentException("Player not found");

		// When a player is removed from a game, all their cards should be returned to the game deck.
		// TODO: Implement this logic here
	}

	/**
	 * Deals a card from the deck to a player.
	 * 
	 * @param Long playerId - Player ID
	 * @return A card from the deck.
	 */
	public Card dealCard(String playerLogin) {
		Card dealtCard = null;
		Player player = players.get(playerLogin);
		for (Deck deck : decks.values()) { // Iterate over game decks.
			dealtCard = deck.dealCard(player);
			if (dealtCard != null)
				break; // If card has been dealt, break the loop.
		}
		return dealtCard;
	}

	public void addDeck(Deck deck) {
		decks.put(deck.getId(), deck);
	}

	/**
	 * Shuffle the game deck.
	 */
	public void shuffleDeck() {
		for (Deck deck : decks.values()) { // Iterate over game decks.
			deck.shuffle();
		}
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
}

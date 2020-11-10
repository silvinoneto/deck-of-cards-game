package com.lmi.games.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	/**
	 * Add a player to the game.
	 * 
	 * @param playerLogin
	 */
	public void addPlayer(String playerLogin) {
		if (players.containsKey(playerLogin))
			throw new IllegalArgumentException("This login is already taken, please use a different one.");
		players.put(playerLogin, new Player(playerLogin));
	}

	/**
	 * Remove a player from the game.
	 * 
	 * @param playerLogin
	 */
	public void removePlayer(String playerLogin) {
		Player player = getPlayer(playerLogin);

		// When a player is removed from a game, all their cards should be returned to the game deck.
		for (Card card : player.getCards()) {
			Deck cardDeck = decks.get(card.getDeckId());
			cardDeck.returnCard(card);
		}
		players.remove(playerLogin); // Removes a player from the game players list.
	}

	/**
	 * Finds a player in the game.
	 * 
	 * @param playerLogin
	 * @return
	 */
	public Player getPlayer(String playerLogin) {
		Player player = players.get(playerLogin);
		if (player == null)
			throw new IllegalArgumentException("Player not found");

		return player;
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

	/**
	 * Add a deck to this game.
	 * 
	 * @param deck
	 */
	public void addDeck(Deck deck) {
		decks.put(deck.getId(), deck);
	}

	/**
	 * Get list of players sorted by total added value of the cards they are holding.
	 * 
	 * @return
	 */
	@JsonIgnore
	public List<Player> getPlayersSortedByTotalValue() {
		List<Player> listPlayers = new ArrayList<Player>(players.values());
		// Use Comparator to sort list of players by total value in descending order.
		Collections.sort(listPlayers, new PlayerComparator()); 
		return listPlayers;
	}

	/**
	 * Get undealt cards count sorted by suit.
	 * 
	 * @return
	 */
	@JsonIgnore
	public Map<Suit, Integer> getUndealtCardsCountBySuit() {
		Map<Suit, Integer> totalSuitCount = new HashMap<>();
		for (Suit suit : Suit.values()) {
			totalSuitCount.put(suit, 0);
		}

		for (Deck deck : decks.values()) { // Iterate over game decks.
			Map<Suit, Integer> deckSuitCount = deck.getDealtCardsBySuit(); // Get current count of dealt cards by suit for selected deck.
			for (Entry<Suit, Integer> suitCount : deckSuitCount.entrySet()) {
				Integer currVal = totalSuitCount.get(suitCount.getKey());
				currVal += Deck.DECK_SIZE - suitCount.getValue(); // Subtract deck size by the count of dealt cards to obtain undealt count.
				totalSuitCount.put(suitCount.getKey(), currVal); // Update count in the result map.
			}
		}
		return totalSuitCount;
	}

	/**
	 * Get undealt cards count sorted by suit and face value.
	 * 
	 * @return
	 */
	@JsonIgnore
	public Map<Card, Integer> getUndealtCardsCountBySuitAndValue() {
		Map<Card, Integer> totalCardCount = new TreeMap<>(); // Using a sorted map collection that automatically places
															 // Card instances in their right position according to the implementation
															 // of the compareTo() method in the Card class.
		for (Deck deck : decks.values()) { // Iterate over game decks.
			List<Card> availableCards = deck.getAvailableCards(); // Get list of available cards.
			for (Card card : availableCards) {
				Integer currCount = totalCardCount.get(card);
				if (currCount == null) {
					currCount = 0;
				}
				// Update card count.
				currCount += 1;
				totalCardCount.put(card, currCount);
			}
		}
		return totalCardCount;
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

package com.lmi.games.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class used to represent a Deck of Cards to be used in a game.
 * 
 * @author silvinoneto
 */
public class Deck implements Identifiable {

	public static final int DECK_SIZE = 52;

	private static final long serialVersionUID = 1L;

	private Long id;
	@JsonIgnore
	private CopyOnWriteArrayList<Card> cards = new CopyOnWriteArrayList<Card>();
	@JsonIgnore
	private AtomicInteger dealtCards = new AtomicInteger(0);
	@JsonIgnore
	private Map<Suit, AtomicInteger> dealtCardsBySuit = new ConcurrentHashMap<Suit, AtomicInteger>();

	@Override
	public Long getId() {
		return id;
	}

	/**
	 * Deck constructor.
	 * 
	 * @param id
	 */
	public Deck(Long id) {
		this.id = id;
		init();
	}

	/*
	 * Deck initialization.
	 */
	private void init() {
        for (Suit suit : Suit.values()) {
            for (int value = Card.ACE; value <= Card.KING; value++) {
                cards.add(new Card(suit, value, this.id));
            }
            dealtCardsBySuit.put(suit, new AtomicInteger(0));
        }
	}

	/**
	 * Deals a card from the deck to a player.
	 * 
	 * @param player
	 * @return A card from the deck.
	 */
	public Card dealCard(Player player) {
		Card card = null;
		if (dealtCards.get() < DECK_SIZE) { // Check if deck still has cards available. 
			card = cards.get(dealtCards.getAndIncrement());
			if (card != null) { // If card has been found.
				player.addCard(card);
				AtomicInteger cardsBySuit = dealtCardsBySuit.get(card.getSuit());
				cardsBySuit.getAndIncrement(); // Update cards by suit counters.
			}
		}
		return card;
	}

	/**
	 * Return a list of all available cards in the deck.
	 * 
	 * @return
	 */
	@JsonIgnore
	public List<Card> getAvailableCards() {
		List<Card> availableCards = new ArrayList<>();
		for (int i = dealtCards.get(); i < Deck.DECK_SIZE; i++) {
			Card card = cards.get(i);
			availableCards.add(card);
		}
		return availableCards;
	}
	
	/**
	 * Returns a card to the deck, when a player quits a game.
	 * 
	 * @param card
	 */
	public void returnCard(Card card) {
		// Update counters in the deck.
		dealtCards.getAndDecrement();
		AtomicInteger cardsBySuit = dealtCardsBySuit.get(card.getSuit());
		cardsBySuit.getAndDecrement();
	}

	/**
	 * Shuffle the deck by swapping the cards positions in the array list.
	 */
	public void shuffle() {
		Random random = new Random();
		int dealtCards = this.dealtCards.get();
		for (int i = dealtCards; i < Deck.DECK_SIZE; i++) {
			// Obtain random position for available cards.
			int randomNumber = dealtCards + random.nextInt((Deck.DECK_SIZE - dealtCards));
            Card tempCard = cards.get(i);
            // Swap cards position.
            cards.set(i, cards.get(randomNumber));
            cards.set(randomNumber, tempCard);
        }
	}

	/**
	 * Gets count of dealt cards by suit.
	 *  
	 * @return
	 */
	public Map<Suit, Integer> getDealtCardsBySuit() {
		Map<Suit, Integer> dealtCardsCount = new HashMap<Suit, Integer>();
		for (Entry<Suit, AtomicInteger> entry : dealtCardsBySuit.entrySet()) {
			dealtCardsCount.put(entry.getKey(), entry.getValue().get());
		}
		return dealtCardsCount;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
}

package com.lmi.games.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class used to represent a Deck of Cards to be used in a game.
 * 
 * @author silvinoneto
 */
public class Deck implements Identifiable {

	private static final long serialVersionUID = 1L;
	private static final int DECK_SIZE = 52;

	private Long id;
	private CopyOnWriteArrayList<Card> cards = new CopyOnWriteArrayList<Card>();
	private AtomicInteger cardsUsed = new AtomicInteger(0);
	private Map<Suit, AtomicInteger> cardsUsedBySuit = new ConcurrentHashMap<Suit, AtomicInteger>();

	@Override
	public Long getId() {
		return id;
	}

	public Deck() {
		init();
	}

	/*
	 * Deck initialization.
	 */
	private void init() {
        for (Suit suit : Suit.values()) {
            for (int value = Card.ACE; value <= Card.KING; value++) {
                cards.add(new Card(suit, value));
            }
            cardsUsedBySuit.put(suit, new AtomicInteger(0));
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
		if (cardsUsed.get() < DECK_SIZE) { // Check if deck still has cards available. 
			card = cards.get(cardsUsed.getAndIncrement());
			if (card != null) { // If card has been found.
				player.addCard(card);
				AtomicInteger cardsBySuit = cardsUsedBySuit.get(card.getSuit());
				cardsBySuit.getAndIncrement(); // Update cards by suit counters.
			}
		}
		return card;
	}

	/**
	 * Shuffle the deck by swapping the cards positions in the array list.
	 */
	public void shuffle() {
		//TODO: Review this implementation for thread-safety
        for (int i = cards.size() - 1; i > 0; i--) {
            int randomNumber = (int) (Math.random() * (i + 1));
            Card tempCard = cards.get(i);
            cards.set(i, cards.get(randomNumber));
            cards.set(randomNumber, tempCard);
        }
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}
}

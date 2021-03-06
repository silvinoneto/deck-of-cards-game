package com.lmi.games.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lmi.games.model.Deck;
import com.lmi.games.model.Game;

/**
 * Game repository component in-memory implementation.
 * 
 * @author silvinoneto
 */
@Repository
public class GameRepositoryImpl extends MemoryRepository<Game> implements GameRepository {

	@Autowired
	private IDGenerator deckIDGenerator;

	private Map<Long, Deck> decks = new ConcurrentHashMap<Long, Deck>();
	
	@Override
	public Game createGame(Game game) {
		return save(game);
	}

	@Override
	public Deck createDeck() {
		Deck deck = new Deck(deckIDGenerator.getNextId());
		decks.put(deck.getId(), deck);
		return deck;
	}

	@Override
	public boolean removeGame(Game game) {
		return remove(game.getId());
	}

	@Override
	public Game findGame(Long id) {
		Game game = findById(id);
		return game;
	}

	@Override
	public Deck findDeck(Long id) {
		return decks.get(id);
	}
}

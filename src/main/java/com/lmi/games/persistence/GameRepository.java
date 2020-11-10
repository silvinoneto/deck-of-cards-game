package com.lmi.games.persistence;

import com.lmi.games.model.Deck;
import com.lmi.games.model.Game;

/**
 * Game repository interface
 * 
 * @author silvinoneto
 */
public interface GameRepository {

	public Game createGame(Game game);

	public Deck createDeck(Deck deck);

	public boolean removeGame(Game game);
}

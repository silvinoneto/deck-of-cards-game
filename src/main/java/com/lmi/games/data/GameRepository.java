package com.lmi.games.data;

import com.lmi.games.model.Deck;
import com.lmi.games.model.Game;

/**
 * Game repository interface
 * 
 * @author silvinoneto
 */
public interface GameRepository {

	public Game createGame(Game game);

	public Deck createDeck();

	public boolean removeGame(Game game);

	public Game findGame(Long id);

	public Deck findDeck(Long id);
}

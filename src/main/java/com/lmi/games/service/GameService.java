package com.lmi.games.service;

import java.util.List;
import java.util.Map;

import com.lmi.games.model.Card;
import com.lmi.games.model.Deck;
import com.lmi.games.model.Game;
import com.lmi.games.model.Player;
import com.lmi.games.model.Suit;
import com.lmi.games.resource.CardResource;

/**
 * Game Service interface.
 * 
 * @author silvinoneto
 */
public interface GameService {

	public Game createGame(Game game);

	public Deck createDeck();
	
	public void addDeck(Game game, Deck deck);

	public boolean removeGame(Long gameId);

	public Game findGame(Long id);

	public Deck findDeck(Long id);

	public Player getPlayer(Game game, String playerLogin);

	public Player addPlayer(Game game, String playerLogin);

	public void removePlayer(Game game, String playerLogin);

	public Card dealCard(Game game, String playerLogin);
	
	public void shuffleDeck(Game game);

	public List<Player> getPlayersSortedByTotalValue(Game game);

	public Map<Suit, Integer> getUndealtCardsCountBySuit(Game game);

	public List<CardResource> getUndealtCardsCountBySuitAndValue(Game game);
}

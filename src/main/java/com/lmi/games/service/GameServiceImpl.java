package com.lmi.games.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lmi.games.data.GameRepository;
import com.lmi.games.exception.AlreadyExistsException;
import com.lmi.games.exception.NotFoundException;
import com.lmi.games.model.Card;
import com.lmi.games.model.Deck;
import com.lmi.games.model.Game;
import com.lmi.games.model.Player;
import com.lmi.games.model.Suit;
import com.lmi.games.resource.CardListResourceAssembler;
import com.lmi.games.resource.CardResource;

/**
 * Implementation class for the Game Service component.
 * 
 * @author silvinoneto
 */
@Component
public class GameServiceImpl implements GameService {

	@Autowired
	private GameRepository repository;
	@Autowired
	private CardListResourceAssembler cardListAssembler;

	@Override
	public Game createGame(Game game) {
		return repository.createGame(game);
	}

	@Override
	public Deck createDeck() {
		return repository.createDeck();
	}

	@Override
	public boolean removeGame(Long gameId) {
		Game game = findGame(gameId);
		return repository.removeGame(game);
	}

	@Override
	public Game findGame(Long id) {
		Game game = repository.findGame(id);
		if (game == null)
			throw new NotFoundException();
		return game;
	}

	@Override
	public void addDeck(Game game, Deck deck) {
		game.addDeck(deck);
	}

	@Override
	public Deck findDeck(Long id) {
		Deck deck = repository.findDeck(id);
		if (deck == null)
			throw new NotFoundException();
		return deck;
	}

	@Override
	public Player getPlayer(Game game, String playerLogin) {
		Player player = game.getPlayer(playerLogin);
		if (player == null)
			throw new NotFoundException();
		return player;
	}

	@Override
	public Player addPlayer(Game game, String playerLogin) {
		Player player = game.getPlayer(playerLogin);
		if (player != null)
			throw new AlreadyExistsException();
		return game.addPlayer(playerLogin);
	}

	@Override
	public void removePlayer(Game game, String playerLogin) {
		Player player = game.getPlayer(playerLogin);
		game.removePlayer(player);
	}

	@Override
	public Card dealCard(Game game, String playerLogin) {
		Card dealtCard = game.dealCard(playerLogin);
		if (dealtCard == null)
			throw new NotFoundException();
		return dealtCard;
	}

	@Override
	public void shuffleDeck(Game game) {
		game.shuffleDeck();
	}

	@Override
	public List<Player> getPlayersSortedByTotalValue(Game game) {
		return game.getPlayersSortedByTotalValue();
	}

	@Override
	public Map<Suit, Integer> getUndealtCardsCountBySuit(Game game) {
		return game.getUndealtCardsCountBySuit();
	}

	@Override
	public List<CardResource> getUndealtCardsCountBySuitAndValue(Game game) {
		Map<Card, Integer> cardsCount = game.getUndealtCardsCountBySuitAndValue();
		List<CardResource> cardList = cardListAssembler.toResourceList(cardsCount);
		return cardList;
	}
}

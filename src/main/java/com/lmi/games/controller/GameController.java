package com.lmi.games.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lmi.games.model.Card;
import com.lmi.games.model.Deck;
import com.lmi.games.model.Game;
import com.lmi.games.model.Player;
import com.lmi.games.model.Suit;
import com.lmi.games.resource.CardResource;
import com.lmi.games.service.GameService;

/**
 * Game REST controller component.
 * 
 * @author silvinoneto
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/games", produces = "application/json")
public class GameController {

	@Autowired
	private GameService service;

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Game> createGame(@RequestBody Game game) {
		Game newGame = service.createGame(game);
		return new ResponseEntity<>(newGame, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Game> deleteGame(@PathVariable Long id) {
		service.removeGame(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}/decks/{deckId}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Deck> addDeck(@PathVariable Long id, @PathVariable Long deckId) {
		Game game = service.findGame(id);
		Deck deck = service.findDeck(deckId);
		service.addDeck(game, deck);
		return new ResponseEntity<>(deck, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/players/{login}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Player> addPlayer(@PathVariable Long id, @PathVariable String login) {
		Game game = service.findGame(id);
		Player player = service.addPlayer(game, login);
		return new ResponseEntity<>(player, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/players/{login}", method = RequestMethod.DELETE, consumes = "application/json")
	public ResponseEntity<Player> deletePlayer(@PathVariable Long id, @PathVariable String login) {
		Game game = service.findGame(id);
		service.removePlayer(game, login);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}/shuffle-deck", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Game> shuffleDeck(@PathVariable Long id) {
		Game game = service.findGame(id);
		service.shuffleDeck(game);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/players/{login}/deal-card", method = RequestMethod.GET, consumes = "application/json")
	public ResponseEntity<Card> dealCard(@PathVariable Long id, @PathVariable String login) {
		Game game = service.findGame(id);
		Card dealtCard = service.dealCard(game, login);
		return new ResponseEntity<>(dealtCard, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/players/{login}/get-cards", method = RequestMethod.GET, consumes = "application/json")
	public ResponseEntity<List<Card>> getPlayerCards(@PathVariable Long id, @PathVariable String login) {
		Game game = service.findGame(id);
		Player player = service.getPlayer(game, login);
		List<Card> playerCards = player.getCards();
		return new ResponseEntity<>(playerCards, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/get-players-sorted-by-total-value", method = RequestMethod.GET, consumes = "application/json")
	public ResponseEntity<List<Player>> getPlayersSortedByTotalValue(@PathVariable Long id) {
		Game game = service.findGame(id);
		List<Player> players = service.getPlayersSortedByTotalValue(game);
		return new ResponseEntity<>(players, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/get-undealt-cards-count-by-suit", method = RequestMethod.GET, consumes = "application/json")
	public ResponseEntity<Map<Suit, Integer>> getUndealtCardsCountBySuit(@PathVariable Long id) {
		Game game = service.findGame(id);
		Map<Suit, Integer> suitCount = service.getUndealtCardsCountBySuit(game);
		return new ResponseEntity<>(suitCount, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/get-undealt-cards-count-by-suit-and-value", method = RequestMethod.GET, consumes = "application/json")
	public ResponseEntity<List<CardResource>> getUndealtCardsCountBySuitAndValue(@PathVariable Long id) {
		Game game = service.findGame(id);
		List<CardResource> cardList = service.getUndealtCardsCountBySuitAndValue(game);
		return new ResponseEntity<>(cardList, HttpStatus.OK);
	}
}

package com.lmi.games.presentation;

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
import com.lmi.games.service.CardListResourceAssembler;
import com.lmi.games.service.GameRepository;

/**
 * Game REST controller component.
 * 
 * @author silvinoneto
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/game", produces = "application/json")
public class GameController {

	@Autowired
	private GameRepository repository;
	@Autowired
	private CardListResourceAssembler cardListAssembler;

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Game> createGame(@RequestBody Game game) {
		Game newGame = repository.createGame(game);
		return new ResponseEntity<>(newGame, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Game> deleteGame(@PathVariable Long id) {
		boolean deleted = repository.removeGame(id);
		HttpStatus statusCode = deleted ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(statusCode);
	}

	@RequestMapping(value = "/{id}/deck/{deckId}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Game> addDeck(@PathVariable Long id, @PathVariable Long deckId) {
		Game game = repository.findGame(id);
		Deck deck = repository.findDeck(deckId);
		if (game == null || deck == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		game.addDeck(deck);
		return new ResponseEntity<>(game, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/player/{login}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Game> addPlayer(@PathVariable Long id, @PathVariable String login) {
		Game game = repository.findGame(id);
		if (game == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		HttpStatus status = HttpStatus.OK;
		try {
			game.addPlayer(login);
		}
		catch (IllegalArgumentException e) {
			status = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(game, status);
	}

	@RequestMapping(value = "/{id}/player/{login}", method = RequestMethod.DELETE, consumes = "application/json")
	public ResponseEntity<Game> deletePlayer(@PathVariable Long id, @PathVariable String login) {
		Game game = repository.findGame(id);
		if (game == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		HttpStatus status = HttpStatus.OK;
		try {
			game.removePlayer(login);
		}
		catch (IllegalArgumentException e) {
			status = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(game, status);
	}

	@RequestMapping(value = "/{id}/shuffleDeck", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Game> shuffleDeck(@PathVariable Long id) {
		Game game = repository.findGame(id);
		if (game == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		game.shuffleDeck();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/player/{login}/dealCard", method = RequestMethod.GET, consumes = "application/json")
	public ResponseEntity<Card> dealCard(@PathVariable Long id, @PathVariable String login) {
		Game game = repository.findGame(id);
		Card dealtCard = null;
		HttpStatus status = HttpStatus.OK;
		if (game == null) {
			status = HttpStatus.NOT_FOUND;
		}
		else {
			dealtCard = game.dealCard(login);
			if (dealtCard == null)
				status = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(dealtCard, status);
	}

	@RequestMapping(value = "/{id}/player/{login}/getCards", method = RequestMethod.GET, consumes = "application/json")
	public ResponseEntity<List<Card>> getPlayerCards(@PathVariable Long id, @PathVariable String login) {
		Game game = repository.findGame(id);
		Player player = null;
		HttpStatus status = HttpStatus.OK;
		List<Card> playerCards = null;
		if (game == null) {
			status = HttpStatus.NOT_FOUND;
		}
		else {
			player = game.getPlayer(login);
			if (player == null)
				status = HttpStatus.NOT_FOUND;
			playerCards = player.getCards();
		}
		return new ResponseEntity<>(playerCards, status);
	}

	@RequestMapping(value = "/{id}/getPlayersSortedByTotalValue", method = RequestMethod.GET, consumes = "application/json")
	public ResponseEntity<List<Player>> getPlayersSortedByTotalValue(@PathVariable Long id) {
		Game game = repository.findGame(id);
		HttpStatus status = HttpStatus.OK;
		if (game == null) {
			status = HttpStatus.NOT_FOUND;
		}
		List<Player> players = game.getPlayersSortedByTotalValue();
		return new ResponseEntity<>(players, status);
	}

	@RequestMapping(value = "/{id}/getUndealtCardsCountBySuit", method = RequestMethod.GET, consumes = "application/json")
	public ResponseEntity<Map<Suit, Integer>> getUndealtCardsCountBySuit(@PathVariable Long id) {
		Game game = repository.findGame(id);
		HttpStatus status = HttpStatus.OK;
		if (game == null) {
			status = HttpStatus.NOT_FOUND;
		}
		Map<Suit, Integer> suitCount = game.getUndealtCardsCountBySuit();
		return new ResponseEntity<>(suitCount, status);
	}

	@RequestMapping(value = "/{id}/getUndealtCardsCountBySuitAndValue", method = RequestMethod.GET, consumes = "application/json")
	public ResponseEntity<List<CardResource>> getUndealtCardsCountBySuitAndValue(@PathVariable Long id) {
		Game game = repository.findGame(id);
		HttpStatus status = HttpStatus.OK;
		if (game == null) {
			status = HttpStatus.NOT_FOUND;
		}
		Map<Card, Integer> cardsCount = game.getUndealtCardsCountBySuitAndValue();
		List<CardResource> cardList = cardListAssembler.toResourceList(cardsCount);
		return new ResponseEntity<>(cardList, status);
	}
}

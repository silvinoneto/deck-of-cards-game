package com.lmi.games.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lmi.games.model.Deck;
import com.lmi.games.model.Game;
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
		if (game == null || deck == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		game.addDeck(deck);
		return new ResponseEntity<>(game, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/player/{login}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Game> addPlayer(@PathVariable Long id, @PathVariable String login) {
		Game game = repository.findGame(id);
		if (game == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		HttpStatus status = HttpStatus.OK;
		try
		{
			game.addPlayer(login);
		}
		catch (IllegalArgumentException e)
		{
			status = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(game, status);
	}

	@RequestMapping(value = "/{id}/player/{login}", method = RequestMethod.DELETE, consumes = "application/json")
	public ResponseEntity<Game> deletePlayer(@PathVariable Long id, @PathVariable String login) {
		Game game = repository.findGame(id);
		if (game == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		HttpStatus status = HttpStatus.OK;
		try
		{
			game.removePlayer(login);
		}
		catch (IllegalArgumentException e)
		{
			status = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(game, status);
	}

	@RequestMapping(value = "/{id}/shuffleDeck", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Game> shuffleDeck(@PathVariable Long id) {
		Game game = repository.findGame(id);
		if (game == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		game.shuffleDeck();
		return new ResponseEntity<>(game, HttpStatus.OK);
	}
}

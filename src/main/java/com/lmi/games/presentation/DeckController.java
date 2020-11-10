package com.lmi.games.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lmi.games.model.Deck;
import com.lmi.games.service.GameRepository;

/**
 * Deck REST controller component.
 * 
 * @author silvinoneto
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/deck", produces = "application/json")
public class DeckController {

	@Autowired
	private GameRepository repository;

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Deck> createGame() {
		Deck newDeck = repository.createDeck();
		return new ResponseEntity<>(newDeck, HttpStatus.CREATED);
	}

}

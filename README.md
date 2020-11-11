# Deck of Cards REST API

This is a very basic cards game API.

### 1) Prerequisites
- Java 8
- Maven
- Git

### 2) Assumptions
There are a few instructions you must consider before playing:

- In order to play a game, players are required to provide a login.
- When a player quits a game, all their cards are returned to the game deck.

### 3) Getting Started
There are a couple ways to run this API.

#### 3.1) Building and running unit tests
To build and run the unit tests available in this project, users should run the following command:<br />
``` mvn install ```

#### 3.2) Building and starting a Web Container
To build and run this API in a Web Container, the following command shoud be used:<br />
``` mvn spring-boot:run ```

### 4) Test
In this section, I've listed a few test examples for testing this API.<br />
NOTE: The path variables placeholder should be replaced by actual values.

- Create a Game<br />
``` curl -H "Content-Type: application/json" -XPOST -d "{}" http://localhost:8080/games ```
- Create a Deck<br />
``` curl -H "Content-Type: application/json" -XPOST -d "{}" http://localhost:8080/decks ```
- Add a Deck to a Game<br />
``` curl -H "Content-Type: application/json" -XPOST -d "{}" http://localhost:8080/games/{id}/decks/{deckId} ```
- Add a Player<br />
``` curl -H "Content-Type: application/json" -XPOST -d "{}" http://localhost:8080/games/{id}/players/{login} ```
- Shuffle a Game Deck<br />
``` curl -H "Content-Type: application/json" -XPOST -d "{}" http://localhost:8080/games/{id}/shuffle-deck ```
- Delete a Player<br />
``` curl -H "Content-Type: application/json" -XDELETE -d "{}" http://localhost:8080/games/{id}/players/{login} ```
- Deal a Card<br />
``` curl -H "Content-Type: application/json" -XGET -d "{}" http://localhost:8080/games/{id}/players/{login}/deal-card ```
- Get a Player's Card List<br />
``` curl -H "Content-Type: application/json" -XGET -d "{}" http://localhost:8080/games/{id}/players/{login}/get-cards ```
- Get a Players List Sorted by Card Values<br />
``` curl -H "Content-Type: application/json" -XGET -d "{}" http://localhost:8080/games/{id}/get-players-sorted-by-total-value ```
- Get a List of Undealt Cards Count By Suit<br />
``` curl -H "Content-Type: application/json" -XGET -d "{}" http://localhost:8080/games/{id}/get-undealt-cards-count-by-suit ```
- Get a List of Undealt Cards Count By Suit and Value<br />
``` curl -H "Content-Type: application/json" -XGET -d "{}" http://localhost:8080/games/{id}/get-undealt-cards-count-by-suit-and-value ```
- Delete a Game<br />
``` curl -H "Content-Type: application/json" -XDELETE -d "{}" http://localhost:8080/games/{id} ```

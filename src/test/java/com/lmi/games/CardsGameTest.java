package com.lmi.games;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.lmi.games.controller.GameController;

/**
 * Card Games REST API Unit Test class.
 * 
 * @author silvinoneto
 */
@WebMvcTest(GameController.class)
@TestMethodOrder(OrderAnnotation.class)
public class CardsGameTest {

	MediaType MEDIA_TYPE_JSON_UTF8 = 
			new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));

	// Sample data used in the test cases.
	private Integer gameId = 1;
	private Integer firstDeck = 1;
	private Integer secondDeck = 2;
	private String firstPlayerLogin = "Mike";
	private String secondPlayerLogin = "Bob";
	private String thirdPlayerLogin = "Jane";
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	@Order(1)
	public void testCreateGame() throws Exception {
		this.mockMvc.perform(
			post("/games")
				.accept(MEDIA_TYPE_JSON_UTF8)
				.contentType(MEDIA_TYPE_JSON_UTF8)
				.content("{}"))
					.andDo(print())
					.andExpect(status().isCreated())
					.andExpect(content().string(containsString("{\"id\":" + gameId + "}")
			)
		);
	}

	@Test
	@Order(2)
	public void testCreateDecks() throws Exception {
		this.mockMvc.perform(
			post("/decks")
				.accept(MEDIA_TYPE_JSON_UTF8)
				.contentType(MEDIA_TYPE_JSON_UTF8)
				.content("{}"))
					.andDo(print())
					.andExpect(status().isCreated())
					.andExpect(content().string(containsString("{\"id\":" + firstDeck + "}")
			)
		);

		this.mockMvc.perform(
			post("/decks")
				.accept(MEDIA_TYPE_JSON_UTF8)
				.contentType(MEDIA_TYPE_JSON_UTF8)
				.content("{}"))
					.andDo(print())
					.andExpect(status().isCreated())
					.andExpect(content().string(containsString("{\"id\":" + secondDeck + "}")
			)
		);
	}

	@Test
	@Order(3)
	public void testAddDecks() throws Exception {
		this.mockMvc.perform(
			post("/games/" + gameId + "/decks/" + firstDeck)
				.accept(MEDIA_TYPE_JSON_UTF8)
				.contentType(MEDIA_TYPE_JSON_UTF8)
				.content("{}"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().string(containsString("{\"id\":" + firstDeck + "}")
			)
		);

		this.mockMvc.perform(
				post("/games/" + gameId + "/decks/" + secondDeck)
				.accept(MEDIA_TYPE_JSON_UTF8)
				.contentType(MEDIA_TYPE_JSON_UTF8)
				.content("{}"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().string(containsString("{\"id\":" + secondDeck + "}")
			)
		);
	}
	
	@Test
	@Order(4)
	public void testShuffleDecks() throws Exception {
		this.mockMvc.perform(
			post("/games/" + gameId + "/shuffle-deck")
				.accept(MEDIA_TYPE_JSON_UTF8)
				.contentType(MEDIA_TYPE_JSON_UTF8)
				.content("{}"))
					.andDo(print())
					.andExpect(status().isOk()
		);
	}

	@Test
	@Order(5)
	public void testAddPlayers() throws Exception {
		this.mockMvc.perform(
			post("/games/" + gameId + "/players/" + firstPlayerLogin)
				.accept(MEDIA_TYPE_JSON_UTF8)
				.contentType(MEDIA_TYPE_JSON_UTF8)
				.content("{}"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().string(containsString("\"login\":\"" + firstPlayerLogin + "\"")
			)
		);

		this.mockMvc.perform(
			post("/games/" + gameId + "/players/" + secondPlayerLogin)
				.accept(MEDIA_TYPE_JSON_UTF8)
				.contentType(MEDIA_TYPE_JSON_UTF8)
				.content("{}"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().string(containsString("\"login\":\"" + secondPlayerLogin + "\"")
			)
		);

		this.mockMvc.perform(
			post("/games/" + gameId + "/players/" + thirdPlayerLogin)
				.accept(MEDIA_TYPE_JSON_UTF8)
				.contentType(MEDIA_TYPE_JSON_UTF8)
				.content("{}"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().string(containsString("\"login\":\"" + thirdPlayerLogin + "\"")
			)
		);
	}

	@Test
	@Order(6)
	public void testDeletePlayer() throws Exception {
		this.mockMvc.perform(
			delete("/games/" + gameId + "/players/" + thirdPlayerLogin)
				.accept(MEDIA_TYPE_JSON_UTF8)
				.contentType(MEDIA_TYPE_JSON_UTF8)
				.content("{}"))
					.andDo(print())
					.andExpect(status().isNoContent());
	}

	@Test
	@Order(7)
	public void testDealCards() throws Exception {
		for (int i = 0; i < 5; i++) {
			this.mockMvc.perform(
				get("/games/" + gameId + "/players/" + firstPlayerLogin + "/deal-card")
					.accept(MEDIA_TYPE_JSON_UTF8)
					.contentType(MEDIA_TYPE_JSON_UTF8)
					.content("{}"))
						.andDo(print())
						.andExpect(status().isOk());

			this.mockMvc.perform(
				get("/games/" + gameId + "/players/" + secondPlayerLogin + "/deal-card")
					.accept(MEDIA_TYPE_JSON_UTF8)
					.contentType(MEDIA_TYPE_JSON_UTF8)
					.content("{}"))
						.andDo(print())
						.andExpect(status().isOk());
		}
	}

	@Test
	@Order(8)
	public void testGetCards() throws Exception {
		this.mockMvc.perform(
			get("/games/" + gameId + "/players/" + firstPlayerLogin + "/get-cards")
				.accept(MEDIA_TYPE_JSON_UTF8)
				.contentType(MEDIA_TYPE_JSON_UTF8)
				.content("{}"))
					.andDo(print())
					.andExpect(status().isOk());

		this.mockMvc.perform(
			get("/games/" + gameId + "/players/" + secondPlayerLogin + "/get-cards")
				.accept(MEDIA_TYPE_JSON_UTF8)
				.contentType(MEDIA_TYPE_JSON_UTF8)
				.content("{}"))
					.andDo(print())
					.andExpect(status().isOk());
	}

	@Test
	@Order(9)
	public void testGetPlayersSortedByTotalValue() throws Exception {
		this.mockMvc.perform(
			get("/games/" + gameId + "/get-players-sorted-by-total-value")
				.accept(MEDIA_TYPE_JSON_UTF8)
				.contentType(MEDIA_TYPE_JSON_UTF8)
				.content("{}"))
					.andDo(print())
					.andExpect(status().isOk());
	}

	@Test
	@Order(10)
	public void testGetUndealtCardsCountBySuit() throws Exception {
		this.mockMvc.perform(
			get("/games/" + gameId + "/get-undealt-cards-count-by-suit")
				.accept(MEDIA_TYPE_JSON_UTF8)
				.contentType(MEDIA_TYPE_JSON_UTF8)
				.content("{}"))
					.andDo(print())
					.andExpect(status().isOk());
	}

	@Test
	@Order(11)
	public void testGetUndealtCardsCountBySuitAndValue() throws Exception {
		this.mockMvc.perform(
			get("/games/" + gameId + "/get-undealt-cards-count-by-suit-and-value")
				.accept(MEDIA_TYPE_JSON_UTF8)
				.contentType(MEDIA_TYPE_JSON_UTF8)
				.content("{}"))
					.andDo(print())
					.andExpect(status().isOk());
	}

	@Test
	@Order(12)
	public void testDeleteGame() throws Exception {
		this.mockMvc.perform(
			delete("/games/" + gameId)
				.accept(MEDIA_TYPE_JSON_UTF8)
				.contentType(MEDIA_TYPE_JSON_UTF8)
				.content("{}"))
					.andDo(print())
					.andExpect(status().isNoContent());
	}
}

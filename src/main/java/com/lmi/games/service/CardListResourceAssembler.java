package com.lmi.games.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.lmi.games.model.Card;
import com.lmi.games.resource.CardResource;

/**
 * Component responsible for converting a Map of Cards into a Card resource list.
 * 
 * @author silvinoneto
 */
@Component
public class CardListResourceAssembler {

	/**
	 * Converter method used to create a list of Card resources.
	 * 
	 * @param cardsCount
	 * @return
	 */
	public List<CardResource> toResourceList(Map<Card, Integer> cardsCount) {
		List<CardResource> cardResources = new ArrayList<>(cardsCount.size());
		for (Entry<Card, Integer> entry : cardsCount.entrySet()) {
			cardResources.add(new CardResource(entry.getKey(), entry.getValue()));
		}
		return cardResources;
	}
}

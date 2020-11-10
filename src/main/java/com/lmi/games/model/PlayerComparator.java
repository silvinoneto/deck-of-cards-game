package com.lmi.games.model;

import java.util.Comparator;

/**
 * Comparator class used to sort players in descending order according to the total value of their cards.
 * 
 * @author silvinoneto
 */
public class PlayerComparator implements Comparator<Player> {

	@Override
	public int compare(Player o1, Player o2) {
		// Descending order.
		return o2.getTotalValue().compareTo(o1.getTotalValue());
	}

}

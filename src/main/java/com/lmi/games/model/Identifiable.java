package com.lmi.games.model;

import java.io.Serializable;

/**
 * Interface to be implemented by model classes that require an ID attribute.
 * 
 * @author silvinoneto
 */
public interface Identifiable extends Serializable {

	public Long getId();

	public void setId(Long id);

}

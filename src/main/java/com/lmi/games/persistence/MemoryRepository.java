package com.lmi.games.persistence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.lmi.games.model.Identifiable;

/**
 * Base class for in-memory repository classes.
 * 
 * @author silvinoneto
 */
public abstract class MemoryRepository<T extends Identifiable> {

	@Autowired
	private IDGenerator idGenerator;

	private Map<Long, T> cache = new ConcurrentHashMap<Long, T>();

	/**
	 * Store objects in memory cache.
	 * 
	 * @param object
	 * @return
	 */
	public T save(T object) {
		object.setId(idGenerator.getNextId());
		cache.put(object.getId(), object);
		return object;
	}

	/**
	 * Remove object by ID.
	 * 
	 * @param id
	 * @return
	 */
	public boolean remove(Long id) {
		return cache.remove(id) != null;
	}

	/**
	 * Get object by ID.
	 * 
	 * @param id
	 * @return
	 */
	public T findById(Long id) {
		return cache.get(id);
	}
}

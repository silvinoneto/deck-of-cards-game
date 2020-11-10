package com.lmi.games.persistence;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Utility class used to generate IDs that will be used by our domain classes.
 * 
 * @author silvinoneto
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class IDGenerator {

	private AtomicLong nextId = new AtomicLong(1);

	public Long getNextId() {
		return nextId.getAndIncrement();
	}
}

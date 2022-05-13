package ar.com.tinello.katas.virtualwallet.domain;

import java.time.LocalDateTime;

import ar.com.tinello.katas.virtualwallet.SystemUUID;

public abstract class Event {
	private final String id;
	private final LocalDateTime createdTime;
	
	public Event() {
		SystemUUID systemUUID = new SystemUUID();
		this.id = systemUUID.randomUUID();
		this.createdTime = LocalDateTime.now();
	}

	public String getId() {
		return id;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

}

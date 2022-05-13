package ar.com.tinello.katas.virtualwallet.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import ar.com.tinello.katas.virtualwallet.customer.domain.CustomerWasCreatedEvent;

public class EventPublisher {

	private final Map<Class<? extends Event>, Consumer<String>> subscribes;
	
	public EventPublisher() {
		this.subscribes = new HashMap<>();
	}
	
	public void publish(final Event event) {
		if (event instanceof CustomerWasCreatedEvent) {
			final var evento = (CustomerWasCreatedEvent)event;
			final var subscribe = subscribes.get(evento.getClass());
			if (subscribe != null) {
				subscribe.accept(evento.getCustomerId());
			}
		}
	}

	public void subscribe(final Class<? extends Event> eventClass, final Consumer<String> consumidor) {
		subscribes.put(eventClass, consumidor);	
	}
	

}

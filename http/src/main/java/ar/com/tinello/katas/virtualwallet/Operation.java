package ar.com.tinello.katas.virtualwallet;

import io.vertx.reactivex.ext.web.RoutingContext;

public interface Operation {

	void execute(RoutingContext context, Provider provider);

    String getId();
}

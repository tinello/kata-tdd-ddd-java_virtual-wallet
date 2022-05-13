package ar.com.tinello.katas.virtualwallet.operations;

import static ar.com.tinello.katas.virtualwallet.Responses.ok;

import ar.com.tinello.katas.virtualwallet.Operation;
import ar.com.tinello.katas.virtualwallet.Provider;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;

public class GetRoot implements Operation {

	@Override
	public void execute(RoutingContext context, Provider provider) {
		ok(context, new JsonObject().put("status", "StatesListing API").toString());
	}

	@Override
	public String getId() {
		return "";
	}

}

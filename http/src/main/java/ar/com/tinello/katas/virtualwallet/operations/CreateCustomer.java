package ar.com.tinello.katas.virtualwallet.operations;

import static ar.com.tinello.katas.virtualwallet.Responses.ok;

import ar.com.tinello.katas.virtualwallet.Operation;
import ar.com.tinello.katas.virtualwallet.Provider;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;

public class CreateCustomer implements Operation {

	@Override
	public void execute(RoutingContext context, Provider provider) {
		final var body = context.getBodyAsJson();
        final var customerId = body.getString("customerId");
        final var firstName = body.getString("firstName");
        final var lastName = body.getString("lastName");
        final var personalNumber = body.getString("personalNumber");
        final var personalPhoneNumber = body.getString("personalPhoneNumber");
        
		provider.createCustomer().execute(customerId, firstName, lastName, personalNumber, personalPhoneNumber);
		ok(context, new JsonObject().encode());
	}

	@Override
	public String getId() {
		return "new_customer";
	}

}

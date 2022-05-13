package ar.com.tinello.katas.virtualwallet.operations;

import static ar.com.tinello.katas.virtualwallet.Responses.internalServerError;
import static ar.com.tinello.katas.virtualwallet.Responses.ok;
import static ar.com.tinello.katas.virtualwallet.checkingAccount.infrastructure.PrimitiveRepresentation.toMap;

import ar.com.tinello.katas.virtualwallet.Operation;
import ar.com.tinello.katas.virtualwallet.Provider;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;

public class GetCustomerDetails implements Operation {

	@Override
	public void execute(RoutingContext context, Provider provider) {
		final var body = context.getBodyAsJson();
        final var customerId = body.getString("customerId");
        
        
        
        provider.getCustomerDetails()
        		.execute(customerId)
        		.subscribe(
        				customer -> ok(context, new JsonObject(toMap(customer)).encode()),
                        error -> internalServerError(context, error)
        				);
	}

	@Override
	public String getId() {
		return "get_customer_details";
	}

}

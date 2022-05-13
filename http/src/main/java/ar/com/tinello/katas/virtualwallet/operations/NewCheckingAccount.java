package ar.com.tinello.katas.virtualwallet.operations;

import static ar.com.tinello.katas.virtualwallet.Responses.internalServerError;
import static ar.com.tinello.katas.virtualwallet.Responses.ok;

import ar.com.tinello.katas.virtualwallet.Operation;
import ar.com.tinello.katas.virtualwallet.Provider;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;

public class NewCheckingAccount implements Operation {

    @Override
    public void execute(RoutingContext context, Provider provider) {
        final var body = context.getBodyAsJson();
        final var customerId = body.getString("customerId");

        provider.newCheckingAccount()
                .execute(customerId)
                .toObservable()
                .subscribe(
                        account -> ok(context, new JsonObject().encode()),
                        error -> internalServerError(context, error)
                        );
    }

    @Override
    public String getId() {
        return "new_checking_account";
    }

}

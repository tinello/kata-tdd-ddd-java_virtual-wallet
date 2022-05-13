package ar.com.tinello.katas.virtualwallet;


import ar.com.tinello.katas.virtualwallet.domain.DomainError;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.api.validation.ValidationException;
import io.vertx.reactivex.ext.web.RoutingContext;

public final class Responses {

    private Responses() {
		super();
		// Bloqueo la creacion
	}

	public static void internalServerError(RoutingContext context, Throwable failure) {
	    var message = failure.getMessage();
        if (failure instanceof DomainError) {
            message = failure.getClass().getSimpleName();
        } else if (!(failure instanceof ValidationException)) {
            failure.printStackTrace();
            message = "Internal Server Error: " + failure.getMessage();
        }
        error(context, 500, message);
    }

    public static void ok(RoutingContext context, String body) {
        context.response().setStatusCode(200)
        		.putHeader("content-type", "application/json")
        		.end(body);
    }

    public static void error(RoutingContext context, int statusCode, String errorMessage) {
        context.response().setStatusCode(statusCode)
                .putHeader("content-type", "application/json")
                .end(new JsonObject().put("message", errorMessage).encode());
    }
}

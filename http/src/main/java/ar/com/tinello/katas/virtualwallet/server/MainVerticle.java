package ar.com.tinello.katas.virtualwallet.server;

import static ar.com.tinello.katas.virtualwallet.Responses.error;
import static ar.com.tinello.katas.virtualwallet.Responses.internalServerError;

import java.util.Arrays;

import ar.com.tinello.katas.virtualwallet.Provider;
import ar.com.tinello.katas.virtualwallet.operations.CreateCustomer;
import ar.com.tinello.katas.virtualwallet.operations.GetCustomerDetails;
import ar.com.tinello.katas.virtualwallet.operations.GetRoot;
import ar.com.tinello.katas.virtualwallet.operations.NewCheckingAccount;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.handler.BodyHandler;

public class MainVerticle extends AbstractVerticle {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private HttpServer server;
	private Provider provider;


	public MainVerticle(Provider provider) {
		super();
		this.provider = provider;
	}

	@Override
	public void start(Promise startSignal) throws Exception {

	    final var getRoot = new GetRoot();
	    final var newCheckingAccount = new NewCheckingAccount();
	    final var createCustomer = new CreateCustomer();
	    final var getCustomerDetails = new GetCustomerDetails();


	    final var router = createRouter();

		router.get("/" + getRoot.getId()).handler(routingContext ->
				getRoot.execute(routingContext, provider)
			);

		router.post("/" + newCheckingAccount.getId()).handler(routingContext ->
		        newCheckingAccount.execute(routingContext, provider)
		     );
		
		router.post("/" + createCustomer.getId()).handler(routingContext ->
				createCustomer.execute(routingContext, provider)
			);
		
		router.post("/" + getCustomerDetails.getId()).handler(routingContext ->
				getCustomerDetails.execute(routingContext, provider)
			);

		server = vertx.createHttpServer(new HttpServerOptions()
                .setPort(8080)
                .setCompressionSupported(true));

		server.requestHandler(router).rxListen().subscribe(httpServer -> {
            logger.info("Server started on port " + httpServer.actualPort());
            startSignal.complete();
        }, throwable -> {
            logger.error("oops, something went wrong during initialization", throwable);
            startSignal.fail(throwable);
        });

	}

	@Override
	public void stop() {
        server.close();
    }

	private Router createRouter() {
	    final var router = Router.router(vertx);
        router.route()
        		.consumes("application/json")
        		.produces("application/json")
        		.handler(BodyHandler.create())
        		.failureHandler(this::failureHandler); // This line, cause the crash on errorHandler
        router.errorHandler(404, this::errorHandler);
        router.errorHandler(500, this::failureHandler);
        return router;
    }

	private void errorHandler(RoutingContext routingContext) {
	    final var httpMethod = routingContext.request().method();
        if (Arrays.asList(HttpMethod.GET, HttpMethod.POST).contains(httpMethod)) {
            error(routingContext, 404, "Not Found");
        } else if (httpMethod == HttpMethod.TRACE) {
            routingContext.response().close();
        } else {
            error(routingContext, 405, "Method Not Allowed");
        }
    }

	private void failureHandler(RoutingContext routingContext) {
        internalServerError(routingContext, routingContext.failure());
    }

}

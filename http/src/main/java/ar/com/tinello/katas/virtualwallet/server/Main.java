package ar.com.tinello.katas.virtualwallet.server;



import ar.com.tinello.katas.virtualwallet.Environment;
import ar.com.tinello.katas.virtualwallet.Provider;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.impl.cpu.CpuCoreSensor;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.Vertx;

public class Main {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] __) {
	    final var options = new DeploymentOptions().setInstances(CpuCoreSensor.availableProcessors());

	    final var vertx = Vertx.vertx();

	    final var environment = new Environment();

	    final var provider = new Provider(vertx, "customerid", environment);

        vertx.deployVerticle(
        		() -> new MainVerticle(provider),
        		options,
        		res -> {
		            if (res.succeeded()) {
		                logger.info("Deployment id is: " + res.result());
		            } else {
		                logger.error("Deployment failed!", res.cause());
		            }
    			}
        	);
    }
}

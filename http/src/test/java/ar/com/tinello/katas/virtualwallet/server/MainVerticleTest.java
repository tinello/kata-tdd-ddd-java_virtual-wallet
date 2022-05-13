package ar.com.tinello.katas.virtualwallet.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import ar.com.tinello.katas.virtualwallet.EnvironmentMock;
import ar.com.tinello.katas.virtualwallet.Provider;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.client.WebClient;
import io.vertx.reactivex.ext.web.codec.BodyCodec;

@ExtendWith(VertxExtension.class)
class MainVerticleTest {

    @BeforeEach
    @DisplayName("Deploy a verticle")
    public void setUp(Vertx vertx, VertxTestContext testContext) {
        final var mainVerticle = new MainVerticle(
                new Provider(vertx, "customerid", new EnvironmentMock()));

        vertx.deployVerticle(mainVerticle, testContext.completing());
    }

    @Test
    void ask_root_context(Vertx vertx, VertxTestContext testContext) {
        final var REPEAT = 1;

        final var checkpoints = testContext.checkpoint(REPEAT);

        final var request = WebClient.create(vertx).get(8080, "localhost", "/").as(BodyCodec.string());

        request.rxSend().repeat(REPEAT).subscribe(response -> testContext.verify(() -> {
            assertThat(response.body()).isEqualTo("{\"status\":\"StatesListing API\"}");
            checkpoints.flag();
        }), testContext::failNow);
    }

    @Test
    void new_checking_account(Vertx vertx, VertxTestContext testContext) {
        final var REPEAT = 1;
        final var checkpoints = testContext.checkpoint(REPEAT);

        final var request = WebClient.create(vertx).post(8080, "localhost", "/new_checking_account").as(BodyCodec.string());

        request.rxSendJsonObject(new JsonObject().put("customerId", "1")).repeat(REPEAT).subscribe(response -> testContext.verify(() -> {
            assertThat(response.body()).isEqualTo("{}");
            checkpoints.flag();
        }), testContext::failNow);
    }


    @Test
    void get_a_inexistent_path(Vertx vertx, VertxTestContext testContext) {
        final var REPEAT = 1;
        final var checkpoints = testContext.checkpoint(REPEAT);

        final var request = WebClient.create(vertx).get(8080, "localhost", "/error_404").as(BodyCodec.string());

        request.rxSendJsonObject(new JsonObject())
        		.repeat(REPEAT)
        		.subscribe(
        				response -> testContext.verify(
        						() -> {
        							assertThat(response.body()).isEqualTo("{\"message\":\"Not Found\"}");
        							checkpoints.flag();
        						}), 
        				testContext::failNow);
    }

    
    @Test
    void create_customer_and_account_through_domain_event(Vertx vertx, VertxTestContext testContext) {
    	final var REPEAT = 1;
        final var checkpoints = testContext.checkpoint(REPEAT);
        
        final var request = WebClient.create(vertx).post(8080, "localhost", "/new_customer").as(BodyCodec.string());
        request.rxSendJsonObject(new JsonObject()
        			.put("customerId", "1")
        			.put("firstName", "1")
        			.put("lastName", "1")
        			.put("personalNumber", "1")
        			.put("personalPhoneNumber", "1")
        		)
        		.repeat(REPEAT)
        		.subscribe(
        				response -> testContext.verify(
        						() -> {
        							assertThat(response.body()).isEqualTo("{}");
        							checkpoints.flag();
        						}), 
        				testContext::failNow
        				);
    }
    
    @Test
    void get_customer_details(Vertx vertx, VertxTestContext testContext) {
        final var REPEAT = 1;
        final var checkpoints = testContext.checkpoint(REPEAT);

        final var request = WebClient.create(vertx).post(8080, "localhost", "/get_customer_details").as(BodyCodec.string());

        request.rxSendJsonObject(new JsonObject()
        			.put("customerId", "2")
        		)
        		.repeat(REPEAT)
        		.subscribe(
        				response -> testContext.verify(
        						() -> {
        							assertThat(response.body()).isEqualTo("{\"customerId\":\"2\",\"firstName\":\"Gustavo\",\"lastName\":\"Tinello\",\"personalNumber\":\"1234\",\"personalPhoneNumber\":\"4321\",\"account\":{\"id\":\"2\",\"openingDate\":\"23/10/2020\",\"state\":\"OPEN\",\"balance\":0.0}}");
        							checkpoints.flag();
        						}), 
        				testContext::failNow
        				);
    }


    @AfterEach
    @DisplayName("Check that the verticle is still there")
    void lastChecks(Vertx vertx) {
        assertThat(vertx.deploymentIDs()).isNotEmpty().hasSize(1);
    }
}

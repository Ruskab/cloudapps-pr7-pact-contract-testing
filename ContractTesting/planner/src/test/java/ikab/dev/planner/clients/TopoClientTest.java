package ikab.dev.planner.clients;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonBody;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName  = "TopoProvider")
class TopoClientTest {

    public static final String ANY_CITY = "Madrid";
    private static final String ANY_LANDSCAPE = "Flat";

    @Pact(consumer = "PlannerConsumer")
    public RequestResponsePact getCity(PactDslWithProvider builder) {
        return builder
                .given("with existent city")
                .uponReceiving("get city by id")
                .path("/api/topographicdetails/%s".formatted(ANY_CITY))
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json"))
                .body(newJsonBody(object -> object.stringType("landscape", ANY_LANDSCAPE)).build())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getCity", port = "8080")
    void getCity_when_city_exists(MockServer mockServer) throws ExecutionException, InterruptedException {
        var topoClient = getMockedTopoService(mockServer);

        var response = topoClient.getLandscape(ANY_CITY);

        assertThat(response.get(), CoreMatchers.is(ANY_LANDSCAPE));
    }

    private TopoClient getMockedTopoService(MockServer mockServer) {
        var restTemplate = new RestTemplateBuilder().rootUri(mockServer.getUrl()).build();
        return new TopoClient(restTemplate);
    }

}
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

    public static final String CITY_MADRID = "Madrid";
    private static final String EXPECTED_CITY = "Madrid";

    @Pact(consumer = "PlannerConsumer")
    public RequestResponsePact getCity(PactDslWithProvider builder) {
        return builder
                .given("with city %s exists".formatted(CITY_MADRID))
                .uponReceiving("get city by id")
                .path("/api/topographicdetails/%s".formatted(CITY_MADRID))
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json"))
                .body(newJsonBody(object -> {
                    object.stringType("id", CITY_MADRID);
                    object.stringType("landscape", EXPECTED_CITY);
                }).build())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getCity", port = "8080")
    void getStudent_whenStudentExist(MockServer mockServer) throws ExecutionException, InterruptedException {
        var topoClient = getMockedTopoService(mockServer);

        var response = topoClient.getLandscape(CITY_MADRID);

        assertThat(response.get(), CoreMatchers.is(EXPECTED_CITY));
    }

    private TopoClient getMockedTopoService(MockServer mockServer) {
        var restTemplate = new RestTemplateBuilder().rootUri(mockServer.getUrl()).build();
        return new TopoClient(restTemplate);
    }

}
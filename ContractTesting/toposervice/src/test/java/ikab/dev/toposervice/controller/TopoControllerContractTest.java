package ikab.dev.toposervice.controller;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.spring.junit5.MockMvcTestTarget;
import ikab.dev.toposervice.model.City;
import ikab.dev.toposervice.service.TopoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.when;

@Provider("TopoProvider")
@Consumer("PlannerConsumer")
@PactFolder( "./../Pacts")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TopoControllerContractTest {


    @InjectMocks
    private TopoController topoController;

    @Mock
    private TopoService topoService;
    private final String ANY_CITY = "Madrid";
    private final String ANY_LANDSCAPE = "Flat";

    @BeforeEach
    void setUp(PactVerificationContext context) {
        MockMvcTestTarget testTarget = new MockMvcTestTarget();
        testTarget.setControllers(topoController);
        context.setTarget(testTarget);
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verifyPact(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("with existent city")
    public void withExistentCity() {
        when(topoService.getCity(ANY_CITY)).thenReturn(new City("1", ANY_LANDSCAPE));
    }
}
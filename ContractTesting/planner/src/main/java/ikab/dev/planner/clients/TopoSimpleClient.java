package ikab.dev.planner.clients;

import ikab.dev.planner.models.LandscapeResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TopoSimpleClient {

    private static final String TOPO_HOST = "localhost";
    private static final int TOPO_PORT = 8080;

    private static final String BASE_URI_STUDENTS = "http://localhost:8080/api/topographicdetails/";


    private final RestTemplate restTemplate;

    public TopoSimpleClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public LandscapeResponse getCity(String city) {
        return restTemplate.getForObject(BASE_URI_STUDENTS + city, LandscapeResponse.class);
    }
}

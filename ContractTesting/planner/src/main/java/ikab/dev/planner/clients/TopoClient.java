package ikab.dev.planner.clients;

import ikab.dev.planner.models.LandscapeResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class TopoClient {

    private static final String TOPO_HOST = "localhost";
    private static final int TOPO_PORT = 8080;


    private final RestTemplate restTemplate;

    public TopoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public CompletableFuture<String> getLandscape(String city) {

        String url = "http://"+TOPO_HOST+":"+TOPO_PORT+"/api/topographicdetails/" + city;
        
        LandscapeResponse response = restTemplate.getForObject(url, LandscapeResponse.class);

        return CompletableFuture.completedFuture(response.getLandscape());
    }
}

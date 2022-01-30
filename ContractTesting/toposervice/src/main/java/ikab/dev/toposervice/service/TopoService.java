package ikab.dev.toposervice.service;

import ikab.dev.toposervice.model.City;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;

@Service
public class TopoService {

    ConcurrentMap<String, City> cities = new ConcurrentHashMap<>();

    public City getCity(String id) {
        return cities.get(id);
    }
    
    @PostConstruct
    public void init() {

        this.cities.clear();

        this.cities.put("Madrid", new City("Madrid", "Flat"));
        this.cities.put("Barcelona", new City("Barcelona", "Flat"));
    }
}

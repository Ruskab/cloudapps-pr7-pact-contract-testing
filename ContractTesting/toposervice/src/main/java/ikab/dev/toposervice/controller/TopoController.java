package ikab.dev.toposervice.controller;

import ikab.dev.toposervice.model.City;
import ikab.dev.toposervice.model.CityDTO;
import ikab.dev.toposervice.service.TopoService;
import org.mockito.Mock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/topographicdetails")
public class TopoController {

    @Mock
    private TopoService topoService;

    @GetMapping("/{city}")
    public CityDTO getCity(@PathVariable String city) {
        return toCityDTO(topoService.getCity(city));
    }

    private CityDTO toCityDTO(City city) {
        return new CityDTO(city.getId(), city.getLandscape());
    }

}

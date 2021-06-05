package cat.inspedralbes.projecte2damb.portafolio.openweatherapi.model;

import java.util.ArrayList;
import java.util.List;

public class City {

    private String cityName;
    private List<Temperature> temperatures;

    public City() {
        temperatures = new ArrayList<Temperature>();
    }

    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public List<Temperature> getTemperatures() {
        return temperatures;
    }
    public void setTemperatures(List<Temperature> temperatures) {
        this.temperatures = temperatures;
    }
    public void addTemperature(Temperature temperature){
        temperatures.add(temperature);
    }
}
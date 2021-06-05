package cat.inspedralbes.projecte2damb.portafolio.util.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cat.inspedralbes.projecte2damb.portafolio.openweatherapi.model.Temperature;

public class ParserOpenWeather {

    // URL = "http://api.openweathermap.org/data/2.5/forecast?q=" + ciutatLlegida + "&appid=b0147f6411b11c4795a9f9e4bebc27a3&units=metric&lang=en";
    String json;

    public ParserOpenWeather (){
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String parseCityName() throws JSONException {
        String cityName;
        JSONObject doc = new JSONObject(json);
        JSONObject city = doc.getJSONObject("city");
        cityName = city.getString("name");
        return cityName;
    }

    public List<Temperature> parseTemperatures() throws JSONException {
        List<Temperature> temperatures = new ArrayList<Temperature>();
        Temperature temperature = new Temperature();

        JSONObject doc = new JSONObject(json);
        JSONArray list = doc.getJSONArray("list");
        JSONObject elementList;

        JSONObject main;
        JSONArray weather;

        for (int i = 0; i < list.length(); i++){
            elementList = list.getJSONObject(i);
            main = elementList.getJSONObject("main");
            weather = elementList.getJSONArray("weather");

            temperature.setTemp(main.getString("temp"));
            temperature.setMaxTemp(main.getString("temp_max"));
            temperature.setMinTemp(main.getString("temp_min"));
            temperature.setPressure(main.getString("pressure"));
            temperature.setHumidity(main.getString("humidity"));
            temperature.setWeatherDescription(weather.getJSONObject(0).getString("description"));
            temperature.setWeatherIcon(weather.getJSONObject(0).getString("icon"));
            temperature.setTimestamp(elementList.getString("dt_txt"));
            temperatures.add(temperature);
        }
        return temperatures;
    }
}

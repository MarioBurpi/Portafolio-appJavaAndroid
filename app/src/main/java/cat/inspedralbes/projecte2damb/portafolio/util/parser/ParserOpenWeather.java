package cat.inspedralbes.projecte2damb.portafolio.util.parser;

import android.util.Log;

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

        JSONObject doc = new JSONObject(json);
        JSONArray list = doc.getJSONArray("list");
        JSONObject elementList;
        JSONObject main;
        JSONArray weather;

        int temp, tempMax, tempMin;  // (1) this will help to delete decimals when rounding temp values.

        for (int i = 0; i < list.length(); i++){
            Temperature temperature = new Temperature();
            elementList = list.getJSONObject(i);
            main = elementList.getJSONObject("main");
            weather = elementList.getJSONArray("weather");

            temp = (int)Double.parseDouble(main.getString("temp"));  // (1) HERE: get String, parse to Double and then cast to Integer.
            tempMax = (int)Double.parseDouble(main.getString("temp_max"));
            tempMin = (int)Double.parseDouble(main.getString("temp_min"));

            temperature.setTemp(String.valueOf(temp)); // (1) will need String again.
            temperature.setMaxTemp(String.valueOf(tempMax));
            temperature.setMinTemp(String.valueOf(tempMin));
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

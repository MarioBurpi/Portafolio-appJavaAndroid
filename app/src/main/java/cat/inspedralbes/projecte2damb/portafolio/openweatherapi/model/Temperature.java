package cat.inspedralbes.projecte2damb.portafolio.openweatherapi.model;

import java.util.HashMap;
import java.util.Map;

import cat.inspedralbes.projecte2damb.portafolio.R;

public class Temperature {

    public final static Integer img01d = R.drawable.img01d;
    public final static Integer img01n = R.drawable.img01n;
    public final static Integer img02d = R.drawable.img02d;
    public final static Integer img02n = R.drawable.img02n;
    public final static Integer img03d = R.drawable.img03d;
    public final static Integer img03n = R.drawable.img03n;
    public final static Integer img04d = R.drawable.img04d;
    public final static Integer img04n = R.drawable.img04n;
    public final static Integer img09d = R.drawable.img09d;
    public final static Integer img09n = R.drawable.img09n;
    public final static Integer img10d = R.drawable.img10d;
    public final static Integer img10n = R.drawable.img10n;
    public final static Integer img11d = R.drawable.img11d;
    public final static Integer img11n = R.drawable.img11n;
    public final static Integer img13d = R.drawable.img13d;
    public final static Integer img13n = R.drawable.img13n;

    private String temp;
    private String maxTemp;
    private String minTemp;
    private String humidity;
    private String pressure;
    private String weatherDescription;
    private String weatherIcon;
    private Map<String, Integer> imgIcon;
    private String timestamp;

    public Temperature(){
        imgIcon = new HashMap<String, Integer>();
        imgIcon.put("img01d", img01d);
        imgIcon.put("img01n", img01n);
        imgIcon.put("img02d", img02d);
        imgIcon.put("img02n", img02n);
        imgIcon.put("img03d", img03d);
        imgIcon.put("img03n", img03n);
        imgIcon.put("img04d", img04d);
        imgIcon.put("img04n", img04n);
        imgIcon.put("img09d", img09d);
        imgIcon.put("img09n", img09n);
        imgIcon.put("img10d", img10d);
        imgIcon.put("img10n", img10n);
        imgIcon.put("img11d", img11d);
        imgIcon.put("img11n", img11n);
        imgIcon.put("img13d", img13d);
        imgIcon.put("img13n", img13n);
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Integer> getImgIcon() {
        return imgIcon;
    }

    public void setImgIcon(Map<String, Integer> imgIcon) {
        this.imgIcon = imgIcon;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "temp='" + temp + '\'' +
                ", maxTemp='" + maxTemp + '\'' +
                ", minTemp='" + minTemp + '\'' +
                ", humidity='" + humidity + '\'' +
                ", pressure='" + pressure + '\'' +
                ", weatherDescription='" + weatherDescription + '\'' +
                ", weatherIcon='" + weatherIcon + '\'' +
                ", imgIcon=" + imgIcon +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}

package cat.inspedralbes.projecte2damb.portafolio.currency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatesInfo {

    private String date;
    private String timestamp;
    private String base;
    private List<String> names;
    private Map<String, String> rates;

    public RatesInfo() {
        names = new ArrayList<String>();
        rates = new HashMap<String, String>();
    }

    public void addRateValue(String name, String value){
        rates.put(name, value);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public Map<String, String> getRates() {
        return rates;
    }

    public void setRates(Map<String, String> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "RatesInfo{" +
                "date='" + date + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", base='" + base + '\'' +
                ", names=" + names.toString() +
                ", rates=" + rates.toString() +
                '}';
    }
}

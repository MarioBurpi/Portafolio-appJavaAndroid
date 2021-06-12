package cat.inspedralbes.projecte2damb.portafolio.currency.parser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cat.inspedralbes.projecte2damb.portafolio.currency.RatesInfo;

public class ParserCurrency {

    String json;

    public ParserCurrency() {
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public RatesInfo parseRate() throws JSONException {
        RatesInfo ratesInfo = new RatesInfo();
        JSONObject doc = new JSONObject(json);
        String base = doc.getString("base");
        String timestamp = doc.getString("timestamp");
        String date = doc.getString("date");
        JSONObject rates = doc.getJSONObject("rates");
        JSONArray names = rates.names();
        ratesInfo.setBase(base);
        ratesInfo.setTimestamp(timestamp);
        ratesInfo.setDate(date);
        ratesInfo.getNames().add(base);
        ratesInfo.addRateValue(base, "1");
        for (int i = 0; i < names.length(); i++) {
            ratesInfo.getNames().add(names.get(i).toString());
            ratesInfo.addRateValue(names.get(i).toString(), rates.getString(names.get(i).toString()));
        }
        Log.d("PARSER DE CURRENCY", "parseRate: " + ratesInfo.toString());
        return ratesInfo;
    }

}

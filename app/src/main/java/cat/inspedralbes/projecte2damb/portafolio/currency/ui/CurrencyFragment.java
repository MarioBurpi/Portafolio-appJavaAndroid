package cat.inspedralbes.projecte2damb.portafolio.currency.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;

import cat.inspedralbes.projecte2damb.portafolio.R;
import cat.inspedralbes.projecte2damb.portafolio.currency.RatesInfo;
import cat.inspedralbes.projecte2damb.portafolio.currency.parser.ParserCurrency;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CurrencyFragment extends Fragment {

    private final String TAG = "Exchange Fragment ::";
    private final String URL_EXCHANGE_RATES_API = "http://api.exchangeratesapi.io/v1/latest?access_key=ce74894b29bf5c6b802dc38b6eb1b450";
    private String json;
    private RatesInfo ratesInfo;
    private View rootView;
    private Spinner spOrigin, spDestination;
    private EditText etValueIn, etValueOut;
    private ImageButton imgButtonConvert;
    private TextView tvResult, tvUnit;

    public CurrencyFragment() {
        requestExchangeRates();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_currency, container, false);
        spOrigin = rootView.findViewById(R.id.spinner_currency_fragment_unit_in);
        spDestination = rootView.findViewById(R.id.spinner_currency_fragment_unit_out);
        etValueIn = rootView.findViewById(R.id.edittext_currency_fragment_value_in);
        etValueOut = rootView.findViewById(R.id.edittext_currency_fragment_value_out);
        imgButtonConvert = rootView.findViewById(R.id.imgbutton_currency_fragment_convert);
        imgButtonConvert.setOnClickListener(this::onClick);
        tvResult = rootView.findViewById(R.id.textview_currency_fragment_result);
        tvUnit = rootView.findViewById(R.id.textview_currency_fragment_unit);

        // Fill the spinners with the currency names (abbreviation of them)
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<String >(rootView.getContext(), android.R.layout.simple_spinner_dropdown_item, ratesInfo.getNames());
        spOrigin.setAdapter(spinnerAdapter);
        spDestination.setAdapter(spinnerAdapter);

        return rootView;
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.imgbutton_currency_fragment_convert:
                String valueIn = etValueIn.getText().toString();
                String valueOut = etValueOut.getText().toString();
                String unitIn = spOrigin.getSelectedItem().toString();
                String unitOut = spDestination.getSelectedItem().toString();
                String rateIn = ratesInfo.getRates().get(unitIn);
                String rateOut = ratesInfo.getRates().get(unitOut);
                String res = "";

                double doubleRes = 0;
                double doubleRateIn = Double.parseDouble(rateIn);
                double doubleRateOut = Double.parseDouble(rateOut);

                if (valueIn.isEmpty() && !valueOut.isEmpty()){
                    double doubleOut = Double.parseDouble(valueOut);
                    doubleRes = doubleOut * doubleRateIn;
                    etValueIn.setText(String.valueOf(doubleRes));
                }
                if (valueOut.isEmpty() && !valueIn.isEmpty()){
                    double doubleIn = Double.parseDouble(valueIn);
                    doubleRes = doubleIn * doubleRateOut;
                    etValueOut.setText(String.valueOf(doubleRes));
                }


                break;
        }
    }
    private void requestExchangeRates() {
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL_EXCHANGE_RATES_API)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure:  F");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    json = responseBody.string();
                    ParserCurrency parserCurrency = new ParserCurrency();
                    parserCurrency.setJson(json);
                    ratesInfo = parserCurrency.parseRate();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onResponse: ***********************************************************************************************");
                }
            }
        });
    }
}
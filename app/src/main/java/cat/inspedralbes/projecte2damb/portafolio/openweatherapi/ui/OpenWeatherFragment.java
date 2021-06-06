package cat.inspedralbes.projecte2damb.portafolio.openweatherapi.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import cat.inspedralbes.projecte2damb.portafolio.R;
import cat.inspedralbes.projecte2damb.portafolio.openweatherapi.model.City;
import cat.inspedralbes.projecte2damb.portafolio.openweatherapi.model.Temperature;
import cat.inspedralbes.projecte2damb.portafolio.openweatherapi.ui.adapters.OpenweatherRecyclerViewAdapter;
import cat.inspedralbes.projecte2damb.portafolio.util.constants.Constants;
import cat.inspedralbes.projecte2damb.portafolio.util.parser.ParserOpenWeather;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OpenWeatherFragment extends Fragment {

    private static final String TAG = "**OpenWeatherFragment";

    //Model
    City city;

    //Utils
    ParserOpenWeather parserOpenWeather;
    OpenweatherRecyclerViewAdapter openweatherRecyclerViewAdapter;

    //UI
    View rootView;
    TextView tvCityName;

    public OpenWeatherFragment() {
        parserOpenWeather = new ParserOpenWeather();
    }

    public OpenweatherRecyclerViewAdapter getAdapter(){
        return openweatherRecyclerViewAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        city = new City();
        rootView = inflater.inflate(R.layout.fragment_openweather, container, false);
        tvCityName = rootView.findViewById(R.id.edittext_fragment_weather_cityname);
        openweatherRecyclerViewAdapter =new OpenweatherRecyclerViewAdapter(rootView.getContext(), city);
        ImageButton btnSearch = rootView.findViewById(R.id.imgbutton_fragment_weather_search);
        btnSearch.setOnClickListener(this::onClickFragment);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview_fragment_weather);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        recyclerView.setAdapter(openweatherRecyclerViewAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        tvCityName.setText("");
    }

    public void onClickFragment(View view) {
        switch (view.getId()){
            case R.id.imgbutton_fragment_weather_search:
                String cityName = tvCityName.getText().toString();
                String url = Constants.OPENWEATHER_URL + cityName + Constants.OPENWEATHER_APPID;
                requestOpenweather(url);
                break;
        }
    }

    private void requestOpenweather(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            private String json;

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.d("OkHttp", "onFailure: F");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    List<Temperature> temperatures;
                    json = responseBody.string();
//                    Log.d(TAG, "onResponse: " + json);
                    parserOpenWeather.setJson(json);
                    temperatures = parserOpenWeather.parseTemperatures();
                    city.setTemperatures(temperatures);
                    for (Temperature temp: temperatures) {
                        openweatherRecyclerViewAdapter.addTemperature(temp);
                    }
                    getActivity().runOnUiThread(() -> openweatherRecyclerViewAdapter.notifyDataSetChanged());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
package cat.inspedralbes.projecte2damb.portafolio.openweatherapi.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cat.inspedralbes.projecte2damb.portafolio.R;
import cat.inspedralbes.projecte2damb.portafolio.openweatherapi.model.City;
import cat.inspedralbes.projecte2damb.portafolio.openweatherapi.model.Temperature;

public class OpenweatherRecyclerViewAdapter extends RecyclerView.Adapter<OpenweatherRecyclerViewAdapter.CustomViewHolder> {

    private final Context context;
    private List<Temperature> temperatures;

    public OpenweatherRecyclerViewAdapter(Context context, City city){
        this.context = context;
        this.temperatures = city.getTemperatures();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_layout_openweather, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        String temperature = temperatures.get(position).getTemp();
        String timestamp = temperatures.get(position).getTimestamp();
        String weatherDescription = temperatures.get(position).getWeatherDescription();
        String minTemp = temperatures.get(position).getMinTemp();
        String maxTemp = temperatures.get(position).getMaxTemp();
        String weatherIcon = "img"+temperatures.get(position).getWeatherIcon();
        Integer imgIcon = temperatures.get(position).getImgIcon().get(weatherIcon);
        Log.d("****************", "onBindViewHolder: " + weatherIcon + " / " + imgIcon);
        holder.tvTemperature.setText(temperature);
        holder.tvTimestamp.setText(timestamp);
        holder.tvWeatherDescription.setText(weatherDescription);
        holder.tvMaxTemp.setText(maxTemp);
        holder.tvMinTemp.setText(minTemp);
        holder.imgIcon.setImageDrawable(context.getResources().getDrawable(imgIcon));
    }

    public void setTemperatures(List<Temperature> temperatures){this.temperatures = temperatures;}
    public void addTemperature(Temperature temperature){
        temperatures.add(temperature);
    }
    @Override
    public int getItemCount() {
        return temperatures.size();
    }

    /**
     * innerclass
     */
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tvTemperature;
        TextView tvTimestamp;
        TextView tvWeatherDescription;
        ImageView imgIcon;
        TextView tvMaxTemp;
        TextView tvMinTemp;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTemperature = itemView.findViewById(R.id.textview_recycler_layout_chat_username);
            tvTimestamp = itemView.findViewById(R.id.textview_recycler_layout_openweather_timestamp);
            tvWeatherDescription = itemView.findViewById(R.id.textview_recycler_layout_openweather_weather_description);
            imgIcon = itemView.findViewById(R.id.imgview_recycler_layout_openweather_icon);
            tvMaxTemp = itemView.findViewById(R.id.textview_recycler_layout_openweather_weather_maxtemp);
            tvMinTemp = itemView.findViewById(R.id.textview_recycler_layout_openweather_weather_mintemp);
        }
    }
}

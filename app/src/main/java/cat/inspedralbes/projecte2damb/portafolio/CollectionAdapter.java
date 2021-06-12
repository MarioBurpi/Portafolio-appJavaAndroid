package cat.inspedralbes.projecte2damb.portafolio;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import cat.inspedralbes.projecte2damb.portafolio.chatfirebase.ui.ChatRootFragment;
import cat.inspedralbes.projecte2damb.portafolio.currency.ui.CurrencyFragment;
import cat.inspedralbes.projecte2damb.portafolio.home.HomeFragment;
import cat.inspedralbes.projecte2damb.portafolio.openweatherapi.ui.OpenWeatherFragment;
import cat.inspedralbes.projecte2damb.portafolio.util.constants.Constants;

public class CollectionAdapter extends FragmentStateAdapter {

    HomeFragment homeFragment;
    OpenWeatherFragment openWeatherFragment;
    ChatRootFragment chatRootFragment;
    CurrencyFragment currencyFragment;

    public CollectionAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        homeFragment = new HomeFragment();
        openWeatherFragment = new OpenWeatherFragment();
        chatRootFragment = new ChatRootFragment();
        currencyFragment = new CurrencyFragment();
    }

    public CollectionAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public CollectionAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position+1) {
            case 1:
                return homeFragment;
            case 2:
                return openWeatherFragment;
            case 3:
                return chatRootFragment;
            case 4:
                return currencyFragment;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return Constants.TOTAL_FRAGMENTS;
    }
}

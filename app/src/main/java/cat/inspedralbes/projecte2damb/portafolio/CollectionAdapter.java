package cat.inspedralbes.projecte2damb.portafolio;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import cat.inspedralbes.projecte2damb.portafolio.chatfirebase.ChatLoginFragment;
import cat.inspedralbes.projecte2damb.portafolio.chatfirebase.ChatRootFragment;
import cat.inspedralbes.projecte2damb.portafolio.home.HomeFragment;
import cat.inspedralbes.projecte2damb.portafolio.openweatherapi.ui.OpenWeatherFragment;
import cat.inspedralbes.projecte2damb.portafolio.util.constants.Constants;

public class CollectionAdapter extends FragmentStateAdapter {

    public CollectionAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
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
                return new HomeFragment();
            case 2:
                return new OpenWeatherFragment();
            case 3:
                return new ChatRootFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return Constants.TOTAL_FRAGMENTS;
    }
}

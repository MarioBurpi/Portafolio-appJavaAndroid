package cat.inspedralbes.projecte2damb.portafolio;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

public class CollectionFragment extends Fragment {

    static final String TAG = "COLLECTION FRAGMENT: ";
    CollectionAdapter collectionAdapter;
    ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ON CREATE VIEW");
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        collectionAdapter = new CollectionAdapter(this);
        viewPager = view.findViewById(R.id.pager_screen);
        viewPager.setAdapter(collectionAdapter);
        Log.d(TAG, "onViewCreated: ON VIEW CREATED");
    }
}


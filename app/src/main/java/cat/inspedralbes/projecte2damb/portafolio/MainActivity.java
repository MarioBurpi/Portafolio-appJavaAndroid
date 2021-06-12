package cat.inspedralbes.projecte2damb.portafolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;

    CollectionAdapter collectionAdapter;
    ViewPager2 pager;
    TabLayoutMediator tabLayoutMediator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_layout);
        collectionAdapter = new CollectionAdapter(this);
        pager = findViewById(R.id.pager_screen);
        pager.setAdapter(collectionAdapter);

        tabLayoutMediator = new TabLayoutMediator(tabLayout, pager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position+1){
                            case 1:
                                tab.setIcon(getResources().getDrawable(R.drawable.profile_icon));
                                break;
                            case 2:
                                tab.setIcon(getResources().getDrawable(R.drawable.openweather_logo));
                                break;
                            case 3:
                                tab.setIcon(getResources().getDrawable(R.drawable.chat_icon));
                                break;
                            case 4:
                                tab.setIcon(getResources().getDrawable(R.drawable.currency_logo));
                                break;
                        }
                    }
                });
        tabLayoutMediator.attach();
    }

    // Handle the backstack
    @Override
    public void onBackPressed(){
        if (pager.getCurrentItem() == 0){
            //TODO show confirm dialog
            super.onBackPressed();
        }else {
            pager.setCurrentItem(pager.getCurrentItem()-1);
        }
    }


}
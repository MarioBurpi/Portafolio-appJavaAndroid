package cat.inspedralbes.projecte2damb.portafolio.chatfirebase.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

import cat.inspedralbes.projecte2damb.portafolio.R;

public class ChatLoginFragment extends Fragment {

    static final String TAG = "ChatLoginFragment:  ";

    View rootView;
    TextView tvNickName;
    ImageButton btnLogin;

    public ChatLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chat_login, container, false);
        btnLogin = rootView.findViewById(R.id.imgbutton_chat_login_fagment);
        btnLogin.setOnClickListener(this::onClick);
        // Inflate the layout for this fragment
        return rootView;
    }

    private void onClick(View view) {
        switch (view.getId()){
            case R.id.imgbutton_chat_login_fagment:
                Random rand = new Random();
                tvNickName = rootView.findViewById(R.id.edittext_fragment_chat_nickname);
                String nickName = tvNickName.getText().toString();
                btnLogin.setImageDrawable(rootView.getResources().getDrawable(R.drawable.power_button_on));
                if (nickName.isEmpty()){
                    nickName = "user" + rand.nextInt(999);
                }
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_root_chat, new ChatRoomFragment(nickName))
                        .commit();
                Log.d(TAG, "onClick: "+ nickName);
                break;
        }



    }
}
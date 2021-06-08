package cat.inspedralbes.projecte2damb.portafolio.chatfirebase;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cat.inspedralbes.projecte2damb.portafolio.R;

public class ChatRoomFragment extends Fragment {

    String nickName;

    View rootView;

    public ChatRoomFragment(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chat_room, container, false);
        // Inflate the layout for this fragment
        return rootView;
    }
}
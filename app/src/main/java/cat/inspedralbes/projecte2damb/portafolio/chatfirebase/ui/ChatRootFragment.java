package cat.inspedralbes.projecte2damb.portafolio.chatfirebase.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import cat.inspedralbes.projecte2damb.portafolio.R;

public class ChatRootFragment extends Fragment {

    View rootView;

    public ChatRootFragment () {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chat_root, container, false);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_root_chat, new ChatLoginFragment())
                .commit();
        return rootView;
    }
}


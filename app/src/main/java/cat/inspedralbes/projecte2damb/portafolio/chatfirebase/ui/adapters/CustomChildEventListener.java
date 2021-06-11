package cat.inspedralbes.projecte2damb.portafolio.chatfirebase.ui.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import cat.inspedralbes.projecte2damb.portafolio.chatfirebase.model.ChatMessage;
import cat.inspedralbes.projecte2damb.portafolio.chatfirebase.ui.soundmanager.SoundManager;

public class CustomChildEventListener implements ChildEventListener {

    ChatMessage chatMessage;
    SoundManager soundManager;
    ChatRoomRecyclerViewAdapter adapter;

    public CustomChildEventListener(ChatMessage chatMessage, SoundManager soundManager, ChatRoomRecyclerViewAdapter adapter){
        this.chatMessage = chatMessage;
        this.soundManager = soundManager;
        this.adapter = adapter;
    }
    @Override
    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        ChatMessage message = snapshot.getValue(ChatMessage.class);
        soundManager.playSound();
        adapter.addMessage(message);
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}

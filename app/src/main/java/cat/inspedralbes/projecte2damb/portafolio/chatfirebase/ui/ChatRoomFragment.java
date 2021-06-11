package cat.inspedralbes.projecte2damb.portafolio.chatfirebase.ui;

import android.app.Activity;
import android.app.usage.NetworkStats;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import cat.inspedralbes.projecte2damb.portafolio.R;
import cat.inspedralbes.projecte2damb.portafolio.chatfirebase.model.ChatMessage;
import cat.inspedralbes.projecte2damb.portafolio.chatfirebase.ui.adapters.ChatRoomRecyclerViewAdapter;
import cat.inspedralbes.projecte2damb.portafolio.chatfirebase.ui.adapters.CustomChildEventListener;
import cat.inspedralbes.projecte2damb.portafolio.chatfirebase.ui.soundmanager.SoundManager;

public class ChatRoomFragment extends Fragment {

    private final String TAG = "**CHAT FRAGMENT::";
    private static final int REQUEST_PHOTO = 110;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference refTextMessage;
    private ChildEventListener childEventListener;
    private FirebaseStorage firebaseStorage;
    private StorageReference refStorage;
    private StorageReference refUserDirectory;
    private StorageReference refNumberPhoto;
    int i = 0;

    private SoundManager soundManager;

    private View rootView;
    private EditText etMessageBox;
    private ImageButton imgButtonSendPhoto;
    private ImageButton imgButtonSendText;
    private RecyclerView recyclerView;
    private ChatRoomRecyclerViewAdapter adapter;

    private ChatMessage chatMessage;
    private String nickName;
    private String userDirectoryPath;
    private int userPhotoPath;

    public ChatRoomFragment(String nickName) {
        this.nickName = nickName;
        i = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userDirectoryPath =  nickName.toLowerCase();

        // View instances
        rootView = inflater.inflate(R.layout.fragment_chat_room, container, false);
        etMessageBox = rootView.findViewById(R.id.edittext_chat_room_message);
        imgButtonSendPhoto = rootView.findViewById(R.id.imgbutton_chat_room_send_photo);
        imgButtonSendPhoto.setOnClickListener(this::onClickSendMessage);
        imgButtonSendText = rootView.findViewById(R.id.imgbutton_chat_room_send_text);
        imgButtonSendText.setOnClickListener(this::onClickSendMessage);
        recyclerView = rootView.findViewById(R.id.recyclerview_fragment_chat_room);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ChatRoomRecyclerViewAdapter();
        adapter.setNickname(nickName);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemViewCacheSize(adapter.getMessages().size());
        // SoundManager instance
        soundManager = new SoundManager(getContext());
        // Firebase:  RealtimeDatabase instances to handle the text messages
        firebaseDatabase = FirebaseDatabase.getInstance("https://portfolio-55f54-default-rtdb.europe-west1.firebasedatabase.app/");
        refTextMessage = firebaseDatabase.getReference("messages");
        refTextMessage.setValue(null);
        // Firebase:  Storage instance to handle the photo messages
        firebaseStorage = FirebaseStorage.getInstance("gs://portfolio-55f54.appspot.com/");
        refStorage = firebaseStorage.getReference();
        refUserDirectory = refStorage.child(userDirectoryPath);
        refUserDirectory.delete();
        childEventListener = new CustomChildEventListener(chatMessage, soundManager, adapter);
        refTextMessage.addChildEventListener(childEventListener);
        return rootView;
    }

    public void onClickSendMessage(View view){
        String messageString = null;
        final ChatMessage[] messageObject = {null};

        switch (view.getId()){
            case R.id.imgbutton_chat_room_send_text:
                messageString = etMessageBox.getText().toString();
                messageObject[0] = new ChatMessage(nickName, messageString, 0);
                refTextMessage.push().setValue(messageObject[0]);
                etMessageBox.setText("");
                break;
            case R.id.imgbutton_chat_room_send_photo:
                Intent intentPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentPhoto, REQUEST_PHOTO);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        ChatMessage messageObject = null;
        if (resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap img = (Bitmap) extras.get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataPhoto = baos.toByteArray();
            int hash = dataPhoto.hashCode();
            // we want to save the message at the RealtimeDatabase (the message content will be the path to the Storage)
            messageObject = new ChatMessage(nickName, userDirectoryPath + "/" + i, 1);
            refTextMessage.push().setValue(messageObject);
            // and then upload the image at the Storage
            refNumberPhoto = refUserDirectory.child(String.valueOf(i));
            UploadTask uploadTask = refNumberPhoto.putBytes(dataPhoto);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "onSuccess: PHOTO NOT UPLOADED");
                }
            });
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    adapter.notifyDataSetChanged();
                    Log.d(TAG, "onSuccess: PHOTO UPLOADED SUCCESFULLY");
                }
            });
            i++;
        }
    }
}
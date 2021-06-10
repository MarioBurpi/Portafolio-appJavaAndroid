package cat.inspedralbes.projecte2damb.portafolio.chatfirebase.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import cat.inspedralbes.projecte2damb.portafolio.R;
import cat.inspedralbes.projecte2damb.portafolio.chatfirebase.model.ChatMessage;
import cat.inspedralbes.projecte2damb.portafolio.chatfirebase.ui.adapters.ChatRoomRecyclerViewAdapter;
import cat.inspedralbes.projecte2damb.portafolio.chatfirebase.ui.soundmanager.SoundManager;

public class ChatRoomFragment extends Fragment {

    private final String TAG = "**CHAT FRAGMENT::";
    private static final int REQUEST_PHOTO = 110;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference refTextMessage;
    private ChildEventListener childEventListener;
    private FirebaseStorage firebaseStorage;
    private StorageReference refStorage;
    private StorageReference refPhotoMessage;
    int i = 0;

    private SoundManager soundManager;

    private View rootView;
    private EditText etMessageBox;
    private ImageButton imgButtonSendPhoto;
    private ImageButton imgButtonSendText;
    private RecyclerView recyclerView;
    private ChatRoomRecyclerViewAdapter adapter;

    private String nickName;
    private String userPathStorage;

    public ChatRoomFragment(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        i = 0;
        userPathStorage =  "img/" + nickName.toLowerCase() + "/" + i;
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
        // SoundManager instance
        soundManager = new SoundManager(getContext());
        // Firebase:  RealtimeDatabase instances to handle the text messages
        firebaseDatabase = FirebaseDatabase.getInstance("https://portfolio-55f54-default-rtdb.europe-west1.firebasedatabase.app/");
        refTextMessage = firebaseDatabase.getReference("message");
        refTextMessage.setValue(null);
        // Firebase:  Storage instance to handle the photo messages
        firebaseStorage = FirebaseStorage.getInstance("gs://portfolio-55f54.appspot.com");
        refStorage = firebaseStorage.getReference();
        refPhotoMessage = refStorage.child("img").child(nickName.toLowerCase()).child("" + i);

        childEventListener = new ChildEventListener() {
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
        };
        return rootView;
    }

    // It is important that the childEventListener is attached onStart and detached onDestroy
    @Override
    public void onStart() {
        super.onStart();
        refTextMessage.addChildEventListener(childEventListener);
    }
//  Detach
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        refTextMessage.removeEventListener(childEventListener);
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
//                ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
//                        new ActivityResultContracts.StartActivityForResult(),
//                        new ActivityResultCallback<ActivityResult>() {
//                            @Override
//                            public void onActivityResult(ActivityResult result) {
//                                messageObject[0] = null;
//                                if (result.getResultCode() == Activity.RESULT_OK){
//                                    Intent data = result.getData();
//                                    Bundle extras = data.getExtras();
//                                    Bitmap img = (Bitmap) extras.get("data");
//                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                                    img.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                                    byte[] dataPhoto = baos.toByteArray();
//                                    int hash = dataPhoto.hashCode();
//                                    // we want to save the message at the RealtimeDatabase (the content of it will be the path to the Storage)
//                                    messageObject[0] = new ChatMessage(nickName, userPathStorage, 1);
//                                    refTextMessage.push().setValue(messageObject);
//                                    // and then upload the image (Bitmap to byte[]) at the Storage
//                                    UploadTask uploadTask = refPhotoMessage.putBytes(dataPhoto);
//                                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                        @Override
//                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                            Log.d(TAG, "onSuccess: PHOTO NOT UPLOADED");
//                                        }
//                                    });
//                                    uploadTask.addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Log.d(TAG, "onSuccess: PHOTO UPLOADED SUCCESFULLY");
//                                        }
//                                    });
//                                }
//                            }
//                        }
//                );
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
            // we want to save the message at the RealtimeDatabase (the content of it will be the path to the Storage)
            messageObject = new ChatMessage(nickName, userPathStorage, 1);
            refTextMessage.push().setValue(messageObject);
            // and then upload the image (Bitmap to byte[]) at the Storage
            UploadTask uploadTask = refPhotoMessage.putBytes(dataPhoto);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "onSuccess: PHOTO NOT UPLOADED");
                }
            });
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onSuccess: PHOTO UPLOADED SUCCESFULLY");
                }
            });
        }
    }
}
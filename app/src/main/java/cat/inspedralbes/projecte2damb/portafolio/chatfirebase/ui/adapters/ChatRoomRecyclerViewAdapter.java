package cat.inspedralbes.projecte2damb.portafolio.chatfirebase.ui.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import cat.inspedralbes.projecte2damb.portafolio.R;
import cat.inspedralbes.projecte2damb.portafolio.chatfirebase.model.ChatMessage;

public class ChatRoomRecyclerViewAdapter extends RecyclerView.Adapter<ChatRoomRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "ChatRecyclerViewAdapter";
    private final int VIEW_TYPE_SENT = 1;
    private final int VIEW_TYPE_RECEIVED = 2;
    private final String URL_FIREBASE_STORAGE = "gs://portfolio-55f54.appspot.com/";

    private String nickName;
    private List<ChatMessage> messages = new ArrayList<ChatMessage>();

    public void setNickname(String nickName){
        this.nickName = nickName;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_layout_received, parent, false);
            return new ViewHolder(view);
        }else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_layout_sent, parent, false);
            return new ViewHolder(view);
        }
    }

    public void addMessage(ChatMessage message){
        messages.add(message);
        notifyDataSetChanged();
        Log.d(TAG, "addMessage: " + messages.toString());
    }

    // This method decides which layout is inflated when a ChatMessage object is received.
    @Override
    public int getItemViewType(int position){
        if (messages.get(position).getNickName().equals(nickName))
            return VIEW_TYPE_SENT;
        else
            return VIEW_TYPE_RECEIVED;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        String messageContent = message.getMessage();
        String messageOwner = message.getNickName();
        // for us to search the photo (child) it's needed to split the messageContent String
        String[] contentSplit = messageContent.split("/");
        // This couple of conditionals will customize the view:
        // one showing our nickname in the bubble if we are the owner/sender of the message.
        // the second one, bigger, will hide the TextView if the message is photo-type and then download the data from FirebaseStorage.
        if (messageOwner == nickName) messageOwner = "You";
        if (message.getPhoto() != 0){
            holder.tvMessageContent.setVisibility(View.GONE);
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            Log.d(TAG, "onBindViewHolder: " + URL_FIREBASE_STORAGE + contentSplit[0]+ contentSplit[1]);
            StorageReference storageReference = firebaseStorage.getReferenceFromUrl(URL_FIREBASE_STORAGE).child(contentSplit[0]).child(contentSplit[1]);
            final long SIZE = 2048*2048;
            storageReference.getBytes(SIZE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap photo = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    holder.imgViewPhotoContent.setImageBitmap(photo);
                }
            });
        }else {
            holder.imgViewPhotoContent.setVisibility(View.GONE);
            holder.tvMessageContent.setText(messageContent);
        }
        holder.tvNicknameContent.setText(messageOwner);

    }

    public List<ChatMessage> getMessages(){return messages;}

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNicknameContent;
        TextView tvMessageContent;
        ImageView imgViewPhotoContent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNicknameContent = itemView.findViewById(R.id.textview_recycler_layout_chat_nickname);
            tvMessageContent = itemView.findViewById(R.id.textview_recycler_layout_chat_message);
            imgViewPhotoContent = itemView.findViewById(R.id.imgview_recycler_layout_chat_photo);
        }
    }
}

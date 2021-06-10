package cat.inspedralbes.projecte2damb.portafolio.chatfirebase.model;

public class ChatMessage {

    private String nickName;
    private String message;
    private int photo;

    public ChatMessage(){}
    public ChatMessage(String nickName, String message, int photo){
        this.nickName = nickName;
        this.message = message;
        this.photo = photo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "nickName='" + nickName + '\'' +
                ", message='" + message + '\'' +
                ", photo=" + photo +
                '}';
    }
}

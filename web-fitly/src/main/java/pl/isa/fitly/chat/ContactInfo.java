package pl.isa.fitly.chat;

public class ContactInfo {
    public ContactInfo(String email, String name, String roomId) {
        this.email = email;
        this.name = name;
        this.roomId = roomId;
    }

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    private String name;
    private String roomId;


}

package Controller;

public class Owner {

    private int OwnerId;
    private String OwnerName;

    public Owner(int ownerId, String ownerName) {
        OwnerId = ownerId;
        OwnerName = ownerName;
    }

    public int getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(int ownerId) {
        OwnerId = ownerId;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }
}

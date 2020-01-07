package Controller;

public class Ownership {
    int ownershipId, ownerId, storeId;

    public Ownership(int ownershipId, int ownerId, int storeId) {
        this.ownershipId = ownershipId;
        this.ownerId = ownerId;
        this.storeId = storeId;
    }

    public int getOwnershipId() {
        return ownershipId;
    }

    public void setOwnershipId(int ownershipId) {
        this.ownershipId = ownershipId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
}

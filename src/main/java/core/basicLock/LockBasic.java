package core.basicLock;

import core.common.EmptyLocksStatusActivity;
import core.common.Lock;
import core.common.LockChangeAction;
import core.common.LockChangeActionData;
import core.Status;

import java.util.*;

public class LockBasic implements Lock {
    public static Map<String, LockBasic> DATABASE = new HashMap<>();

    private String id;
    private Status lockStatus;
    private String ownerId;
    private List<String> tag;
    private LockChangeAction lockChangeAction;

    public LockBasic() {
        this.id = UUID.randomUUID().toString();
        this.lockStatus = Status.UNLOCKED;
        this.ownerId = UUID.randomUUID().toString();
        this.tag = new ArrayList<>();
        this.lockChangeAction = new EmptyLocksStatusActivity();

        DATABASE.put(id, this);
    }

    public LockBasic(LockChangeAction lockChangeAction) {
        this.id = UUID.randomUUID().toString();
        this.lockStatus = Status.UNLOCKED;
        this.ownerId = UUID.randomUUID().toString();
        this.tag = new ArrayList<>();
        this.lockChangeAction = lockChangeAction;
        DATABASE.put(id, this);
    }


    public LockBasic(List<String> tag) {
        this.id = UUID.randomUUID().toString();
        this.lockStatus = Status.UNLOCKED;
        this.ownerId = UUID.randomUUID().toString();
        this.tag = new ArrayList<>(tag);
        this.lockChangeAction = new EmptyLocksStatusActivity();

        DATABASE.put(id, this);
    }

    public LockBasic(List<String> tag, LockChangeAction lockChangeAction) {
        this.id = UUID.randomUUID().toString();
        this.lockStatus = Status.UNLOCKED;
        this.ownerId = UUID.randomUUID().toString();
        this.tag = new ArrayList<>(tag);
        this.lockChangeAction = lockChangeAction;
        DATABASE.put(id, this);
    }

    public String getId() {
        return id;
    }

    public Status getLockStatus() {
        return lockStatus;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public List<String> getTag() {
        return tag;
    }

    public LockChangeActionData lock() {
        this.lockStatus = Status.LOCKED;
        try {
            return lockChangeAction.atLock(this);
        } catch (Exception e) {
            throw e;
        }
    }

    public LockChangeActionData unlock() {
        this.lockStatus = Status.UNLOCKED;
        try {
            return lockChangeAction.atUnlock(this);
        } catch (Exception e) {
            throw e;
        }
    }

}

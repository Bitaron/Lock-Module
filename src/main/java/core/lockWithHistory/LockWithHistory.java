package core.lockWithHistory;

import core.Status;
import core.common.*;
import core.lockWithChangeRequest.LockChangeRequest;

import java.util.*;

public class LockWithHistory implements Lock {
    private String id;
    private Status lockStatus;
    private String ownerId;
    private List<String> tag;
    private LockChangeAction<Lock> lockChangeAction;
    LockChangeRequest activeLockChangeRequest;
    private List<LockChangeRequest> lockChangeRequestHistory;


    public LockWithHistory() {
        this.id = UUID.randomUUID().toString();
        this.lockStatus = Status.UNLOCKED;
        this.ownerId = UUID.randomUUID().toString();
        this.tag = new ArrayList<>();
        this.lockChangeAction = new EmptyLocksStatusActivity();

    }

    public LockWithHistory(LockChangeAction<Lock> lockChangeAction) {
        this.id = UUID.randomUUID().toString();
        this.lockStatus = Status.UNLOCKED;
        this.ownerId = UUID.randomUUID().toString();
        this.tag = new ArrayList<>();
        this.lockChangeAction = lockChangeAction;
    }

    public LockWithHistory(List<String> tag) {
        this.id = UUID.randomUUID().toString();
        this.lockStatus = Status.UNLOCKED;
        this.ownerId = UUID.randomUUID().toString();
        this.tag = new ArrayList<>(tag);
        this.lockChangeAction = new EmptyLocksStatusActivity();

    }

    public LockWithHistory(List<String> tag, LockChangeAction<Lock> lockChangeAction) {
        this.id = UUID.randomUUID().toString();
        this.lockStatus = Status.UNLOCKED;
        this.ownerId = UUID.randomUUID().toString();
        this.tag = new ArrayList<>(tag);
        this.lockChangeAction = lockChangeAction;
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

    public LockChangeRequest getActiveLockChangeRequest() {
        return activeLockChangeRequest;
    }

    public List<LockChangeRequest> getLockChangeRequestHistory() {
        return lockChangeRequestHistory;
    }

    public LockChangeRequest request(LockChangeRequestAction lockChangeRequestAction) {
        if (activeLockChangeRequest == null) {
            Status requestedStatus = Status.LOCKED;
            if (this.lockStatus == Status.LOCKED) {
                requestedStatus = Status.UNLOCKED;
            }
            if (lockChangeRequestAction == null) {
                lockChangeRequestAction = new EmptyLockRequestActionActivity();
            }
            LockChangeRequest lockChangeRequest = new LockChangeRequest(this.id, requestedStatus, lockChangeRequestAction);
            this.activeLockChangeRequest = lockChangeRequest;

            if (this.lockChangeRequestHistory == null) {
                this.lockChangeRequestHistory = new LinkedList<>();
            }
            this.lockChangeRequestHistory.add(lockChangeRequest);
        }
        return this.activeLockChangeRequest;
    }

    public LockChangeActionData accept(LockChangeRequestData lockChangeRequestData) {
        if (this.activeLockChangeRequest != null) {
            if (this.activeLockChangeRequest.getChangeRequestStatus() == Status.LOCKED) {
                lock();
            } else {
                unlock();
            }

            this.activeLockChangeRequest.action(true, "");
            LockChangeRequest lockChangeRequest = this.activeLockChangeRequest;
            this.activeLockChangeRequest = null;

            if (lockChangeRequest.getLockChangeRequestAction() != null) {
                try {
                    return lockChangeRequest.getLockChangeRequestAction().accept(this,
                            lockChangeRequestData);
                } catch (Exception e) {
                    throw e;
                }
            }
        }

        return null;
    }

    private LockChangeActionData<Lock> lock() {
        this.lockStatus = Status.LOCKED;
        try {
            return lockChangeAction.atLock(this);
        } catch (Exception e) {
            throw e;
        }
    }

    private LockChangeActionData<Lock> unlock() {
        this.lockStatus = Status.UNLOCKED;
        try {
            return lockChangeAction.atUnlock(this);
        } catch (Exception e) {
            throw e;
        }
    }
}

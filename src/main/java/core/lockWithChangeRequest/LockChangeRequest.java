package core.lockWithChangeRequest;

import core.Status;
import core.common.LockChangeRequestAction;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class LockChangeRequest {
    private String id;
    private String lockId;
    private Status changeRequestStatus;
    private Timestamp timestamp;
    private boolean isAccepted;
    private String remark;
    private LockChangeRequestAction lockChangeRequestAction;

    public LockChangeRequest(String lockId, Status status) {
        this.id = UUID.randomUUID().toString();
        this.lockId = lockId;
        this.changeRequestStatus = status;
        this.timestamp = new Timestamp(new Date().getTime());
    }

    public LockChangeRequest(String lockId, Status status,
                             LockChangeRequestAction lockChangeRequestAction) {
        this.lockId = lockId;
        this.changeRequestStatus = status;
        this.timestamp = new Timestamp(new Date().getTime());
        this.lockChangeRequestAction = lockChangeRequestAction;
    }

    public void action(boolean isAccepted, String remark) {
        this.isAccepted = isAccepted;
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public String getLockId() {
        return lockId;
    }

    public Status getChangeRequestStatus() {
        return changeRequestStatus;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public String getRemark() {
        return remark;
    }

    public LockChangeRequestAction getLockChangeRequestAction() {
        return lockChangeRequestAction;
    }
}

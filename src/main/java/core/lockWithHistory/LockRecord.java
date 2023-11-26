package core.lockWithHistory;

import core.Status;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class LockRecord {
    public String id;
    public String lockId;
    public Status status;
    public Timestamp timestamp;

    public LockRecord(String lockId, Status status) {
        this.id = UUID.randomUUID().toString();
        this.lockId = lockId;
        this.status = status;
        this.timestamp = new Timestamp(new Date().getTime());
    }
}

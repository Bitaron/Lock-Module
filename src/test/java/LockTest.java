import core.Status;
import core.basicLock.LockBasic;
import core.common.LockChangeActionData;
import core.common.LockChangeRequestData;
import core.lockWithChangeRequest.LockChangeRequest;
import core.lockWithChangeRequest.LockWithChangeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LockTest {

    @DisplayName("Simple lock test")
    @Test
    void testSimpleLockUnlock() {
        LockBasic lockBasic = new LockBasic();
        assertEquals(Status.UNLOCKED, lockBasic.getLockStatus());
        lockBasic.lock();
        assertEquals(Status.LOCKED, lockBasic.getLockStatus());
        lockBasic.unlock();
        assertEquals(Status.UNLOCKED, lockBasic.getLockStatus());
    }

    @DisplayName("Lock change action test")
    @Test
    void testLockChangeAction() {
        TestLockActionInterfaceImpl testLockActionInterface = new TestLockActionInterfaceImpl();
        LockBasic lockBasic = new LockBasic(testLockActionInterface);
        assertEquals(Status.UNLOCKED, lockBasic.getLockStatus());

        LockChangeActionData<TestData> lockData = lockBasic.lock();
        assertEquals(Status.LOCKED, lockBasic.getLockStatus());
        assertEquals(1, lockData.getClientData().getTestData());

        LockChangeActionData<TestData> unlockData = lockBasic.unlock();
        assertEquals(0, unlockData.getClientData().getTestData());
        assertEquals(Status.UNLOCKED, lockBasic.getLockStatus());
    }

    @DisplayName("Lock change request test")
    @Test
    void testLockChangeRequest() {
        LockWithChangeRequest lockWithChangeRequest = new LockWithChangeRequest();
        assertEquals(Status.UNLOCKED, lockWithChangeRequest.getLockStatus());

        LockChangeRequest lockChangeRequest = lockWithChangeRequest.request(null);
        LockChangeRequest activeRequest = lockWithChangeRequest.getActiveLockChangeRequest();
        assertEquals(lockChangeRequest.getId(), activeRequest.getId());

        List<LockChangeRequest> history = lockWithChangeRequest.getLockChangeRequestHistory();
        assertEquals(lockChangeRequest.getLockId(), history.get(0).getLockId());

        lockWithChangeRequest.accept(null);
        assertEquals(Status.LOCKED, lockWithChangeRequest.getLockStatus());
        assertEquals(null, activeRequest.getId());
        history = lockWithChangeRequest.getLockChangeRequestHistory();
        assertEquals(lockChangeRequest.getLockId(), history.get(0).getLockId());
        assertEquals(true, history.get(0).isAccepted());

    }

    @DisplayName("Lock change request action test")
    @Test
    void testLockChangeActionRequest() {
        TestLockChangeRequestActionInterfaceImpl lockChangeRequestActionInterface =
                new TestLockChangeRequestActionInterfaceImpl();
        TestData argument = new TestData(1);
        LockChangeRequestData<TestData> argumentData = new LockChangeRequestData<>(argument);
        LockWithChangeRequest lockWithChangeRequest = new LockWithChangeRequest();
        assertEquals(Status.UNLOCKED, lockWithChangeRequest.getLockStatus());

        LockChangeRequest lockChangeRequest =
                lockWithChangeRequest.request(lockChangeRequestActionInterface);
        LockChangeRequest activeRequest = lockWithChangeRequest.getActiveLockChangeRequest();
        assertEquals(lockChangeRequest.getId(), activeRequest.getId());

        List<LockChangeRequest> history = lockWithChangeRequest.getLockChangeRequestHistory();
        assertEquals(lockChangeRequest.getLockId(), history.get(0).getLockId());

        LockChangeActionData<TestData> returnData = lockWithChangeRequest.accept(argumentData);
        assertEquals(Status.LOCKED, lockWithChangeRequest.getLockStatus());
        assertEquals(null, activeRequest.getId());
        history = lockWithChangeRequest.getLockChangeRequestHistory();
        assertEquals(lockChangeRequest.getLockId(), history.get(0).getLockId());
        assertEquals(true, history.get(0).isAccepted());
        assertEquals(argumentData.getData().getTestData() + 1, returnData.getClientData().getTestData());

    }

}

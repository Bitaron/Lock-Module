import core.common.Lock;
import core.common.LockChangeAction;
import core.common.LockChangeActionData;

public class TestLockActionInterfaceImpl implements LockChangeAction<TestData> {


    @Override
    public LockChangeActionData<TestData> atLock(Lock lockBasic) {
        TestData testData = new TestData(1);
        return new LockChangeActionData<>(testData, lockBasic);
    }

    @Override
    public LockChangeActionData<TestData> atUnlock(Lock lockBasic) {
        TestData testData = new TestData(0);
        return new LockChangeActionData<>(testData, lockBasic);
    }

}

import core.common.Lock;
import core.common.LockChangeActionData;
import core.common.LockChangeRequestAction;
import core.common.LockChangeRequestData;

public class TestLockChangeRequestActionInterfaceImpl implements LockChangeRequestAction<TestData, TestData> {
    @Override
    public LockChangeActionData<TestData> accept(Lock lock, LockChangeRequestData<TestData> argument) {
        TestData testData = new TestData(argument.getData().getTestData() + 1);
        return new LockChangeActionData<>(testData, lock);
    }

    @Override
    public LockChangeActionData<TestData> reject(Lock lock, LockChangeRequestData<TestData> argument) {
        TestData testData = new TestData(argument.getData().getTestData() - 1);
        return new LockChangeActionData<>(testData, lock);
    }
}

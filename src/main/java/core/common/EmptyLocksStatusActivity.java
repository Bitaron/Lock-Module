package core.common;

public class EmptyLocksStatusActivity implements LockChangeAction<Lock> {

    @Override
    public LockChangeActionData<Lock> atLock(Lock lock) {
        return new LockChangeActionData<>(lock, lock);
    }

    @Override
    public LockChangeActionData<Lock> atUnlock(Lock lock) {
        return new LockChangeActionData<>(lock, lock);
    }
}

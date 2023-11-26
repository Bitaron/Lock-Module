package core.common;

public interface LockChangeAction<T> {
    LockChangeActionData<T> atLock(Lock lock);

    LockChangeActionData<T> atUnlock(Lock lock);
}

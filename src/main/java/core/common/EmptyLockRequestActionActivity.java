package core.common;

public class EmptyLockRequestActionActivity implements LockChangeRequestAction<Lock, Lock>{
    @Override
    public LockChangeActionData<Lock> accept(Lock lock, LockChangeRequestData<Lock> argument) {
        return null;
    }

    @Override
    public LockChangeActionData<Lock> reject(Lock lock, LockChangeRequestData<Lock> argument) {
        return null;
    }
}

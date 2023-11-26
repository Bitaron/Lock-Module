package core.common;

public interface LockChangeRequestAction<R, A> {
    LockChangeActionData<R> accept(Lock lock, LockChangeRequestData<A> argument);

    LockChangeActionData<R> reject(Lock lock, LockChangeRequestData<A> argument);
}

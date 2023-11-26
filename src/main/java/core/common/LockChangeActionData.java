package core.common;

import core.common.Lock;

public class LockChangeActionData<T> {
    T clientData;
    Lock lock;

    public LockChangeActionData(T clientData, Lock lock) {
        this.clientData = clientData;
        this.lock = lock;
    }

    public T getClientData() {
        return clientData;
    }

    public Lock getLock() {
        return lock;
    }
}

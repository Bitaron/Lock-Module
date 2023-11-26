package core.common;

public class LockChangeRequestData<T> {
    T data;

    public LockChangeRequestData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}

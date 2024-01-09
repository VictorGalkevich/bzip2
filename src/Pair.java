public class Pair<T, E> {
    private T v1;
    private E v2;

    public Pair(T v1, E v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public T getV1() {
        return v1;
    }

    public E getV2() {
        return v2;
    }
}

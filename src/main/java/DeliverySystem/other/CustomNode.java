package main.java.DeliverySystem.other;

public class CustomNode<T> {
    T data;
    CustomNode<T> next;

    CustomNode(T data) {
        this.data = data;
        this.next = null;
    }

    public T getData() {
        return data;
    }

    public CustomNode<T> getNext() {
        return next;
    }
}

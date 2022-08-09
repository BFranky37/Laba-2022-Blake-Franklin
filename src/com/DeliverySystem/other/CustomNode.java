package com.DeliverySystem.other;

public class CustomNode<T> {
    T data;
    CustomNode<T> next;

    CustomNode(T data) {
        this.data = data;
        this.next = null;
    }
}

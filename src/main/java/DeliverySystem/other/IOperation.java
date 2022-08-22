package DeliverySystem.other;

@FunctionalInterface
public interface IOperation<T> {
    T operation(T object1, T object2);
}

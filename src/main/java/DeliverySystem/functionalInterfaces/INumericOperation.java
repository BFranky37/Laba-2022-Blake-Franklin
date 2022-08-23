package DeliverySystem.functionalInterfaces;

@FunctionalInterface
public interface INumericOperation<T> {
    T operation(T object1, T object2);
}
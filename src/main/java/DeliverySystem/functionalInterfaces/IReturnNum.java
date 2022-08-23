package DeliverySystem.functionalInterfaces;

@FunctionalInterface
public interface IReturnNum<T> {
    int getItem(T item);
}

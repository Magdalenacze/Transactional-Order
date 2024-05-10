package pl.akademiaspecjalistowit.transactionalorder.order;

public interface OrderPlacedEventListener {
    void notifyOrderPlaced(OrderEntity orderEntityAfterValidations);
}

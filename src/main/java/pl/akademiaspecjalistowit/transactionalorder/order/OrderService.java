package pl.akademiaspecjalistowit.transactionalorder.order;

public interface OrderService {

    void placeAnOrder(OrderDto orderDto);

    void realizationOrder(Long id);

    void cancelOrder(Long id);
}

package pl.akademiaspecjalistowit.transactionalorder.order;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public void placeAnOrder(OrderDto orderDto) {
        OrderEntity orderEntity = new OrderEntity(
            orderDto.getProductName(),
            orderDto.getQuantity());

        orderRepository.save(orderEntity);
    }
}

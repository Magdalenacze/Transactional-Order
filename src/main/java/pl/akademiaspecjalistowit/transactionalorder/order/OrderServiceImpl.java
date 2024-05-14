package pl.akademiaspecjalistowit.transactionalorder.order;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.akademiaspecjalistowit.transactionalorder.product.ProductEntity;
import pl.akademiaspecjalistowit.transactionalorder.product.ProductException;
import pl.akademiaspecjalistowit.transactionalorder.product.ProductReadService;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductReadService productReadService;
    private final OrderPlacedEventListener orderPlacedEventListener;

    @Override
    @Transactional
    public void placeAnOrder(OrderDto orderDto) {
        OrderEntity orderEntity = productReadService.getProductByName(orderDto.getProductName())
                .map(productEntity -> {
                    return placeAnOrderWithStockUpdates(orderDto, productEntity);
                }).orElseThrow(() -> new OrderServiceException("Zamówienie nie może być zrealizowane, " +
                        "ponieważ zawiera pozycję niedostępną w magazynie"));
        orderRepository.save(orderEntity);
        orderPlacedEventListener.notifyOrderPlaced(orderEntity);
    }

    private static OrderEntity placeAnOrderWithStockUpdates(OrderDto orderDto, ProductEntity productEntity) {
        try {
            return new OrderEntity(productEntity, orderDto.getQuantity());
        } catch (ProductException e) {
            throw new OrderServiceException(
                    "Zamówienie nie może być zrealizowane, ponieważ ilość pozycji " +
                            "w magazynie jest niewystarczająca");
        }
    }
}
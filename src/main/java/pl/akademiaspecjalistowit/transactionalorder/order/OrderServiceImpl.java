package pl.akademiaspecjalistowit.transactionalorder.order;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.akademiaspecjalistowit.transactionalorder.product.ProductEntity;
import pl.akademiaspecjalistowit.transactionalorder.product.ProductException;
import pl.akademiaspecjalistowit.transactionalorder.product.ProductReadService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductReadService productReadService;
    private final OrderRealizedEventListener orderPlacedEventListener;

    @Override
    @Transactional
    public void placeAnOrder(OrderDto orderDto) {
        List<ProductEntity> productEntities = orderDto.getProducts()
                .stream()
                .map(productReadService::getProductByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        rejectIncompleteOrder(orderDto, productEntities);
        OrderEntity orderEntity = placeAnOrderWithStockUpdates(orderDto, productEntities);
        orderRepository.save(orderEntity);
    }

    @Override
    public void orderRealization(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).get();
        orderRepository.deleteById(orderId);
        orderPlacedEventListener.notifyOrderRealized(orderEntity);
    }

    @Override
    public void cancelOrder(Long id) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        orderEntity.orElseThrow(() -> new OrderServiceException("Zamówienie nie zostało anulowane, " +
                "ponieważ takie nie istnieje"));
        orderEntity.get().getProductEntityList()
                .stream()
                .forEach(e -> e.updateProductStockStatusAfterCancelingOrder(orderEntity.get().getQuantity()));
        orderRepository.save(orderEntity.get());
    }

    private static void rejectIncompleteOrder(OrderDto orderDto, List<ProductEntity> productEntities) {
        if (orderDto.getProducts().size() > productEntities.size()) {
            throw new OrderServiceException("Zamówienie zostało odrzucone, ponieważ niektóre pozycje" +
                    " są aktualnie niedostępne");
        }
    }

    private static OrderEntity placeAnOrderWithStockUpdates(OrderDto orderDto, List<ProductEntity> productEntityList) {
        try {
            return new OrderEntity(productEntityList, orderDto.getQuantity());
        } catch (ProductException e) {
            throw new OrderServiceException(
                    "Zamówienie nie może być zrealizowane, ponieważ ilość pozycji " +
                            "w magazynie jest niewystarczająca");
        }
    }
}
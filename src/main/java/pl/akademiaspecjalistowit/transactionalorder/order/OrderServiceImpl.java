package pl.akademiaspecjalistowit.transactionalorder.order;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.akademiaspecjalistowit.transactionalorder.product.ProductEntity;
import pl.akademiaspecjalistowit.transactionalorder.product.ProductException;
import pl.akademiaspecjalistowit.transactionalorder.product.ProductReadService;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductReadService productReadService;

    @Override
    public void placeAnOrder(OrderDto orderDto) {
        OrderEntity orderEntity = new OrderEntity(
            orderDto.getProductName(),
            orderDto.getQuantity());
        OrderEntity orderEntityAfterValidations = checkProductAvailability(orderEntity);
        orderRepository.save(orderEntityAfterValidations);
    }

    private OrderEntity checkProductAvailability(OrderEntity orderEntity) {
        Optional<ProductEntity> productByName = productReadService.getProductByName(orderEntity.getProductName());
        return productByName.map(product -> {
            try {
                product.checkAvailabilityForOrder(orderEntity);
            } catch (ProductException e) {

                throw new OrderServiceException(
                    "Zamównie nie może być zrealizowane ponieważ ilosć " +
                        "pozycji w magazynie jest niewystarczająca");
            }
            return orderEntity;
        }).orElseThrow(() -> new OrderServiceException("Zamównie nie moze być realizowane, ponieważ " +
            "zawiera pozycje niedostępną w magazynie"));
    }

}

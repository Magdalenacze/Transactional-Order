package pl.akademiaspecjalistowit.transactionalorder.order;

import pl.akademiaspecjalistowit.transactionalorder.product.ProductEntity;

import java.util.List;

public interface OrderRealizedEventListener {
    void notifyOrderRealized(OrderEntity orderEntity);
}

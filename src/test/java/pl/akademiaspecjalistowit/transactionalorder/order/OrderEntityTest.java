package pl.akademiaspecjalistowit.transactionalorder.order;


import static org.junit.jupiter.api.Assertions.assertThrows;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.akademiaspecjalistowit.transactionalorder.product.ProductEntity;

class OrderEntityTest {

    @Test
    void should_create_order_for_valid_quantity() {
        //given
        int validQuantity = 10;
        ProductEntity pizza = new ProductEntity("pizza", 12);

        //when
        OrderEntity orderEntity = new OrderEntity(pizza, validQuantity);

        //then
        Assertions.assertThat(pizza).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void should_not_create_order_for_invalid_quantity(int invalidQuantity) {
        //given
        ProductEntity pizza = new ProductEntity("pizza", invalidQuantity);

        //when
        Executable e = () -> new OrderEntity(pizza, invalidQuantity);

        //then
        assertThrows(OrderException.class, e);
    }
}
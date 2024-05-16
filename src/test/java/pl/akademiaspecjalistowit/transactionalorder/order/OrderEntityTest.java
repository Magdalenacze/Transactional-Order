package pl.akademiaspecjalistowit.transactionalorder.order;


import static org.junit.jupiter.api.Assertions.assertThrows;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.akademiaspecjalistowit.transactionalorder.product.ProductEntity;

import java.util.List;

class OrderEntityTest {

    @Test
    void should_create_order_for_valid_quantity() {
        //given
        int validQuantity = 10;
        List<ProductEntity> productEntityList = List.of(
                new ProductEntity("pizza", 12),
                new ProductEntity("cola", 15));

        //when
        OrderEntity orderEntity = new OrderEntity(productEntityList, validQuantity);

        //then
        Assertions.assertThat(productEntityList).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void should_not_create_order_for_invalid_quantity(int invalidQuantity) {
        //given
        List<ProductEntity> productEntityList = List.of(
                new ProductEntity("pizza", invalidQuantity),
                new ProductEntity("cola", invalidQuantity));

        //when
        Executable e = () -> new OrderEntity(productEntityList, invalidQuantity);

        //then
        assertThrows(OrderException.class, e);
    }
}
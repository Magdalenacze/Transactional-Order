package pl.akademiaspecjalistowit.transactionalorder.product;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pl.akademiaspecjalistowit.transactionalorder.order.OrderEntity;

import java.util.List;

class ProductEntityTest {

    @Test
    public void should_throw_exception_if_product_quantity_is_insufficient() {
        //given
        List<ProductEntity> productEntityList = List.of(
                new ProductEntity("pizza", 12),
                new ProductEntity("cola", 15));

        //when
        Executable e = () -> new OrderEntity(productEntityList, 17);

        //then
        ProductException productException = assertThrows(ProductException.class, e);
        assertThat(productException.getMessage()).isEqualTo("Ilość produktów nie jest wystarczająca");
    }

    @Test
    public void should_not_throw_exception_if_product_quantity_is_sufficient() {
        //given
        List<ProductEntity> productEntityList = List.of(
                new ProductEntity("pizza", 12),
                new ProductEntity("cola", 15));

        //when
        Executable e = () -> new OrderEntity(productEntityList, 12);

        //then
        assertDoesNotThrow(e);
    }

    @Test
    public void order_decreases_product_amount() {
        //given
        int initialQuantity = 12;
        List<ProductEntity> productEntityList = List.of(
                new ProductEntity("pizza", initialQuantity),
                new ProductEntity("cola", initialQuantity));

        //when
        OrderEntity orderEntity = new OrderEntity(productEntityList, 10);

        //then
        assertThat(productEntityList.get(0).getQuantity())
                .isEqualTo(initialQuantity - orderEntity.getQuantity());
        assertThat(productEntityList.get(1).getQuantity())
                .isEqualTo(initialQuantity - orderEntity.getQuantity());
    }
}
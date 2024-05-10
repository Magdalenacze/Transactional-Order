package pl.akademiaspecjalistowit.transactionalorder.product;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pl.akademiaspecjalistowit.transactionalorder.order.OrderEntity;

class ProductEntityTest {

    @Test
    public void should_throw_exception_if_product_quantity_is_insufficient() {
        //given
        ProductEntity pizza = new ProductEntity("pizza", 10);
        OrderEntity pizzaOrder = new OrderEntity("pizza", 12);

        //when
        Executable e = () -> pizza.applyOrder(pizzaOrder);

        //then
        ProductException productException = assertThrows(ProductException.class, e);
        assertThat(productException.getMessage()).isEqualTo("Ilość produktów nie jest wystarczająca");
    }

    @Test
    public void should_not_throw_exception_if_product_quantity_is_sufficient() {
        //given
        ProductEntity pizza = new ProductEntity("pizza", 12);
        OrderEntity pizzaOrder = new OrderEntity("pizza", 12);

        //when
        Executable e = () -> pizza.applyOrder(pizzaOrder);

        //then
        assertDoesNotThrow(e);
    }

    @Test
    public void order_decreases_product_amount() {
        //given
        int initialPizzaQuantity = 12;
        ProductEntity pizza = new ProductEntity("pizza", initialPizzaQuantity);
        OrderEntity pizzaOrder = new OrderEntity("pizza", 10);

        //when
        pizza.applyOrder(pizzaOrder);

        //then
        assertThat(pizza.getQuantity()).isEqualTo(initialPizzaQuantity - pizzaOrder.getQuantity());
    }
}
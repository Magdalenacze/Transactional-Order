package pl.akademiaspecjalistowit.transactionalorder.order;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;


    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    public void should_place_an_order_for_valid_input() {
        //given
        OrderDto orderDto = prepareValidOrderDto();

        //when
        orderService.placeAnOrder(orderDto);

        //then
        OrderEntity orderEntity = orderIsSavedInDatabase();
        //and
        theOrderMatchesInputValues(orderDto, orderEntity);
    }

    @Test
    public void order_will_not_be_placed_if_input_values_are_incorrect() {
        //given
        OrderDto invalidOrderDto = prepareInvalidOrderDto();

        //when
        Executable e = () -> orderService.placeAnOrder(invalidOrderDto);

        //then
        orderIsNotSavedInTheDatabase();
    }

    private void theOrderMatchesInputValues(OrderDto orderDto, OrderEntity orderEntity) {
        assertThat(orderDto.getProductName()).isEqualTo(orderEntity.getProductName());
        assertThat(orderDto.getQuantity()).isEqualTo(orderEntity.getQuantity());
    }

    private OrderEntity orderIsSavedInDatabase() {
        List<OrderEntity> all = orderRepository.findAll();
        assertThat(all).hasSize(1);
        return all.get(0);
    }

    private void orderIsNotSavedInTheDatabase() {
        List<OrderEntity> all = orderRepository.findAll();
        assertThat(all).hasSize(0);
    }

    private OrderDto prepareValidOrderDto() {
        int validQuantity = 10;
        return new OrderDto("exampleProduct", validQuantity);
    }

    private OrderDto prepareInvalidOrderDto() {
        int validQuantity = -1;
        return new OrderDto("exampleProduct", validQuantity);
    }
}
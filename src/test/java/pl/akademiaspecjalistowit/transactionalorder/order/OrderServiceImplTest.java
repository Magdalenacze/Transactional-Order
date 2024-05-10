package pl.akademiaspecjalistowit.transactionalorder.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.akademiaspecjalistowit.transactionalorder.product.ProductDto;
import pl.akademiaspecjalistowit.transactionalorder.product.ProductRepository;
import pl.akademiaspecjalistowit.transactionalorder.product.ProductService;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;


    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    public void should_place_an_order_for_valid_input_and_when_products_are_available() {
        //given
        OrderDto orderDto = prepareValidOrderDto();
        //and
        productForTestOrderIsAvailable(orderDto);

        //when
        orderService.placeAnOrder(orderDto);

        //then
        OrderEntity orderEntity = orderIsSavedInDatabase();
        //and
        theOrderMatchesInputValues(orderDto, orderEntity);
    }

    @Test
    public void order_will_not_be_placed_if_product_is_not_available() {
        //given
        OrderDto validOrderDto = prepareValidOrderDto();

        //when
        Executable e = () -> orderService.placeAnOrder(validOrderDto);

        //then
        orderIsNotSavedInTheDatabase();
        OrderServiceException orderServiceException = assertThrows(OrderServiceException.class, e);
        assertThat(orderServiceException.getMessage()).contains("zawiera pozycje niedostępną w magazynie");
    }

    @Test
    public void order_will_not_be_placed_if_product_availability_is_insufficient() {
        //given
        OrderDto validOrderDto = prepareValidOrderDto();
        productForTestOrderIsAvailableWithQuantity(validOrderDto, validOrderDto.getQuantity() -1);

        //when
        Executable e = () -> orderService.placeAnOrder(validOrderDto);

        //then
        orderIsNotSavedInTheDatabase();
        OrderServiceException orderServiceException = assertThrows(OrderServiceException.class, e);
        assertThat(orderServiceException.getMessage()).contains("ilosć pozycji w magazynie jest niewystarczająca");
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

    private void productForTestOrderIsAvailable(OrderDto orderDto) {
        productService.addProduct(new ProductDto(
            orderDto.getProductName(),
            orderDto.getQuantity()));
    }

    private void productForTestOrderIsAvailableWithQuantity(OrderDto orderDto, int quantity) {
        productService.addProduct(new ProductDto(
            orderDto.getProductName(),
            quantity));
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
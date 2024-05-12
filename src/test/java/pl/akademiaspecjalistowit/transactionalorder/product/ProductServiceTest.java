package pl.akademiaspecjalistowit.transactionalorder.product;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.akademiaspecjalistowit.transactionalorder.order.OrderDto;
import pl.akademiaspecjalistowit.transactionalorder.order.OrderService;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderService orderService;


    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void should_add_new_product() {
        //given
        ProductDto exampleProduct = new ProductDto("test1", 45);
        ProductEntity referenceEntity = new ProductEntity(
            exampleProduct.getName(),
            exampleProduct.getQuantity());

        //when
        productService.addProduct(exampleProduct);

        //when
        List<ProductEntity> all = productRepository.findAll();
        assertThat(all).hasSize(1);
        ProductEntity productEntity = all.get(0);
        assertThat(productEntity).isEqualTo(referenceEntity);
    }


    @Test
    void should_get_product() {
        //given
        ProductDto exampleProduct = new ProductDto("test2", 20);
        productService.addProduct(exampleProduct);

        //when
        List<ProductDto> products = productService.getProducts();

        //when
        assertThat(products).containsExactlyInAnyOrder(exampleProduct);
    }

    @Test
    public void zero_product_quantity_is_removed_from_the_database() {
        //given
        int initialPizzaQuantity = 12;
        ProductDto pizza = new ProductDto("pizza", initialPizzaQuantity);
        productService.addProduct(pizza);
        OrderDto pizzaOrder = new OrderDto("pizza", 12);

        //when
        orderService.placeAnOrder(pizzaOrder);

        //then
        List<ProductEntity> entities = productRepository.findAll();
        assertThat(entities).hasSize(0);
    }
}
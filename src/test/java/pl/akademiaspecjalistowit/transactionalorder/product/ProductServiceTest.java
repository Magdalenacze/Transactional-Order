package pl.akademiaspecjalistowit.transactionalorder.product;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;


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
        ProductEntity referenceEntity = new ProductEntity(
            exampleProduct.getName(),
            exampleProduct.getQuantity());
        Mockito.doReturn(List.of(referenceEntity)).when(productRepository).findAll();

        //when
        List<ProductDto> products = productService.getProducts();

        //when
        assertThat(products).containsExactlyInAnyOrder(exampleProduct);
    }
}
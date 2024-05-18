package pl.akademiaspecjalistowit.transactionalorder.product;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.akademiaspecjalistowit.transactionalorder.order.OrderEntity;
import pl.akademiaspecjalistowit.transactionalorder.order.OrderRealizedEventListener;

@AllArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements ProductService, ProductReadService, OrderRealizedEventListener {

    private final ProductRepository productRepository;

    @Override
    public void addProduct(ProductDto productDto) {
        ProductEntity productEntity =
            new ProductEntity(productDto.getName(), productDto.getQuantity());

        productRepository.save(productEntity);
    }

    @Override
    public List<ProductDto> getProducts() {
        List<ProductEntity> all = productRepository.findAll();
        return all.stream().map(e -> new ProductDto(e.getName(), e.getQuantity())).toList();
    }

    @Override
    public Optional<ProductEntity> getProductByName(String productName) {
        return productRepository.getProductEntityByName(productName);
    }

    private void removeBoughtOutProductsByName(List<ProductEntity> productEntityList){
        productEntityList
                .stream()
                .forEach(e -> productRepository.removeBoughtOutProducts(e.getName()));
    }

    @Override
    public void notifyOrderRealized(OrderEntity orderEntity) {
        removeBoughtOutProductsByName(orderEntity.getProductEntityList());
    }
}

package pl.akademiaspecjalistowit.transactionalorder.product;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public void addProduct(ProductDto productDto) {
        ProductEntity productEntity =
            new ProductEntity(productDto.getName(), productDto.getQuantity());
        productRepository.save(productEntity);
    }

    @Override
    public List<ProductDto> getProducts() {
        throw new RuntimeException("get products is not implemented YET");
    }
}

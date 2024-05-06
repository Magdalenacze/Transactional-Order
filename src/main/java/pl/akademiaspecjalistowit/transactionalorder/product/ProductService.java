package pl.akademiaspecjalistowit.transactionalorder.product;

import java.util.List;

public interface ProductService {

    void addProduct(ProductDto productDto);

    List<ProductDto> getProducts();
}

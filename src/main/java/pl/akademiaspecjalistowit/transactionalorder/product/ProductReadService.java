package pl.akademiaspecjalistowit.transactionalorder.product;

import java.util.Optional;

public interface ProductReadService {
    Optional<ProductEntity> getProductByName(String productName);
}

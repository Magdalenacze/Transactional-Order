package pl.akademiaspecjalistowit.transactionalorder.product;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> getProductEntityByName(String name);

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductEntity p WHERE p.quantity = 0 AND p.name =:productName")
    void removeBoughtOutProducts(String productName);
}

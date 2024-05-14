package pl.akademiaspecjalistowit.transactionalorder.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.akademiaspecjalistowit.transactionalorder.product.ProductEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer quantity;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity productEntity;

    public OrderEntity(ProductEntity productEntity, Integer quantity) {
        this.productEntity = productEntity;
        this.quantity = quantity;
        validate(quantity);
        productEntity.applyOrder(this);
    }

    private void validate(Integer quantity) {
        if (quantity <= 0) {
            throw new OrderException("Zamówienie pownno zawierać nie ujemną ilość pozycji");
        }
    }
}

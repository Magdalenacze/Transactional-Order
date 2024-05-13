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

    private String productName;

    private Integer quantity;

    @OneToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    public OrderEntity(String productName, Integer quantity) {
        validate(quantity);
        this.productName = productName;
        this.quantity = quantity;
    }

    private void validate(Integer quantity) {
        if (quantity <= 0) {
            throw new OrderException("Zamówienie pownno zawierać nie ujemną ilość pozycji");
        }
    }
}

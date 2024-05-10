package pl.akademiaspecjalistowit.transactionalorder.order;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String productName;

    private Integer quantity;

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

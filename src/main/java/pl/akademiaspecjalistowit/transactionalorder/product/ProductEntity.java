package pl.akademiaspecjalistowit.transactionalorder.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.akademiaspecjalistowit.transactionalorder.order.OrderEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    private Integer quantity;

    public ProductEntity(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductEntity that = (ProductEntity) o;
        return name.equals(that.name) && quantity.equals(that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantity);
    }


    public void applyOrder(OrderEntity orderEntity){
        checkAvailabilityForOrder(orderEntity);
        this.quantity -= orderEntity.getQuantity();
    }

    private void checkAvailabilityForOrder(OrderEntity orderEntity) {
        if (this.quantity.compareTo(orderEntity.getQuantity()) < 0) {
            throw new ProductException("Ilość produktów nie jest wystarczająca");
        }
    }
}

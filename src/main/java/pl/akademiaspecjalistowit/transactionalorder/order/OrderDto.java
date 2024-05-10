package pl.akademiaspecjalistowit.transactionalorder.order;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OrderDto {
    private String productName;
    private Integer quantity;
}

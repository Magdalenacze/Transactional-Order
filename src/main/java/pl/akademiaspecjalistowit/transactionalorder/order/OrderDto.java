package pl.akademiaspecjalistowit.transactionalorder.order;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OrderDto {
    private List<String> products;
    private Integer quantity;
}

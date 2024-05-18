package pl.akademiaspecjalistowit.transactionalorder.order;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void placeAnOrder(@RequestBody OrderDto orderDto) {
        orderService.placeAnOrder(orderDto);
    }

    @DeleteMapping("{id}")
    public void cancelOrder(@PathVariable("id") Long id) {
        orderService.cancelOrder(id);
    }

    @PostMapping("{id}")
    public void realizationOrder(@PathVariable("id") Long id) {
        orderService.orderRealization(id);
    }
}
package microservice.order_service.contoller;

import microservice.order_service.dto.CreateOrderDTO;
import microservice.order_service.dto.OrderDetailsDTO;
import microservice.order_service.entity.Order;
import microservice.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@RequestBody CreateOrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<OrderDetailsDTO> getOrderDetailsById(@PathVariable("id") Long id) {
        return orderService.getOrderDetailsById(id);
    }


}

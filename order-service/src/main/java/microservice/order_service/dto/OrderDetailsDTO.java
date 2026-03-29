package microservice.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDTO {
    private Long orderId;
    private CustomerDTO customer;
    private List<ProductDTO> products;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private Double total;
}

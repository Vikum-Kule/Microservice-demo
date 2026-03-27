package microservice.order_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderId;
    private Long customerId; // keep the customer reference only

    /*
    In real world  scenarios, we need to keep the order item instead of this.
    In this system we keep like this to make the system simple.
    Assumption: we assign all the products with quantity 1
    */
    @ElementCollection // use this annotation uses to tell the jpa, this attribute is a basic object collection and there is no any relationship with another entity.
    @CollectionTable(name = "order_product_ids" , joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "product_id")
    private List<Long> productIds;

    @Column(updatable = false)
    @Builder.Default
    private LocalDate orderDate = LocalDate.now();

    @Column(nullable = false)
    private LocalDate deliveryDate;


}

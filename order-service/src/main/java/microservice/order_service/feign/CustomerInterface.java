package microservice.order_service.feign;

import microservice.order_service.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("customer-service")
public interface CustomerInterface {

    @GetMapping("/customer/find/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById( @PathVariable Long id);
}

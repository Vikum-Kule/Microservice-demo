package com.microservice.customer_service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDTO {
    private String firstName;
    private String lastName;
    private String email;
}

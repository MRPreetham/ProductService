package org.example.productservice.Dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ExceptionDto {
    private String message;
}

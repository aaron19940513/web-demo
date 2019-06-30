package com.sam.demo.validate;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParamValidationResult {
    String message;
    String param;
}

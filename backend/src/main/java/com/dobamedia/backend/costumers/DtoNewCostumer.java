package com.dobamedia.backend.costumers;

import javax.validation.constraints.NotBlank;

public record DtoNewCostumer(
        @NotBlank
        String name,
        @NotBlank
        String paket
) {
}

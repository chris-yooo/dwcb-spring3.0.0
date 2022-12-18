package com.dobamedia.backend.storage;

import javax.validation.constraints.NotBlank;

public record DtoNewStorage(
        @NotBlank
        String boxname,
        @NotBlank
        String name,
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotBlank
        String link
) {
}

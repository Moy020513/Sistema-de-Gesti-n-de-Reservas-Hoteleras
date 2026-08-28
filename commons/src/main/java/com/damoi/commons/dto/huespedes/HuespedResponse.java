package com.damoi.commons.dto.huespedes;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record HuespedResponse(
        Long id,
        String nombre,
        String paterno,
        String materno,
        String email,
        String telefono,
        String documento,
        String nacionalidad
) {
}

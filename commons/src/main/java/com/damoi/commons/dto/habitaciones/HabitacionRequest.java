package com.damoi.commons.dto.habitaciones;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record HabitacionRequest(
        @NotNull(message = "El número de habitación es obligatorio")
        @Min(value = 1, message = "El número de habitación debe ser mayor a 0")
        @Max(value = 999, message = "El número de habitación no puede exceder 999")
        Double numero,

        @NotBlank(message = "El tipo de habitación es obligatorio")
        @Size(max = 50, message = "El tipo de habitación no puede exceder 50 caracteres")
        String tipo,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        @DecimalMax(value = "999999.99", message = "El precio no puede exceder 999999.99")
        BigDecimal precio,

        @NotNull(message = "La capacidad es obligatoria")
        @Min(value = 1, message = "La capacidad debe ser mayor a 0")
        @Max(value = 99, message = "La capacidad no puede exceder 99")
        Integer capacidad
) {
}

package com.damoi.commons.dto.habitaciones;

import java.math.BigDecimal;

public record HabitacionResponse(
        Long id,
        Double numero,
        String tipo,
        BigDecimal precio,
        Integer capacidad

) {
}

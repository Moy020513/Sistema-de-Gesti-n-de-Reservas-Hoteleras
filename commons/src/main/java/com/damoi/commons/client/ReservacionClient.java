package com.damoi.commons.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "reservas")
public interface ReservacionClient {

    @GetMapping("/habitacion-disponible/{id}")
    boolean validarHabitacionDisponible(@PathVariable Long id);
}

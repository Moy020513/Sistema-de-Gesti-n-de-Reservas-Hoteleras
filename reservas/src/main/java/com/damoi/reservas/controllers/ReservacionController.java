package com.damoi.reservas.controllers;

import com.damoi.commons.controller.CommonController;
import com.damoi.commons.dto.habitaciones.HabitacionResponse;
import com.damoi.commons.dto.huespedes.HuespedRequest;
import com.damoi.commons.dto.huespedes.HuespedResponse;
import com.damoi.reservas.dto.ReservacionRequest;
import com.damoi.reservas.dto.ReservacionResponse;
import com.damoi.reservas.services.ReservacionService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class ReservacionController extends CommonController<ReservacionRequest, ReservacionResponse, ReservacionService> {
    public ReservacionController(ReservacionService service) {
        super(service);
    }
    @GetMapping("/id-habitacion/{id}")
    public ResponseEntity<HabitacionResponse> obtenerHabitacionPorIdSinEstado(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id){
        return ResponseEntity.ok(service.obtenerReservacionPorId(id));
    }

    @GetMapping("/habitacion-disponible/{id}")
    public ResponseEntity<Boolean> buscarHabitacionDisponible(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarReservacionDisponible(id));
    }

}
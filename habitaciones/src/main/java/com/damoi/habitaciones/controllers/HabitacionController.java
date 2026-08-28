package com.damoi.habitaciones.controllers;


import com.damoi.commons.controller.CommonController;
import com.damoi.commons.dto.habitaciones.HabitacionRequest;
import com.damoi.commons.dto.habitaciones.HabitacionResponse;
import com.damoi.commons.mapper.CommonMapper;
import com.damoi.habitaciones.service.HabitacionService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class HabitacionController extends CommonController<HabitacionRequest, HabitacionResponse, HabitacionService> {

    public HabitacionController(HabitacionService service) {
        super(service);
    }

    @GetMapping("/id-habitacion/{id}")
    public ResponseEntity<HabitacionResponse> obtenerHabitacionPorIdSinEstado(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id){
        return ResponseEntity.ok(service.obtenerHabitacionPorId(id));
    }

    @PutMapping("/{idHabitacion}/disponibilidad/{idDisponibilidad}")
    public ResponseEntity<Void> actualizarDisponibilidadHabitacion(
            @PathVariable Long idHabitacion,
            @PathVariable Long idDisponibilidad
    ){
        service.actualizarEstadoHabitacion(idHabitacion, idDisponibilidad);
        return ResponseEntity.noContent().build();
}

    @GetMapping("/habitacion-disponible/{id}")
    public ResponseEntity<Boolean> buscarHabitacionDisponible(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarHabitacionDisponible(id));
    }
}

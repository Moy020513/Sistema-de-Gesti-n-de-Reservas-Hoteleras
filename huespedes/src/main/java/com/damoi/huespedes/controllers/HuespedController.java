package com.damoi.huespedes.controllers;

import com.damoi.commons.controller.CommonController;
import com.damoi.commons.dto.huespedes.HuespedRequest;
import com.damoi.commons.dto.huespedes.HuespedResponse;
import com.damoi.huespedes.entities.Huesped;
import com.damoi.huespedes.services.HuespedService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class HuespedController extends CommonController<HuespedRequest, HuespedResponse, HuespedService> {
    public HuespedController(HuespedService service) {
        super(service);
    }
    @GetMapping("/id-paciente/{id}")
    public ResponseEntity<HuespedResponse> buscarPacienteSinEstado(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id){

        return ResponseEntity.ok(service.huespedSinEstado(id));
    }

}

package com.damoi.habitaciones.service;

import com.damoi.commons.dto.habitaciones.HabitacionRequest;
import com.damoi.commons.dto.habitaciones.HabitacionResponse;
import com.damoi.commons.service.CrudService;

public interface HabitacionService extends CrudService<HabitacionRequest, HabitacionResponse> {

    HabitacionResponse obtenerHabitacionPorId(Long id);
    void eliminar(Long id);
    boolean buscarHabitacionDisponible(Long id);
}

package com.damoi.huespedes.services;

import com.damoi.commons.dto.huespedes.HuespedRequest;
import com.damoi.commons.dto.huespedes.HuespedResponse;
import com.damoi.commons.enums.EstadoRegistro;
import com.damoi.commons.service.CrudService;
import com.damoi.huespedes.entities.Huesped;
import com.damoi.huespedes.mapper.HuespedMapper;
import com.damoi.huespedes.repository.HuespedRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class HuespedServiceImp implements HuespedService {
    private final HuespedRepository huespedRepository;
    private final HuespedMapper huespedMapper;

    @Override
    @Transactional(readOnly = true)
    public List<HuespedResponse> listar() {
        return huespedRepository.getByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
                .map(huespedMapper::entidadResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HuespedResponse obtenerPorId(Long id) {
        return huespedMapper.entidadResponse(huespedRepository.getByEstadoRegistroAndId(EstadoRegistro.ACTIVO, id)
                .orElseThrow(() ->
                        new IllegalArgumentException("No se encontró ningún huésped con el id ingresado...")));
    }

    @Override
    public HuespedResponse registrar(HuespedRequest request) {
        Huesped huesped = huespedMapper.requestAEntidad(request);
        huespedRepository.save(huesped);

        return huespedMapper.entidadResponse(huesped);
    }

    @Override
    public HuespedResponse actualizar(HuespedRequest request, Long id) {
        Huesped huesped = huespedRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No se encontró ningún huésped con el id ingresado...")
        );
        huesped.actualizarHuesped(request.nombre(), request.apellidoPaterno(), request.apellidoMaterno(),
                request.email(), request.telefono(), request.documento(), request.nacionalidad());
        return huespedMapper.entidadResponse(huesped);
    }

    @Override
    public void eliminar(Long id) {
        Huesped huesped = huespedRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No se encontró ningún huésped con el id ingresado...")
        );
        huesped.eliminar();
    }

    @Override
    @Transactional(readOnly = true)
    public HuespedResponse huespedSinEstado(Long id) {
        return huespedMapper.entidadResponse(huespedRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("No se encontró ningún huésped con el id ingresado...")));
    }
}

package com.damoi.huespedes.services;

import com.damoi.commons.client.ReservacionClient;
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

    private final ReservacionClient reservacionClient;
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
        return huespedMapper.entidadResponse(obtenerHuesped(id));
    }

    @Override
    public HuespedResponse registrar(HuespedRequest request) {
        Huesped huesped = huespedMapper.requestAEntidad(request);
        validarDatosParaInsercion(request);
        huespedRepository.save(huesped);

        return huespedMapper.entidadResponse(huesped);
    }

    @Override
    public HuespedResponse actualizar(HuespedRequest request, Long id) {
        Huesped huesped = obtenerHuesped(id);
        validarDatosParaActualizacion(request, id);
        huesped.actualizarHuesped(request.nombre(), request.apellidoPaterno(), request.apellidoMaterno(),
                request.email(), request.telefono(), request.documento(), request.nacionalidad());
        return huespedMapper.entidadResponse(huesped);
    }

    @Override
    public void eliminar(Long id) {
        Huesped huesped = obtenerHuesped(id);

        if(reservacionClient.validarReservacionPorHuesped(huesped.getId()))
            throw new IllegalStateException("No se puede eliminar, tiene una reservacion asociada");

        huesped.eliminar();
    }

    @Override
    @Transactional(readOnly = true)
    public HuespedResponse huespedSinEstado(Long id) {
        return huespedMapper.entidadResponse(huespedRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("No se encontró ningún huésped con el id ingresado...")));
    }

    private Huesped obtenerHuesped(Long id){
        return huespedRepository.getByEstadoRegistroAndId(EstadoRegistro.ACTIVO, id)
                .orElseThrow(()-> new IllegalArgumentException("No se encontró ningún huésped con el id ingresado..."));
    }

    private void validarDatosParaInsercion(HuespedRequest request){
        if (huespedRepository.existsByEmailIgnoreCaseAndEstadoRegistro(request.email(), EstadoRegistro.ACTIVO))
            throw new IllegalArgumentException("Ya existe un huesped con el email ingresado");
        if (huespedRepository.existsByTelefonoAndEstadoRegistro(request.telefono(), EstadoRegistro.ACTIVO))
            throw new IllegalArgumentException("Ya existe un huesped con el telefono ingresado");
        if (huespedRepository.existsByDocumentoIgnoreCaseAndEstadoRegistro(request.documento(), EstadoRegistro.ACTIVO))
            throw new IllegalArgumentException("Ya existe un huesped con el numero de documento ingresado");
    }

    private void validarDatosParaActualizacion(HuespedRequest request, Long id){
        if (huespedRepository.existsByEmailIgnoreCaseAndEstadoRegistroAndIdNot(request.email(),
                EstadoRegistro.ACTIVO, id))
            throw new IllegalArgumentException("Ya existe un huesped con el email ingresado");
        if (huespedRepository.existsByTelefonoAndEstadoRegistroAndIdNot(request.telefono(),
                EstadoRegistro.ACTIVO, id))
            throw new IllegalArgumentException("Ya existe un huesped con el telefono ingresado");
        if (huespedRepository.existsByDocumentoIgnoreCaseAndEstadoRegistroAndIdNot(request.documento(),
                EstadoRegistro.ACTIVO, id))
            throw new IllegalArgumentException("Ya existe un huesped con el numero de documento ingresado");
    }

}

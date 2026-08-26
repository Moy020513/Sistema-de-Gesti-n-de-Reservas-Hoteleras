package com.damoi.huespedes.mapper;

import com.damoi.commons.dto.huespedes.HuespedRequest;
import com.damoi.commons.dto.huespedes.HuespedResponse;
import com.damoi.commons.enums.EstadoRegistro;
import com.damoi.commons.mapper.CommonMapper;
import com.damoi.huespedes.entities.Huesped;
import org.springframework.stereotype.Component;

@Component
public class HuespedMapper implements CommonMapper<HuespedRequest, HuespedResponse, Huesped> {
    @Override
    public Huesped requestAEntidad(HuespedRequest request) {
        if(request == null) return null;

        return Huesped.builder()
                .nombre(request.nombre().trim())
                .apellidoPaterno(request.apellidoPaterno().trim())
                .apellidoMaterno(request.apellidoMaterno().trim())
                .email(request.email().toLowerCase().trim())
                .telefono(request.telefono().trim())
                .documento(request.documento().trim())
                .nacionalidad(request.nacionalidad().trim())
                .estadoRegistro(EstadoRegistro.ACTIVO).build();
    }

    @Override
    public HuespedResponse entidadResponse(Huesped entidad) {
        if(entidad == null) return null;

        return new HuespedResponse(
                entidad.getId(),
                String.join(" ", entidad.getNombre(),
                        entidad.getApellidoPaterno(), entidad.getApellidoMaterno()),
                entidad.getEmail(),
                entidad.getTelefono(),
                entidad.getDocumento(),
                entidad.getNacionalidad()
        );
    }
}

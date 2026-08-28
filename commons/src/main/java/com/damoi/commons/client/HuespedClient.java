package com.damoi.commons.client;

import com.damoi.commons.dto.huespedes.HuespedResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "huespedes")
public interface HuespedClient {

    @GetMapping("/{id}")
    HuespedResponse obtenerHuespedActivoPorId(@PathVariable Long id);

    @GetMapping("/id-huesped/{id}")
    HuespedResponse obtenerHuespedSinEstadoPorId(@PathVariable Long id);


}

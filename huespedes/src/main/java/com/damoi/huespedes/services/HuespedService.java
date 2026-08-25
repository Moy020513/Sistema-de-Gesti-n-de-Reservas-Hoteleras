package com.damoi.huespedes.services;

import com.damoi.commons.dto.huespedes.HuespedRequest;
import com.damoi.commons.dto.huespedes.HuespedResponse;
import com.damoi.commons.service.CrudService;

public interface HuespedService  extends CrudService<HuespedRequest, HuespedResponse> {
    HuespedResponse huespedSinEstado(Long id);
}

package com.damoi.commons.dto.huespedes;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record HuespedRequest(
        @NotBlank(message = "No puede ir nulo o en blanco")
        @Size(min = 2, max = 50, message = "Debe contener 2-50 caracteres")
        String nombre,
        @NotBlank(message = "No puede ir nulo o en blanco")
        @Size(min = 2, max = 50, message = "Debe contener 2-50 caracteres")
        String apellidoPaterno,
        @NotBlank(message = "No puede ir nulo o en blanco")
        @Size(min = 2, max = 50, message = "Debe contener 2-50 caracteres")
        String apellidoMaterno,
        @NotBlank(message = "No puede ir nulo o en blanco")
        @Size(min = 6, max = 100, message = "Debe contener 6-100 caracteres")
        @Email(message = "Debe contener el formato ejemplo@dominio.com")
        String email,
        @NotBlank(message = "No puede ir nulo o en blanco")
        @Size(min = 10, max = 10, message = "Debe contener 10 caracteres")
        @Pattern(regexp = "^[0-9]{10}$", message = "Deben ser 10 digitos exactos")
        String telefono,
        @NotBlank(message = "No puede ir nulo o en blanco")
        @Size(min = 6, max = 20, message = "Debe contener 6-20 caracteres")
        String documento,
        @NotBlank(message = "No puede ir nulo o en blanco")
        @Size(min = 2, max = 20, message = "Debe contener 2-20 caracteres")
        String nacionalidad
) {
}

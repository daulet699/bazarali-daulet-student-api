package kz.iitu.bazaralidaulet.studentapi;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StudentRequest(
        @NotBlank @Size(max = 120) String fullName,
        @NotBlank @Email String email,
        @NotBlank String groupCode,
        @NotBlank String studentNumber
) {}

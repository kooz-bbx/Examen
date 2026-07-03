package cl.duoc.autenticacion_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El username no puede estar vacio.")
    @Size(max = 100, message = "El username no puede superar 100 caracteres.")
    private String username;

    @NotBlank(message = "La contrasena no puede estar vacia.")
    @Size(min = 4, max = 255, message = "La contrasena debe tener entre 4 y 255 caracteres.")
    private String password;

    @NotBlank(message = "El rol no puede estar vacio.")
    @Size(max = 50, message = "El rol no puede superar 50 caracteres.")
    private String rol;
}

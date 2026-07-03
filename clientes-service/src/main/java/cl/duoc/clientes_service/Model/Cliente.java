package cl.duoc.clientes_service.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 150, message = "El nombre no puede superar 150 caracteres.")
    private String nombre;

    @NotBlank(message = "El email no puede estar vacío.")
    @Email(message = "El email debe tener un formato válido.")
    @Size(max = 150, message = "El email no puede superar 150 caracteres.")
    private String email;

    @Size(max = 255, message = "La contraseña no puede superar 255 caracteres.")
    private String password;

    @Size(max = 20, message = "El teléfono no puede superar 20 caracteres.")
    private String telefono;

    @Size(max = 100, message = "La ciudad no puede superar 100 caracteres.")
    private String ciudad;
}

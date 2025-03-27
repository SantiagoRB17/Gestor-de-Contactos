package co.edu.uniquindio.poo.gestor_de_contactos.modelo;

import javafx.scene.image.Image;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder(toBuilder = true)
public class Contacto {
    private String nombre, apellido, email,telefono,id;
    private LocalDate fechaCumpleano;
    private Image imagenPefil;
}

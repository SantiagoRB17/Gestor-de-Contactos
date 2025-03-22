package co.edu.uniquindio.poo.gestor_de_contactos.modelo;

import co.edu.uniquindio.poo.gestor_de_contactos.validaciones.ValidacionCorreo;
import co.edu.uniquindio.poo.gestor_de_contactos.validaciones.ValidacionTelefono;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@AllArgsConstructor
public class GestionContactos {

    private ArrayList<Contacto> contactos;

    /**
     * Metodo que permite crear y agregar un contacto a la lista de contactos
     * @param nombre nombre del contacto
     * @param apellido apellido del contacto
     * @param email email de contacto
     * @param telefono telefono del contacto
     * @param fechaCumpleano fecha de nacimiento del contacto
     * @throws Exception Lanza una excepcion en caso de los valores de nombre, apellido, email y fecha de nacimiento
     * esten vacios, además de lanzar una excepcion si el correo o telefono no cumplen con el formato de la expresion regular
     */
    public void agregarContacto(String nombre, String apellido, String email, String telefono, LocalDate fechaCumpleano ) throws Exception {
        if(nombre==null || apellido==null || email==null){
            throw new Exception("Todos los campos son obligatorios");
        }
        if(!ValidacionCorreo.validarExpresionRegular(email)){
            throw new Exception("Email invalido");
        }
        if(fechaCumpleano==null){
            throw new Exception("La fecha de cumpleano es obligatoria");
        }
        if(!ValidacionTelefono.validarTelefono(telefono)){
            throw new Exception("Telefono invalido");
        }
        Contacto contacto= Contacto.builder()
                .id(UUID.randomUUID().toString())
                .nombre(nombre)
                .apellido(apellido)
                .email(email)
                .telefono(telefono).fechaCumpleano(fechaCumpleano)
                .build();
        contactos.add(contacto);
    }

}


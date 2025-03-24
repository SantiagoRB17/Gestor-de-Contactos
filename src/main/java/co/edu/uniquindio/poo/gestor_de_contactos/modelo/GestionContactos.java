package co.edu.uniquindio.poo.gestor_de_contactos.modelo;

import co.edu.uniquindio.poo.gestor_de_contactos.validaciones.ValidacionCorreo;
import co.edu.uniquindio.poo.gestor_de_contactos.validaciones.ValidacionTelefono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GestionContactos {

    private ArrayList<Contacto> contactos;


    public GestionContactos(){
        contactos=new ArrayList<>();
    }

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
        if(!verificarContacto(telefono)){
            if(nombre.isEmpty() || apellido.isEmpty() || email.isEmpty()){
                throw new Exception("Todos los campos son obligatorios");
            }
            if(!ValidacionCorreo.validarExpresionRegular(email)){
                throw new Exception("Email invalido");
            }
            if(fechaCumpleano==null){
                throw new Exception("La fecha de cumpleaños es obligatoria");
            }
            if(!ValidacionTelefono.validarTelefono(telefono)){
                throw new Exception("Telefono invalido");
            }
            Contacto contacto= Contacto.builder()
                    .id(UUID.randomUUID().toString())
                    .nombre(nombre)
                    .apellido(apellido)
                    .email(email)
                    .telefono(telefono)
                    .fechaCumpleano(fechaCumpleano)
                    .build();
            contactos.add(contacto);
        } else{
            throw new Exception("Ya existe un contacto con este numero de telefono");
        }
    }

    /**
     * Metodo que verifica si un contacto ya existe en la lista de contactos
     * @param telefono numero del contacto
     * @return falso si no existe true si existe
     */
    public boolean verificarContacto(String telefono){
        boolean encontrado=false;
        for(Contacto contacto: contactos){
            if(contacto.getTelefono().equals(telefono)){
                return true;
            }
        }
        return encontrado;
    }

    /**
     * Lista de los contactos disponibles
     * @return Lista de contactos
     */
    public List<Contacto> listarContactos(){
        return contactos;
    }


}


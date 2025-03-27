package co.edu.uniquindio.poo.gestor_de_contactos.modelo;

import co.edu.uniquindio.poo.gestor_de_contactos.validaciones.ValidacionCorreo;
import co.edu.uniquindio.poo.gestor_de_contactos.validaciones.ValidacionTelefono;
import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public void agregarContacto(String nombre, String apellido, String email, String telefono, LocalDate fechaCumpleano, Image imagen) throws Exception {
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
            if(imagen == null) {
                imagen = new Image(getClass().getResource("/perfilvacio.jpg").toExternalForm());
            }
            Contacto contacto= Contacto.builder()
                    .id(UUID.randomUUID().toString())
                    .nombre(nombre)
                    .apellido(apellido)
                    .email(email)
                    .telefono(telefono)
                    .fechaCumpleano(fechaCumpleano)
                    .imagenPefil(imagen)
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

    /**
     * Metodo para crear una lista con las opciones de busqueda para el combo box
     * @return lista con las opciones de busqueda
     */
    public ArrayList<String> listarOpciones(){
        ArrayList<String> opciones=new ArrayList<>();
        opciones.add("Telefono");
        opciones.add("Nombre");
        return opciones;
    }

    /**
     * Metodo que elimina el contacto de la lista de contactos
     * @param id
     * @throws Exception
     */
    public void eliminarContacto(String id) throws Exception {
        if(buscarContacto(id)!=-1){
            contactos.remove(buscarContacto(id));
        }
        else {

            throw new Exception("No existe el contacto");
        }
    }

    /**
     * Metodo que busca un contacto segun su id
     * @param id id del usuario
     * @return posicion del usuario en la lista
     */
    public int buscarContacto(String id){
        int posContacto=0;
        for(int i=0; i<contactos.size(); i++){
            if(contactos.get(i).getId().equals(id)){
                return posContacto=i;
            }
        }
        return-1;
    }

    /**
     * Metodo que busca un contacto segun su telefono
     * @param telefono telefono del contacto a buscar
     * @return retorna una lista de contactos con ese telefono
     */
    public List<Contacto> buscarContactoTelefono(String telefono){
        return contactos
                .stream()
                .filter(c -> c.getTelefono().equals(telefono))
                .collect(Collectors.toList());
    }

    /**
     * Metodo que busca un contacto según su nombre
     * @param nombre nombre del contacto a buscar
     * @return retorna una lista de contactos con ese nombre
     */
    public List<Contacto> buscarContactoNombre(String nombre){
        return contactos
                .stream()
                .filter(c -> c.getNombre().equalsIgnoreCase(nombre))
                .collect(Collectors.toList());
    }
}





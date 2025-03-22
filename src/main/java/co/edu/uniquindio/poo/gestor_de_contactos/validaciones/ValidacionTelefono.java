package co.edu.uniquindio.poo.gestor_de_contactos.validaciones;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacionTelefono {
    public static boolean validarTelefono(String telefono){
        String regexTelefono="^\\+?\\d{1,3}?\\d{7,15}$";
        Pattern expresionValida=Pattern.compile(regexTelefono);
        Matcher matcherTelefono=expresionValida.matcher(telefono);
        boolean valido;
        valido=matcherTelefono.matches();
        return valido;
    }
}

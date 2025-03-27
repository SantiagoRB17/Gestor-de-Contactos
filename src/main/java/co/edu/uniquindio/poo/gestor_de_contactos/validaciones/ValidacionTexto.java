package co.edu.uniquindio.poo.gestor_de_contactos.validaciones;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacionTexto {
    public static boolean validarTexto(String texto){
        String regexTexto="[a-zA-ZñÑ]+";
        Pattern expresionValida= Pattern.compile(regexTexto);
        Matcher matcher= expresionValida.matcher(texto);
        return matcher.matches();
    }
}

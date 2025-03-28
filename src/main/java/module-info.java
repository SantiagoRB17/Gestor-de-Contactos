module co.edu.uniquindio.poo.gestor_de_contactos {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires static lombok;
    requires com.dlsc.gemsfx;
    requires jfxtras.controls;
    requires java.desktop;

    opens co.edu.uniquindio.poo.gestor_de_contactos to javafx.fxml;
    exports co.edu.uniquindio.poo.gestor_de_contactos;
    exports co.edu.uniquindio.poo.gestor_de_contactos.modelo;
    exports co.edu.uniquindio.poo.gestor_de_contactos.controladores;
    opens co.edu.uniquindio.poo.gestor_de_contactos.controladores to javafx.fxml;
}
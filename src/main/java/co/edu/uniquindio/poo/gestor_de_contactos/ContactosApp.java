package co.edu.uniquindio.poo.gestor_de_contactos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ContactosApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ContactosApp.class.getResource("/principal.fxml"));
        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mis Contactos");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(ContactosApp.class,args);
    }

}

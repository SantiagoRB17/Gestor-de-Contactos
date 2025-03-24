package co.edu.uniquindio.poo.gestor_de_contactos.controladores;

import co.edu.uniquindio.poo.gestor_de_contactos.modelo.Contacto;
import co.edu.uniquindio.poo.gestor_de_contactos.modelo.GestionContactos;
import com.dlsc.gemsfx.CalendarPicker;
import com.dlsc.gemsfx.EmailField;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {

    @FXML
    private VBox Vbox_formulario;

    @FXML
    private VBox Vbox_tabla;

    @FXML
    private Button btn_cargarFoto;

    @FXML
    private Button btn_crear;

    @FXML
    private Button btn_editar;

    @FXML
    private Button btn_eliminar;

    @FXML
    private TableColumn<Contacto, String> cl_apellido;

    @FXML
    private TableColumn<Contacto, String> cl_cumpleaños;

    @FXML
    private TableColumn<Contacto, String> cl_email;

    @FXML
    private TableColumn<Contacto, String> cl_nombre;

    @FXML
    private TableColumn<Contacto, String> cl_telefono;

    @FXML
    private CalendarPicker clp_fechacumpleanos;

    @FXML
    private ComboBox<String> cmb_opcionesBusqueda;

    @FXML
    private ImageView img_imagenDisplay;

    @FXML
    private TableView<Contacto> tb_tablaContactos;

    @FXML
    private TextField txt_apellido;

    @FXML
    private TextField txt_buscar;

    @FXML
    private TextField txt_buscarTelONom;

    @FXML
    private EmailField txt_correo;

    @FXML
    private TextField txt_nombre;

    @FXML
    private TextField txt_telefono;

    @FXML
    void cargarFoto(ActionEvent event) {

    }


    @FXML
    void editarContacto(ActionEvent event) {

    }

    @FXML
    void eliminarContacto(ActionEvent event) {

    }

    private final GestionContactos gestionContactos;
    private Contacto contactoSeleccionado;
    private ObservableList<Contacto> contactosObservable;

    public PrincipalController() {
        gestionContactos=new GestionContactos();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        cl_nombre.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getNombre()));
        cl_apellido.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getApellido()));
        cl_cumpleaños.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getFechaCumpleano().toString()));
        cl_email.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getEmail()));
        cl_telefono.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getTelefono()));

        //Inicializar lista observable y cargar los contactos
        contactosObservable= FXCollections.observableArrayList();
        cargarContactos();

        //Evento click en la tabla
        tb_tablaContactos.setOnMouseClicked(event -> {
            //Obtener el contacto seleccionado
            contactoSeleccionado = tb_tablaContactos.getSelectionModel().getSelectedItem();

            if (contactoSeleccionado != null) {
                txt_nombre.setText(contactoSeleccionado.getNombre());
                txt_apellido.setText(contactoSeleccionado.getApellido());
                clp_fechacumpleanos.setValue(contactoSeleccionado.getFechaCumpleano());
                txt_telefono.setText(contactoSeleccionado.getTelefono());
                txt_correo.setEmailAddress(contactoSeleccionado.getEmail());
            }
        });

    }

    /**
     * Controlador del boton de crear contacto
     * @param e
     */
    public void crearContacto(ActionEvent e){
        try{
            gestionContactos.agregarContacto(
                    txt_nombre.getText(),
                    txt_apellido.getText(),
                    txt_correo.getEmailAddress(),
                    txt_telefono.getText(),
                    clp_fechacumpleanos.getValue()
            );
            limpiarCampos();
            actualizarContactos();
            mostrarAlerta("Contacto creado correctamente", Alert.AlertType.INFORMATION);
        }catch(Exception ex){
            mostrarAlerta(ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Agrega los contactos a la lista observable y los muestra en la tabla
     */
    public void cargarContactos(){
        contactosObservable.setAll(gestionContactos.listarContactos());
        tb_tablaContactos.setItems(contactosObservable);
    }
    /**
     * Actualiza la lista observable de contactos
     */
    public void actualizarContactos(){
        contactosObservable.setAll(gestionContactos.listarContactos());
    }

    /**
     * Muestra una alerta en pantalla
     * @param mensaje Mensaje a mostrar
     * @param tipo Tipo de alerta
     */
    public void mostrarAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Informacion");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

    /**
     * Limpia los campos de texto del formulario
     */
    public void limpiarCampos(){
        System.out.println(txt_correo);
        txt_nombre.clear();
        txt_apellido.clear();
        txt_correo.setEmailAddress("");
        txt_telefono.clear();
        clp_fechacumpleanos.setValue(null);
    }
}


package co.edu.uniquindio.poo.gestor_de_contactos.controladores;

import co.edu.uniquindio.poo.gestor_de_contactos.modelo.Contacto;
import co.edu.uniquindio.poo.gestor_de_contactos.modelo.GestionContactos;
import co.edu.uniquindio.poo.gestor_de_contactos.validaciones.ValidacionTelefono;
import co.edu.uniquindio.poo.gestor_de_contactos.validaciones.ValidacionTexto;
import com.dlsc.gemsfx.CalendarPicker;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import jfxtras.scene.layout.HBox;


import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    private TextField txt_correo;

    @FXML
    private TextField txt_nombre;

    @FXML
    private TextField txt_telefono;




    private final GestionContactos gestionContactos;
    private Contacto contactoSeleccionado;
    private ObservableList<Contacto> contactosObservable;

    public PrincipalController() {
        gestionContactos=new GestionContactos();
    }

    Image imagenDefault=new Image(getClass().getResource("/perfilvacio.jpg").toExternalForm());
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

        cmb_opcionesBusqueda.setItems(FXCollections.observableList(gestionContactos.listarOpciones()));

        img_imagenDisplay.setImage(imagenDefault);

        //Evento click en la tabla
        tb_tablaContactos.setOnMouseClicked(event -> {
            //Obtener el contacto seleccionado
            contactoSeleccionado = tb_tablaContactos.getSelectionModel().getSelectedItem();

            if (contactoSeleccionado != null) {
                txt_nombre.setText(contactoSeleccionado.getNombre());
                txt_apellido.setText(contactoSeleccionado.getApellido());
                clp_fechacumpleanos.setValue(contactoSeleccionado.getFechaCumpleano());
                txt_telefono.setText(contactoSeleccionado.getTelefono());
                txt_correo.setText(contactoSeleccionado.getEmail());
                img_imagenDisplay.setImage(contactoSeleccionado.getImagenPefil());
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
                    txt_correo.getText(),
                    txt_telefono.getText(),
                    clp_fechacumpleanos.getValue(),
                    img_imagenDisplay.getImage()
            );
            limpiarCampos();
            actualizarContactos(gestionContactos.listarContactos());
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
    public void actualizarContactos(List<Contacto> contactos){
        contactosObservable.setAll(contactos);
    }

    public void refrescarTabla(ActionEvent e){
        actualizarContactos(gestionContactos.listarContactos());
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
     * Controlador del boton cargar foto
     * @param e
     */
    public void cargarFoto(ActionEvent e){
        //Creacion de la instancia de la clase file chooser
        FileChooser fc = new FileChooser();
        fc.setTitle("Cargar Imagen");
        //Creacion del fitro para solo imagenes
        FileChooser.ExtensionFilter filtro = new FileChooser.ExtensionFilter("Archivos de Imagen", "*.jpg","*.png");
        fc.getExtensionFilters().add(filtro);

        //Obtener la ventana del boton para asociarla al file chooser
        Window ventana=btn_cargarFoto.getScene().getWindow();

        File file = fc.showOpenDialog(ventana);

        if (file != null) {
            img_imagenDisplay.setImage(new Image(file.toURI().toString()));
        }
    }

    /**
     * Controlador del boton limpiar
     * @param e
     */
    public void limpiarFormulario(ActionEvent e){
        limpiarCampos();
    }
    /**
     * Limpia los campos de texto del formulario
     */
    public void limpiarCampos(){
        txt_nombre.clear();
        txt_apellido.clear();
        txt_correo.clear();
        txt_telefono.clear();
        img_imagenDisplay.setImage(imagenDefault);
        clp_fechacumpleanos.setValue(null);
    }
    /**
     * Controlador del boton eliminar contacto
     * @param e
     */
    public void eliminarContacto(ActionEvent e) {
        try {
            if (contactoSeleccionado != null) {
                gestionContactos.eliminarContacto(contactoSeleccionado.getId());

                limpiarCampos();
                actualizarContactos(gestionContactos.listarContactos());
                mostrarAlerta("Contacto eliminado", Alert.AlertType.INFORMATION);
            }
        } catch (Exception ex) {
            mostrarAlerta(ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Metodo que filtra la tabla dependiendo de la opcion seleccionada en el combo box
     * @param actionEvent
     */
    public void filtrarTabla(ActionEvent actionEvent) {
        try {
            if(!verificarIngresado()){
                mostrarAlerta("Ingrese un formato valido", Alert.AlertType.ERROR);
            }
        }catch(Exception ex){
            mostrarAlerta(ex.getMessage(), Alert.AlertType.ERROR);
        }

        String valor = txt_buscarTelONom.getText();
        String tipo = cmb_opcionesBusqueda.getSelectionModel().getSelectedItem();

        List<Contacto> contactos = new ArrayList<>();

        if(!valor.isEmpty()){

            contactos = switch (tipo){
                case "Nombre" -> gestionContactos.buscarContactoNombre(valor);
                case "Telefono" -> gestionContactos.buscarContactoTelefono(valor);
                default -> throw new IllegalStateException("Unexpected value: " + tipo);
            };

                actualizarContactos(contactos);
        }

    }

    /**
     * Metodo que verifica si se seleccionó una opcion en el combo box o se ingresó el formato correcto
     * @return true si pasa las validaciones false si no las pasa
     * @throws Exception arroja una excepcion si no selecciona una opcion
     */
    public boolean verificarIngresado() throws  Exception{

        String valor = txt_buscarTelONom.getText();
        String tipo = cmb_opcionesBusqueda.getSelectionModel().getSelectedItem();

        boolean validacion = false;

        if(tipo==null || tipo.isEmpty()){
            throw new Exception("Seleccione una opcion");
        } else if (valor==null || valor.isEmpty()) {
            validacion=false;
        }else{
            switch(tipo){
                case "Nombre":
                    validacion= ValidacionTexto.validarTexto(valor);
                    break;
                case "Telefono":
                    validacion=ValidacionTelefono.validarTelefono(valor);
                    break;
                default:
                    throw new Exception("Seleccione una opcion");
            }
        }
        return validacion;
    }

    /**
     * Metodo que muestra la primera coincidencia de un contacto segun su nombre
     * @param e
     */
    public void mostarContacto(ActionEvent e){
        List<Contacto> contacto= gestionContactos.buscarContactoNombre(txt_buscar.getText());
        if(contacto.isEmpty()){
            mostrarAlerta("Contacto no encontrado", Alert.AlertType.ERROR);
            txt_buscar.clear();
        }else{
            Contacto contactoEncontrado = contacto.getFirst();
            mostrarAlerta(contactoEncontrado.toString(), Alert.AlertType.INFORMATION);
            txt_buscar.clear();
        }
    }







    public void editarContacto(ActionEvent event) {
        try {
            if (contactoSeleccionado == null) {
                mostrarAlerta("Error", "Seleccione un contacto para editar", Alert.AlertType.ERROR);
                return;
            }

            // Validar campos
            if (!validarCampos()) return;

            // Editar el contacto
            gestionContactos.editarContacto(
                    contactoSeleccionado.getId(),
                    txt_nombre.getText(),
                    txt_apellido.getText(),
                    txt_correo.getText(),
                    txt_telefono.getText(),
                    clp_fechacumpleanos.getValue(),
                    img_imagenDisplay.getImage()
            );

            mostrarAlerta("Éxito", "Contacto editado correctamente", Alert.AlertType.INFORMATION);
            actualizarTabla();
            limpiarFormulario(event);

        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean validarCampos() {
        if (txt_nombre.getText().isEmpty() || txt_apellido.getText().isEmpty() ||
                txt_correo.getText().isEmpty() || txt_telefono.getText().isEmpty() ||
                clp_fechacumpleanos.getValue() == null) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void actualizarTabla() {
        tb_tablaContactos.getItems().setAll(gestionContactos.listarContactos());
    }




    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}


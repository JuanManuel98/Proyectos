/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apredecc;

import com.jckinc.aprendecc.dominio.Estudiante;
import com.jckinc.aprendecc.servicio.EstudianteServicio;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author talos
 */
public class MainAplicationController implements Initializable {
    ObservableList<String> ol = FXCollections.observableArrayList("-Tipo de Usuario-", "Alumno", "Profesor");
    @FXML
    private Button RegisterBottom;
    @FXML
    private Button RegisterBottom2;
    @FXML
    private ComboBox optionUsuario;
    @FXML
    private Label labelUsuario;
    @FXML
    private TextField textCorreo;
    @FXML
    private TextField textPassword;
    @FXML
    private Label labelAcceso;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        optionUsuario.setValue("-Tipo de Usuario-");
        optionUsuario.setItems(ol);
    }
    @FXML
    private void ingresarUsuario(ActionEvent event){
        if(optionUsuario.getValue().equals("Alumno")){
            
        }
        if(optionUsuario.getValue().equals("Profesor")){
            
        }
    }

    @FXML
    private void changeScreenButtonPushed(ActionEvent event) throws IOException {
        
        //Hay que manejar posibles errores dentro de un try catch   
        try{
            System.out.println("You are going to the back window!");
            //Leeme el source del archivo que te digo fxml y te pongo el path

            FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
            Parent root1= (Parent)fxmlLoader.load();

            //Creame un nuevo Stage o para que nos entendamos una nueva ventana windows vacia
            Stage stage = (Stage) RegisterBottom.getScene().getWindow(); 

            //Y ahora dentro del Stage me metes la escena que anteriormente hemos leido y metido en root1
            stage.setScene(new Scene(root1));

            // Y ahora le digo que me muestres el stage
            stage.show();
        }catch (Exception e){e.printStackTrace();}
    } 
    
    
    @FXML
    private void changeScreenButtonPushed2(ActionEvent event) throws IOException {
        String correo = textCorreo.getText();
        String cadena = "";
        String password = textPassword.getText();
        EstudianteServicio eServicio = new EstudianteServicio();
        Estudiante estudi = new Estudiante();
        estudi = eServicio.consultar(correo, password);
        if(estudi!=null){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Ejercicio.fxml"));
                if(estudi.getExpLevel()!= 1){
                    fxmlLoader = new FXMLLoader(getClass().getResource("Ejercicio"+estudi.getExpLevel()+".fxml"));
                }
                ApredecC.setEstudiante(estudi);
                Parent root1= (Parent)fxmlLoader.load();

                //Creame un nuevo Stage o para que nos entendamos una nueva ventana windows vacia
                Stage stage = (Stage) RegisterBottom2.getScene().getWindow(); 

                //Y ahora dentro del Stage me metes la escena que anteriormente hemos leido y metido en root1
                stage.setScene(new Scene(root1));

                // Y ahora le digo que me muestres el stage
                stage.show();
            }catch (Exception e){e.printStackTrace();}
        }
        else{
            labelAcceso.setText("Correo de Usuario o contraseña incorrectos");
        }
    } 
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apredecc;

import com.jckinc.aprendecc.dominio.Estudiante;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import com.jckinc.aprendecc.dominio.Profesor;
import com.jckinc.aprendecc.servicio.EstudianteServicio;
import com.jckinc.aprendecc.servicio.ProfesorServicio;
import java.awt.event.ItemEvent;
import java.sql.Date;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
/**
 *
 * @author talos
 */
public class FXMLDocumentController implements Initializable {
    ObservableList<String> opciones = FXCollections.observableArrayList("-Tipo de Usuario-", "Alumno", "Profesor");
    
    
    @FXML
    private Label label;
    @FXML
    private Label codigoUsuario;
    private StringProperty prueba;
    @FXML
    private Label labelUsuario;
    @FXML
    private TextField textNombre;
    @FXML
    private TextField textCorreo;
    @FXML
    private TextField textPassword;
    @FXML
    private TextField textCodigo;
    @FXML
    private TextField textEscuela;
    @FXML
    private Button botonRetorno;
    
    private String nombre, correo, password, codigo, escuela; 
    
    @FXML
    private ComboBox optionP_A;
    
    @FXML
    private void comboButtonAction(ActionEvent event) {
        if(optionP_A.getValue().equals("Profesor")){
            labelUsuario.setText("Ingrese su código de empleado");            
        }
        if(optionP_A.getValue().equals("Alumno")){
            labelUsuario.setText("Ingrese la matricula");
        }
        if(optionP_A.getValue().equals("-Tipo de Usuario-")){
            labelUsuario.setText("Selccione una opcion");
        }
    }
    
    @FXML
    private void backWindowAction(ActionEvent event) {
        try{
            //LÃ©eme el source del archivo que te digo fxml y te pongo el path

            FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("MainAplication.fxml"));
            Parent root1= (Parent)fxmlLoader.load();

            //Creame un nuevo Stage o para que nos entendamos una nueva ventana windows vacia
            Stage stage = (Stage) botonRetorno.getScene().getWindow(); 

            //Y ahora dentro del Stage me metes la escena que anteriormente hemos leido y metido en root1
            stage.setScene(new Scene(root1));

            // Y ahora le digo que me muestres el stage
            stage.show();
        }catch (Exception e){e.printStackTrace();}
    }
    
    @FXML
    private void crearProfeAction(ActionEvent event){
        if(optionP_A.getValue().equals("Profesor")){
            ProfesorServicio pServicio = new ProfesorServicio();
            label.setText("Creando Profesor");
            Profesor profe = new Profesor();
            profe.setEmail(textCorreo.getText());
            profe.setName(textNombre.getText());
            profe.setEmployeeCode(textCodigo.getText());
            profe.setPassword(textPassword.getText());
        
            pServicio.crear(profe);
            if(profe.getId()!=null){
                label.setText("id:"+profe.getId());
            }
            else{
                label.setText("No fue posible crear el profesor");
            }
       
            Profesor profebd = pServicio.consultar(profe.getId());
            System.out.println(profe.getId() + profe.getName()+ profe.getEmployeeCode() + profe.getEmail() + profe.getPassword());
            System.out.println(profebd.getId() + profebd.getName()+ profebd.getEmployeeCode() + profebd.getEmail() + profebd.getPassword());
       
            if(profebd.equals(profe)){
                label.setText("el profesor se inserto con exito!");
            }
            else{
                label.setText("No fue posible crear el profesor");
            }            
        }
        if(optionP_A.getValue().equals("Alumno")){
            EstudianteServicio eServicio = new EstudianteServicio();
            label.setText("Creando Estudiante");
            Estudiante estudi = new Estudiante();
            estudi.setName(textNombre.getText());
            estudi.setPlate(textCodigo.getText());
            estudi.setEmail(textCorreo.getText());
            estudi.setPassword(textPassword.getText());
            estudi.setBirthdate(new java.sql.Date(1998, 05, 11));
            estudi.setExpLevel(1);
        
            eServicio.crear(estudi);
            if(estudi.getId()!=null){
                label.setText("id:"+estudi.getId());
            }
            else{
                label.setText("No fue posible crear el estudiante");
            }
       
            Estudiante estudibd = eServicio.consultar(estudi.getId());
            System.out.println(estudi.getId() + estudi.getName()+ estudi.getPlate() + estudi.getEmail() + estudi.getPassword());
            System.out.println(estudibd.getId() + estudibd.getName()+ estudibd.getPlate() + estudibd.getEmail() + estudibd.getPassword());
       
            if(estudibd.equals(estudi)){
                label.setText("el estudiante se inserto con exito!");
            }
            else{
                label.setText("No fue posible crear el estudiante");
            }
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        optionP_A.setValue("-Tipo de Usuario-");
        optionP_A.setItems(opciones);    
        
    }    
    
}

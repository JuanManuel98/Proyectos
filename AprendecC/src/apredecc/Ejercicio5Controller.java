/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apredecc;

import com.jckinc.aprendecc.servicio.EstudianteServicio;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author talos
 */
public class Ejercicio5Controller implements Initializable {
    private Integer resultado;
    @FXML
    private TextField text1;
    @FXML
    private TextField text2;
    @FXML
    private TextField text3;
    @FXML
    private TextField text4;
    @FXML
    private TextField text5;
    @FXML
    private TextField text6;
    @FXML
    private TextField text7;
    @FXML
    private Button botonRetorno;
    @FXML
    private Label labelCorrectas;
    @FXML
    private Label labelIncorrectas;
    
    @FXML
    private void comprobarRespuestas(ActionEvent event){
        resultado = 0;
        if(text1.getText().equals("DIVIDENDO")){
            resultado++;
        }
        if(text2.getText().equals("DIVIDENDO")){
            resultado++;
        }
        if(text3.getText().equals("DIVISOR")){
            resultado++;
        }
        if(text4.getText().equals("DIVIDENDO>0")){
            resultado++;
        }
        if(text5.getText().equals("CONT")){
            resultado++;
        }
        if(text6.getText().equals("DIVIDENDO")){
            resultado++;
        }
        if(text7.getText().equals("ESCRIBIR")){
            resultado++;
        }
        labelCorrectas.setText("Correctas: "+resultado);
        labelIncorrectas.setText("Incorrectas: "+ (7-resultado));
        /*if(resultado==7){
            ApredecC.getEstudiante().setExpLevel(5);
            System.out.println(ApredecC.getEstudiante().getBirthdate());
            EstudianteServicio eServicio = new EstudianteServicio();
            if(eServicio.actualizar(ApredecC.getEstudiante())){
                System.out.println("Se modifico con exito");
            }
            else{
                System.out.println("Error desconocido, no se pudieron modificar los valores");
            } 
        }*/
    }
    
    @FXML
    private void backWindowAction(ActionEvent event) {
        try{
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

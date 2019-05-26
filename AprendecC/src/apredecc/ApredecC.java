/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apredecc;

import com.jckinc.aprendecc.dominio.Estudiante;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author talos
 */
public class ApredecC extends Application {
    private static Estudiante estudiAct;
    
    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("MainAplication.fxml"));
        
        Scene scene = new Scene(root);
        escenarioPrincipal.setScene(scene);
        //this.escenarioPrincipal.setTitle("Aplicacion JCK");
        escenarioPrincipal.show();
    }
    
    public static void setEstudiante(Estudiante estudi){
        estudiAct = estudi;
    }
    
    public static Estudiante getEstudiante(){
        return estudiAct;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

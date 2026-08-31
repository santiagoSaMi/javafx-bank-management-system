/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author santi
 */
public class PrincipalController implements Initializable {

    @FXML
    private Button btn_goClientes;
    @FXML
    private Button btn_goProductos;
    @FXML
    private Button btn_goTransacciones;
    @FXML
    private Button btn_salir;
    @FXML
    private ImageView img_display;
    
    //======================================================================
    private Image startup;
    private Image clientes;
    private Image productos;
    private Image transacciones;
    @FXML
    private ImageView img_logo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Image logo=new Image("./imagesFijas/logo.jpg");
        this.img_logo.setImage(logo);
        this.startup=new Image("./imagesFijas/startup.jpg");
        this.clientes=new Image("./imagesFijas/clientes.jpg");
        this.productos=new Image("./imagesFijas/productos.jpg");
        this.transacciones=new Image("./imagesFijas/transacciones.jpg");
        this.img_display.setImage(this.startup);
    }    

    @FXML
    private void doGoClientes(ActionEvent event) {
        try
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/vista/clientes.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root);
            
            Stage stage=new Stage();
            stage.setOnCloseRequest(even -> {even.consume();});
            stage.setResizable(false);
            stage.setTitle("ADMINISTRACIÓN DE CLIENTES");
            //stage.initModality(Modality.WINDOW_MODAL); //no cierra la otra ventana
            stage.setScene(scene);
            stage.show();
            
            Stage myStage=(Stage)this.btn_goClientes.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex){ }
    }

    @FXML
    private void doGoProductos(ActionEvent event) {
        try
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/vista/productos.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root);
            
            Stage stage=new Stage();
            stage.setOnCloseRequest(even -> {even.consume();});
            stage.setResizable(false);
            stage.setTitle("ADMINISTRACIÓN DE PRODUCTOS");
            //stage.initModality(Modality.WINDOW_MODAL); //no cierra la otra ventana
            stage.setScene(scene);
            stage.show();
            
            Stage myStage=(Stage)this.btn_goProductos.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex){ }
    }

    @FXML
    private void doGoTransacciones(ActionEvent event) {
        try
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/vista/transacciones.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root);
            
            Stage stage=new Stage();
            stage.setOnCloseRequest(even -> {even.consume();});
            stage.setResizable(false);
            stage.setTitle("CAJERO AUTOMÁTICO");
            //stage.initModality(Modality.WINDOW_MODAL); //no cierra la otra ventana
            stage.setScene(scene);
            stage.show();
            
            Stage myStage=(Stage)this.btn_goTransacciones.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex){ }
    }

    @FXML
    private void doSalir(ActionEvent event) {
        Stage stage = (Stage) this.btn_salir.getScene().getWindow();
        stage.close();
        System.exit(0);
    }

    @FXML
    private void showClientes(MouseEvent event) {
        this.img_display.setImage(this.clientes);
    }

    @FXML
    private void showStartup(MouseEvent event) {
        this.img_display.setImage(this.startup);
    }

    @FXML
    private void showProductos(MouseEvent event) {
        this.img_display.setImage(this.productos);
    }

    @FXML
    private void showTransacciones(MouseEvent event) {
        this.img_display.setImage(this.transacciones);
    }
    
}

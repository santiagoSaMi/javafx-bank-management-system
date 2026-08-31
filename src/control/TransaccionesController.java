/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import gestion.GestionClientes;
import gestion.GestionProductos;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import negocio.Cliente;
import negocio.Producto;

/**
 * FXML Controller class
 *
 * @author santi
 */
public class TransaccionesController implements Initializable {

    @FXML
    private ImageView img_inicio;
    @FXML
    private Button btn_volver;
    @FXML
    private Button btn_ingresar;
    @FXML
    private ImageView img_principal;
    @FXML
    private Button btn_retiro;
    @FXML
    private Button btn_avance;
    @FXML
    private Button btn_cambioClave;
    @FXML
    private Button btn_compra;
    @FXML
    private Button btn_deposito;
    @FXML
    private Button btn_saldo;
    @FXML
    private Button btn_pagos;
    @FXML
    private Button btn_salir;
    @FXML
    private ComboBox<String> cbx_productos;
    @FXML
    private Label lbl_nombre;
    @FXML
    private Pane pan_inicio;
    @FXML
    private Pane pan_principal;
    @FXML
    private PasswordField txf_clave;
    
    //==================================================================
    private GestionClientes gesCl;
    private GestionProductos gesPro;
    private ArrayList<Cliente> clientes;
    private Producto productoActual;
    private String idActual;
    private StringBuilder clave;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Image inicio=new Image("./imagesFijas/cajeroInicio.png");
        Image principal=new Image("./imagesFijas/cajeroPrincipal.png");
        this.img_inicio.setImage(inicio);
        this.img_principal.setImage(principal);
        
        this.gesCl=new GestionClientes();
        this.gesPro=new GestionProductos();
        this.productoActual=null;
        this.idActual="";
        this.pan_inicio.setVisible(true);
        this.pan_principal.setVisible(true);
        this.pan_inicio.toFront();
        
        this.traerClientes();
    }    

    @FXML
    private void doVolver(ActionEvent event) {
        try
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/vista/principal.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root);
            
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.setOnCloseRequest(even -> {even.consume();});
            stage.setResizable(false);
            stage.setTitle("PÁGINA PRINCIPAL");
            stage.show();
            
            Stage myStage=(Stage)this.btn_volver.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex){ }
    }

    @FXML
    private void doIngresar(ActionEvent event) {
        String key;
        boolean existe=false;
        
        key=this.txf_clave.getText();
        if(key.isEmpty())
            this.showMessages("Debe digitar una clave.", 1);
        else
        {
            for(Cliente cliente: this.clientes)
            {
                if(cliente.getClave().equals(key))
                {
                    this.idActual=cliente.getIdentificacion();
                    
                    this.login(cliente);
                    existe=true;
                    break;
                }
            }
            if(!existe)
                this.showMessages("No existe cliente.", 1);
        }
    }

    @FXML
    private void doRetiro(ActionEvent event) {
        float valorUno,cantidad,transaccion;
        String cant;
        
        valorUno=this.productoActual.getValorUno();
        cant=this.showInputMessage("Digite cuánto va a retirar en miles COP: ");
        if(cant.isEmpty())
            this.showMessages("Error en la transacción. Debe digitar un valor.", 1);
        else
        {
            if(!cant.equals("cancel"))
            {
                cantidad=Float.parseFloat(cant);
                transaccion=valorUno-cantidad;
                if(transaccion>=0)
                {
                    this.gesPro.restarValorUno(this.productoActual.getIdentificacionProducto(), valorUno, cantidad);
                    this.productoActual.setValorUno(transaccion);
                    this.showMessages("Transacción aprobada. Retire su dinero.", 2);
                }
                else
                    this.showMessages("Transacción rechazada. Fondos insuficientes.", 1);
            }
        }
    }

    @FXML
    private void doAvance(ActionEvent event) {
        float valorUno,cantidad,transaccion;
        String cant;
        
        valorUno=this.productoActual.getValorUno();
        cant=this.showInputMessage("Digite la cantidad del avance en millones: ");
        if(cant.isEmpty())
            this.showMessages("Error en la transacción. Debe digitar un valor.", 1);
        else
        {
            if(!cant.equals("cancel"))
            {
                cantidad=Float.parseFloat(cant);
                transaccion=valorUno-cantidad;
                if(transaccion>=0)
                {
                    this.gesPro.restarValorUno(this.productoActual.getIdentificacionProducto(), valorUno, cantidad);
                    this.productoActual.setValorUno(transaccion);
                    this.showMessages("Transacción aprobada. Avance hecho.", 2);
                }
                else
                    this.showMessages("Transacción rechazada. Fondos insuficientes.", 1);
            }
        }
    }

    @FXML
    private void doCambioClave(ActionEvent event) {
        String newKey,errores="";
        String patronClave="\\d{4}";
        
        newKey=this.showInputMessage("Ingrese la nueva clave.");
        if(!newKey.isEmpty())
        {
            if(this.gesCl.pruebaExistenciaClave(newKey))
                errores+="Esa clave ya existe. \n";
            if(!newKey.matches(patronClave))
                errores+="La clave es de 4 dígitos. \n";
            if(errores.length()==0)
            {
                this.gesCl.cambiarClave(this.idActual, newKey);
                this.showMessages("Nueva clave asignada.", 2);
            }
            else
            {
                if(!newKey.equals("cancel"))
                    this.showMessages(errores, 1);
            }
        }
        else
            this.showMessages("Debe digitar una clave.", 1);
    }

    @FXML
    private void doCompra(ActionEvent event) {
        float valorUno,cantidad,transaccion;
        String cant;
        
        valorUno=this.productoActual.getValorUno();
        cant=this.showInputMessage("Digite el precio de la compra en millones COP: ");
        if(cant.isEmpty())
            this.showMessages("Error en la transacción. Debe digitar un valor.", 1);
        else
        {
            if(!cant.equals("cancel"))
            {
                cantidad=Float.parseFloat(cant);
                transaccion=valorUno-cantidad;
                if(transaccion>=0)
                {
                    this.gesPro.restarValorUno(this.productoActual.getIdentificacionProducto(), valorUno, cantidad);
                    this.productoActual.setValorUno(transaccion);
                    this.showMessages("Transacción aprobada.", 2);
                }
                else
                    this.showMessages("Transacción rechazada. Fondos insuficientes.", 1);
            }
        }
    }

    @FXML
    private void doDeposito(ActionEvent event) {
        float valorUno,cantidad;
        String cant;
        
        valorUno=this.productoActual.getValorUno();
        cant=this.showInputMessage("Digite cuánto va a depositar en miles COP:");
        if(cant.isEmpty())
            this.showMessages("Error en la transacción. Debe digitar un valor.", 1);
        else
        {
            if(!cant.equals("cancel"))
            {
                cantidad=Float.parseFloat(cant);
                this.gesPro.sumarValorUno(this.productoActual.getIdentificacionProducto(), valorUno, cantidad);
                this.productoActual.setValorUno(valorUno+cantidad);
                this.showMessages("Transacción aprobada.", 2);
            }
        }
    }

    @FXML
    private void doSaldo(ActionEvent event) {
        String idProducto,cantidad=" miles de COP.",mensaje,etiquetaValor;
        Float valorUno;
        
        if((this.productoActual.getTipoProducto().equals("4"))||(this.productoActual.getTipoProducto().equals("5")))
        {
            etiquetaValor="Cupo: ";
            cantidad=" millones de COP.";
        }
        else
            etiquetaValor="Saldo: ";
        
        idProducto=this.productoActual.getIdentificacionProducto();
        valorUno=this.productoActual.getValorUno();
        if(valorUno>=1000)
        {
            valorUno=valorUno/1000;
            cantidad=" millones de COP.";
        }
        mensaje="Producto: " + idProducto + "\n" + etiquetaValor + valorUno.toString() + cantidad;
        this.showMessages(mensaje, 2);
    }

    @FXML
    private void doPagos(ActionEvent event) {
        float valorUno,cantidad;
        String cant;
        
        valorUno=this.productoActual.getValorUno();
        cant=this.showInputMessage("Digite cuánto va a pagar en millones COP.");
        if(cant.isEmpty())
            this.showMessages("Error en la transacción. Debe digitar un valor.", 1);
        else
        {
            if(!cant.equals("cancel"))
            {
                cantidad=Float.parseFloat(cant);
                this.gesPro.sumarValorUno(this.productoActual.getIdentificacionProducto(), valorUno, cantidad);
                this.productoActual.setValorUno(valorUno+cantidad);
                this.showMessages("Transacción aprobada.", 2);
            }
        }
    }

    @FXML
    private void doSalir(ActionEvent event) {
        try
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/vista/principal.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root);
            
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.setOnCloseRequest(even -> {even.consume();});
            stage.setResizable(false);
            stage.setTitle("PÁGINA PRINCIPAL");
            stage.show();
            
            Stage myStage=(Stage)this.btn_salir.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex){ }
    }

    @FXML
    private void doPonerProductos(ActionEvent event) {
        String index;
        this.productoActual=null;
        
        index=this.cbx_productos.getSelectionModel().getSelectedItem();
        this.deshabilitarTodo();
        
        if(!"".equals(index))
        {
            if("Cuenta de Ahorros".equals(index))
            {
                this.productoActual=this.gesPro.buscarProducto("1", this.idActual);
                if(this.productoActual!=null)
                {
                    this.btn_retiro.setDisable(false);
                    this.btn_deposito.setDisable(false);
                    this.btn_saldo.setDisable(false);
                }
                else
                    this.showMessages("Debe activar el producto.", 1);
            }
            if("Cuenta Corriente".equals(index))
            {
                this.productoActual=this.gesPro.buscarProducto("2", this.idActual);
                if(this.productoActual!=null)
                {
                    this.btn_retiro.setDisable(false);
                    this.btn_deposito.setDisable(false);
                    this.btn_saldo.setDisable(false);
                }
                else
                    this.showMessages("Debe activar el producto.", 1);
            }
            if("CDT".equals(index))
            {
                this.productoActual=this.gesPro.buscarProducto("3", this.idActual);
                if(this.productoActual==null)
                    this.showMessages("Debe activar el producto.", 1);
                else
                    this.tasaDeInteres();
            }
            if("Tarjeta Visa".equals(index))
            {
                this.productoActual=this.gesPro.buscarProducto("4", this.idActual);
                if(this.productoActual!=null)
                {
                    this.btn_avance.setDisable(false);
                    this.btn_compra.setDisable(false);
                    this.btn_saldo.setDisable(false);
                    this.btn_pagos.setDisable(false);
                }
                else
                    this.showMessages("Debe activar el producto.", 1);
                
            }
            if("Tarjeta American".equals(index))
            {
                this.productoActual=this.gesPro.buscarProducto("5", this.idActual);
                if(this.productoActual!=null)
                {
                    this.btn_avance.setDisable(false);
                    this.btn_compra.setDisable(false);
                    this.btn_saldo.setDisable(false);
                    this.btn_pagos.setDisable(false);
                }
                else
                    this.showMessages("Debe activar el producto.", 1);
            }
        }
    }
    
    
    //=================================================================
    private void traerClientes()
    {
        this.clientes=this.gesCl.getTodos();
        if(this.clientes.isEmpty())
            this.showMessages("No hay clientes.", 1);
    }
    private void login(Cliente cliente)
    {
        this.pan_principal.toFront();
        this.lbl_nombre.setText(cliente.getNombre());
        this.llenarCombo(cliente.getProductos());
        this.deshabilitarTodo();
    }
    
    private void llenarCombo(boolean[] productos)
    {
        this.cbx_productos.getItems().clear();
        
        if(productos[0]==true)
            this.cbx_productos.getItems().add("Cuenta de Ahorros");
        if(productos[1]==true)
            this.cbx_productos.getItems().add("Cuenta Corriente");
        if(productos[2]==true)
            this.cbx_productos.getItems().add("CDT");
        if(productos[3]==true)
            this.cbx_productos.getItems().add("Tarjeta Visa");
        if(productos[4]==true)
            this.cbx_productos.getItems().add("Tarjeta American");
    }
    
    private void deshabilitarTodo()
    {
        this.btn_retiro.setDisable(true);
        this.btn_deposito.setDisable(true);
        this.btn_avance.setDisable(true);
        this.btn_compra.setDisable(true);
        this.btn_saldo.setDisable(true);
        this.btn_pagos.setDisable(true);
    }
    
    private boolean showMessages(String mesg, int caso)
    {
        Alert msg;
        boolean ok=false;
        
        if(caso==1) //Error
        {
            msg=new Alert(Alert.AlertType.ERROR);
            msg.setTitle("ERROR");
            
            msg.setHeaderText(null);
            msg.setContentText(mesg);
            msg.showAndWait();
        }
        if(caso==2) //Notificacion
        {
            msg=new Alert(Alert.AlertType.INFORMATION);
            msg.setTitle("ACCIÓN EXITOSA");
            
            msg.setHeaderText(null);
            msg.setContentText(mesg);
            msg.showAndWait();
        }
        if(caso==3)
        {
            msg=new Alert(Alert.AlertType.CONFIRMATION);
            msg.setTitle("CONFIRMACIÓN");
            
            msg.setHeaderText(null);
            msg.setContentText(mesg);
            msg.initStyle(StageStyle.UTILITY);
            
            Optional<ButtonType> result = msg.showAndWait();
            if(result.get()==ButtonType.OK)
                ok=true;
        }
        return ok;
    }
    private String showInputMessage(String mesg)
    {
        Alert msg;
        TextField input;
        GridPane grid;
        msg=new Alert(Alert.AlertType.CONFIRMATION);
        msg.setTitle("ACCIÓN");
        
        msg.setHeaderText(mesg);
        input=new TextField();
        grid=new GridPane();
        grid.add(input, 1, 0);
        msg.getDialogPane().setContent(input);
        
        Optional<ButtonType> result = msg.showAndWait();
            if(result.get()==ButtonType.OK)
                return input.getText();
            else
                return "cancel";
    }

    @FXML
    private void validaClave(KeyEvent event) {
        this.clave=new StringBuilder(this.txf_clave.getText());
        char c=event.getCharacter().charAt(0);
        if(!((Character.isDigit(c)||(c == java.awt.event.KeyEvent.VK_BACK_SPACE)||(c == java.awt.event.KeyEvent.VK_DELETE))))
        {
            int posCursor=this.txf_clave.getCaretPosition();
            
            this.clave.deleteCharAt(posCursor-1);
            
            this.txf_clave.clear();
            this.txf_clave.setText(this.clave.toString());
        }
        this.txf_clave.positionCaret(this.clave.length());
    }
    private void tasaDeInteres()
    {
        String tasa=this.showInputMessage("Introduzca una tasa de interes en %:");
        if(!tasa.isEmpty())
        {
            if(!tasa.equals("cancel"))
            {
                float tas=Float.parseFloat(tasa);
                tas=tas/100;
                float monto=(float)(this.productoActual.getValorUno()*(Math.pow((1+tas), (this.productoActual.getValorDos()))));
                this.showMessages("Su CDT está con: \n" + "Inversión de " + this.productoActual.getValorUno() + " millones de COP.\n" + "Plazo de " + this.productoActual.getValorDos() + " meses. \n" + "A una tasa del " + tasa + "%.", 2);
                this.showMessages("Dando un monto o valor final de: " + monto + " millones de COP.", 2);
            }
        }
        else
            this.showMessages("Error en la operación. Debe introducir un dato.", 1);
    }
}

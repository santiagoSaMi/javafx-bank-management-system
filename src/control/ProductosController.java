/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import gestion.GestionClientes;
import gestion.GestionProductos;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import negocio.Cliente;
import negocio.Producto;

/**
 * FXML Controller class
 *
 * @author santi
 */
public class ProductosController implements Initializable {

    @FXML
    private TextField txf_idCliente;
    @FXML
    private ComboBox<String> cbx_productos;
    @FXML
    private TextField txf_idProducto;
    @FXML
    private TextField txf_saldoInversion;
    @FXML
    private TextField txf_plazo;
    @FXML
    private DatePicker fec_fecha;
    @FXML
    private Button btn_crear;
    @FXML
    private Button btn_modificar;
    @FXML
    private Button btn_delete;
    @FXML
    private Button btn_regresar;
    @FXML
    private Button btn_buscar;
    @FXML
    private Button btn_verTodos;
    @FXML
    private Label lbl_valorUno;
    @FXML
    private Label lbl_valorDos;
    
    //=======================
    //variables de clase
    private GestionProductos gesPro;
    private GestionClientes gesCl;
    private ArrayList<Producto> productos;
    private ArrayList<Cliente> clientes;
    private int pos;
    private String idActual;
    private String productoActual;
    private StringBuilder idCliente;
    private StringBuilder valorUno;
    private StringBuilder valorDos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.gesPro=new GestionProductos();
        this.gesCl=new GestionClientes();
        this.pos=0;
        this.productos=null;
        this.clientes=null;
        this.idActual="";
        this.productoActual="";
        this.idCliente=null;
        this.valorUno=null;
        this.valorDos=null;
        
        this.deshabilitarTodo();
        this.traerProductos();
    }    

    @FXML
    private void doCrear(ActionEvent event) {
        String id,tipo=Integer.toString(this.pos),fecha,saldoInversion,plazo;
        String errores;
        LocalDate fec;
        
        errores=this.contarErrores();
        
        if(errores.length()==0)
        {
            fec=this.fec_fecha.getValue();
            fecha=fec.toString();
            id=this.txf_idProducto.getText();
            saldoInversion=this.txf_saldoInversion.getText();
            plazo=this.txf_plazo.getText();
            if(this.pos!=3)
            {
                Producto producto=new Producto(id,tipo,this.idActual,fecha,Float.parseFloat(saldoInversion),0);
                if(this.gesPro.guardarProducto(producto)==true)
                {
                    this.showMessages("El producto fue registrado exitosamente.", 2);
                    this.btn_crear.setDisable(true);
                    this.btn_modificar.setDisable(false);
                    this.btn_delete.setDisable(false);
                    this.traerProductos();
                    this.mostrarDatos(producto);
                }
            }
            else
            {
                Producto producto=new Producto(id,tipo,this.idActual,fecha,Float.parseFloat(saldoInversion),Integer.parseInt(plazo));
                if(this.gesPro.guardarProducto(producto)==true)
                {
                    this.showMessages("El producto fue registrado exitosamente.", 2);
                    this.btn_crear.setDisable(true);
                    this.btn_modificar.setDisable(false);
                    this.btn_delete.setDisable(false);
                    this.traerProductos();
                    this.mostrarDatos(producto);
                }
            }
        }
        else
            this.showMessages(errores, 1);
    }

    @FXML
    private void doModificar(ActionEvent event) {
        String id,tipo=Integer.toString(this.pos),fecha,saldoInversion,plazo;
        String errores;
        LocalDate fec;
        
        errores=this.contarErrores();
        if(errores.length()==0)
        {
            fec=this.fec_fecha.getValue();
            fecha=fec.toString();
            id=this.txf_idProducto.getText();
            saldoInversion=this.txf_saldoInversion.getText();
            plazo=this.txf_plazo.getText();
            Producto newProducto;
            if(this.pos!=3)
                newProducto=new Producto(id,tipo,this.idActual,fecha,Float.parseFloat(saldoInversion),0);
            else
                newProducto=new Producto(id,tipo,this.idActual,fecha,Float.parseFloat(saldoInversion),Integer.parseInt(plazo));
            
            this.gesPro.modificarProducto(this.productoActual, newProducto);
            this.showMessages("Cambios guardados.", 2);
            this.mostrarDatos(newProducto);
            this.traerProductos();
        }
        else
            this.showMessages(errores, 1);
    }

    @FXML
    private void doDelete(ActionEvent event) {
        boolean seguro;
        
        seguro=this.showMessages("¿Seguro? Esta acción no se puede deshacer.", 3);
        if(seguro)
        {
            this.gesPro.eliminarProducto(this.productoActual);
            this.gesCl.eliminarProducto(this.idActual, this.pos);
            this.showMessages("Producto eliminado.", 2);
            this.limpiarCajas();
            this.doBuscar(event);
            this.traerProductos();
        }
    }

    @FXML
    private void doRegresar(ActionEvent event) {
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
            
            Stage myStage=(Stage)this.btn_regresar.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex){ }
    }

    @FXML
    private void doBuscar(ActionEvent event) {
        String idCl;
        boolean existe=false;
        
        idCl=this.txf_idCliente.getText();
        if(idCl.isEmpty())
            this.showMessages("Debe digitar una identificación.", 1);
        else
        {
            this.clientes=this.gesCl.getTodos();
            for(Cliente cliente: this.clientes)
            {
                if(cliente.getIdentificacion().equals(idCl))
                {
                    this.llenarCombo(cliente.getProductos());
                    this.idActual=cliente.getIdentificacion();
                    
                    existe=true;
                    this.showMessages("Cliente encontrado.", 2);
                    break;
                }
            }
            if(!existe)
                this.showMessages("Ese cliente no existe.", 1);
        }
        
    }

    @FXML
    private void doVerTodos(ActionEvent event) {
        try
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/vista/allProductos.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root);
            
            AllProductosController controlVentana2=loader.getController();
            controlVentana2.setProductos(this.productos);
            
            Stage stage=new Stage();
            stage.setOnCloseRequest(even -> {even.consume();});
            stage.setResizable(false);
            stage.setTitle("TABLA DE PRODUCTOS");
            //stage.initModality(Modality.WINDOW_MODAL); //no cierra la otra ventana
            stage.setScene(scene);
            stage.show();
            
            Stage myStage=(Stage)this.btn_verTodos.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex){ }
    }
    
    private void llenarCombo(boolean[] productos)
    {
        this.cbx_productos.getItems().clear();
        this.limpiarCajas();
        
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
    private void mostrarDatos(Producto producto)
    {
        LocalDate fecha;
        
        fecha=LocalDate.parse(producto.getFecha());
        
        this.fec_fecha.setValue(fecha);
        this.txf_idProducto.setText(producto.getIdentificacionProducto());
        this.txf_saldoInversion.setText(Float.toString(producto.getValorUno()));
        this.productoActual=producto.getIdentificacionProducto();
        if(this.pos==3)
            this.txf_plazo.setText(Integer.toString(producto.getValorDos()));
    }
    private void idGenerator()
    {
        boolean ok=false;
        String patron="\\d{9}";
        while(!ok)
        {
            String id=Integer.toString((int)(Math.random()*999999999));
            if(id.matches(patron)&&!this.gesPro.pruebaExistenciaIdProducto(id))
            {
                ok=true;
                this.txf_idProducto.setText(id);
            }
        }
    }
    private void limpiarCajas()
    {
        this.fec_fecha.setValue(null);
        this.txf_idProducto.setText("");
        this.txf_saldoInversion.setText("");
        this.txf_plazo.setText("");
    }
    private void traerProductos()
    {
        this.productos=this.gesPro.getTodos();
        
        if(this.productos.isEmpty())
        {
            this.showMessages("No hay productos. Creélos introduciendo una ID válida.", 2);
        }
        
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

    @FXML
    private void doPonerProductos(ActionEvent event) {
        String index;
        Producto producto;
        
        index=this.cbx_productos.getSelectionModel().getSelectedItem();
        this.deshabilitarTodo();
        
        if(!"".equals(index))
        {
            if("Cuenta de Ahorros".equals(index))
            {
                this.pos=1;
                
                this.fec_fecha.setDisable(false);
                this.txf_idProducto.setDisable(false);
                this.txf_saldoInversion.setDisable(false);
                this.lbl_valorUno.setText("Saldo(miles COP)");
                
                producto=this.gesPro.buscarProducto("1", this.idActual);
                if(producto==null)
                {
                    this.btn_crear.setDisable(false);
                    
                    this.productoActual="new";
                    this.showMessages("Debe crear el producto.", 2);
                    this.idGenerator();
                    
                    
                }
                else
                {
                    this.btn_modificar.setDisable(false);
                    this.btn_delete.setDisable(false);
                    
                    this.mostrarDatos(producto);
                }
            }
            if("Cuenta Corriente".equals(index))
            {
                this.pos=2;
                
                this.fec_fecha.setDisable(false);
                this.txf_idProducto.setDisable(false);
                this.txf_saldoInversion.setDisable(false);
                this.lbl_valorUno.setText("Saldo(miles COP)");
                
                producto=this.gesPro.buscarProducto("2", this.idActual);
                if(producto==null)
                {
                    this.btn_crear.setDisable(false);
                    
                    this.productoActual="new";
                    this.showMessages("Debe crear el producto.", 2);
                    this.idGenerator();
                    
                }
                else
                {
                    this.btn_modificar.setDisable(false);
                    this.btn_delete.setDisable(false);
                    
                    this.mostrarDatos(producto);
                }
            }
            if("CDT".equals(index))
            {
                this.pos=3;
                
                this.fec_fecha.setDisable(false);
                this.txf_idProducto.setDisable(false);
                this.txf_saldoInversion.setDisable(false);
                this.txf_plazo.setDisable(false);
                this.lbl_valorUno.setText("Inversion(millones COP)");
                this.lbl_valorDos.setText("Plazo(meses)");
                
                producto=this.gesPro.buscarProducto("3", this.idActual);
                if(producto==null)
                {
                    this.btn_crear.setDisable(false);
                    
                    this.productoActual="new";
                    this.showMessages("Debe crear el producto.", 2);
                    this.idGenerator();
                }
                else
                {
                    this.btn_modificar.setDisable(false);
                    this.btn_delete.setDisable(false);
                    
                    this.mostrarDatos(producto);
                }
            }
            if("Tarjeta Visa".equals(index))
            {
                this.pos=4;
                
                this.fec_fecha.setDisable(false);
                this.txf_idProducto.setDisable(false);
                this.txf_saldoInversion.setDisable(false);
                this.lbl_valorUno.setText("Cupo(millones COP)");
                
                producto=this.gesPro.buscarProducto("4", this.idActual);
                if(producto==null)
                {
                    this.btn_crear.setDisable(false);
                    
                    this.productoActual="new";
                    this.showMessages("Debe crear el producto.", 2);
                    this.idGenerator();
                }
                else
                {
                    this.btn_modificar.setDisable(false);
                    this.btn_delete.setDisable(false);
                    
                    this.mostrarDatos(producto);
                }
            }
            if("Tarjeta American".equals(index))
            {
                this.pos=5;
                
                this.fec_fecha.setDisable(false);
                this.txf_idProducto.setDisable(false);
                this.txf_saldoInversion.setDisable(false);
                this.lbl_valorUno.setText("Cupo(millones COP)");
                
                producto=this.gesPro.buscarProducto("5", this.idActual);
                if(producto==null)
                {
                    this.btn_crear.setDisable(false);
                    
                    this.productoActual="new";
                    this.showMessages("Debe crear el producto.", 2);
                    this.idGenerator();
                }
                else
                {
                    this.btn_modificar.setDisable(false);
                    this.btn_delete.setDisable(false);
                    
                    this.mostrarDatos(producto);
                }
            }
        }
    }
    
    private void deshabilitarTodo()
    {
        this.btn_crear.setDisable(true);
        this.btn_modificar.setDisable(true);
        this.btn_delete.setDisable(true);
        
        this.fec_fecha.setValue(null);
        this.txf_idProducto.clear();
        this.txf_saldoInversion.clear();
        this.txf_plazo.clear();
        this.fec_fecha.setDisable(true);
        this.txf_idProducto.setDisable(true);
        this.txf_saldoInversion.setDisable(true);
        this.txf_plazo.setDisable(true);
        
        this.lbl_valorUno.setText("");
        this.lbl_valorDos.setText("");
    }
    private String contarErrores()
    {
        String errores="";
        LocalDate fecha;
        
        fecha=this.fec_fecha.getValue();
        if(fecha==null)
            errores+="El campo Fecha de Apertura está vacío. \n";
        if(this.txf_idProducto.getText().equals(""))
        {
            errores+="El campo ID Producto está vacío. \n";
            this.idGenerator();
        }
        if(this.txf_saldoInversion.getText().equals(""))
            errores+="El campo Saldo/Inversión/Cupo está vacío. \n";
        if(this.pos==3)
        {
            if(this.txf_plazo.getText().equals(""))
                errores+="El campo Plazo está vacío. \n";
        }
        return errores;
    }

    @FXML
    private void validaID(KeyEvent event) {
        this.idCliente=new StringBuilder(this.txf_idCliente.getText());
        char c=event.getCharacter().charAt(0);
        if(!((Character.isDigit(c)||(c == java.awt.event.KeyEvent.VK_BACK_SPACE)||(c == java.awt.event.KeyEvent.VK_DELETE))))
        {
            int posCursor=this.txf_idCliente.getCaretPosition();
            
            this.idCliente.deleteCharAt(posCursor-1);
            
            this.txf_idCliente.clear();
            this.txf_idCliente.setText(this.idCliente.toString());
        }
        this.txf_idCliente.positionCaret(this.idCliente.length());
    }

    @FXML
    private void validaValorUno(KeyEvent event) {
        this.valorUno=new StringBuilder(this.txf_saldoInversion.getText());
        char c=event.getCharacter().charAt(0);
        if(!((Character.isDigit(c)||(c == java.awt.event.KeyEvent.VK_BACK_SPACE)||(c == java.awt.event.KeyEvent.VK_DELETE)||(c == java.awt.event.KeyEvent.VK_PERIOD))))
        {
            int posCursor=this.txf_saldoInversion.getCaretPosition();
            
            this.valorUno.deleteCharAt(posCursor-1);
            
            this.txf_saldoInversion.clear();
            this.txf_saldoInversion.setText(this.valorUno.toString());
        }
        this.txf_saldoInversion.positionCaret(this.valorUno.length());
    }

    @FXML
    private void validaValorDos(KeyEvent event) {
        this.valorDos=new StringBuilder(this.txf_plazo.getText());
        char c=event.getCharacter().charAt(0);
        if(!((Character.isDigit(c)||(c == java.awt.event.KeyEvent.VK_BACK_SPACE)||(c == java.awt.event.KeyEvent.VK_DELETE))))
        {
            int posCursor=this.txf_plazo.getCaretPosition();
            
            this.valorDos.deleteCharAt(posCursor-1);
            
            this.txf_plazo.clear();
            this.txf_plazo.setText(this.valorDos.toString());
        }
        this.txf_plazo.positionCaret(this.valorDos.length());
    }

    @FXML
    private void doValidar(ActionEvent event) {
        LocalDate hoy=LocalDate.now();
        LocalDate fechaPuesta=this.fec_fecha.getValue();
        if(fechaPuesta!=null && fechaPuesta.isBefore(hoy))
        {
            this.showMessages("Fecha no válida.", 1);
            fechaPuesta=null;
            this.fec_fecha.setValue(fechaPuesta);
        }
    }
    
}

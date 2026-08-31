/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import gestion.GestionProductos;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import negocio.Producto;

/**
 * FXML Controller class
 *
 * @author santi
 */
public class AllProductosController implements Initializable {

    @FXML
    private TableView<Producto> tbl_productos;
    @FXML
    private TableColumn<?, ?> col_idProducto;
    @FXML
    private TableColumn<?, ?> col_idCliente;
    @FXML
    private TableColumn<?, ?> col_producto;
    @FXML
    private TableColumn<?, ?> col_apertura;
    @FXML
    private TableColumn<?, ?> col_disponible;
    @FXML
    private Button btn_regresar;
    @FXML
    private ComboBox<String> cbx_idCl;
    @FXML
    private ComboBox<String> cbx_producto;
    @FXML
    private TextField txf_idPr;
    
    //==============================================================================
    private ArrayList<Producto> productos;
    private ObservableList<Producto> obsProductos;
    private ObservableList<Producto> filtrados;
    private GestionProductos gesPro;
    private StringBuilder id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.gesPro=new GestionProductos();
        this.productos=this.gesPro.getTodos();
        this.obsProductos=FXCollections.observableArrayList();
        this.tbl_productos.setItems(this.obsProductos);
        this.filtrados=FXCollections.observableArrayList();
        this.id=null;
        this.llenarCombos();
        this.modelarTabla();
    }    

    @FXML
    private void doRegresar(ActionEvent event) {
        try
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/vista/productos.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root);
            
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.setOnCloseRequest(even -> {even.consume();});
            stage.setResizable(false);
            stage.setTitle("ADMINISTRACIÓN DE PRODUCTOS");
            stage.show();
            
            Stage myStage=(Stage)this.btn_regresar.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex){ }
    }

    @FXML
    private void doFilIdCl(ActionEvent event) {
        String index;
        
        this.filtrados.clear();
        index=this.cbx_idCl.getSelectionModel().getSelectedItem();
        
        if(index=="Todos")
            this.tbl_productos.setItems(this.obsProductos);
        else
        {
            if(index!="Todos")
            {
                for(Producto producto: this.obsProductos)
                {
                    if(producto.getIdentificacionCliente().equals(index))
                        this.filtrados.add(producto);
                }
            }
            this.tbl_productos.setItems(this.filtrados);
        }
        
    }

    @FXML
    private void doFilProducto(ActionEvent event) {
        int index;
        
        this.filtrados.clear();
        index=this.cbx_producto.getSelectionModel().getSelectedIndex();
        
        if(index==0)
            this.tbl_productos.setItems(this.obsProductos);
        else
        {
            if(index==1)
            {
                for(Producto producto: this.obsProductos)
                {
                    if(producto.getTipoProducto().equals("1"))
                        this.filtrados.add(producto);
                }
            }
            if(index==2)
            {
                for(Producto producto: this.obsProductos)
                {
                    if(producto.getTipoProducto().equals("2"))
                        this.filtrados.add(producto);
                }
            }
            if(index==3)
            {
                for(Producto producto: this.obsProductos)
                {
                    if(producto.getTipoProducto().equals("3"))
                        this.filtrados.add(producto);
                }
            }
            if(index==4)
            {
                for(Producto producto: this.obsProductos)
                {
                    if(producto.getTipoProducto().equals("4"))
                        this.filtrados.add(producto);
                }
            }
            if(index==5)
            {
                for(Producto producto: this.obsProductos)
                {
                    if(producto.getTipoProducto().equals("5"))
                        this.filtrados.add(producto);
                }
            }
            this.tbl_productos.setItems(this.filtrados);
        }
    }

    @FXML
    private void doFilIdPr(KeyEvent event) {
        String filtroId;
        
        filtroId=this.txf_idPr.getText();
        if(filtroId.isEmpty())
            this.tbl_productos.setItems(this.obsProductos);
        else
        {
            this.filtrados.clear();
            for(Producto producto: this.obsProductos)
            {
                if((producto.getIdentificacionProducto().toLowerCase()).contains(filtroId.toLowerCase()))
                    this.filtrados.add(producto);
            }
            this.tbl_productos.setItems(this.filtrados);
        }
        
    }
    //=============================================================================
    private void llenarCombos()
    {
        this.cbx_producto.getItems().add("Todos");
        this.cbx_producto.getItems().add("Cta. Ahorros");
        this.cbx_producto.getItems().add("Cta. Corriente");
        this.cbx_producto.getItems().add("CDT");
        this.cbx_producto.getItems().add("Tarj. Visa");
        this.cbx_producto.getItems().add("Tarj. American");
        
        this.cbx_idCl.getItems().add("Todos");
        if(!this.productos.isEmpty())
        {
            for(Producto producto: this.productos)
            {
                if(!this.cbx_idCl.getItems().contains(producto.getIdentificacionCliente()))
                {
                    this.cbx_idCl.getItems().add(producto.getIdentificacionCliente());
                }
            }
        }
    }
    private void modelarTabla()
    {
        this.col_idProducto.setCellValueFactory(new PropertyValueFactory("identificacionProducto"));
        this.col_idCliente.setCellValueFactory(new PropertyValueFactory("identificacionCliente"));
        this.col_producto.setCellValueFactory(new PropertyValueFactory("nombreProducto"));
        this.col_apertura.setCellValueFactory(new PropertyValueFactory("fecha"));
        this.col_disponible.setCellValueFactory(new PropertyValueFactory("valorUno"));
    }
    public void setProductos(ArrayList<Producto> productos)
    {
        this.productos=productos;
        this.obsProductos=FXCollections.observableArrayList(this.productos);
        this.tbl_productos.setItems(this.obsProductos);
    }

    @FXML
    private void validaID(KeyEvent event) {
        this.id=new StringBuilder(this.txf_idPr.getText());
        char c=event.getCharacter().charAt(0);
        if(!((Character.isDigit(c)||(c == java.awt.event.KeyEvent.VK_BACK_SPACE)||(c == java.awt.event.KeyEvent.VK_DELETE))))
        {
            int posCursor=this.txf_idPr.getCaretPosition();
            
            this.id.deleteCharAt(posCursor-1);
            
            this.txf_idPr.clear();
            this.txf_idPr.setText(this.id.toString());
        }
        this.txf_idPr.positionCaret(this.id.length());
    }
}

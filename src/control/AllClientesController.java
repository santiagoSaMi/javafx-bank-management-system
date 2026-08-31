/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import java.io.File;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import negocio.Cliente;

/**
 * FXML Controller class
 *
 * @author santi
 */
public class AllClientesController implements Initializable {

    @FXML
    private ComboBox<String> cbx_genero;
    @FXML
    private ComboBox<String> cbx_producto;
    @FXML
    private TextField txf_nombre;
    @FXML
    private TableView<Cliente> tbl_clientes;
    @FXML
    private TableColumn<?, ?> col_id;
    @FXML
    private TableColumn<?, ?> col_nombre;
    @FXML
    private TableColumn<?, ?> col_genero;
    @FXML
    private ImageView img_foto;
    @FXML
    private Button btn_atras;
    
    private ArrayList<Cliente> clientes;
    private ObservableList<Cliente> obsClientes;
    private ObservableList<Cliente> filtrados;
    private StringBuilder nombre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.obsClientes=FXCollections.observableArrayList();
        this.filtrados=FXCollections.observableArrayList();
        this.nombre=null;
        this.llenarCombos();
        this.modelarTabla();
    }    

    @FXML
    private void doFilGenero(ActionEvent event) {
        int index;
        
        this.filtrados.clear();
        index=this.cbx_genero.getSelectionModel().getSelectedIndex();
        
        if(index==0)
            this.tbl_clientes.setItems(this.obsClientes);
        else
        {
            if(index>0)
            {
                for(Cliente cliente: this.obsClientes)
                {
                    if(cliente.getGenero()==this.cbx_genero.getSelectionModel().getSelectedItem().charAt(0))
                        this.filtrados.add(cliente);
                }
            }
            this.tbl_clientes.setItems(this.filtrados);
        }
    }

    @FXML
    private void doFilProducto(ActionEvent event) {
        int index;
        
        this.filtrados.clear();
        index=this.cbx_producto.getSelectionModel().getSelectedIndex();
        
        if(index==0)
            this.tbl_clientes.setItems(this.obsClientes);
        else
        {
            if(index>0)
            {
                for(Cliente cliente: this.obsClientes)
                {
                    boolean products[]=cliente.getProductos();
                    if(products[index-1]==true)
                        this.filtrados.add(cliente);
                }
            }
            this.tbl_clientes.setItems(this.filtrados);
        }
    }

    @FXML
    private void doFilNombre(KeyEvent event) {
        String filtroName;
        
        filtroName=this.txf_nombre.getText();
        if(filtroName.isEmpty())
            this.tbl_clientes.setItems(this.obsClientes);
        else
        {
            this.filtrados.clear();
            for(Cliente cliente:this.obsClientes)
            {
                if((cliente.getNombre().toLowerCase()).contains(filtroName.toLowerCase()))
                    this.filtrados.add(cliente);
            }
            this.tbl_clientes.setItems(this.filtrados);
        }
    }

    @FXML
    private void doPoneFoto(MouseEvent event) {
        Cliente perso=this.tbl_clientes.getSelectionModel().getSelectedItem();
        if(perso!=null)
        {
            File imgFile=new File("././images/"+perso.getFoto());
            String url=imgFile.toURI().toString();
            Image image=new Image(url,true);
            this.img_foto.setImage(image);
        }
    }

    @FXML
    private void doAtras(ActionEvent event) {
        try
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/vista/clientes.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root);
            
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.setOnCloseRequest(even -> {even.consume();});
            stage.setResizable(false);
            stage.setTitle("ADMINISTRACIÓN DE CLIENTES");
            stage.show();
            
            Stage myStage=(Stage)this.btn_atras.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex){ }
    }
    
    //========================================================================
    
    private void llenarCombos()
    {
        this.cbx_genero.getItems().add("Todos");
        this.cbx_genero.getItems().add("Masculino");
        this.cbx_genero.getItems().add("Femenino");
        this.cbx_genero.getItems().add("Otro");
        
        this.cbx_producto.getItems().add("Todos");
        this.cbx_producto.getItems().add("Cta. Ahorros");
        this.cbx_producto.getItems().add("Cta. Corriente");
        this.cbx_producto.getItems().add("CDT");
        this.cbx_producto.getItems().add("Tarj. Visa");
        this.cbx_producto.getItems().add("Tarj. American");
    }
    
    private void modelarTabla()
    {
        this.col_id.setCellValueFactory(new PropertyValueFactory("identificacion"));
        this.col_nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.col_genero.setCellValueFactory(new PropertyValueFactory("genero"));
    }
    
    public void setClientes(ArrayList<Cliente> clientes)
    {
        this.clientes=clientes;
        this.obsClientes=FXCollections.observableArrayList(this.clientes);
        this.tbl_clientes.setItems(this.obsClientes);
    }

    @FXML
    private void validaNombre(KeyEvent event) {
        this.nombre=new StringBuilder(this.txf_nombre.getText());
        char c=event.getCharacter().charAt(0);
        if(!((Character.isLetter(c)||(c == java.awt.event.KeyEvent.VK_BACK_SPACE)||(c == java.awt.event.KeyEvent.VK_DELETE)||(c == java.awt.event.KeyEvent.VK_SPACE))))
        {
            int posCursor=this.txf_nombre.getCaretPosition();
            
            this.nombre.deleteCharAt(posCursor-1);
            
            this.txf_nombre.clear();
            this.txf_nombre.setText(this.nombre.toString());
        }
        this.txf_nombre.positionCaret(this.nombre.length());
    }
}

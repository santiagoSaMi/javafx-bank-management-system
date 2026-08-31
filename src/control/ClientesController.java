/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import gestion.GestionClientes;
import gestion.GestionProductos;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import negocio.Cliente;

/**
 * FXML Controller class
 *
 * @author santi
 */
public class ClientesController implements Initializable {

    @FXML
    private TextField txf_id;
    @FXML
    private TextField txf_nombre;
    @FXML
    private CheckBox chb_ahorros;
    @FXML
    private CheckBox chb_corriente;
    @FXML
    private CheckBox chb_visa;
    @FXML
    private CheckBox chb_american;
    @FXML
    private CheckBox chb_cdt;
    @FXML
    private ImageView img_foto;
    @FXML
    private Button btn_buscar;
    @FXML
    private Button btn_buscarFoto;
    @FXML
    private Button btn_Limpiar;
    @FXML
    private Button btn_saveModify;
    @FXML
    private Button btn_back;
    @FXML
    private Button btn_next;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_verTodo;
    @FXML
    private Button btn_salir;
    @FXML
    private RadioButton rbt_masculino;
    @FXML
    private RadioButton rbt_otro;
    @FXML
    private RadioButton rbt_femenino;
    @FXML
    private TextField txf_clave;
    
    private GestionClientes gesCl;
    private GestionProductos gesPro;
    private ArrayList<Cliente> clientes;
    private int pos;
    private String laFoto;
    private String idActual;
    private String rutaImages;
    private StringBuilder id;
    private StringBuilder nombre;
    private StringBuilder clave;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.gesCl=new GestionClientes();
        this.gesPro=new GestionProductos();
        this.pos=0;
        this.id=null;
        this.nombre=null;
        this.clave=null;
        this.rutaImages="././images/";
        this.laFoto="sinrostro.jpg";
        
        ToggleGroup tg=new ToggleGroup();
        this.rbt_masculino.setToggleGroup(tg);
        this.rbt_femenino.setToggleGroup(tg);
        this.rbt_otro.setToggleGroup(tg);
        this.rbt_otro.setSelected(true);
        
        this.traerClientes();
    }    

    @FXML
    private void doBuscar(ActionEvent event) {
        String id;
        int posi=1;
        boolean existe=false;
        
        id=this.txf_id.getText();
        if(id.isEmpty())
            this.showMessages("Debe digitar una identificación.", 1);
        else
        {
            for(Cliente cliente: this.clientes)
            {
                posi++;
                if(cliente.getIdentificacion().equals(id))
                {
                    this.ponerCliente(cliente);
                    this.idActual=cliente.getIdentificacion();
                    this.pos=posi;
                    existe=true;
                    break;
                }
            }
            if(!existe)
                this.showMessages("No existe cliente.", 1);
        }
    }

    @FXML
    private void doLimpiar(ActionEvent event) {
        this.txf_id.clear();
        this.txf_nombre.clear();
        this.txf_clave.clear();
        this.rbt_masculino.setSelected(false);
        this.rbt_femenino.setSelected(false);
        this.rbt_otro.setSelected(true);
        this.limpiarChecks();
        this.laFoto="sinrostro.jpg";
        this.ponerFoto();
        this.txf_id.requestFocus();
        this.idActual="new";
    }

    @FXML
    private void doGuardarNuevo(ActionEvent event) {
        String id,name,key;
        char gender='*';
        boolean products[]={false,false,false,false,false};
        String errores="";
        int conta=0;
        String patronClave="\\d{4}";
        
        id=this.txf_id.getText();
        if(id.isEmpty())
            errores+="El campo Identificación está vacío.\n";
        name=this.txf_nombre.getText();
        if(name.isEmpty())
            errores+="El campo Nombres y apellidos está vacío.\n";
        key=this.txf_clave.getText();
        if(key.isEmpty())
            errores+="El campo Clave está vacío. \n";
        if(!this.txf_clave.getText().matches(patronClave))
            errores+="La clave son solo 4 digitos. \n";
        
        if(this.rbt_masculino.isSelected())
            gender='M';
        if(this.rbt_femenino.isSelected())
            gender='F';
        if(this.rbt_otro.isSelected())
            gender='O';
        
        if(this.chb_ahorros.isSelected())
            products[0]=true;
        if(this.chb_corriente.isSelected())
            products[1]=true;
        if(this.chb_cdt.isSelected())
            products[2]=true;
        if(this.chb_visa.isSelected())
            products[3]=true;
        if(this.chb_american.isSelected())
            products[4]=true;
        
        for(int i=0;i<products.length;i++)
        {
            if(products[i]==true)
                conta++;
        }
        if(conta==0)
            errores+="Debe elegir al menos un producto.\n";
        
        if(errores.length()==0) //Crear
        {
            Cliente cliente=new Cliente(id,name,this.laFoto,key,gender,products);
            if(this.idActual.equals("new"))
            {
                if(this.gesCl.pruebaExistencia(id)||this.gesCl.pruebaExistenciaClave(key))
                    this.showMessages("Esa identificación/clave ya existe.", 1);
                else
                {
                    if(this.gesCl.guardarCliente(cliente)==true)
                    {
                        this.showMessages("El cliente se ha creado exitosamente.", 2);
                        this.traerClientes();
                    }
                    else
                        this.showMessages("Error creando cliente.", 1);
                }
            }
            else //Modificar
            {
                Iterator<Cliente> iter=this.clientes.iterator();
                int pos=-1;
                while(iter.hasNext())
                {
                    pos++;
                    if(iter.next().getIdentificacion().equals(this.idActual))
                    {
                        iter.remove();
                        this.clientes.add(pos, cliente);
                        break;
                    }
                }
                this.gesCl.reemplazarArchivo(this.clientes);
                this.showMessages("Cambios guardados.", 2);
                this.traerClientes();
            }
        }
        else
            this.showMessages(errores, 1);
    }


    @FXML
    private void doNext(ActionEvent event) {
        if(this.pos<this.clientes.size()-1)
        {
            this.pos++;
            Cliente siguiente=this.clientes.get(this.pos);
            this.ponerCliente(siguiente);
            this.idActual=siguiente.getIdentificacion();
        }
    }

    @FXML
    private void doEliminar(ActionEvent event) {
        boolean seguro;
        
        seguro=this.showMessages("¿Seguro? Esta acción no se puede deshacer.", 3);
        if(seguro)
        {
            this.gesPro.eliminarCliente(this.idActual);
            Iterator<Cliente> iter=this.clientes.iterator();
            while(iter.hasNext())
            {
                if(iter.next().getIdentificacion().equals(this.idActual))
                {
                    iter.remove();
                    break;
                }
            }
            this.gesCl.reemplazarArchivo(this.clientes);
            this.showMessages("Cliente eliminado.", 2);
            this.traerClientes();
        }
    }

    @FXML
    private void doVerTodo(ActionEvent event) {
        try
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/vista/allClientes.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root);
            
            AllClientesController controlVentana2=loader.getController();
            controlVentana2.setClientes(this.clientes);
            
            Stage stage=new Stage();
            stage.setOnCloseRequest(even -> {even.consume();});
            stage.setResizable(false);
            stage.setTitle("TABLA DE CLIENTES");
            //stage.initModality(Modality.WINDOW_MODAL); //no cierra la otra ventana
            stage.setScene(scene);
            stage.show();
            
            Stage myStage=(Stage)this.btn_verTodo.getScene().getWindow();
            myStage.close();
        }
        catch(IOException ex){ }
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
    //======================================================================================
    private void traerClientes()
    {
        this.pos=0;
        
        this.clientes=this.gesCl.getTodos();
        if(!this.clientes.isEmpty())
        {
            Cliente primero=this.clientes.get(this.pos);
            this.ponerCliente(primero);
            this.idActual=primero.getIdentificacion();
            
        }
        else
            this.idActual="new";
    }
    private void ponerCliente(Cliente cliente)
    {
        boolean productos[];
        this.limpiarChecks();
        
        this.txf_id.setText(cliente.getIdentificacion());
        this.txf_nombre.setText(cliente.getNombre());
        this.txf_clave.setText(cliente.getClave());
        
        if(cliente.getGenero()=='M')
            this.rbt_masculino.setSelected(true);
        if(cliente.getGenero()=='F')
            this.rbt_femenino.setSelected(true);
        if(cliente.getGenero()=='O')
            this.rbt_otro.setSelected(true);
        
        productos=cliente.getProductos();
        if(productos[0]==true) //ahorros
            this.chb_ahorros.setSelected(true);
        if(productos[1]==true) //corriente
            this.chb_corriente.setSelected(true);
        if(productos[2]==true) //cdt
            this.chb_cdt.setSelected(true);
        if(productos[3]==true) //visa
            this.chb_visa.setSelected(true);
        if(productos[4]==true) //american
            this.chb_american.setSelected(true);
        
        this.laFoto=cliente.getFoto();
        this.ponerFoto();
    }
    private void limpiarChecks()
    {
        this.chb_ahorros.setSelected(false);
        this.chb_corriente.setSelected(false);
        this.chb_cdt.setSelected(false);
        this.chb_visa.setSelected(false);
        this.chb_american.setSelected(false);
    }
    private void ponerFoto()
    {
        File imgFile = new File(this.rutaImages+this.laFoto);
        String url = imgFile.toURI().toString();
        Image image = new Image(url,true);
        this.img_foto.setImage(image);
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
    private void doBack(ActionEvent event) {
        if(this.pos>0)
        {
            this.pos--;
            Cliente anterior=this.clientes.get(this.pos);
            this.ponerCliente(anterior);
            this.idActual=anterior.getIdentificacion();
        }
    }

    @FXML
    private void doBuscarFoto(ActionEvent event) {
        FileChooser fc=new FileChooser();
        fc.setTitle("BUSCAR FOTO");
        fc.setInitialDirectory(new File(this.rutaImages));
        try
        {
            File imgFile=fc.showOpenDialog(new Stage());
            if(imgFile.canExecute())
            {
                this.laFoto=imgFile.getName();
                if(this.laFoto!=null)
                {
                    Image image=new Image("file:"+imgFile.getAbsolutePath());
                    this.img_foto.setImage(image);
                }
            }
        }
        catch(Exception ex) { }
    }

    @FXML
    private void validaID(KeyEvent event) {
        this.id=new StringBuilder(this.txf_id.getText());
        char c=event.getCharacter().charAt(0);
        if(!((Character.isDigit(c)||(c == java.awt.event.KeyEvent.VK_BACK_SPACE)||(c == java.awt.event.KeyEvent.VK_DELETE))))
        {
            int posCursor=this.txf_id.getCaretPosition();
            
            this.id.deleteCharAt(posCursor-1);
            
            this.txf_id.clear();
            this.txf_id.setText(this.id.toString());
        }
        this.txf_id.positionCaret(this.id.length());
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
}

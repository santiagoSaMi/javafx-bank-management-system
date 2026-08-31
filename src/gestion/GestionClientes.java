/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import negocio.Cliente;

/**
 *
 * @author santi
 */
public class GestionClientes {
    //variables de clase
    private String ruta;
    
    //metodos
    public GestionClientes()
    {
        this.ruta="./Archivos/misClientes.txt";
        this.verificarArchivo();
    }
    
    public boolean guardarCliente(Cliente cliente)
    {
        boolean ok=false;
        try
        {
            File file=new File(this.ruta);
            FileWriter fr=new FileWriter(file, true);
            PrintWriter pw=new PrintWriter(fr);
            pw.println(cliente);
            pw.close();
            ok=true;
        }
        catch(IOException ioe)
        {
            System.out.println("ERROR GUARDANDO CLIENTE.");
        }
        return ok;
    }
    public ArrayList<Cliente> getTodos()
    {
        ArrayList<Cliente> clientes=new ArrayList<Cliente>();
        FileReader file;
        BufferedReader br;
        String registro;
        
        try
        {
            file=new FileReader(this.ruta);
            br=new BufferedReader(file);
            while((registro=br.readLine())!=null)
            {
                String[] campos=registro.split(",");
                boolean productos[]=new boolean[5];
                for(int i=5;i<=9;i++)
                {
                    productos[i-5]=Boolean.parseBoolean(campos[i]);
                }
                Cliente cliente=new Cliente(campos[0],campos[1],campos[2],campos[3],campos[4].charAt(0),productos);
                clientes.add(cliente);
            }
        }
        catch(IOException ex)
        {
            System.out.println("ERROR RECOPILANDO CLIENTES");
        }
        return clientes;
    }
    
    public Cliente buscarCliente(String id)
    {
        Cliente cliente=null;
        FileReader file;
        BufferedReader br;
        String registro;
        
        try
        {
            file=new FileReader(this.ruta);
            br=new BufferedReader(file);
            while((registro=br.readLine())!=null)
            {
                String[] campos = registro.split(",");
                if(campos[0].equals(id))
                {
                    boolean productos[]=new boolean[5];
                    for(int i=5;i<=9;i++)
                    {
                        productos[i-5]=Boolean.parseBoolean(campos[i]);
                    }
                    cliente=new Cliente(campos[0],campos[1],campos[2],campos[3],campos[4].charAt(0),productos);
                    break;
                }
            }
        }
        catch(IOException ioe)
        {
            System.out.println("ERROR BUSCANDO CLIENTE");
        }
        return cliente;
    }
    
    public void eliminarProducto(String idCliente, int tipoProducto)
    {
        ArrayList<Cliente> clientes=this.getTodos();
        boolean products[];
        
        for(Cliente cliente: clientes)
        {
            if(cliente.getIdentificacion().equals(idCliente))
            {
                products=cliente.getProductos();
                products[tipoProducto-1]=false;
                cliente.setProductos(products);
                break;
            }
        }
        this.reemplazarArchivo(clientes);
    }
    
    public boolean pruebaExistencia(String id)
    {
        boolean existe=false;
        FileReader file;
        BufferedReader br;
        String registro;
        
        try
        {
            file=new FileReader(this.ruta);
            br=new BufferedReader(file);
            while((registro=br.readLine())!=null)
            {
                String[] campos=registro.split(",");
                if(campos[0].equals(id))
                {
                    existe=true;
                    break;
                }
            }
        }
        catch(IOException ioe)
        {
            System.out.println("ERROR VERIFICANDO DATOS.");
        }
        return existe;
    }
    public boolean pruebaExistenciaClave(String key)
    {
        boolean existe=false;
        FileReader file;
        BufferedReader br;
        String registro;
        
        try
        {
            file=new FileReader(this.ruta);
            br=new BufferedReader(file);
            while((registro=br.readLine())!=null)
            {
                String[] campos=registro.split(",");
                if(campos[3].equals(key))
                {
                    existe=true;
                    break;
                }
            }
        }
        catch(IOException ioe)
        {
            System.out.println("ERROR VERIFICANDO DATOS.");
        }
        return existe;
    }
    public void cambiarClave(String idActual, String newKey)
    {
        ArrayList<Cliente> clientes=this.getTodos();
        
        for(Cliente cliente: clientes)
        {
            if(cliente.getIdentificacion().equals(idActual))
            {
                cliente.setClave(newKey);
                break;
            }
        }
        this.reemplazarArchivo(clientes);
    }
    
    public void reemplazarArchivo(ArrayList<Cliente> clientes)
    {
        try
        {
            File file=new File(this.ruta);
            FileWriter fr=new FileWriter(file,false);
            PrintWriter pw=new PrintWriter(fr);
            for(Cliente cliente: clientes)
                pw.println(cliente);
            pw.close();
        }
        catch(IOException cosito)
        {
            System.out.println("ERROR ACTUALIZANDO DATOS.");
        }
    }
    private void verificarArchivo()
    {
        try
        {
            File filex=new File(this.ruta);
            if(!filex.exists())
                filex.createNewFile();
        }
        catch(IOException ex)
        {
            System.out.println("Problemas con la ruta.");
        }
    }
}

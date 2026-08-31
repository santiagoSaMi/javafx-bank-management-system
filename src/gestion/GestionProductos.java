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
import java.util.Iterator;
import negocio.Producto;

/**
 *
 * @author santi
 */
public class GestionProductos {
    //variables de clase
    private String ruta;
    
    //metodos
    public GestionProductos()
    {
        this.ruta="./Archivos/misProductos.txt";
        this.verificarArchivo();
    }
    
    public boolean guardarProducto(Producto producto)
    {
        boolean ok=false;
        try
        {
            File file=new File(this.ruta);
            FileWriter fr=new FileWriter(file, true);
            PrintWriter pw=new PrintWriter(fr);
            pw.println(producto);
            pw.close();
            ok=true;
        }
        catch(IOException ioe)
        {
            System.out.println("ERROR GUARDANDO PRODUCTO.");
        }
        return ok;
    }
    public ArrayList<Producto> getTodos()
    {
        ArrayList<Producto> productos=new ArrayList<Producto>();
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
                
                Producto producto=new Producto(campos[0],campos[1],campos[2],campos[3],Float.parseFloat(campos[4]),Integer.parseInt(campos[5]));
                productos.add(producto);
            }
        }
        catch(IOException ex)
        {
            System.out.println("ERROR RECOPILANDO PRODUCTOS");
        }
        return productos;
    }
    
    public Producto buscarProducto(String tipo, String idCliente)
    {
        Producto producto=null;
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
                if(campos[1].equals(tipo) && campos[2].equals(idCliente))
                {
                    
                    producto=new Producto(campos[0],campos[1],campos[2],campos[3],Float.parseFloat(campos[4]),Integer.parseInt(campos[5]));
                    break;
                }
            }
        }
        catch(IOException ioe)
        {
            System.out.println("ERROR BUSCANDO CLIENTE");
        }
        return producto;
    }
    
    public void eliminarProducto(String idProducto)
    {
        ArrayList<Producto> productos=this.getTodos();
        
        Iterator<Producto> iter=productos.iterator();
        while(iter.hasNext())
        {
            if(iter.next().getIdentificacionProducto().equals(idProducto))
            {
                iter.remove();
                break;
            }
        }
        this.reemplazarArchivo(productos);
    }
    
    public void eliminarCliente(String idCliente)
    {
        ArrayList<Producto> productos=this.getTodos();
        
        Iterator<Producto> iter=productos.iterator();
        while(iter.hasNext())
        {
            if(iter.next().getIdentificacionCliente().equals(idCliente))
            {
                iter.remove();
            }
        }
        this.reemplazarArchivo(productos);
    }
    public void restarValorUno(String idProducto, Float valorInicial, Float resta)
    {
        ArrayList<Producto> productos=this.getTodos();
        
        for(Producto producto: productos)
        {
            if(producto.getIdentificacionProducto().equals(idProducto))
            {
                producto.setValorUno(valorInicial-resta);
                break;
            }
        }
        this.reemplazarArchivo(productos);
    }
    public void sumarValorUno(String idProducto, Float valorInicial, Float suma)
    {
        ArrayList<Producto> productos=this.getTodos();
        
        for(Producto producto: productos)
        {
            if(producto.getIdentificacionProducto().equals(idProducto))
            {
                producto.setValorUno(valorInicial+suma);
                break;
            }
        }
        this.reemplazarArchivo(productos);
    }
    
    public void modificarProducto(String productoActual, Producto productoNuevo)
    {
        ArrayList<Producto> productos=this.getTodos();
        Iterator<Producto> iter=productos.iterator();
        int pos=-1;
        
        while(iter.hasNext())
        {
            pos++;
            if(iter.next().getIdentificacionProducto().equals(productoActual))
            {
                iter.remove();
                productos.add(pos, productoNuevo);
                break;
            }
        }
        this.reemplazarArchivo(productos);
    }
    
    public boolean pruebaExistenciaIdProducto(String idProducto)
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
                if(campos[0].equals(idProducto))
                {
                    existe=true;
                    break;
                }
            }
        }
        catch(IOException ioe)
        {
            System.out.println("ERROR VERIFICANDO");
        }
        return existe;
    }
    
    public void reemplazarArchivo(ArrayList<Producto> productos)
    {
        try
        {
            File file=new File(this.ruta);
            FileWriter fr=new FileWriter(file,false);
            PrintWriter pw=new PrintWriter(fr);
            for(Producto producto: productos)
                pw.println(producto);
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

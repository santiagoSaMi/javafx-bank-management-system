/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

/**
 *
 * @author santi
 */
public class Cliente {
    private String identificacion;
    private String nombre;
    private String foto;
    private String clave;
    private char genero;
    private boolean productos[]; //cuenta de ahorros, cuenta corriente, cdt, tarjeta visa, tarjeta americana

    public Cliente(String identificacion, String nombre, String foto, String clave, char genero, boolean[] productos) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.foto = foto;
        this.clave = clave;
        this.genero = genero;
        this.productos = productos;
    }

    //analizadores
    public String getIdentificacion() {
        return identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFoto() {
        return foto;
    }

    public String getClave() {
        return clave;
    }

    public char getGenero() {
        return genero;
    }

    public boolean[] getProductos() {
        return productos;
    }

    //modificadores
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public void setProductos(boolean[] productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        String produs="";
        for(int i=0;i<this.productos.length;i++)
        {
            if(i!=this.productos.length-1)
                produs+=this.productos[i]+",";
            else
                produs+=this.productos[i];
        }
        return this.identificacion + "," + this.nombre + "," + this.foto + "," + this.clave + "," + this.genero + "," + produs;
    }
    
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

/**
 *
 * @author santi
 */
public class Producto {
    private String identificacionProducto;
    private String tipoProducto;
    private String identificacionCliente;
    private String fecha;
    private float valorUno;
    private int valorDos;
    private String nombreProducto;

    public Producto(String identificacionProducto, String tipoProducto, String identificacionCliente, String fecha, float valorUno, int valorDos) {
        this.identificacionProducto = identificacionProducto;
        this.tipoProducto = tipoProducto;
        this.identificacionCliente = identificacionCliente;
        this.fecha = fecha;
        this.valorUno = valorUno;
        this.valorDos = valorDos;
        this.seteadorProducto(Integer.parseInt(this.tipoProducto));
    }
    
    //analizadores
    public String getIdentificacionProducto() {
        return identificacionProducto;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public String getIdentificacionCliente() {
        return identificacionCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public float getValorUno() {
        return valorUno;
    }

    public int getValorDos() {
        return valorDos;
    }
    
    //modificadores
    public void setIdentificacionProducto(String identificacionProducto) {
        this.identificacionProducto = identificacionProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public void setIdentificacionCliente(String identificacionCliente) {
        this.identificacionCliente = identificacionCliente;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setValorUno(float valorUno) {
        this.valorUno = valorUno;
    }

    public void setValorDos(int valorDos) {
        this.valorDos = valorDos;
    }
    private void seteadorProducto(int n)
    {
        switch(n)
        {
            case 1:
                this.nombreProducto="Cuenta de Ahorros";
                break;
            case 2:
                this.nombreProducto="Cuenta Corriente";
                break;
            case 3:
                this.nombreProducto="CDT";
                break;
            case 4:
                this.nombreProducto="Tarjeta Visa";
                break;
            case 5:
                this.nombreProducto="Tarjeta American";
                break;
            default:
                this.nombreProducto=null;
                break;
        }
    }

    public String getNombreProducto() {
        return nombreProducto;
    }
    
    
    @Override
    public String toString() {
        return this.identificacionProducto + "," + this.tipoProducto + "," + this.identificacionCliente + "," + this.fecha + "," + this.valorUno + "," + this.valorDos;
    }
}

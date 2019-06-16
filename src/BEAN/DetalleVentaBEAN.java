
package BEAN;

public class DetalleVentaBEAN {
    
    private String codProducto,numTicket;
    private double montoSubtotal;
    private int cantidad;

    public String getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(String codProducto) {
        this.codProducto = codProducto;
    }

    public String getNumTicket() {
        return numTicket;
    }

    public void setNumTicket(String numTicket) {
        this.numTicket = numTicket;
    }

    public double getMontoSubtotal() {
        return montoSubtotal;
    }

    public void setMontoSubtotal(double montoSubtotal) {
        this.montoSubtotal = montoSubtotal;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
}

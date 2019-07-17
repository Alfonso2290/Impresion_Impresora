
package UtilidadesDetalle;

import javax.swing.table.DefaultTableModel;

public class ModeloTablaDetalleVenta extends DefaultTableModel{
  
    String[] titulos;
    Object[][] datos;

    public ModeloTablaDetalleVenta(Object[][] datos, String[] titulos) {
        super();
        this.titulos=titulos;
        this.datos=datos;
        setDataVector(datos, titulos);
    }

    public ModeloTablaDetalleVenta() {}

    public boolean isCellEditable (int row, int column)
    {
        return false;
    }
}



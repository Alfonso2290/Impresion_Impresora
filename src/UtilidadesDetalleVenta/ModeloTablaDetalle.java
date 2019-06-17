
package UtilidadesDetalleVenta;

import javax.swing.table.DefaultTableModel;

public class ModeloTablaDetalle extends DefaultTableModel{
  
    String[] titulos;
    Object[][] datos;

    public ModeloTablaDetalle(Object[][] datos, String[] titulos) {
        super();
        this.titulos=titulos;
        this.datos=datos;
        setDataVector(datos, titulos);
    }

    public ModeloTablaDetalle() {}

    public boolean isCellEditable (int row, int column)
    {
        if (column!=UtilidadesDetalle.CARRITO){   
            return false; 
        }else{
            return true;
        }
    }
}



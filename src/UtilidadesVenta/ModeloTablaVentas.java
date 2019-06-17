
package UtilidadesVenta;

import javax.swing.table.DefaultTableModel;

public class ModeloTablaVentas extends DefaultTableModel{
  
    String[] titulos;
    Object[][] datos;

    public ModeloTablaVentas(Object[][] datos, String[] titulos) {
        super();
        this.titulos=titulos;
        this.datos=datos;
        setDataVector(datos, titulos);
    }

    public ModeloTablaVentas() {}

    public boolean isCellEditable (int row, int column)
    {
        if ( column!=UtilidadesVentas.ELIMINAR){   
            return false; 
        }else{
            return true;
        }
    }
}



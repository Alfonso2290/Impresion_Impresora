
package UtilidadesProducto;

import javax.swing.table.DefaultTableModel;

public class ModeloTablaProductos extends DefaultTableModel{
  
    String[] titulos;
    Object[][] datos;

    public ModeloTablaProductos(Object[][] datos, String[] titulos) {
        super();
        this.titulos=titulos;
        this.datos=datos;
        setDataVector(datos, titulos);
    }

    public ModeloTablaProductos() {}

    public boolean isCellEditable (int row, int column)
    {
        if (column!=UtilidadesProductos.MODIFICAR_PRODUCTO && column!=UtilidadesProductos.ELIMINAR_PRODUCTO){   
            return false; 
        }else{
            return true;
        }
    }
}



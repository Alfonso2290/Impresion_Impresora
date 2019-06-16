
package UtilidadesCliente;

import javax.swing.table.DefaultTableModel;

public class ModeloTabla extends DefaultTableModel{
  
    String[] titulos;
    Object[][] datos;

    public ModeloTabla(Object[][] datos, String[] titulos) {
        super();
        this.titulos=titulos;
        this.datos=datos;
        setDataVector(datos, titulos);
    }

    public ModeloTabla() {}

    public boolean isCellEditable (int row, int column)
    {
        if (column!=UtilidadesCliente.MODIFICAR && column!=UtilidadesCliente.ELIMINAR){   
            return false; 
        }else{
            return true;
        }
    }
}


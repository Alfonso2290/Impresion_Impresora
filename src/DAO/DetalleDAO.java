
package DAO;

import BEAN.DetalleBEAN;
import java.sql.*;
import java.util.*;
import UTIL.ConexionBD;

public class DetalleDAO {
    
    private ConexionBD conexion=null;
    private PreparedStatement instruccion=null;
    private ResultSet tabla=null;
    private String sql="";
    private ArrayList<DetalleBEAN> lista;
    
    public ArrayList<DetalleBEAN> getListadoDetalleVenta(String numeroTicket){
        
        try{
            conexion=new ConexionBD();
            sql="SELECT D.NUM_TICKET,D.COD_PRODUCTO,D.CANTIDAD,D.MONTO_SUBTOTAL, ";
            sql+="P.NOMBRE,P.PRECIO_VENTA,V.FECHA,V.MONTO_TOTAL,V.IGV,V.MONTO_NETO ";
            sql+="FROM PRODUCTO P INNER JOIN DETALLE_VENTA D ";
            sql+="ON P.COD_PRODUCTO=D.COD_PRODUCTO ";
            sql+="INNER JOIN VENTA V ";
            sql+="ON V.NUM_TICKET=D.NUM_TICKET ";
            sql+="WHERE V.NUM_TICKET=? ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1,numeroTicket );
            tabla=instruccion.executeQuery();
            lista=new ArrayList<DetalleBEAN>();
            
            while(tabla.next()){
                DetalleBEAN detalle=new DetalleBEAN();
                
                detalle.setNumTicket(tabla.getString(1));
                detalle.setCodProducto(tabla.getString(2));
                detalle.setCantidad(tabla.getInt(3));
                detalle.setMontoSubtotal(tabla.getDouble(4));
                detalle.setNombreProducto(tabla.getString(5));
                detalle.setPrecioVenta(tabla.getDouble(6));
                detalle.setFechaRegistro(tabla.getString(7));
                detalle.setMontoTotal(tabla.getDouble(8));
                detalle.setIgv(tabla.getDouble(9));
                detalle.setMontoNeto(tabla.getDouble(10));
                
                lista.add(detalle);
            }
            
            
        }catch(Exception ex){
            System.out.println("Error");
        }
        return lista;
    }
}

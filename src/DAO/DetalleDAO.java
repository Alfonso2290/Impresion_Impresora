
package DAO;

import BEAN.DetalleBEAN;
import java.sql.*;
import java.util.*;
import UTIL.ConexionBD;
import javax.swing.JOptionPane;

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
    
    public void registrarDetalleVenta(DetalleBEAN detalle){
        
        try{
            conexion=new ConexionBD();
            sql="INSERT INTO DETALLE_VENTA VALUES(?,?,?,?)";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1, detalle.getNumTicket());
            instruccion.setString(2, detalle.getCodProducto());
            instruccion.setInt(3, detalle.getCantidad());
            instruccion.setDouble(4, detalle.getMontoSubtotal());
            instruccion.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se ha registrado la el detalle de venta exitosamente");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error!!..No se pudo registrar el detalle de venta");
        }
        
    }
    
    public ArrayList<DetalleBEAN> getListaNombreProductos(){
        
        try{
            conexion=new ConexionBD();
            sql="SELECT DISTINCT NOMBRE FROM PRODUCTO ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            tabla=instruccion.executeQuery();
            lista=new ArrayList<DetalleBEAN>();
            
            while(tabla.next()){
                DetalleBEAN producto=new DetalleBEAN();
                producto.setNombreProducto(tabla.getString(1));
                
                lista.add(producto);
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return lista;
    }
    
    public ArrayList<DetalleBEAN> getListaDetalleVentas(){
        
        try{
            conexion=new ConexionBD();
            sql="SELECT DISTINCT D.NUM_TICKET,D.COD_PRODUCTO,P.NOMBRE,P.CANTIDAD_STOCK, ";
            sql+="D.CANTIDAD,P.PRECIO_VENTA,D.MONTO_SUBTOTAL,V.FECHA ";
            sql+="FROM VENTA V INNER JOIN DETALLE_VENTA D ";
            sql+="ON D.NUM_TICKET=V.NUM_TICKET ";
            sql+="INNER JOIN PRODUCTO P ";
            sql+="ON P.COD_PRODUCTO=D.COD_PRODUCTO ";
            sql+="ORDER BY P.NOMBRE ASC ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            tabla=instruccion.executeQuery();
            lista=new ArrayList<DetalleBEAN>();
            
            while(tabla.next()){
                DetalleBEAN detalle=new DetalleBEAN();
                detalle.setNumTicket(tabla.getString(1));
                detalle.setCodProducto(tabla.getString(2));
                detalle.setNombreProducto(tabla.getString(3));
                detalle.setStock(tabla.getInt(4));
                detalle.setCantidad(tabla.getInt(5));
                detalle.setPrecioVenta(tabla.getDouble(6));
                detalle.setMontoSubtotal(tabla.getDouble(7));
                detalle.setFechaRegistro(tabla.getString(8));
                
                lista.add(detalle);
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return lista;
    }
    
    public ArrayList<DetalleBEAN> getListaDetalleVentasFiltroNombreProducto(DetalleBEAN det){
        
        try{
            conexion=new ConexionBD();
            sql="SELECT DISTINCT D.NUM_TICKET,D.COD_PRODUCTO,P.NOMBRE,P.CANTIDAD_STOCK, ";
            sql+="D.CANTIDAD,P.PRECIO_VENTA,D.MONTO_SUBTOTAL,V.FECHA ";
            sql+="FROM VENTA V INNER JOIN DETALLE_VENTA D ";
            sql+="ON D.NUM_TICKET=V.NUM_TICKET ";
            sql+="INNER JOIN PRODUCTO P ";
            sql+="ON P.COD_PRODUCTO=D.COD_PRODUCTO ";
            sql+="WHERE P.NOMBRE=? ";
            sql+="ORDER BY P.NOMBRE ASC ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1,det.getNombreProducto());
            tabla=instruccion.executeQuery();
            lista=new ArrayList<DetalleBEAN>();
            
            while(tabla.next()){
                DetalleBEAN detalle=new DetalleBEAN();
                detalle.setNumTicket(tabla.getString(1));
                detalle.setCodProducto(tabla.getString(2));
                detalle.setNombreProducto(tabla.getString(3));
                detalle.setStock(tabla.getInt(4));
                detalle.setCantidad(tabla.getInt(5));
                detalle.setPrecioVenta(tabla.getDouble(6));
                detalle.setMontoSubtotal(tabla.getDouble(7));
                detalle.setFechaRegistro(tabla.getString(8));
                
                lista.add(detalle);
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return lista;
    }
    
    public ArrayList<DetalleBEAN> getListaDetalleVentasFiltroFechaInicio(DetalleBEAN det){
        
        try{
            conexion=new ConexionBD();
            sql="SELECT DISTINCT D.NUM_TICKET,D.COD_PRODUCTO,P.NOMBRE,P.CANTIDAD_STOCK, ";
            sql+="D.CANTIDAD,P.PRECIO_VENTA,D.MONTO_SUBTOTAL,V.FECHA ";
            sql+="FROM VENTA V INNER JOIN DETALLE_VENTA D ";
            sql+="ON D.NUM_TICKET=V.NUM_TICKET ";
            sql+="INNER JOIN PRODUCTO P ";
            sql+="ON P.COD_PRODUCTO=D.COD_PRODUCTO ";
            sql+="WHERE V.FECHA=? ";
            sql+="ORDER BY P.NOMBRE ASC ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1,det.getFechaInicio());
            tabla=instruccion.executeQuery();
            lista=new ArrayList<DetalleBEAN>();
            
            while(tabla.next()){
                DetalleBEAN detalle=new DetalleBEAN();
                detalle.setNumTicket(tabla.getString(1));
                detalle.setCodProducto(tabla.getString(2));
                detalle.setNombreProducto(tabla.getString(3));
                detalle.setStock(tabla.getInt(4));
                detalle.setCantidad(tabla.getInt(5));
                detalle.setPrecioVenta(tabla.getDouble(6));
                detalle.setMontoSubtotal(tabla.getDouble(7));
                detalle.setFechaRegistro(tabla.getString(8));
                
                lista.add(detalle);
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return lista;
    }
    
    public ArrayList<DetalleBEAN> getListaDetalleVentasFiltroFechaFinal(DetalleBEAN det){
        
        try{
            conexion=new ConexionBD();
            sql="SELECT DISTINCT D.NUM_TICKET,D.COD_PRODUCTO,P.NOMBRE,P.CANTIDAD_STOCK, ";
            sql+="D.CANTIDAD,P.PRECIO_VENTA,D.MONTO_SUBTOTAL,V.FECHA ";
            sql+="FROM VENTA V INNER JOIN DETALLE_VENTA D ";
            sql+="ON D.NUM_TICKET=V.NUM_TICKET ";
            sql+="INNER JOIN PRODUCTO P ";
            sql+="ON P.COD_PRODUCTO=D.COD_PRODUCTO ";
            sql+="WHERE V.FECHA<=? ";
            sql+="ORDER BY P.NOMBRE ASC ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1,det.getFechaFinal());
            tabla=instruccion.executeQuery();
            lista=new ArrayList<DetalleBEAN>();
            
            while(tabla.next()){
                DetalleBEAN detalle=new DetalleBEAN();
                detalle.setNumTicket(tabla.getString(1));
                detalle.setCodProducto(tabla.getString(2));
                detalle.setNombreProducto(tabla.getString(3));
                detalle.setStock(tabla.getInt(4));
                detalle.setCantidad(tabla.getInt(5));
                detalle.setPrecioVenta(tabla.getDouble(6));
                detalle.setMontoSubtotal(tabla.getDouble(7));
                detalle.setFechaRegistro(tabla.getString(8));
                
                lista.add(detalle);
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return lista;
    }
    
    public ArrayList<DetalleBEAN> getListaDetalleVentasFiltroNombreProductoFechaInicio(DetalleBEAN det){
        
        try{
            conexion=new ConexionBD();
            sql="SELECT DISTINCT D.NUM_TICKET,D.COD_PRODUCTO,P.NOMBRE,P.CANTIDAD_STOCK, ";
            sql+="D.CANTIDAD,P.PRECIO_VENTA,D.MONTO_SUBTOTAL,V.FECHA ";
            sql+="FROM VENTA V INNER JOIN DETALLE_VENTA D ";
            sql+="ON D.NUM_TICKET=V.NUM_TICKET ";
            sql+="INNER JOIN PRODUCTO P ";
            sql+="ON P.COD_PRODUCTO=D.COD_PRODUCTO ";
            sql+="WHERE V.FECHA=? AND P.NOMBRE=? ";
            sql+="ORDER BY P.NOMBRE ASC ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1,det.getFechaInicio());
            instruccion.setString(2, det.getNombreProducto());
            tabla=instruccion.executeQuery();
            lista=new ArrayList<DetalleBEAN>();
            
            while(tabla.next()){
                DetalleBEAN detalle=new DetalleBEAN();
                detalle.setNumTicket(tabla.getString(1));
                detalle.setCodProducto(tabla.getString(2));
                detalle.setNombreProducto(tabla.getString(3));
                detalle.setStock(tabla.getInt(4));
                detalle.setCantidad(tabla.getInt(5));
                detalle.setPrecioVenta(tabla.getDouble(6));
                detalle.setMontoSubtotal(tabla.getDouble(7));
                detalle.setFechaRegistro(tabla.getString(8));
                
                lista.add(detalle);
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return lista;
    }
    
    public ArrayList<DetalleBEAN> getListaDetalleVentasFiltroNombreProductoFechaFinal(DetalleBEAN det){
        
        try{
            conexion=new ConexionBD();
            sql="SELECT DISTINCT D.NUM_TICKET,D.COD_PRODUCTO,P.NOMBRE,P.CANTIDAD_STOCK, ";
            sql+="D.CANTIDAD,P.PRECIO_VENTA,D.MONTO_SUBTOTAL,V.FECHA ";
            sql+="FROM VENTA V INNER JOIN DETALLE_VENTA D ";
            sql+="ON D.NUM_TICKET=V.NUM_TICKET ";
            sql+="INNER JOIN PRODUCTO P ";
            sql+="ON P.COD_PRODUCTO=D.COD_PRODUCTO ";
            sql+="WHERE V.FECHA<=? AND P.NOMBRE=? ";
            sql+="ORDER BY P.NOMBRE ASC ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1,det.getFechaFinal());
            instruccion.setString(2, det.getNombreProducto());
            tabla=instruccion.executeQuery();
            lista=new ArrayList<DetalleBEAN>();
            
            while(tabla.next()){
                DetalleBEAN detalle=new DetalleBEAN();
                detalle.setNumTicket(tabla.getString(1));
                detalle.setCodProducto(tabla.getString(2));
                detalle.setNombreProducto(tabla.getString(3));
                detalle.setStock(tabla.getInt(4));
                detalle.setCantidad(tabla.getInt(5));
                detalle.setPrecioVenta(tabla.getDouble(6));
                detalle.setMontoSubtotal(tabla.getDouble(7));
                detalle.setFechaRegistro(tabla.getString(8));
                
                lista.add(detalle);
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return lista;
    }
    
    public ArrayList<DetalleBEAN> getListaDetalleVentasFiltroFechaInicioFechaFinal(DetalleBEAN det){
        
        try{
            conexion=new ConexionBD();
            sql="SELECT DISTINCT D.NUM_TICKET,D.COD_PRODUCTO,P.NOMBRE,P.CANTIDAD_STOCK, ";
            sql+="D.CANTIDAD,P.PRECIO_VENTA,D.MONTO_SUBTOTAL,V.FECHA ";
            sql+="FROM VENTA V INNER JOIN DETALLE_VENTA D ";
            sql+="ON D.NUM_TICKET=V.NUM_TICKET ";
            sql+="INNER JOIN PRODUCTO P ";
            sql+="ON P.COD_PRODUCTO=D.COD_PRODUCTO ";
            sql+="WHERE V.FECHA BETWEEN ? AND ? ";
            sql+="ORDER BY P.NOMBRE ASC ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1,det.getFechaInicio());
            instruccion.setString(2, det.getFechaFinal());
            tabla=instruccion.executeQuery();
            lista=new ArrayList<DetalleBEAN>();
            
            while(tabla.next()){
                DetalleBEAN detalle=new DetalleBEAN();
                detalle.setNumTicket(tabla.getString(1));
                detalle.setCodProducto(tabla.getString(2));
                detalle.setNombreProducto(tabla.getString(3));
                detalle.setStock(tabla.getInt(4));
                detalle.setCantidad(tabla.getInt(5));
                detalle.setPrecioVenta(tabla.getDouble(6));
                detalle.setMontoSubtotal(tabla.getDouble(7));
                detalle.setFechaRegistro(tabla.getString(8));
                
                lista.add(detalle);
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return lista;
    }
    
    public ArrayList<DetalleBEAN> getListaDetalleVentasFiltroNombreProductoFechaInicioFechaFinal(DetalleBEAN det){
        
        try{
            conexion=new ConexionBD();
            sql="SELECT DISTINCT D.NUM_TICKET,D.COD_PRODUCTO,P.NOMBRE,P.CANTIDAD_STOCK, ";
            sql+="D.CANTIDAD,P.PRECIO_VENTA,D.MONTO_SUBTOTAL,V.FECHA ";
            sql+="FROM VENTA V INNER JOIN DETALLE_VENTA D ";
            sql+="ON D.NUM_TICKET=V.NUM_TICKET ";
            sql+="INNER JOIN PRODUCTO P ";
            sql+="ON P.COD_PRODUCTO=D.COD_PRODUCTO ";
            sql+="WHERE P.NOMBRE=? AND V.FECHA BETWEEN ? AND ? ";
            sql+="ORDER BY P.NOMBRE ASC ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1,det.getNombreProducto());
            instruccion.setString(2,det.getFechaInicio());
            instruccion.setString(3, det.getFechaFinal());
            tabla=instruccion.executeQuery();
            lista=new ArrayList<DetalleBEAN>();
            
            while(tabla.next()){
                DetalleBEAN detalle=new DetalleBEAN();
                detalle.setNumTicket(tabla.getString(1));
                detalle.setCodProducto(tabla.getString(2));
                detalle.setNombreProducto(tabla.getString(3));
                detalle.setStock(tabla.getInt(4));
                detalle.setCantidad(tabla.getInt(5));
                detalle.setPrecioVenta(tabla.getDouble(6));
                detalle.setMontoSubtotal(tabla.getDouble(7));
                detalle.setFechaRegistro(tabla.getString(8));
                
                lista.add(detalle);
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return lista;
    }
}

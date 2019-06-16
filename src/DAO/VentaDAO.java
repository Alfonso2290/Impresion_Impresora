
package DAO;

import java.sql.*;
import java.util.*;
import UTIL.ConexionBD;
import BEAN.VentaBEAN;
import javax.swing.JOptionPane;

public class VentaDAO {

    private ConexionBD conexion=null;
    private PreparedStatement instruccion=null;
    private ResultSet tabla=null;
    private String sql="";
    private ArrayList<VentaBEAN> lista;
    
    public ArrayList<VentaBEAN> getListaVentas(){
        
        try{
            conexion=new ConexionBD();
            sql="SELECT * FROM VENTA ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            tabla=instruccion.executeQuery();
            lista=new ArrayList<VentaBEAN>();
            
            while(tabla.next()){
                VentaBEAN venta=new VentaBEAN();
                venta.setNumTicket(tabla.getString(1));
                venta.setDni(tabla.getString(2));
                venta.setFecha(tabla.getString(3));
                venta.setMontoTotal(tabla.getDouble(4));
                venta.setIgv(tabla.getDouble(5));
                venta.setMontoNeto(tabla.getDouble(6));
                
                lista.add(venta);
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return lista;
    }
    
    public ArrayList<VentaBEAN> getListaDNIVentas(){
        
        try{
            conexion=new ConexionBD();
            sql="SELECT DISTINCT DNI FROM VENTA ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            tabla=instruccion.executeQuery();
            lista=new ArrayList<VentaBEAN>();
            
            while(tabla.next()){
                VentaBEAN venta=new VentaBEAN();
                venta.setDni(tabla.getString(1));
                
                lista.add(venta);
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return lista;
    }
    
    public ArrayList<VentaBEAN> listarVentasFiltroDNI(VentaBEAN ven){
        
        try{
            conexion=new ConexionBD();
            sql="SELECT * FROM VENTA WHERE DNI=?";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1,ven.getDni());
            tabla=instruccion.executeQuery();
            lista=new ArrayList<VentaBEAN>();
            
            while(tabla.next()){
                VentaBEAN venta=new VentaBEAN();
                venta.setNumTicket(tabla.getString(1));
                venta.setDni(tabla.getString(2));
                venta.setFecha(tabla.getString(3));
                venta.setMontoTotal(tabla.getDouble(4));
                venta.setIgv(tabla.getDouble(5));
                venta.setMontoNeto(tabla.getDouble(6));
                
                lista.add(venta);
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return lista;
    }
    
    public void eliminarVenta(VentaBEAN venta){

        try{
            conexion=new ConexionBD();
            sql="DELETE FROM VENTA WHERE NUM_TICKET=? ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1, venta.getNumTicket());
            instruccion.executeUpdate();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error!!..No se pudo eliminar el registro de la venta");
        }
    }
}



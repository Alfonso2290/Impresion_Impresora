package DAO;

import java.sql.*;
import java.util.*;
import UTIL.ConexionBD;
import BEAN.ClienteBEAN;
import javax.swing.JOptionPane;

public class ClienteDAO {
    private ConexionBD conexion=null;
    private PreparedStatement instruccion=null;
    private ResultSet tabla=null;
    private String sql="";
    private ArrayList<ClienteBEAN> lista;
    
    public ArrayList<ClienteBEAN> getListaClientes(){
        
        try{
            conexion=new ConexionBD();
            sql="SELECT * FROM CLIENTE ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            tabla=instruccion.executeQuery();
            lista=new ArrayList<ClienteBEAN>();
            
            while(tabla.next()){
                ClienteBEAN cliente=new ClienteBEAN();
                cliente.setDni(tabla.getString(1));
                cliente.setNombre(tabla.getString(2));
                cliente.setFechaNacimiento(tabla.getString(3));
                cliente.setCelular(tabla.getString(4));
                cliente.setEmail(tabla.getString(5));
                
                lista.add(cliente);
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return lista;
    }
    
    public ArrayList<ClienteBEAN> getListaNombreClientes(){
        
        try{
            conexion=new ConexionBD();
            sql="SELECT DISTINCT NOMBRE FROM CLIENTE ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            tabla=instruccion.executeQuery();
            lista=new ArrayList<ClienteBEAN>();
            
            while(tabla.next()){
                ClienteBEAN cliente=new ClienteBEAN();
                cliente.setNombre(tabla.getString(1));
                
                lista.add(cliente);
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return lista;
    }
    
    public ArrayList<ClienteBEAN> listarContactosFiltroNombres(ClienteBEAN cliente){
        
        try{
            conexion=new ConexionBD();
            sql="SELECT * FROM CLIENTE WHERE NOMBRE=?";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1,cliente.getNombre());
            tabla=instruccion.executeQuery();
            lista=new ArrayList<ClienteBEAN>();
            
            while(tabla.next()){
                ClienteBEAN clie=new ClienteBEAN();
                clie.setDni(tabla.getString(1));
                clie.setNombre(tabla.getString(2));
                clie.setFechaNacimiento(tabla.getString(3));
                clie.setCelular(tabla.getString(4));
                clie.setEmail(tabla.getString(5));
                
                lista.add(clie);
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return lista;
    }
    
    public int verificarCliente(ClienteBEAN cliente)
    {
        int i=0;
        try 
        {
            conexion=new ConexionBD();
            sql="SELECT COUNT(*) ";
            sql+="FROM CLIENTE  ";
            sql+="WHERE DNI=?";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1, cliente.getDni());
            tabla=instruccion.executeQuery();
            
            if(tabla.next())
                i=tabla.getInt(1);
            
        } 
        catch (Exception e) {
        }
        
        return i;
    }
    
    public int registrarCliente(ClienteBEAN cliente){
        int i=0;
        try{
            conexion=new ConexionBD();
            sql="INSERT INTO CLIENTE VALUES(?,?,?,?,?)";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1, cliente.getDni());
            instruccion.setString(2, cliente.getNombre());
            instruccion.setString(3, cliente.getFechaNacimiento());
            instruccion.setString(4, cliente.getCelular());
            instruccion.setString(5, cliente.getEmail());
            i=instruccion.executeUpdate();
            
        }catch(Exception e){
            System.out.println("Error!!..No se pudo registrar el cliente");
        }
        
        return i;
    }
    
    public void eliminarCliente(ClienteBEAN cliente){

        try{
            conexion=new ConexionBD();
            sql="DELETE FROM CLIENTE WHERE DNI=? ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1, cliente.getDni());
            instruccion.executeUpdate();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error!!..No se pudo eliminar el registro del cliente");
        }
    }
    
    public int modificarCliente(ClienteBEAN cliente){
        int i=0;
        try{
            conexion=new ConexionBD();
            sql="UPDATE CLIENTE SET NOMBRE=?,FECHA_NACIMIENTO=?,";
            sql+="CELULAR=?,EMAIL=? ";
            sql+="WHERE DNI=? ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1, cliente.getNombre());
            instruccion.setString(2, cliente.getFechaNacimiento());
            instruccion.setString(3, cliente.getCelular());
            instruccion.setString(4, cliente.getEmail());
            instruccion.setString(5, cliente.getDni());
            i=instruccion.executeUpdate();
            
        }catch(Exception e){
            System.out.println("Error!!..No se pudo modificar el registro del cliente");
        }
        
        return i;
    }
    
    public int verificarDniVenta(ClienteBEAN cliente)
    {
        int i=0;
        try 
        {
            conexion=new ConexionBD();
            sql="SELECT COUNT(*) ";
            sql+="FROM CLIENTE  ";
            sql+="WHERE DNI=?";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1, cliente.getDni());
            tabla=instruccion.executeQuery();
            
            if(tabla.next())
                i=tabla.getInt(1);
            
        } 
        catch (Exception e) {
        }
        
        return i;
    }
}

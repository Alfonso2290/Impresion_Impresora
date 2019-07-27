
package DAO;

import BEAN.ClienteBEAN;
import java.sql.*;
import java.util.*;
import UTIL.ConexionBD;
import BEAN.ProductoBEAN;
import javax.swing.JOptionPane;

public class ProductoDAO {
    
    private ConexionBD conexion=null;
    private PreparedStatement instruccion=null;
    private ResultSet tabla=null;
    private String sql="";
    private ArrayList<ProductoBEAN> lista;
    
    public String getNombreProductoMasConsumido(ClienteBEAN cliente){
        String nombreProducto="";
        try{
            conexion=new ConexionBD();
            sql="SELECT TOP 1 D.NOMBRE AS PRODUCTO ";
            sql+="FROM CLIENTE A INNER JOIN VENTA B ";
            sql+="ON A.DNI=B.DNI ";
            sql+="INNER JOIN DETALLE_VENTA C ";
            sql+="ON B.NUM_TICKET=C.NUM_TICKET ";
            sql+="INNER JOIN PRODUCTO D ";
            sql+="ON C.COD_PRODUCTO=D.COD_PRODUCTO ";
            sql+="WHERE A.DNI=? ";
            sql+="GROUP BY (D.NOMBRE) ";
            sql+="ORDER BY SUM(C.MONTO_SUBTOTAL) DESC ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1, cliente.getDni());
            tabla=instruccion.executeQuery();
            
            if(tabla.next()){
                nombreProducto=tabla.getString(1);
            }
            
            
        }catch(Exception ex){
            System.out.println("Error");
            nombreProducto=null;
        }
        return nombreProducto;
    }
    
    public String getCodigoProductoMasConsumido(String nombreProducto){
        String codigoProducto="";
        try{
            conexion=new ConexionBD();
            sql="SELECT COD_PRODUCTO FROM PRODUCTO ";
            sql+="WHERE NOMBRE=? ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1, nombreProducto);
            tabla=instruccion.executeQuery();
            
            if(tabla.next()){
                codigoProducto=tabla.getString(1);
            }
            
            
        }catch(Exception ex){
            System.out.println("Error");
            codigoProducto=null;
        }
        return codigoProducto;
    }
    
    public ArrayList<ProductoBEAN> getProductoMasConsumido(String codigoProducto,String nombreProducto){
        
        try{
            conexion=new ConexionBD();
            sql="SELECT IMAGEN FROM PRODUCTO ";
            sql+="WHERE COD_PRODUCTO=? ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1, codigoProducto);
            tabla=instruccion.executeQuery();
            lista=new ArrayList<ProductoBEAN>();
            
            if(tabla.next()){
                ProductoBEAN producto=new ProductoBEAN();
                producto.setImagen(tabla.getBytes(1));
                producto.setNombre(nombreProducto);
                
                lista.add(producto);
            }
            
            
        }catch(Exception ex){
            System.out.println("Error");
            lista=null;
        }
        return lista;
    }
    
    public ArrayList<ProductoBEAN> getListaProductos(){
        
        try{
            conexion=new ConexionBD();
            sql="SELECT * FROM PRODUCTO ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            tabla=instruccion.executeQuery();
            lista=new ArrayList<ProductoBEAN>();
            
            while(tabla.next()){
                ProductoBEAN producto=new ProductoBEAN();
                producto.setCodProducto(tabla.getString(1));
                producto.setNombre(tabla.getString(2));
                producto.setDescripcion(tabla.getString(3));
                producto.setPrecioVenta(tabla.getDouble(4));
                producto.setCantidad(tabla.getInt(5));
                producto.setEstado(tabla.getInt(6));
                producto.setImagen(tabla.getBytes(7));
                
                lista.add(producto);
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return lista;
    }
    
    public ArrayList<ProductoBEAN> getListaProductosDisponibles(){
        
        try{
            conexion=new ConexionBD();
            sql="SELECT * FROM PRODUCTO WHERE SW_ACTIVIDAD<>0 ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            tabla=instruccion.executeQuery();
            lista=new ArrayList<ProductoBEAN>();
            
            while(tabla.next()){
                ProductoBEAN producto=new ProductoBEAN();
                producto.setCodProducto(tabla.getString(1));
                producto.setNombre(tabla.getString(2));
                producto.setDescripcion(tabla.getString(3));
                producto.setPrecioVenta(tabla.getDouble(4));
                producto.setCantidad(tabla.getInt(5));
                producto.setEstado(tabla.getInt(6));
                producto.setImagen(tabla.getBytes(7));
                
                lista.add(producto);
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return lista;
    }
    
    public ArrayList<ProductoBEAN> getListaNombreProductos(){
        
        try{
            conexion=new ConexionBD();
            sql="SELECT DISTINCT NOMBRE FROM PRODUCTO ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            tabla=instruccion.executeQuery();
            lista=new ArrayList<ProductoBEAN>();
            
            while(tabla.next()){
                ProductoBEAN producto=new ProductoBEAN();
                producto.setNombre(tabla.getString(1));
                
                lista.add(producto);
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return lista;
    }
    
    public ArrayList<ProductoBEAN> getListaNombreProductosDisponibles(){
        
        try{
            conexion=new ConexionBD();
            sql="SELECT DISTINCT NOMBRE FROM PRODUCTO WHERE SW_ACTIVIDAD<>0 ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            tabla=instruccion.executeQuery();
            lista=new ArrayList<ProductoBEAN>();
            
            while(tabla.next()){
                ProductoBEAN producto=new ProductoBEAN();
                producto.setNombre(tabla.getString(1));
                
                lista.add(producto);
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return lista;
    }
    
    public ArrayList<ProductoBEAN> listarProductosFiltroNombres(ProductoBEAN pro){
        
        try{
            conexion=new ConexionBD();
            sql="SELECT * FROM PRODUCTO WHERE NOMBRE=?";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1,pro.getNombre());
            tabla=instruccion.executeQuery();
            lista=new ArrayList<ProductoBEAN>();
            
            while(tabla.next()){
                ProductoBEAN producto=new ProductoBEAN();
                producto.setCodProducto(tabla.getString(1));
                producto.setNombre(tabla.getString(2));
                producto.setDescripcion(tabla.getString(3));
                producto.setPrecioVenta(tabla.getDouble(4));
                producto.setCantidad(tabla.getInt(5));
                producto.setEstado(tabla.getInt(6));
                producto.setImagen(tabla.getBytes(7));
                
                lista.add(producto);
            }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return lista;
    }
    
    public int verificarProducto(ProductoBEAN producto)
    {
        int i=0;
        try 
        {
            conexion=new ConexionBD();
            sql="SELECT COUNT(*) ";
            sql+="FROM PRODUCTO  ";
            sql+="WHERE COD_PRODUCTO=?";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1, producto.getCodProducto());
            tabla=instruccion.executeQuery();
            
            if(tabla.next())
                i=tabla.getInt(1);
            
        } 
        catch (Exception e) {
        }
        
        return i;
    }
    
    public int verificarNombreProducto(ProductoBEAN producto)
    {
        int i=0;
        try 
        {
            conexion=new ConexionBD();
            sql="SELECT COUNT(*) ";
            sql+="FROM PRODUCTO  ";
            sql+="WHERE NOMBRE=?";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1, producto.getNombre());
            tabla=instruccion.executeQuery();
            
            if(tabla.next())
                i=tabla.getInt(1);
            
        } 
        catch (Exception e) {
        }
        
        return i;
    }
    
    public int registrarProducto(ProductoBEAN producto){
        int i=0;
        try{
            conexion=new ConexionBD();
            sql="INSERT INTO PRODUCTO VALUES(?,?,?,?,?,?,?)";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1, producto.getCodProducto());
            instruccion.setString(2, producto.getNombre());
            instruccion.setString(3, producto.getDescripcion());
            instruccion.setDouble(4, producto.getPrecioVenta());
            instruccion.setInt(5, producto.getCantidad());
            instruccion.setInt(6, producto.getEstado());
            instruccion.setBytes(7, producto.getImagen());
            i=instruccion.executeUpdate();
            
        }catch(Exception e){
            System.out.println("Error!!..No se pudo registrar el producto");
        }
        
        return i;
    }
    
    public int modificarProducto(ProductoBEAN producto){
        int i=0;
        try{
            conexion=new ConexionBD();
            sql="UPDATE PRODUCTO SET NOMBRE=?,DES_PRODUCTO=?,";
            sql+="PRECIO_VENTA=?,CANTIDAD_STOCK=?,SW_ACTIVIDAD=?,IMAGEN=? ";
            sql+="WHERE COD_PRODUCTO=? ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1, producto.getNombre());
            instruccion.setString(2, producto.getDescripcion());
            instruccion.setDouble(3, producto.getPrecioVenta());
            instruccion.setInt(4, producto.getCantidad());
            instruccion.setInt(5, producto.getEstado());
            instruccion.setBytes(6, producto.getImagen());
            instruccion.setString(7, producto.getCodProducto());
            i=instruccion.executeUpdate();
            
        }catch(Exception e){
            System.out.println("Error!!..No se pudo modificar el registro del producto");
        }
        
        return i;
    }
    
    public void eliminarProducto(ProductoBEAN producto){

        try{
            conexion=new ConexionBD();
            sql="DELETE FROM PRODUCTO WHERE COD_PRODUCTO=? ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1, producto.getCodProducto());
            instruccion.executeUpdate();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error!!..No se pudo eliminar el registro del producto");
        }
    }
    
    public byte[] getImagenProducto(String codigoProducto){
        byte[] imagen=null;
        try{
            conexion=new ConexionBD();
            sql="SELECT IMAGEN FROM PRODUCTO ";
            sql+="WHERE COD_PRODUCTO=? ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1, codigoProducto);
            tabla=instruccion.executeQuery();
            
            if(tabla.next()){
                imagen=tabla.getBytes(1);
            }
            
            
        }catch(Exception ex){
            System.out.println("Error");
            lista=null;
        }
        return imagen;
    }
    
    public ProductoBEAN getProductoSeleccion(ProductoBEAN prod){
        ProductoBEAN producto=null;
        try{
            conexion=new ConexionBD();
            sql="SELECT * FROM PRODUCTO ";
            sql+="WHERE COD_PRODUCTO=? ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setString(1, prod.getCodProducto());
            tabla=instruccion.executeQuery();
            
            while(tabla.next()){
                producto=new ProductoBEAN();
                producto.setCodProducto(tabla.getString(1));
                producto.setNombre(tabla.getString(2));
                producto.setDescripcion(tabla.getString(3));
                producto.setPrecioVenta(tabla.getDouble(4));
                producto.setCantidad(tabla.getInt(5));
                producto.setEstado(tabla.getInt(6));
                producto.setImagen(tabla.getBytes(7));
 
            }
            
            
        }catch(Exception ex){
            System.out.println("Error");
        }
        
        return producto;
    }
    
    public void modificarStockProducto(ProductoBEAN producto){
        
        try{
            conexion=new ConexionBD();
            sql="UPDATE PRODUCTO SET CANTIDAD_STOCK=? ";
            sql+="WHERE COD_PRODUCTO=? ";
            instruccion=conexion.getConexionBD().prepareStatement(sql);
            instruccion.setInt(1, producto.getCantidad());
            instruccion.setString(2, producto.getCodProducto());
            instruccion.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se ha actualizado correctamente el stock del producto");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error!!..No se pudo actualizar el stock del producto");
        }
        
    }
}

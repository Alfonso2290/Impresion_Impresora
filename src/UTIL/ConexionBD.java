
package UTIL;

import java.sql.*;

public class ConexionBD {
    
    private Connection on;
    
    public Connection getConexionBD(){
        
        on=null;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            on=DriverManager.getConnection("jdbc:sqlserver://192.168.1.7:1433;databaseName=BDCAFETERIA","sa","Lacristina1");
            //System.out.println("Conexión exitosa");
            
        }catch(Exception e){
            System.out.println("Error en conexión");
            e.printStackTrace();
        }
        
        return on;
    }
    
    
}

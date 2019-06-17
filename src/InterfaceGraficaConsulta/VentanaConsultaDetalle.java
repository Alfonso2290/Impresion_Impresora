
package InterfaceGraficaConsulta;

import BEAN.DetalleBEAN;
import DAO.DetalleDAO;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.*;

public class VentanaConsultaDetalle extends JFrame
{
    private String numeroTicket;
    private PanelConsultaDetalle lamina;
    private ArrayList<DetalleBEAN> lista;
    
    public VentanaConsultaDetalle(String numeroTicket){
        this.numeroTicket=numeroTicket;
        setTitle("Detalle Venta");
        cargarLista();
        definirTamañoVentana();
        setLocationRelativeTo(null);
        setResizable(false);
        Inicio();
    }
    
    private void Inicio(){
        lamina=new PanelConsultaDetalle(numeroTicket);
        setBackground(Color.LIGHT_GRAY.brighter());
        lamina.setOpaque(true);
        add(lamina);
    }
    
    private void cargarLista(){
        DetalleDAO DAO=new DetalleDAO();
        lista=DAO.getListadoDetalleVenta(numeroTicket);
    }
    
    private void definirTamañoVentana(){
        int y=220;
        if(lista!=null){
            for(int i=0;i<lista.size();i++){
                y+=15;
            }
        }
        setSize(240,y);
    }
}

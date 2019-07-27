
package InterfaceGraficaRegistro;

import BEAN.DetalleBEAN;
import BEAN.ProductoBEAN;
import DAO.DetalleDAO;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import javax.swing.*;
import java.util.*;

public class VentanaImpresionTicketCupon extends JFrame{

    private PanelImpresionTicketCupon miPanel;
    private ArrayList<ProductoBEAN> lista;
    private ArrayList<DetalleBEAN> listaDetalle;
    private JPanel lamina;
    private int y;
    private String numeroTicket;

    public VentanaImpresionTicketCupon(ArrayList<ProductoBEAN> lista,String numeroTicket) {
        this.lista=lista;
        this.numeroTicket=numeroTicket;
        inicioComponentes();
    }

    private void inicioComponentes() {
        setTitle("Ticket + Cupon");
        cargarLista();
        definirTamañoVentana();
        setLocationRelativeTo(null);
        setResizable(false);
        
        Color ColorFuente=new Color(232,44,12);
        Font fuenteCamposLabel=new Font("Decker", Font.BOLD, 16);
        
        setLayout(new BorderLayout());

        miPanel=new PanelImpresionTicketCupon(this.lista,y,listaDetalle,numeroTicket);
        add(miPanel,BorderLayout.CENTER);
        
        lamina=new JPanel();
        lamina.setLayout(new GridLayout(1,2,10,10));
        lamina.setBackground(Color.LIGHT_GRAY.brighter());

        add(lamina,BorderLayout.SOUTH);
    }
    
    private void cargarLista(){
        DetalleDAO DAO=new DetalleDAO();
        listaDetalle=DAO.getListadoDetalleVenta(numeroTicket);
    }
    
    private void definirTamañoVentana(){
        y=300 + 220;
        if(listaDetalle!=null){
            for(int i=0;i<listaDetalle.size();i++){
                y+=15;
            }
        }
        setSize(240,y);
    }
    
    public PanelImpresionTicketCupon getMiPanel() {
        return miPanel;
    }
    
    
}

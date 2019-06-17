
package InterfaceGraficaConsulta;

import BEAN.DetalleBEAN;
import DAO.DetalleDAO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import javax.swing.*;
import java.util.*;

public class PanelConsultaDetalle extends JPanel{
    
    private String numeroTicket;
    private Font fondo=new Font("Decker", Font.PLAIN, 11);
    private ArrayList<DetalleBEAN> lista;
    private int y=90;
    private double montoTotal,montoNeto,IGV;
    private DecimalFormat formato;
    
    public PanelConsultaDetalle(String numeroTicket){
        this.numeroTicket=numeroTicket;
        setFont(fondo);
        formato=new DecimalFormat("##.##");
        //setForeground(Color.BLUE.brighter());
        cargarLista();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponents(g);
        Graphics2D g2=(Graphics2D)g;
//        g2.drawLine(10,45,225,45);
//        g2.drawLine(10,75,225,75);
        
        g2.drawString("------------------------------------------------------", 10, 45);
        g2.drawString("------------------------------------------------------", 10, 75);
        g2.drawString("Num. Ticket: " + numeroTicket,10,30);
      
        g2.drawString("CODIGO  CONCEPTO  CANT.   P.U. IMPORTE",10,60);
        y+=5;
        for(DetalleBEAN obj:lista){
            
            g2.drawString(obj.getCodProducto(),10,y);
            g2.drawString(obj.getNombreProducto(),70,y);
            g2.drawString(obj.getCantidad() + "",135,y);
            g2.drawString("S/." + formato.format(obj.getPrecioVenta()),160,y);
            g2.drawString("S/." + formato.format(obj.getMontoSubtotal()),190,y);
                            
            montoTotal=obj.getMontoTotal();
            montoNeto=obj.getMontoNeto();
            IGV=obj.getIgv()*100;
            y+=15;
        }
        //g2.drawLine(10,y+=15,225,y);
        g2.drawString("------------------------------------------------------", 10, y+=5);
        g2.drawString("Base imponible:",75,y+=15);
        g2.drawString("S/." + formato.format(montoTotal), 190,y );
        g2.drawString("IGV:",75,y+=15);
        g2.drawString(formato.format(IGV) + "%",190,y);
        //g2.drawLine(10,y+=15,225,y);
        g2.drawString("------------------------------------------------------", 10, y+=15);
        g2.drawString("TOTAL:",75,y+=15);
        g2.drawString("S/." + formato.format(montoNeto),185,y);
    }
    
    private void cargarLista(){
        DetalleDAO DAO=new DetalleDAO();
        lista=DAO.getListadoDetalleVenta(numeroTicket);
    }
}
        
        /*Rectangle2D obj=new Rectangle2D.Double(0,0,240,400);
        g2.setPaint(Color.LIGHT_GRAY.brighter());
        g2.fill(obj);*/
//        g2.setBackground(Color.RED);
//        g2.fill((Shape) Color.BLACK);
//        g2.drawString("Número de Ticket:",15,30);
//        g2.setFont(fondo);
//        g.setColor(Color.yellow);

package InterfaceGraficaRegistro;

import BEAN.*;
import DAO.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.*;

public class VentanaRegistroVentas extends JFrame implements ActionListener,FocusListener
{
    private PanelRegistroVentas miPanel;
    private DecimalFormat formato;
    
    public VentanaRegistroVentas()
    {
        setTitle("Registrar Venta");
        Dimension tamañoPantalla=Toolkit.getDefaultToolkit().getScreenSize();
        setSize(tamañoPantalla.width*4/5,470);
        setLocationRelativeTo(null);
        setResizable(false);
        Inicio();
    }
    
    private void Inicio()
    {
        formato=new DecimalFormat("##.##");
        miPanel=new PanelRegistroVentas();
        miPanel.setBackground(Color.LIGHT_GRAY.brighter());
        
        miPanel.getBtnGuardar().addActionListener(this);
        miPanel.getBtnCancelar().addActionListener(this);
        miPanel.getBtnAtras().addActionListener(this);
        miPanel.getTxtNumTicket().addFocusListener(this);
        miPanel.getTxtDni().addFocusListener(this);
        
        
        miPanel.getBtnAtras().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
                VentanaRegistros ventana=new VentanaRegistros();
                ventana.setVisible(true);
                ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        
        add(miPanel);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==miPanel.getBtnCancelar())
        {
            eventoCancelar();
        }
        
        if(e.getSource()==miPanel.getBtnGuardar())
        {
            eventoGuardar();
        }
        
    }
    
    private void eventoGuardar()
    {
        String dni,ticket,fecha;
        double montoTotal,igv,montoNeto;
        ArrayList<DetalleBEAN> lista=miPanel.listaGuardar;
        if(lista.size()!=0){
            dni=miPanel.getTxtDni().getText();
            ticket=miPanel.getTxtNumTicket().getText();
            montoTotal=miPanel.montoT;
            igv=miPanel.igvActual/100;
            montoNeto=miPanel.montoN;
            
            VentaDAO dao=new VentaDAO();
            fecha=dao.getFechaRegistroActual();

            if(ticket.equals("") || ticket.length()<10)
            {
                JOptionPane.showMessageDialog(null, "Ustede debe llenar el campo Número de Ticket");
                miPanel.getTxtNumTicket().requestFocus();
            }
            else if(miPanel.getMensaje().getText().equals("")==false)
            {
                JOptionPane.showMessageDialog(null, "El Número de Ticket ingresado ya está registrado");
                miPanel.getTxtNumTicket().requestFocus();
            }
            else if(dni.equals("") || dni.length()<8)
            {
                JOptionPane.showMessageDialog(null, "Ustede debe llenar el campo DNI");
                miPanel.getTxtDni().requestFocus();
            }
            else if(miPanel.getMensaje2().getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "El DNI ingresado es invalido");
                miPanel.getTxtDni().requestFocus();
            }
            else
            {
                VentaBEAN venta=new VentaBEAN();
                venta.setDni(dni);
                venta.setNumTicket(ticket);
                venta.setFecha(fecha);
                venta.setMontoNeto(montoNeto);
                venta.setMontoTotal(montoTotal);
                venta.setIgv(igv);
                
                VentaDAO ventaDAO=new VentaDAO();
                ventaDAO.registrarVenta(venta);
                
                for(DetalleBEAN obj: lista){
                    DetalleBEAN det=new DetalleBEAN();
                    det.setNumTicket(ticket);
                    det.setCodProducto(obj.getCodProducto());
                    det.setCantidad(obj.getCantidad());
                    det.setMontoSubtotal(obj.getMontoSubtotal());
                    
                    DetalleDAO detDAO=new DetalleDAO();
                    detDAO.registrarDetalleVenta(det);
                }
                
                for(DetalleBEAN obj: lista){
                    ProductoBEAN producto=new ProductoBEAN();
                    producto.setCodProducto(obj.getCodProducto());
                    producto.setCantidad(obj.getStock());
                    
                    ProductoDAO proDAO=new ProductoDAO();
                    proDAO.modificarStockProducto(producto);
                }
                
                miPanel.montoT=0;
                miPanel.igvActual=18;
                miPanel.montoN=0;
                
                parametrizarTicket();
                dispose();
                VentanaRegistros ventana=new VentanaRegistros();
                ventana.setVisible(true);
                ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Usted debe realizar alguna compra");
            miPanel.getTxtNumTicket().requestFocus();
        }
    }
    
    public void parametrizarTicket(){
        
        String dniCliente=miPanel.getTxtDni().getText();
        ClienteBEAN cliente=new ClienteBEAN();
        cliente.setDni(dniCliente);
        ProductoDAO productoDAO=new ProductoDAO();
        ArrayList<ProductoBEAN> lista;
        String nombreProducto=productoDAO.getNombreProductoMasConsumido(cliente);
        String codigoProducto=productoDAO.getCodigoProductoMasConsumido(nombreProducto);
        lista=productoDAO.getProductoMasConsumido(codigoProducto,nombreProducto);
        
        String numeroTicket=miPanel.getTxtNumTicket().getText();
        VentanaImpresionTicketCupon miVentana=new VentanaImpresionTicketCupon(lista,numeroTicket);
        miVentana.setVisible(true);
        
        
        try {
            PrinterJob imp=PrinterJob.getPrinterJob();
            imp.setPrintable(new Printable() {

                @Override
                public int print(Graphics g, PageFormat pagFor, int index) throws PrinterException {

                        if(index>0)
                                return NO_SUCH_PAGE;

                        Graphics2D g2=(Graphics2D)g;
                        g2.translate(pagFor.getImageableX()+8, pagFor.getImageableY()+8);
                        g2.scale(1.0,1.0);
                        miVentana.getMiPanel().printAll(g);

                        return PAGE_EXISTS;
                }
            });;
            boolean top=imp.printDialog();
            if(top) {
                    imp.print();
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }finally{
            miVentana.dispose();
        }
    }

    
    @Override
    public void focusGained(FocusEvent e)
    {
        if(e.getSource()==miPanel.getTxtNumTicket())
            miPanel.getMensaje().setText("");
        if(e.getSource()==miPanel.getTxtDni())
            miPanel.getMensaje2().setText("");
    }
    
    @Override
    public void focusLost(FocusEvent e)
    {

        if(e.getSource()==miPanel.getTxtNumTicket())
        {
            String ticket=miPanel.getTxtNumTicket().getText();
            VentaBEAN venta=new VentaBEAN();
            venta.setNumTicket(ticket);
            
            VentaDAO ventaDAO=new VentaDAO();
            int verificacion=ventaDAO.verificarNumeroTicket(venta);
            if(verificacion>0 && ticket.compareTo("")!=0 )
            {
                miPanel.getMensaje().setText("Ticket Registrado");
            }
        }
        
        if(e.getSource()==miPanel.getTxtDni())
        {
            String dni=miPanel.getTxtDni().getText();
            ClienteBEAN cliente=new ClienteBEAN();
            cliente.setDni(dni);
            
            ClienteDAO clienteDAO=new ClienteDAO();
            int verificacion=clienteDAO.verificarDniVenta(cliente);
            if(verificacion>0 && dni.compareTo("")!=0 )
            {
                miPanel.getMensaje2().setText("DNI Válido");
            }
        }
    }
    
    private void eventoCancelar()
    {
        int rpta=JOptionPane.showConfirmDialog(null,"Esta seguro que desea cancelar?\nLos Datos ingresados en la ventana se perderán","Alerta!!" ,JOptionPane.YES_NO_OPTION);
        if(rpta==JOptionPane.YES_OPTION)
        {
            dispose();
            VentanaRegistros ventana=new VentanaRegistros();
            ventana.setVisible(true);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }
    
}


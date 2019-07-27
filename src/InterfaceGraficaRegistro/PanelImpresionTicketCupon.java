
package InterfaceGraficaRegistro;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import BEAN.*;
import java.awt.image.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import javax.imageio.ImageIO;

public class PanelImpresionTicketCupon extends JPanel{

	private JLabel titulo,msj1,msj2,msj3,icono,t1,t2,t3;
	private String porcentaje,mensaje,directorio;
        private int x,y,dx,dy,imgx,imgy,imgdx,imgdy;
        private ArrayList<ProductoBEAN> lista;
        private ArrayList<DetalleBEAN> listaDetalle;
        private int tamañoInicioY,crecimientoY=90;
        private String numeroTicket;
        private JLabel numTicket,linea1,cabecera,linea2,linea3,linea4,lblIgv,lblMontoNeto,lblMontoTotal;
        private DecimalFormat formato;
        private double montoTotal,montoNeto,IGV;
        private JLabel msje1,msje2,msje3;
        
	public PanelImpresionTicketCupon(ArrayList<ProductoBEAN> lista,int y,ArrayList<DetalleBEAN> listaDetalle,String numeroTicket) {
            this.lista=lista;
            this.tamañoInicioY=y-300;
            this.listaDetalle=listaDetalle;
            this.numeroTicket=numeroTicket;
            formato=new DecimalFormat("##.##");
            setBackground(Color.LIGHT_GRAY.brighter());
            inicioComponentes();
	}
	
	private void inicioComponentes() {
            setLayout(null);

            Font fondo1=new Font("Verdana",Font.PLAIN,11);
            Font fondo2=new Font("Verdana",Font.BOLD,10);
            Font fondo3=new Font("Verdana",Font.ITALIC,10);
            Font fondo4=new Font("",Font.PLAIN,9);

            numTicket=new JLabel("Num. Ticket: " + numeroTicket);
            numTicket.setBounds(10,30,220,20);
            numTicket.setFont(fondo4);
            add(numTicket);
            
            linea1=new JLabel("----------------------------------------------------------------------");
            linea1.setBounds(10,45,220,20);
            linea1.setFont(fondo4);
            add(linea1);
            
            cabecera=new JLabel("CODIGO  CONCEPTO  CANT.   P.U. IMPORTE");
            cabecera.setBounds(10,60,220,20);
            cabecera.setFont(fondo4);
            add(cabecera);
            
            linea2=new JLabel("----------------------------------------------------------------------");
            linea2.setBounds(10,75,220,20);
            linea2.setFont(fondo4);
            add(linea2);
            
            crecimientoY+=5;
            
            for(DetalleBEAN obj:listaDetalle){
            
            JLabel codigoProducto=new JLabel(obj.getCodProducto() + "");
            codigoProducto.setBounds(10,crecimientoY,220,20);
            codigoProducto.setFont(fondo4);
            add(codigoProducto);
            
            JLabel nombreProducto=new JLabel(obj.getNombreProducto() + "");
            nombreProducto.setBounds(70,crecimientoY,220,20);
            nombreProducto.setFont(fondo4);
            add(nombreProducto);
            
            JLabel cantidadProducto=new JLabel(obj.getCantidad() + "");
            cantidadProducto.setBounds(135,crecimientoY,220,20);
            cantidadProducto.setFont(fondo4);
            add(cantidadProducto);
            
            JLabel precioDeVenta=new JLabel("S/." + formato.format(obj.getPrecioVenta()) + "");
            precioDeVenta.setBounds(160,crecimientoY,220,20);
            precioDeVenta.setFont(fondo4);
            add(precioDeVenta);
            
            JLabel montoSubtotal=new JLabel("S/." + formato.format(obj.getMontoSubtotal()) + "");
            montoSubtotal.setBounds(190,crecimientoY,220,20);
            montoSubtotal.setFont(fondo4);
            add(montoSubtotal);
            
            montoTotal=obj.getMontoTotal();
            montoNeto=obj.getMontoNeto();
            IGV=obj.getIgv()*100;
            crecimientoY+=15;
            }
            
            linea3=new JLabel("----------------------------------------------------------------------");
            linea3.setBounds(10,crecimientoY+=5,220,20);
            linea3.setFont(fondo4);
            add(linea3);
            
            msje1=new JLabel("Base imponible:");
            msje1.setBounds(75,crecimientoY+=15,220,20);
            msje1.setFont(fondo4);
            add(msje1);
            
            lblMontoTotal=new JLabel("S/." + formato.format(montoTotal));
            lblMontoTotal.setBounds(190,crecimientoY,220,20);
            lblMontoTotal.setFont(fondo4);
            add(lblMontoTotal);
            
            msje2=new JLabel("IGV:");
            msje2.setBounds(75,crecimientoY+=15,220,20);
            msje2.setFont(fondo4);
            add(msje2);
            
            lblIgv=new JLabel(formato.format(IGV) + "%");
            lblIgv.setBounds(190,crecimientoY,220,20);
            lblIgv.setFont(fondo4);
            add(lblIgv);
            
            linea4=new JLabel("----------------------------------------------------------------------");
            linea4.setBounds(10,crecimientoY+=15,220,20);
            linea4.setFont(fondo4);
            add(linea4);
            
            msje3=new JLabel("TOTAL:");
            msje3.setBounds(75,crecimientoY+=15,220,20);
            msje3.setFont(fondo4);
            add(msje3);
            
            lblMontoNeto=new JLabel("S/." + formato.format(montoNeto));
            lblMontoNeto.setBounds(185,crecimientoY,220,20);
            lblMontoNeto.setFont(fondo4);
            add(lblMontoNeto);
            
            //*********************************************
            
            titulo=new JLabel("BONO CUPÓN");
            titulo.setBounds(50, tamañoInicioY + 10, 150, 30);
            titulo.setFont(new Font("Verdana",Font.BOLD,18));

            msj1=new JLabel("Usted tiene un descuento de");
            msj1.setBounds(10, tamañoInicioY + 50, 190, 20);
            msj1.setFont(fondo1);
            
            
            if(lista==null || lista.size()==0){
                porcentaje="5%";
                mensaje="en cualquiera de nuestros productos";
                x=10;y=100;dx=300;dy=30;
                directorio="/imagenes/cafeteria.png";
                imgx=30;imgy=140;imgdx=180;imgdy=120;
                
                URL ruta=this.getClass().getResource(directorio);
                icono=new JLabel(new ImageIcon(ruta));
                icono.setBounds(imgx,imgy,imgdx,imgdy);
            }else{
                porcentaje="20%";
                x=80;y=tamañoInicioY + 60;dx=200;dy=30;
                imgx=30;imgy=tamañoInicioY + 90;imgdx=180;imgdy=100;
                icono=new JLabel();
                icono.setBounds(imgx,imgy,imgdx,imgdy);
                 
 
                for(ProductoBEAN pro:lista){
                 
                    mensaje="en " + pro.getNombre().toUpperCase();
                    try{
                        byte[] bi = pro.getImagen();
                        InputStream is=new ByteArrayInputStream(bi);//cambio instancia
                        BufferedImage image=ImageIO.read(is);
                        ImageIcon foto=new ImageIcon(bi);
                        
                        Image img=foto.getImage();
                        Image newimg=img.getScaledInstance(imgdx,imgdy, java.awt.Image.SCALE_SMOOTH);

                        ImageIcon newicon=new ImageIcon(newimg);
                        icono.setIcon(newicon);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                } 
            }
            msj2=new JLabel(porcentaje);
            msj2.setBounds(175, tamañoInicioY + 45, 60, 30);
            msj2.setFont(new Font("Verdana",Font.BOLD,16));
            
            msj3=new JLabel(mensaje);
            msj3.setBounds(x,y,dx,dy);
            msj3.setFont(fondo1);

            t1=new JLabel("Términos y Condiciones");
            t1.setBounds(3, tamañoInicioY + 190, 150, 20);
            t1.setFont(fondo2);

            t2=new JLabel("Válido hasta el 30/12/2019");
            t2.setBounds(45, tamañoInicioY + 210, 200, 20);
            t2.setFont(fondo3);

            t3=new JLabel("en cualquiera de nuestros establecimientos");
            t3.setBounds(3, tamañoInicioY + 220, 300, 20);
            t3.setFont(fondo3);

            add(titulo);
            add(msj1);
            add(msj2);
            add(msj3);
            add(icono);
            add(t1);
            add(t2);
            add(t3);
        }
}

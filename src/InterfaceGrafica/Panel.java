package InterfaceGrafica;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import BEAN.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

public class Panel extends JPanel{

	private JLabel titulo,msj1,msj2,msj3,icono,t1,t2,t3;
	private String porcentaje,mensaje,directorio;
        private int x,y,dx,dy,imgx,imgy,imgdx,imgdy;
        private ArrayList<ProductoBEAN> lista;
        
	public Panel(ArrayList<ProductoBEAN> lista) {
            this.lista=lista;
            setBackground(Color.LIGHT_GRAY.brighter());
            inicioComponentes();
	}
	
	private void inicioComponentes() {
            setLayout(null);

            Font fondo1=new Font("Verdana",Font.PLAIN,11);
            Font fondo2=new Font("Verdana",Font.BOLD,10);
            Font fondo3=new Font("Verdana",Font.ITALIC,10);

            titulo=new JLabel("BONO CUPÓN");
            titulo.setBounds(50, 10, 150, 30);
            titulo.setFont(new Font("Verdana",Font.BOLD,18));

            msj1=new JLabel("Usted tiene un descuento de");
            msj1.setBounds(10, 80, 190, 20);
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
                x=80;y=100;dx=200;dy=30;
                imgx=30;imgy=150;imgdx=180;imgdy=100;
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
                        Image newimg=img.getScaledInstance(240, 240, java.awt.Image.SCALE_SMOOTH);

                        ImageIcon newicon=new ImageIcon(newimg);
                        icono.setIcon(newicon);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                } 
            }
            msj2=new JLabel(porcentaje);
            msj2.setBounds(175, 75, 60, 30);
            msj2.setFont(new Font("Verdana",Font.BOLD,16));
            
            msj3=new JLabel(mensaje);
            msj3.setBounds(x,y,dx,dy);
            msj3.setFont(fondo1);

            t1=new JLabel("Términos y Condiciones");
            t1.setBounds(3, 275, 150, 20);
            t1.setFont(fondo2);

            t2=new JLabel("Válido hasta el 30/12/2019");
            t2.setBounds(45, 295, 200, 20);
            t2.setFont(fondo3);

            t3=new JLabel("en cualquiera de nuestros establecimientos");
            t3.setBounds(3, 305, 300, 20);
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

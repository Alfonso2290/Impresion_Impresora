package InterfaceGrafica;

import javax.swing.*;
import java.awt.*;
import java.net.*;

public class Panel extends JPanel{

	private JLabel titulo,msj1,msj2,msj3,icono,t1,t2,t3;
	private String nomProducto,porcentaje,mensaje,directorio;
        private int x,y,dx,dy,imgx,imgy,imgdx,imgdy;
        
	public Panel(String nomProducto) {
            this.nomProducto=nomProducto;
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
            
            
            if(nomProducto==null || nomProducto.equals("")){
                porcentaje="5%";
                mensaje="en cualquiera de nuestros productos";
                x=10;y=100;dx=300;dy=30;
                directorio="/imagenes/cafeteria.png";
                imgx=30;imgy=140;imgdx=180;imgdy=120;
            }else{
                porcentaje="20%";
                mensaje="en " + nomProducto.toUpperCase();
                x=80;y=100;dx=200;dy=30;
                directorio="/imagenes/" + nomProducto + ".png";
                imgx=30;imgy=150;imgdx=180;imgdy=100;
            }
            msj2=new JLabel(porcentaje);
            msj2.setBounds(175, 75, 60, 30);
            msj2.setFont(new Font("Verdana",Font.BOLD,16));
            
            msj3=new JLabel(mensaje);
            msj3.setBounds(x,y,dx,dy);
            msj3.setFont(fondo1);

            URL ruta=this.getClass().getResource(directorio);
            icono=new JLabel(new ImageIcon(ruta));
            icono.setBounds(imgx,imgy,imgdx,imgdy);

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


package Principal;

import InterfaceGrafica.VentanaConsultaDNI;
import InterfaceGraficaConsulta.VentanaConsultaClientes;
import InterfaceGraficaConsulta.VentanaConsultas;
import InterfaceGraficaRegistro.VentanaRegistros;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaProcesos extends JFrame{
    
    private JPanel lamina;
    private JLabel icono,titulo;
    private JButton btnRegistrar,btnConsultar,btnImprimir;
    
    public VentanaProcesos(){
        
        setTitle("Procesos");
        setSize(500,300);
        setResizable(false);
        setLocationRelativeTo(null);

        inicioComponentes();
    }
    
    private void inicioComponentes(){
        
        setLayout(new GridLayout(1,2));
        Color ColorFuente=new Color(232,44,12);
        Font fuenteCamposLabel=new Font("Decker", Font.BOLD, 16);
        Font fuenteTitulo=new Font("Decker", Font.BOLD, 14);
        
        lamina=new JPanel();
        lamina.setLayout(null);
        
        titulo=new JLabel("MANTENIMIENTO DEL SISTEMA");
        titulo.setBounds(25,20,240,30);
        titulo.setForeground(ColorFuente);
        titulo.setFont(fuenteTitulo);
        
        btnRegistrar=new JButton("Registrar");
        btnRegistrar.setBounds(25,70,200,30);
        btnRegistrar.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,btnRegistrar));
        btnRegistrar.setBackground(null);
        btnRegistrar.setForeground(ColorFuente);
        btnRegistrar.setFont(fuenteCamposLabel);
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
                VentanaRegistros ventana=new VentanaRegistros();
                ventana.setVisible(true);
                ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        
        btnConsultar=new JButton("Consultar");
        btnConsultar.setBounds(25,110,200,30);
        btnConsultar.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,btnConsultar));
        btnConsultar.setBackground(null);
        btnConsultar.setForeground(ColorFuente);
        btnConsultar.setFont(fuenteCamposLabel);
        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
                VentanaConsultas ventana=new VentanaConsultas();
                ventana.setVisible(true);
                ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        
        btnImprimir=new JButton("Imprimir Cupón");
        btnImprimir.setBounds(25,150,200,30);
        btnImprimir.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,btnImprimir));
        btnImprimir.setBackground(null);
        btnImprimir.setForeground(ColorFuente);
        btnImprimir.setFont(fuenteCamposLabel);
        btnImprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                dispose();
                VentanaConsultaDNI ventana=new VentanaConsultaDNI();
                ventana.setVisible(true);
                ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        
        lamina.add(btnRegistrar);
        lamina.add(btnConsultar);
        lamina.add(btnImprimir);
        lamina.add(titulo);
        lamina.setBackground(Color.LIGHT_GRAY.brighter());
        add(lamina);
        
        icono=new JLabel(new ImageIcon("src/imagenes/Iconocafeteria.png"));
        icono.setOpaque(true);
        icono.setBackground(Color.LIGHT_GRAY.brighter());
        add(icono);
        
    }
    
    private class ColorBotones extends MouseAdapter
    {
        private Color colorFondo,colorLetra;
        private JButton boton;
        
        public ColorBotones(Color colorFondo,Color colorLetra,JButton boton)
        {
            this.colorFondo=colorFondo;
            this.colorLetra=colorLetra;
            this.boton=boton;
        }
        
        @Override
        public void mouseEntered(MouseEvent e)
        {
            this.boton.setBackground(colorFondo);
            this.boton.setForeground(colorLetra);
        }
        
        @Override
        public void mouseExited(MouseEvent e)
        {
            this.boton.setBackground(null);
            this.boton.setForeground(colorFondo);
            
        }
    }
}

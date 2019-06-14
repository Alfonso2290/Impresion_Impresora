package Principal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaPrincipal extends JFrame{
    
    private JPanel miPanel;
    private JLabel mensaje1,mensaje2,mensaje3;
    private JButton ingresar;
    
    public VentanaPrincipal(){
        
        setTitle("Ingreso al Sistemas");
        setSize(200,200);
        setResizable(false);
        setLocationRelativeTo(null);
        inicioComponentes();
    }
    
    private void inicioComponentes(){
        
        setLayout(new BorderLayout());
        Color ColorFuente=new Color(232,44,12);
        Font fuenteCamposLabel=new Font("Decker", Font.BOLD, 16);
        
        miPanel=new JPanel();
        miPanel.setLayout(null);
        miPanel.setBackground(Color.LIGHT_GRAY.brighter());
        
        mensaje1=new JLabel("BIENVENIDOS");
        mensaje1.setFont(new Font("Decker",Font.BOLD,16));
        mensaje1.setBounds(45,30,110,20);
        mensaje1.setForeground(ColorFuente);
        
        mensaje2=new JLabel("AL");
        mensaje2.setFont(new Font("Decker",Font.BOLD,16));
        mensaje2.setBounds(75,55,50,20);
        mensaje2.setForeground(ColorFuente);
        
        mensaje3=new JLabel("SISTEMA");
        mensaje3.setFont(new Font("Decker",Font.BOLD,16));
        mensaje3.setBounds(60,80,100,20);
        mensaje3.setForeground(ColorFuente);
        
        ingresar=new JButton("Ingresar");
        ingresar.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,ingresar));
        ingresar.setBackground(null);
        ingresar.setForeground(ColorFuente);
        ingresar.setFont(fuenteCamposLabel);
        ingresar.addKeyListener(new cambioCampo());
        ingresar.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e){
                eventoIngreso();
            }
            
        });
        
        add(ingresar,BorderLayout.SOUTH);
        miPanel.add(mensaje1);
        miPanel.add(mensaje2);
        miPanel.add(mensaje3);
        add(miPanel);
    }
    
    private class cambioCampo extends KeyAdapter{
        
        @Override
        public void keyPressed(KeyEvent e)
        {
          
            if(e.VK_ENTER==e.getKeyCode())
                eventoIngreso();
        }
    }
    
    private void eventoIngreso(){
        
        dispose();
        VentanaProcesos procesos=new VentanaProcesos();
        procesos.setVisible(true);
        procesos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

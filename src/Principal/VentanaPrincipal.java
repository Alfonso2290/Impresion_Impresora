package Principal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class VentanaPrincipal extends JFrame{
    
    private JPanel miPanel;
    private JLabel icono;
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
        miPanel.setBackground(new Color(215,215,215));
        
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
        
        URL ruta=this.getClass().getResource("/imagenes/logo.jpg");
        icono=new JLabel(new ImageIcon(ruta));
        icono.setBounds(0,0,200,150);
        
        add(ingresar,BorderLayout.SOUTH);
        miPanel.add(icono);
        add(miPanel,BorderLayout.CENTER);
        
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

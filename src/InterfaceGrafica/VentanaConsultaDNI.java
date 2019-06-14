package InterfaceGrafica;

import BEAN.ClienteBEAN;
import BEAN.ProductoBEAN;
import DAO.ClienteDAO;
import DAO.ProductoDAO;
import Principal.VentanaProcesos;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class VentanaConsultaDNI extends JFrame {

    private JButton btnContinuar,btnAtras;
    private JLabel dni,titulo;
    private JTextField txtDni;
    private JPanel panel;
    private String dniCliente;
    private JSeparator h1;
    
    public VentanaConsultaDNI(){
        
        setTitle("Ingreso");
        setSize(260,230);
        setLocationRelativeTo(null);

        inicioComponentes();
    }
    
    private void inicioComponentes(){

        panel=new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.LIGHT_GRAY.brighter());
        
        Color ColorFuente=new Color(232,44,12);
        Font fuenteCampos=new Font("Decker", Font.PLAIN, 14);
        Font fuenteCamposLabel=new Font("Decker", Font.BOLD, 16);
        Font fuenteTitulo=new Font("Decker", Font.BOLD, 20);
        
        titulo=new JLabel("Ingreso de Cliente");
        titulo.setBounds(30,20,200,30);
        titulo.setFont(fuenteTitulo);
        titulo.setForeground(ColorFuente);
        
        dni=new JLabel("DNI:");
        dni.setBounds(50,70,40,20);
        dni.setFont(fuenteCamposLabel);
        dni.setForeground(ColorFuente);
        
        txtDni=new JTextField();
        txtDni.setBounds(50,95,150,20);
        txtDni.addKeyListener(new validarCampos());
        txtDni.setFont(fuenteCampos);
        txtDni.setBorder(null);
        txtDni.addKeyListener(new cambioCampo());
        
        h1=new JSeparator();
        h1.setBounds(50,115,150,20);
        h1.setOpaque(false);
        h1.setBackground(Color.gray);
       
        btnContinuar=new JButton("Continuar");
        btnContinuar.setBounds(115,150,120,30);
        btnContinuar.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,btnContinuar));
        btnContinuar.setBackground(null);
        btnContinuar.setForeground(ColorFuente);
        btnContinuar.setFont(fuenteCamposLabel);
        btnContinuar.addKeyListener(new cambioCampo());
        btnContinuar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                eventoIngreso();
            }
        });
        
        btnAtras=new JButton("Atras",new ImageIcon("src/imagenes/atras.png"));
        btnAtras.setBounds(10,150,100,30);
        btnAtras.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,btnAtras));
        btnAtras.setBackground(null);
        btnAtras.setForeground(ColorFuente);
        btnAtras.setFont(fuenteCamposLabel);
        btnAtras.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
                VentanaProcesos ventana=new VentanaProcesos();
                ventana.setVisible(true);
                ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        
        panel.add(txtDni);
        panel.add(dni);
        panel.add(h1);
        panel.add(btnContinuar);
        panel.add(btnAtras);
        panel.add(titulo);
        add(panel);
    }
    
    private void eventoIngreso(){
        if(txtDni.getText().length()<8){
           JOptionPane.showMessageDialog(null, "Usted debe ingresar el DNI del cliente");
           txtDni.requestFocus();
        }else{
            dniCliente=txtDni.getText();
            ClienteBEAN cliente=new ClienteBEAN();
            cliente.setDni(dniCliente);
            ProductoDAO productoDAO=new ProductoDAO();
            ArrayList<ProductoBEAN> lista;
            String nombreProducto=productoDAO.getNombreProductoMasConsumido(cliente);
            String codigoProducto=productoDAO.getCodigoProductoMasConsumido(nombreProducto);
            lista=productoDAO.getProductoMasConsumido(codigoProducto,nombreProducto);
            dispose();
            Ventana miVentana=new Ventana(lista);
            miVentana.setVisible(true);
            miVentana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }
    
    private class validarCampos extends KeyAdapter
    {
        @Override
        public void keyTyped(KeyEvent e)
        {
            char teclaPresionada=e.getKeyChar();
            if(e.getSource()==txtDni)
            {
                if(teclaPresionada<'0' || teclaPresionada>'9')
                    e.consume(); 
                if (txtDni.getText().length()== 8) 
                    e.consume();
                
            }
        }
    }
    
    private class cambioCampo extends KeyAdapter{
        
        @Override
        public void keyPressed(KeyEvent e)
        {
            if(e.getSource()==txtDni)
            {
                if(e.VK_ENTER==e.getKeyCode())
                    btnContinuar.requestFocus();
            }
            
            if(e.getSource()==btnContinuar)
            {
                if(e.VK_ENTER==e.getKeyCode())
                    eventoIngreso();
            }
        }
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

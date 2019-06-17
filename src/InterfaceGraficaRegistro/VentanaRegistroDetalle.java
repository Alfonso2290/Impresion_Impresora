
package InterfaceGraficaRegistro;

import BEAN.ProductoBEAN;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class VentanaRegistroDetalle extends JFrame{
    
    private ProductoBEAN productoParametro;
    private JLabel imagen,codigo,nombre,pv,stock,cantidad,titulo;
    private JTextField txtCantidad;
    private JLabel txtCodigo,txtNombre,txtPv,txtStock;
    private JPanel lamina;
    private DecimalFormat formato;
    private JButton btnFinalizar;
    private JSeparator h1;
    private String dni;
    
    public VentanaRegistroDetalle(ProductoBEAN productoParametro,String dni){
        this.productoParametro=productoParametro;
        this.dni=dni;
        setTitle("Selección de Producto");
        setSize(400,400);
        setLocationRelativeTo(null);
        setResizable(false);
        Inicio();
    }
    
    private void Inicio(){
        
        setLayout(new BorderLayout());
        
        lamina=new JPanel();
        lamina.setLayout(null);
        lamina.setBackground(Color.LIGHT_GRAY.brighter());
        
        formato=new DecimalFormat("##.##");
        Color ColorFuente=new Color(232,44,12);
        Font fuenteTitulo=new Font("Decker", Font.BOLD, 20);
        Font fuenteCampos=new Font("Decker", Font.PLAIN, 14);
        Font fuenteCamposLabel=new Font("Decker", Font.BOLD, 16);
        Font fuenteMensaje=new Font("Decker",Font.PLAIN,12);
        Font fuenteCamposboton=new Font("Decker", Font.PLAIN, 14);
        
        titulo=new JLabel("SELECCIÓN PRODUCTO");
        titulo.setBounds(85,10,250,30);
        titulo.setFont(fuenteTitulo);
        titulo.setForeground(ColorFuente);
        
        imagen=new JLabel();
        imagen.setBounds(135,70,150,100);
        imagen.setFont(fuenteCamposboton);
        imagen.setForeground(ColorFuente);
        
        codigo=new JLabel("Código: ");
        codigo.setBounds(80,190,100,20);
        codigo.setFont(fuenteCamposboton);
        codigo.setForeground(ColorFuente);
        
        txtCodigo=new JLabel();
        txtCodigo.setBounds(230,190,200,20);
        txtCodigo.setFont(fuenteCamposboton);
        
        nombre=new JLabel("Nombre: ");
        nombre.setBounds(80,215,100,20);
        nombre.setFont(fuenteCamposboton);
        nombre.setForeground(ColorFuente);
        
        txtNombre=new JLabel();
        txtNombre.setBounds(230,215,100,20);
        txtNombre.setFont(fuenteCamposboton);
        
        pv=new JLabel("Precio de Venta: ");
        pv.setBounds(80,240,150,20);
        pv.setFont(fuenteCamposboton);
        pv.setForeground(ColorFuente);
        
        txtPv=new JLabel();
        txtPv.setBounds(230,240,200,20);
        txtPv.setFont(fuenteCamposboton);
        
        stock=new JLabel("Stock: ");
        stock.setBounds(80,265,100,20);
        stock.setFont(fuenteCamposboton);
        stock.setForeground(ColorFuente);
        
        txtStock=new JLabel();
        txtStock.setBounds(230,265,200,20);
        txtStock.setFont(fuenteCamposboton);
        
        cantidad=new JLabel("Cantidad: ");
        cantidad.setBounds(80,290,100,20);
        cantidad.setFont(fuenteCamposboton);
        cantidad.setForeground(ColorFuente);
        
        txtCantidad=new JTextField();
        txtCantidad.setBounds(230,290,100,20);
        txtCantidad.setFont(fuenteCampos);
        txtCantidad.addKeyListener(new validarCampos());
        txtCantidad.setBorder(null);
        
        h1=new JSeparator();
        h1.setBounds(230,310,100,20);
        h1.setOpaque(false);
        h1.setBackground(Color.gray);
        
        llenarCampos();
        
        lamina.add(titulo);
        lamina.add(codigo);
        lamina.add(txtCodigo);
        lamina.add(nombre);
        lamina.add(txtNombre);
        lamina.add(pv);
        lamina.add(txtPv);
        lamina.add(stock);
        lamina.add(txtStock);
        lamina.add(imagen);
        lamina.add(cantidad);
        lamina.add(txtCantidad);
        lamina.add(h1);
        add(lamina,BorderLayout.CENTER);
        
        btnFinalizar=new JButton("Comprar");
        btnFinalizar.setBounds(650,320,300,30);
        btnFinalizar.setFont(fuenteCamposLabel);
        btnFinalizar.setBackground(null);
        btnFinalizar.setForeground(ColorFuente);
        btnFinalizar.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,btnFinalizar));
        btnFinalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
            }
        });
        
        add(btnFinalizar,BorderLayout.SOUTH);
    }
    
    private class validarCampos extends KeyAdapter
    {
        @Override
        public void keyTyped(KeyEvent e)
        {
            char teclaPresionada=e.getKeyChar();
            if(e.getSource()==txtCantidad)
            {
                if(teclaPresionada<'0' || teclaPresionada>'9')
                    e.consume();
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
    
    public void llenarCampos(){
        
        if(productoParametro!=null){
            txtCodigo.setText(productoParametro.getCodProducto());
            txtNombre.setText(productoParametro.getNombre());
            txtPv.setText("S/." + formato.format(productoParametro.getPrecioVenta()) + "");
            txtStock.setText(productoParametro.getCantidad() + "");
            
            try{
                byte[] bi = productoParametro.getImagen();
                InputStream is=new ByteArrayInputStream(bi);
                BufferedImage image=ImageIO.read(is);
                ImageIcon foto=new ImageIcon(bi);

                Image img=foto.getImage();
                Image newimg=img.getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_DEFAULT);

                ImageIcon newicon=new ImageIcon(newimg);
                imagen.setIcon(newicon);                
            }catch(Exception ex){}
                
        }
    }
    
}

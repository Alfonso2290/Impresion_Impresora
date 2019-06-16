
package InterfaceGraficaRegistro;

import BEAN.ProductoBEAN;
import DAO.ProductoDAO;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class PanelRegistroVentas extends JPanel  
{
    private JLabel mensaje,titulo,ticket,dni,montoTotal,igv,montoNeto,mensaje2;
    private JTextField txtNumTicket,txtDni,txtModIGV;
    private JLabel txtMontoTotal,txtIGV,txtMontoNeto,editar,titulo2;
    private JButton btnGuardar,btnCancelar,btnAtras,btnFinalizar;
    private JSeparator h1,h2,h3,h4,h5;
    private DefaultTableModel modelo;
    private JTable tabla;
    private JScrollPane scroll;
    private ArrayList<ProductoBEAN> lista;
    private DecimalFormat formato;
    private static double montoN=0,montoT=0,igvActual=18;
    private static ArrayList<ProductoBEAN> listaTabla=new ArrayList<ProductoBEAN>();
    
    public PanelRegistroVentas()
    {
        Inicio();
        txtNumTicket.requestFocus();
    }
    
    private void Inicio()
    {
        setLayout(null);
        
        formato=new DecimalFormat("##.##");
        
        Color ColorFuente=new Color(232,44,12);
        Font fuenteTitulo=new Font("Decker", Font.BOLD, 20);
        Font fuenteCampos=new Font("Decker", Font.PLAIN, 14);
        Font fuenteCamposLabel=new Font("Decker", Font.BOLD, 16);
        Font fuenteMensaje=new Font("Decker",Font.PLAIN,12);
        Font fuenteCamposboton=new Font("Decker", Font.PLAIN, 14);
        
        titulo2=new JLabel("Selección de productos");
        titulo2.setBounds(350,65,200,20);
        titulo2.setFont(fuenteCamposLabel);
        titulo2.setForeground(ColorFuente);
        
        modelo=new DefaultTableModel();
        
        tabla=new JTable();
        scroll=new JScrollPane(tabla);
        tabla.setBackground(ColorFuente.brighter().brighter());
        tabla.setForeground(Color.WHITE);
        tabla.setGridColor(ColorFuente);
        
        tabla.setFont(new Font("Arial",Font.BOLD,10));
        
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Descripción");
        modelo.addColumn("Precio Venta");
        modelo.addColumn("Stock");
        modelo.addColumn("Cantidad");
        
        tabla.setModel(modelo);
        tabla.setEnabled(true);
        
        centrarTextoTabla();

        capturarListaTabla();
        
        mensaje=new JLabel();
        mensaje.setBounds(250,150,350,50);
        mensaje.setFont(new Font("Arial",Font.BOLD,18));
        mensaje.setForeground(Color.RED);
        
        if(lista.size()!=0)
        {
            for(ProductoBEAN obj:lista)
            {
                modelo.addRow(new Object[]{obj.getCodProducto(),obj.getNombre(),
                obj.getDescripcion(),formato.format(obj.getPrecioVenta()),
                obj.getCantidad(),0});
            }

            scroll.setBounds(350,110,450,200);
            
            mensaje.setText("");
            
            add(scroll);
        }
        else
        {
            mensaje.setText("<< No tienes productos actualmente >>");
        }
        
        add(mensaje);
        
        titulo=new JLabel("REGISTRAR VENTA");
        titulo.setBounds(300,10,300,30);
        titulo.setFont(fuenteTitulo);
        titulo.setForeground(ColorFuente);
        
        ticket=new JLabel("Número de Ticket");
        ticket.setBounds(50,70,200,20);
        ticket.setFont(fuenteCamposLabel);
        ticket.setForeground(ColorFuente);
        
        txtNumTicket=new JTextField();
        txtNumTicket.setBounds(50,95,250,20);
        txtNumTicket.addKeyListener(new validarCampos());
        txtNumTicket.setFont(fuenteCampos);
        txtNumTicket.setBorder(null);
        
        h1=new JSeparator();
        h1.setBounds(50,115,250,20);
        h1.setOpaque(false);
        h1.setBackground(Color.gray);
        
        mensaje2=new JLabel();
        mensaje2.setBounds(190,70,120,20);
        mensaje2.setForeground(ColorFuente);
        mensaje2.setFont(fuenteMensaje);
        
        dni=new JLabel("DNI");
        dni.setBounds(50,130,150,20);
        dni.setFont(fuenteCamposLabel);
        dni.setForeground(ColorFuente);
        
        txtDni=new JTextField();
        txtDni.setBounds(50,155,250,20);
        txtDni.addKeyListener(new validarCampos());
        txtDni.setFont(fuenteCampos);
        txtDni.setBorder(null);
        
        h2=new JSeparator();
        h2.setBounds(50,175,250,20);
        h2.setOpaque(false);
        h2.setBackground(Color.gray);
        
        montoTotal=new JLabel("Monto Bruto");
        montoTotal.setBounds(50,190,200,20);
        montoTotal.setFont(fuenteCamposLabel);
        montoTotal.setForeground(ColorFuente);
        
        txtMontoTotal=new JLabel("S/." + montoT);
        txtMontoTotal.setBounds(50,215,250,20);
        txtMontoTotal.setFont(fuenteCampos);
        txtMontoTotal.setBorder(null);
        
        h3=new JSeparator();
        h3.setBounds(50,235,250,20);
        h3.setOpaque(false);
        h3.setBackground(Color.gray);

        igv=new JLabel("IGV");
        igv.setBounds(50,250,150,20);
        igv.setFont(fuenteCamposLabel);
        igv.setForeground(ColorFuente);
        
        txtIGV=new JLabel(igvActual + "%");
        txtIGV.setBounds(50,275,150,20);
        txtIGV.setFont(fuenteCampos);
        txtIGV.setBorder(null);
        
        editar=new JLabel("Editar");
        editar.setBounds(265,275,100,20);
        Font font = editar.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        editar.setFont(font.deriveFont(attributes));
        editar.setForeground(ColorFuente);
        editar.addMouseListener(new EventoMouse());
        
        txtModIGV=new JTextField();
        txtModIGV.setBounds(50,275,150,20);
        txtModIGV.setFont(fuenteCampos);
        txtModIGV.setBorder(null);
        txtModIGV.addKeyListener(new validarCampos());
        txtModIGV.addFocusListener(new FocusEvento());
        txtModIGV.setVisible(false);
        
        h4=new JSeparator();
        h4.setBounds(50,295,250,20);
        h4.setOpaque(false);
        h4.setBackground(Color.gray);
        
        montoNeto=new JLabel("Monto Neto");
        montoNeto.setBounds(50,310,180,20);
        montoNeto.setFont(fuenteCamposLabel);
        montoNeto.setForeground(ColorFuente);
        
        txtMontoNeto=new JLabel("S/." + montoN);
        txtMontoNeto.setBounds(50,335,250,20);
        txtMontoNeto.setFont(fuenteCampos);
        txtMontoNeto.setBorder(null);
        
        h5=new JSeparator();
        h5.setBounds(50,355,250,20);
        h5.setOpaque(false);
        h5.setBackground(Color.gray);
        
        btnGuardar=new JButton("Guardar");
        btnGuardar.setBounds(280,400,110,30);
        btnGuardar.setFont(fuenteCamposLabel);
        btnGuardar.setBackground(null);
        btnGuardar.setForeground(ColorFuente);
        btnGuardar.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,btnGuardar));
        
        btnCancelar=new JButton("Cancelar");
        btnCancelar.setBounds(405,400,120,30);
        btnCancelar.setFont(fuenteCamposLabel);
        btnCancelar.setBackground(null);
        btnCancelar.setForeground(ColorFuente);
        btnCancelar.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,btnCancelar));
        
        btnAtras=new JButton(new ImageIcon("src/imagenes/atras.png"));
        btnAtras.setBounds(10,400,30,30);
        btnAtras.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,btnAtras));
        btnAtras.setBackground(null);
        btnAtras.setForeground(ColorFuente);
        btnAtras.setFont(fuenteCamposLabel);
        
        btnFinalizar=new JButton("Finalizar");
        btnFinalizar.setBounds(600,400,120,30);
        btnFinalizar.setFont(fuenteCamposLabel);
        btnFinalizar.setBackground(null);
        btnFinalizar.setForeground(ColorFuente);
        btnFinalizar.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,btnFinalizar));
        btnFinalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Prueba();
            }
        });
        
        add(btnAtras);
        add(titulo2);
        add(montoNeto);
        add(txtMontoNeto);
        add(montoTotal);
        add(txtMontoTotal);
        add(igv);
        add(txtIGV);
        add(btnGuardar);
        add(btnCancelar);
        add(mensaje);
        add(h3);
        add(h4);
        add(h5);
        add(editar);
        add(mensaje2);
        add(dni);
        add(txtDni);
        add(ticket);
        add(txtNumTicket);
        add(txtModIGV);
        add(h1);
        add(h2);
        add(titulo);
        add(btnFinalizar);
    }

    public class EventoMouse extends MouseAdapter{
    
        public void mouseClicked(MouseEvent e){
            
            if(e.getSource()==editar){
                txtIGV.setVisible(false);
                txtModIGV.setVisible(true);
                txtModIGV.requestFocus();
            }
        }
        
    }
    
    public void Prueba(){
        
        int m=tabla.getRowCount();
        int n=tabla.getColumnCount();
        for(int i=0;i<tabla.getRowCount();i++){
            ProductoBEAN prod=new ProductoBEAN();
            for(int j=0;j<tabla.getColumnCount();j++){
                switch(j)
                {
                    case 0:prod.setCodProducto(tabla.getValueAt(i, j).toString());break;
                    case 1:prod.setNombre(tabla.getValueAt(i, j).toString());break;
                    case 2:prod.setDescripcion(tabla.getValueAt(i, j).toString());break;
                    case 3:prod.setPrecioVenta(Double.parseDouble(tabla.getValueAt(i, j).toString()));break;
                    case 4:prod.setCantidad(Integer.parseInt(tabla.getValueAt(i, j).toString()));break;
                    case 5:prod.setCantidadComprar(Integer.parseInt(tabla.getValueAt(i, j).toString()));break;
                }  
            }
            listaTabla.add(prod);
        }
        
        btnFinalizar.setEnabled(false);
        tabla.setEnabled(false);
        
        for(ProductoBEAN p:listaTabla){
            System.out.println(p);
        }
    }
    
    public class FocusEvento extends FocusAdapter{
        
        public void focusLost(FocusEvent e){
            
            if(e.getSource()==txtModIGV){
                igvActual=Double.parseDouble(txtModIGV.getText());
                txtIGV.setText(formato.format(igvActual) + "%");
                txtModIGV.setVisible(false);
                txtIGV.setVisible(true);
                txtMontoNeto.requestFocus();
            }
        }
    }
    
    public void limpiarCampos()
    {
        txtNumTicket.setText("");
        txtMontoNeto.setText("");
        txtMontoTotal.setText("");
        txtDni.setText("");
        txtIGV.setText("");
        txtNumTicket.requestFocus();
    }
    
    private class validarCampos extends KeyAdapter
    {
        @Override
        public void keyTyped(KeyEvent e)
        {
            char teclaPresionada=e.getKeyChar();
            if(e.getSource()==txtDni || e.getSource()==txtNumTicket)
            {
                if(teclaPresionada<'0' || teclaPresionada>'9')
                    e.consume();
            }
            
            if(e.getSource()==txtDni){
                if(txtDni.getText().length()==8)
                    e.consume();
            }
            
            if(e.getSource()==txtNumTicket){
                if(txtNumTicket.getText().length()==10)
                    e.consume();
            }
            
            if(e.getSource()==txtModIGV)
            {
                if((teclaPresionada<'0' || teclaPresionada>'9') && teclaPresionada!=(char)46)
                    e.consume();
            }
            
            if(e.getSource()==txtModIGV){
                
                if(verificarPuntosDecimales(txtModIGV.getText().toString())==false)
                {
                    if(teclaPresionada<'0' || teclaPresionada>'9')
                        e.consume();
                }
            }
            
        }
    }
    
    private boolean verificarPuntosDecimales(String cadena){
        int cont=0;
        for(int i=0;i<cadena.length();i++){
            if(cadena.charAt(i)=='.'){
                cont++;
            }
        }
        if(cont<1)
            return true;
        else
            return false;
    }
    
    
    private void centrarTextoTabla()
    {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        
        for(int i=0;i<tabla.getColumnCount();i++)
            tabla.getColumnModel().getColumn(i).setCellRenderer(tcr);
    }
    
    private void capturarListaTabla()
    {
        ProductoDAO productoDAO=new ProductoDAO();
        lista=productoDAO.getListaProductosDisponibles();
    }
    //para disminuir stock
    private void llenarTabla(String msj)
    {
        if(lista.size()!=0)
        {
            scroll.setVisible(true);
            limpiarTabla();
            for(ProductoBEAN obj:lista)
            {
                modelo.addRow(new Object[]{obj.getCodProducto(),obj.getNombre(),
                obj.getDescripcion(),formato.format(obj.getPrecioVenta()),
                obj.getCantidad(),obj.getEstado()});
            }
            mensaje.setBounds(250,150,350,50);
            mensaje.setText("");
        }
        else
        {
            scroll.setVisible(false);
            mensaje.setBounds(180,150,470,50);
            mensaje.setText(msj);
        }
    }
    
    public void limpiarTabla()
    {
        for(int i=0;i<tabla.getRowCount();i++)
        {
            modelo.removeRow(i);
            i--;
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




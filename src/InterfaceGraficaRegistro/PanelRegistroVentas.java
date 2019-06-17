
package InterfaceGraficaRegistro;

import BEAN.DetalleBEAN;
import BEAN.ProductoBEAN;
import DAO.ProductoDAO;
import UtilidadesDetalleVenta.GestionCeldasDetalle;
import UtilidadesDetalleVenta.GestionEncabezadoTablaDetalle;
import UtilidadesDetalleVenta.ModeloTablaDetalle;
import UtilidadesDetalleVenta.UtilidadesDetalle;
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
import javax.swing.table.JTableHeader;

public class PanelRegistroVentas extends JPanel  
{
    private JLabel mensaje,titulo,ticket,dni,montoTotal,igv,montoNeto,mensaje2;
    private JTextField txtNumTicket,txtDni,txtModIGV;
    private JLabel txtMontoTotal,txtIGV,txtMontoNeto,editar,titulo2,mensaje3;
    private JButton btnGuardar,btnCancelar,btnAtras,btnFinalizar;
    private JSeparator h1,h2,h3,h4,h5;
    private DefaultTableModel modelo;
    private JTable tabla;
    private JScrollPane scroll;
    private ArrayList<ProductoBEAN> lista;
    private DecimalFormat formato;
    public static double montoN=0,montoT=0,igvActual=18;
    public static ArrayList<DetalleBEAN> listaGuardar;
    private int filasTabla;
    private int columnasTabla;
    
    public PanelRegistroVentas()
    {
        listaGuardar=new ArrayList<DetalleBEAN>();
        setBorder(new EmptyBorder(5, 5, 5, 5));
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
        
        scroll=new JScrollPane();
        tabla=new JTable();
        tabla.setBackground(Color.WHITE);
        tabla.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        tabla.addMouseListener(new EventoEliminar());
        tabla.setOpaque(false);
        scroll.setViewportView(tabla);

        mensaje3=new JLabel();
        mensaje3.setBounds(250,70,350,50);
        mensaje3.setFont(new Font("Arial",Font.BOLD,18));
        mensaje3.setForeground(Color.RED);
        
        capturarListaTabla();
        
        if(lista.size()!=0)
        {
            construirTabla();

            scroll.setBounds(350,110,700,200);
            
            mensaje3.setText("");
            
            add(scroll);
        }
        else
        {
            mensaje3.setText("<< No tienes productos actualmente >>");
        }
        
        add(mensaje3);
        
        titulo=new JLabel("REGISTRAR VENTA");
        titulo.setBounds(450,10,300,30);
        titulo.setFont(fuenteTitulo);
        titulo.setForeground(ColorFuente);
        
        ticket=new JLabel("Número de Ticket");
        ticket.setBounds(50,70,200,20);
        ticket.setFont(fuenteCamposLabel);
        ticket.setForeground(ColorFuente);
        
        mensaje=new JLabel();
        mensaje.setBounds(210,70,120,20);
        mensaje.setForeground(ColorFuente);
        mensaje.setFont(fuenteMensaje);
        
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
        mensaje2.setBounds(210,130,120,20);
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
        btnGuardar.setBounds(430,400,110,30);
        btnGuardar.setFont(fuenteCamposLabel);
        btnGuardar.setBackground(null);
        btnGuardar.setForeground(ColorFuente);
        btnGuardar.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,btnGuardar));
        btnGuardar.setEnabled(false);
        
        btnCancelar=new JButton("Cancelar");
        btnCancelar.setBounds(555,400,120,30);
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
        btnFinalizar.setBounds(650,320,120,30);
        btnFinalizar.setFont(fuenteCamposLabel);
        btnFinalizar.setBackground(null);
        btnFinalizar.setForeground(ColorFuente);
        btnFinalizar.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,btnFinalizar));
        btnFinalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                finalizarCompra();
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
        add(mensaje);
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
    
    private void finalizarCompra(){
        btnGuardar.setEnabled(true);
        tabla.setEnabled(false);
        btnFinalizar.setEnabled(false);
        for(DetalleBEAN obj: listaGuardar){
            montoT+=obj.getMontoSubtotal();
        }
        montoN=montoT + (montoT*igvActual/100);
        
        txtMontoTotal.setText("S/." + montoT);
        txtMontoNeto.setText("S/." + montoN);
    }
    
    public class FocusEvento extends FocusAdapter{
        
        public void focusLost(FocusEvent e){
            
            if(e.getSource()==txtModIGV){
                igvActual=Double.parseDouble(txtModIGV.getText());
                txtIGV.setText(formato.format(igvActual) + "%");
                txtModIGV.setVisible(false);
                txtIGV.setVisible(true);
                txtMontoNeto.requestFocus();
                
                igvActual=Double.parseDouble(txtModIGV.getText());
                montoN=montoT + (montoT*igvActual/100);
        
                txtMontoTotal.setText("S/." + montoT);
                txtMontoNeto.setText("S/." + montoN);
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
    
    private void capturarListaTabla()
    {
        ProductoDAO productoDAO=new ProductoDAO();
        lista=productoDAO.getListaProductosDisponibles();
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

    public JLabel getMensaje() {
        return mensaje;
    }

    public JLabel getMensaje2() {
        return mensaje2;
    }
    
    public JTextField getTxtNumTicket() {
        return txtNumTicket;
    }

    public JTextField getTxtDni() {
        return txtDni;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JButton getBtnAtras() {
        return btnAtras;
    }

    public static ArrayList<DetalleBEAN> getListaGuardar() {
        return listaGuardar;
    }
    
    public static double getMontoN() {
        return montoN;
    }

    public static double getMontoT() {
        return montoT;
    }

    public static double getIgvActual() {
        return igvActual;
    }
    
    
    
    private void construirTabla() {
        
        ArrayList<String> titulosList=new ArrayList<>();
        
        titulosList.add(" ");
        titulosList.add("Código");
        titulosList.add("Nombre");
        titulosList.add("Descripción");
        titulosList.add("Precio Venta");
        titulosList.add("Stock");
        titulosList.add("Cantidad");

        String titulos[] = new String[titulosList.size()];
        for (int i = 0; i < titulos.length; i++) {
            titulos[i]=titulosList.get(i);
        }
    
        Object[][] data =obtenerMatrizDatos(titulosList);
        construirTabla(titulos,data);
    }

    private Object[][] obtenerMatrizDatos(ArrayList<String> titulosList) {

        String informacion[][] = new String[lista.size()][titulosList.size()];

        for (int x = 0; x < informacion.length; x++) {

            informacion[x][UtilidadesDetalle.CARRITO] = "CARRITO";
            informacion[x][UtilidadesDetalle.CODIGO] = lista.get(x).getCodProducto()+ "";
            informacion[x][UtilidadesDetalle.NOMBRE] = lista.get(x).getNombre()+ "";
            informacion[x][UtilidadesDetalle.DESCRIPCION] = lista.get(x).getDescripcion()+ "";
            informacion[x][UtilidadesDetalle.PRECIO_VENTA] = lista.get(x).getPrecioVenta()+ "";
            informacion[x][UtilidadesDetalle.STOCK] = lista.get(x).getCantidad() + "";
            informacion[x][UtilidadesDetalle.CANTIDAD] = lista.get(x).getCantidadComprar() + "";
        }

        return informacion;
    }

    private void construirTabla(String[] titulos, Object[][] data) {
      
        modelo=new ModeloTablaDetalle(data, titulos);
        tabla.setModel(modelo);
        
        filasTabla=tabla.getRowCount();
        columnasTabla=tabla.getColumnCount();

        tabla.getColumnModel().getColumn(UtilidadesDetalle.CARRITO).setCellRenderer(new GestionCeldasDetalle("icono"));
        tabla.getColumnModel().getColumn(UtilidadesDetalle.CODIGO).setCellRenderer(new GestionCeldasDetalle("texto"));
        tabla.getColumnModel().getColumn(UtilidadesDetalle.NOMBRE).setCellRenderer(new GestionCeldasDetalle("texto"));
        tabla.getColumnModel().getColumn(UtilidadesDetalle.DESCRIPCION).setCellRenderer(new GestionCeldasDetalle("texto"));
        tabla.getColumnModel().getColumn(UtilidadesDetalle.PRECIO_VENTA).setCellRenderer(new GestionCeldasDetalle("numerico"));
        tabla.getColumnModel().getColumn(UtilidadesDetalle.STOCK).setCellRenderer(new GestionCeldasDetalle("numerico"));
        tabla.getColumnModel().getColumn(UtilidadesDetalle.CANTIDAD).setCellRenderer(new GestionCeldasDetalle("numerico"));

        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setRowHeight(25);
        tabla.setGridColor(new java.awt.Color(0, 0, 0)); 
        
        tabla.getColumnModel().getColumn(UtilidadesDetalle.CARRITO).setPreferredWidth(30);
        tabla.getColumnModel().getColumn(UtilidadesDetalle.CODIGO).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(UtilidadesDetalle.NOMBRE).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(UtilidadesDetalle.DESCRIPCION).setPreferredWidth(350);
        tabla.getColumnModel().getColumn(UtilidadesDetalle.PRECIO_VENTA).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(UtilidadesDetalle.STOCK).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(UtilidadesDetalle.CANTIDAD).setPreferredWidth(130);

        JTableHeader jtableHeader = tabla.getTableHeader();
        jtableHeader.setDefaultRenderer(new GestionEncabezadoTablaDetalle());
        tabla.setTableHeader(jtableHeader);

        scroll.setViewportView(tabla);
     }
    
    public class EventoEliminar extends MouseAdapter{
        
        public void mouseClicked(MouseEvent e){
            int fila=tabla.rowAtPoint(e.getPoint());
            int columna=tabla.columnAtPoint(e.getPoint());
            if(columna==UtilidadesDetalle.CARRITO){
                SeleccionProducto(fila);
            }
        }
        
        public void SeleccionProducto(int fila){
            
            if(btnFinalizar.isEnabled()){
                double precioUnitario,subtotal;
                int stock,nuevo_stock;
                String codProducto;
                stock=Integer.parseInt(tabla.getValueAt(fila, UtilidadesDetalle.STOCK).toString());
                int cantidad=Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad a comprar"));

                if(stock>=cantidad){
                    precioUnitario=Double.parseDouble(tabla.getValueAt(fila, UtilidadesDetalle.PRECIO_VENTA).toString());
                    subtotal=cantidad*precioUnitario;
                    codProducto=tabla.getValueAt(fila, UtilidadesDetalle.CODIGO).toString();
                    nuevo_stock=stock-cantidad;

                    DetalleBEAN bean=new DetalleBEAN();
                    bean.setCantidad(cantidad);
                    bean.setMontoSubtotal(subtotal);
                    bean.setCodProducto(codProducto);
                    bean.setStock(nuevo_stock);

                    listaGuardar.add(bean);

                    tabla.setValueAt(nuevo_stock + "", fila, UtilidadesDetalle.STOCK);
                    tabla.setValueAt(cantidad + "", fila , UtilidadesDetalle.CANTIDAD);

                }else{
                    JOptionPane.showMessageDialog(null, "La cantidad solicitada sobrepasa el Stock");
                }
            }
        }
        
    }
}




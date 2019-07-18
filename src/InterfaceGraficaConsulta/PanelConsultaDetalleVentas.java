
package InterfaceGraficaConsulta;

import BEAN.DetalleBEAN;
import DAO.DetalleDAO;
import UtilidadesDetalle.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.event.*;
import java.net.URL;
import java.text.DecimalFormat;
import javax.swing.border.*;

public class PanelConsultaDetalleVentas extends JPanel
{
    private DefaultTableModel modelo;
    private JTable tabla;
    private JScrollPane scroll;
    private JButton btnBuscar,btnAtras;
    private JLabel retornar,fechaI,fechaF,agregar,importeTotal;
    private JComboBox cbProducto;
    private ArrayList<DetalleBEAN> lista,listaProductos;
    private JLabel mensaje;
    private DecimalFormat formato;
    private int filasTabla;
    private int columnasTabla;
    private JDateChooser fechaInicio,fechaFinal;
    private double it=0.0;
    
    public PanelConsultaDetalleVentas()
    {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        Inicio();
    }

    private void Inicio()
    {
        setLayout(null);
        
        formato=new DecimalFormat("##.##");
        
        Color ColorFuente=new Color(232,44,12);
        Font fuenteCamposLabel=new Font("Decker", Font.BOLD, 16);
        Font fuenteCampos=new Font("Decker", Font.PLAIN, 14);
        capturarListas();
        
        fechaI=new JLabel("F. Inicio");
        fechaI.setBounds(60, 30, 80, 30);
        fechaI.setFont(fuenteCamposLabel);
        fechaI.setForeground(ColorFuente);
        
        fechaInicio=new JDateChooser();
        fechaInicio.setBounds(140, 30, 100, 30);
        
        fechaF=new JLabel("F. Final");
        fechaF.setBounds(260, 30, 80, 30);
        fechaF.setFont(fuenteCamposLabel);
        fechaF.setForeground(ColorFuente);
        
        fechaFinal=new JDateChooser();
        fechaFinal.setBounds(340, 30, 100, 30);
        
        cbProducto=new JComboBox();
        llenarComboBox();
        cbProducto.setBounds(460, 30, 200, 30);
        cbProducto.setFont(fuenteCampos);
        
        btnBuscar=new JButton("Buscar");
        btnBuscar.setBounds(670, 30, 100, 30);
        btnBuscar.addActionListener(new filtrar());
        btnBuscar.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,btnBuscar));
        btnBuscar.setFont(fuenteCamposLabel);
        btnBuscar.setForeground(ColorFuente);
        btnBuscar.setBackground(null);
        
        String ruta="/imagenes/retornar.png";
        URL url=this.getClass().getResource(ruta);
        ImageIcon icono=new ImageIcon(url);
        retornar=new JLabel(icono);
        retornar.setBounds(780, 30, 30, 30);
        retornar.addMouseListener(new AccionMouse());
        
        scroll=new JScrollPane();
        tabla=new JTable();
        tabla.setBackground(Color.WHITE);
        tabla.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        tabla.setOpaque(false);
        scroll.setViewportView(tabla);

        mensaje=new JLabel();
        mensaje.setBounds(250,150,350,50);
        mensaje.setFont(new Font("Arial",Font.BOLD,18));
        mensaje.setForeground(Color.RED);
        
        String ruta2="/imagenes/agregar.png";
        URL path=this.getClass().getResource(ruta2);
        ImageIcon icono2=new ImageIcon(path);
        agregar=new JLabel(icono2);
        agregar.setBounds(680, 343, 30, 30);
        agregar.addMouseListener(new AccionMouse());
        
        importeTotal=new JLabel("S/." + it);
        importeTotal.setBounds(720, 343, 200, 30);
        importeTotal.setFont(fuenteCamposLabel);
        importeTotal.setForeground(ColorFuente);
        importeTotal.setVisible(false);
        
        capturarListaTabla();
        
        if(lista.size()!=0)
        {
            construirTabla();

            scroll.setBounds(10,80,800,250);
            
            mensaje.setText("");
            
            add(cbProducto);
            add(fechaInicio);
            add(fechaFinal);
            add(fechaI);
            add(fechaF);
            add(agregar);
            add(importeTotal);
            add(btnBuscar);
            add(retornar);
            add(scroll);
        }
        else
        {
            mensaje.setText("<< No tienes ventas actualmente >>");
        }
        
        add(mensaje);
        
        
        btnAtras=new JButton(new ImageIcon("src/imagenes/atras.png"));
        btnAtras.setBounds(10,353,30,20);
        btnAtras.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,btnAtras));
        btnAtras.setBackground(null);
        btnAtras.setForeground(ColorFuente);
        btnAtras.setFont(fuenteCamposLabel);
        
        add(btnAtras);
    }
    
    private void capturarListas()
    {
        DetalleDAO detalleDAO=new DetalleDAO();
        listaProductos=detalleDAO.getListaNombreProductos();
    }
    
    private void llenarComboBox(){
        cbProducto.addItem("-Seleccionar Producto-");
        for(DetalleBEAN obj: listaProductos)
        {
            cbProducto.addItem(obj.getNombreProducto());
        }
    }
    
    private void capturarListaTabla()
    {
        DetalleDAO detDAO=new DetalleDAO();
        lista=detDAO.getListaDetalleVentas();
    }
    
    private class filtrar implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String nombreProducto=cbProducto.getSelectedItem().toString();
            
            DetalleBEAN detalle=new DetalleBEAN();
            detalle.setNombreProducto(nombreProducto);
            
            if(fechaInicio.getCalendar()!=null){
                int diaI=fechaInicio.getCalendar().get(Calendar.DAY_OF_MONTH);
                int mesI=fechaInicio.getCalendar().get(Calendar.MONTH) + 1;
                int añoI=fechaInicio.getCalendar().get(Calendar.YEAR);
                String fechaInicioFiltro=diaI + "-" + mesI + "-" + añoI;
                detalle.setFechaInicio(fechaInicioFiltro);
            }
            
            if(fechaFinal.getCalendar()!=null){
                int diaF=fechaFinal.getCalendar().get(Calendar.DAY_OF_MONTH);
                int mesF=fechaFinal.getCalendar().get(Calendar.MONTH) + 1;
                int añoF=fechaFinal.getCalendar().get(Calendar.YEAR);
                String fechaFinalFiltro=diaF + "-" + mesF + "-" + añoF;
                detalle.setFechaFinal(fechaFinalFiltro);
            }
            
            DetalleDAO detD=new DetalleDAO();
            
            if(e.getSource()==btnBuscar)
            {
                if(nombreProducto.equals("-Seleccionar Producto-")==false && fechaInicio.getCalendar()==null && fechaFinal.getCalendar()==null)
                {
                    capturarListas();
                    vaciarComboBox();
                    llenarComboBox();
                    lista=detD.getListaDetalleVentasFiltroNombreProducto(detalle);
                    llenarTabla("");
                }
                else if(nombreProducto.equals("-Seleccionar Producto-") && fechaInicio.getCalendar()!=null && fechaFinal.getCalendar()==null){
                    capturarListas();
                    vaciarComboBox();
                    llenarComboBox();
                    lista=detD.getListaDetalleVentasFiltroFechaInicio(detalle);
                    llenarTabla("");
                }else if(nombreProducto.equals("-Seleccionar Producto-") && fechaInicio.getCalendar()==null && fechaFinal.getCalendar()!=null){
                    capturarListas();
                    vaciarComboBox();
                    llenarComboBox();
                    lista=detD.getListaDetalleVentasFiltroFechaFinal(detalle);
                    llenarTabla("");
                }else if(nombreProducto.equals("-Seleccionar Producto-")==false && fechaInicio.getCalendar()!=null && fechaFinal.getCalendar()==null){
                    capturarListas();
                    vaciarComboBox();
                    llenarComboBox();
                    lista=detD.getListaDetalleVentasFiltroNombreProductoFechaInicio(detalle);
                    llenarTabla("");
                }else if(nombreProducto.equals("-Seleccionar Producto-")==false && fechaInicio.getCalendar()==null && fechaFinal.getCalendar()!=null){
                    capturarListas();
                    vaciarComboBox();
                    llenarComboBox();
                    lista=detD.getListaDetalleVentasFiltroNombreProductoFechaFinal(detalle);
                    llenarTabla("");
                }else if(nombreProducto.equals("-Seleccionar Producto-") && fechaInicio.getCalendar()!=null && fechaFinal.getCalendar()!=null){
                    capturarListas();
                    vaciarComboBox();
                    llenarComboBox();
                    lista=detD.getListaDetalleVentasFiltroFechaInicioFechaFinal(detalle);
                    llenarTabla("");
                }else if(nombreProducto.equals("-Seleccionar Producto-")==false && fechaInicio.getCalendar()!=null && fechaFinal.getCalendar()!=null){
                    capturarListas();
                    vaciarComboBox();
                    llenarComboBox();
                    lista=detD.getListaDetalleVentasFiltroNombreProductoFechaInicioFechaFinal(detalle);
                    llenarTabla("");
                }else
                {
                    lista=detD.getListaDetalleVentas();
                    llenarTabla("");
                    //JOptionPane.showMessageDialog(null, "Para realizar un filtro Usted debe seleccionar un Nombre y/o Distrito");
                }
                importeTotal.setVisible(false);
            }
            
        }
    }
    
    private void llenarTabla(String msj)
    {
        if(lista.size()!=0)
        {
            scroll.setVisible(true);
            limpiarTabla();
            construirTabla();
            mensaje.setBounds(250,150,350,50);
            mensaje.setText("");
        }
        else
        {
            scroll.setVisible(false);
            mensaje.setBounds(250,150,470,50);
            mensaje.setText("<< No tienes ventas del producto filtrado>>");
        }
    }
    
    private class AccionMouse extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e)
        {
            if(e.getSource()==retornar)
            {   
                capturarListas();
                vaciarComboBox();
                llenarComboBox();
                capturarListaTabla();
                llenarTabla("");
                cbProducto.setSelectedIndex(0);
                importeTotal.setVisible(false);
            }
            
            if(e.getSource()==agregar)
            {   
                if(lista.size()!=0){
                    importeTotal.setVisible(true);
                    actualizarImporteTotal();
                }
            }
        }
    }
    
    public void actualizarImporteTotal(){
        it=0.0;
        for(int i=0;i<tabla.getRowCount();i++){
            it+=Double.parseDouble(tabla.getValueAt(i,UtilidadesDetalleVenta.SUBTOTAL).toString());
        }

        importeTotal.setText("S/." + formato.format(it));    
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JComboBox getCbNombre() {
        return cbProducto;
    }

    public JButton getBtnAtras() {
        return btnAtras;
    }
    
    
 
    public void limpiarTabla()
    {
        for(int i=0;i<tabla.getRowCount();i++)
        {
            modelo.removeRow(i);
            i--;
        }
    }
    
    public void vaciarComboBox(){
        for(int i=0;i<cbProducto.getItemCount();i++){
            cbProducto.removeItemAt(i);
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
    
    private void construirTabla() {
        
        ArrayList<String> titulosList=new ArrayList<>();

        titulosList.add("Ticket");
        titulosList.add("Código");
        titulosList.add("Producto");
        titulosList.add("Stock");
        titulosList.add("Cantidad");
        titulosList.add("Precio Venta");
        titulosList.add("Importe");
        titulosList.add("Fecha");

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

            informacion[x][UtilidadesDetalleVenta.NUM_TICKET] = lista.get(x).getNumTicket()+ "";
            informacion[x][UtilidadesDetalleVenta.COD_PRODUCTO] = lista.get(x).getCodProducto()+ "";
            informacion[x][UtilidadesDetalleVenta.NOM_PRODUCTO] = lista.get(x).getNombreProducto()+ "";
            informacion[x][UtilidadesDetalleVenta.STOCK] = lista.get(x).getStock()+ "";
            informacion[x][UtilidadesDetalleVenta.CANTIDAD] = lista.get(x).getCantidad()+ "";
            informacion[x][UtilidadesDetalleVenta.PRECIO_VENTA] = lista.get(x).getPrecioVenta()+ "";
            informacion[x][UtilidadesDetalleVenta.SUBTOTAL] =lista.get(x).getMontoSubtotal()+ "";
            informacion[x][UtilidadesDetalleVenta.FECHA] = lista.get(x).getFechaRegistro()+ "";
        }

        return informacion;
    }

    private void construirTabla(String[] titulos, Object[][] data) {
      
        modelo=new ModeloTablaDetalleVenta(data, titulos);
        tabla.setModel(modelo);
        
        filasTabla=tabla.getRowCount();
        columnasTabla=tabla.getColumnCount();
        
        tabla.getColumnModel().getColumn(UtilidadesDetalleVenta.NUM_TICKET).setCellRenderer(new GestionCeldasDetalleVenta("texto"));
        tabla.getColumnModel().getColumn(UtilidadesDetalleVenta.COD_PRODUCTO).setCellRenderer(new GestionCeldasDetalleVenta("texto"));
        tabla.getColumnModel().getColumn(UtilidadesDetalleVenta.NOM_PRODUCTO).setCellRenderer(new GestionCeldasDetalleVenta("texto"));
        tabla.getColumnModel().getColumn(UtilidadesDetalleVenta.STOCK).setCellRenderer(new GestionCeldasDetalleVenta("numerico"));
        tabla.getColumnModel().getColumn(UtilidadesDetalleVenta.CANTIDAD).setCellRenderer(new GestionCeldasDetalleVenta("numerico"));
        tabla.getColumnModel().getColumn(UtilidadesDetalleVenta.PRECIO_VENTA).setCellRenderer(new GestionCeldasDetalleVenta("numerico"));
        tabla.getColumnModel().getColumn(UtilidadesDetalleVenta.SUBTOTAL).setCellRenderer(new GestionCeldasDetalleVenta("numerico"));
        tabla.getColumnModel().getColumn(UtilidadesDetalleVenta.FECHA).setCellRenderer(new GestionCeldasDetalleVenta("texto"));
        
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setRowHeight(25);
        tabla.setGridColor(new java.awt.Color(0, 0, 0)); 
        
        tabla.getColumnModel().getColumn(UtilidadesDetalleVenta.NUM_TICKET).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(UtilidadesDetalleVenta.COD_PRODUCTO).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(UtilidadesDetalleVenta.NOM_PRODUCTO).setPreferredWidth(250);
        tabla.getColumnModel().getColumn(UtilidadesDetalleVenta.STOCK).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(UtilidadesDetalleVenta.CANTIDAD).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(UtilidadesDetalleVenta.PRECIO_VENTA).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(UtilidadesDetalleVenta.SUBTOTAL).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(UtilidadesDetalleVenta.FECHA).setPreferredWidth(150);

        JTableHeader jtableHeader = tabla.getTableHeader();
        jtableHeader.setDefaultRenderer(new GestionEncabezadoTablaDetalleVenta());
        tabla.setTableHeader(jtableHeader);

        scroll.setViewportView(tabla);
     }
    
    
}



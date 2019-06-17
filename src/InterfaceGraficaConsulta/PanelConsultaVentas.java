
package InterfaceGraficaConsulta;

import BEAN.VentaBEAN;
import DAO.VentaDAO;
import UtilidadesVenta.GestionCeldasVentas;
import UtilidadesVenta.GestionEncabezadoTablaVentas;
import UtilidadesVenta.ModeloTablaVentas;
import UtilidadesVenta.UtilidadesVentas;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.event.*;
import java.net.URL;
import java.text.DecimalFormat;
import javax.swing.border.*;

public class PanelConsultaVentas extends JPanel
{
    private DefaultTableModel modelo;
    private JTable tabla;
    private JScrollPane scroll;
    private JButton btnBuscar,btnAtras;
    private JLabel retornar;
    private JComboBox cbDni;
    private ArrayList<VentaBEAN> lista,listaDNI;
    private JLabel mensaje;
    private DecimalFormat formato;
    private int filasTabla;
    private int columnasTabla;
    
    public PanelConsultaVentas()
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

        cbDni=new JComboBox();
        llenarComboBox();
        cbDni.setBounds(460, 30, 200, 30);
        cbDni.setFont(fuenteCampos);
        
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
        tabla.addMouseListener(new EventoEliminar());
        tabla.setOpaque(false);
        scroll.setViewportView(tabla);

        mensaje=new JLabel();
        mensaje.setBounds(250,150,350,50);
        mensaje.setFont(new Font("Arial",Font.BOLD,18));
        mensaje.setForeground(Color.RED);
        
        capturarListaTabla();
        
        if(lista.size()!=0)
        {
            construirTabla();

            scroll.setBounds(10,80,800,250);
            
            mensaje.setText("");
            
            add(cbDni);
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
        btnAtras.setBounds(10,333,30,20);
        btnAtras.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,btnAtras));
        btnAtras.setBackground(null);
        btnAtras.setForeground(ColorFuente);
        btnAtras.setFont(fuenteCamposLabel);
        
        add(btnAtras);
    }
    
    private void capturarListas()
    {
        VentaDAO ventaDAO=new VentaDAO();
        listaDNI=ventaDAO.getListaDNIVentas();
    }
    
    private void llenarComboBox(){
        cbDni.addItem("-Seleccionar DNI-");
        for(VentaBEAN obj: listaDNI)
        {
            cbDni.addItem(obj.getDni());
        }
    }
    
    private void capturarListaTabla()
    {
        VentaDAO ventaDAO=new VentaDAO();
        lista=ventaDAO.getListaVentas();
    }
    
    private class filtrar implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String dni=cbDni.getSelectedItem().toString();
            
            VentaBEAN venta=new VentaBEAN();
            venta.setDni(dni);
            VentaDAO ventaD=new VentaDAO();
            
            if(e.getSource()==btnBuscar)
            {
                if(dni.equals("-Seleccionar DNI-")==false)
                {
                    capturarListas();
                    vaciarComboBox();
                    llenarComboBox();
                    lista=ventaD.listarVentasFiltroDNI(venta);
                    llenarTabla("");
                }
                else
                {
                    lista=ventaD.getListaVentas();
                    llenarTabla("");
                    //JOptionPane.showMessageDialog(null, "Para realizar un filtro Usted debe seleccionar un Nombre y/o Distrito");
                }
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
            mensaje.setBounds(180,150,470,50);
            mensaje.setText(msj);
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
                cbDni.setSelectedIndex(0);
            }
        }
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JComboBox getCbNombre() {
        return cbDni;
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
        for(int i=0;i<cbDni.getItemCount();i++){
            cbDni.removeItemAt(i);
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
        titulosList.add("DNI");
        titulosList.add("Fecha");
        titulosList.add("Monto Total");
        titulosList.add("IGV");
        titulosList.add("Monto Neto");
        titulosList.add(" ");
        titulosList.add(" ");

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

            informacion[x][UtilidadesVentas.NUMERO_TICKET] = lista.get(x).getNumTicket()+ "";
            informacion[x][UtilidadesVentas.DNI] = lista.get(x).getDni()+ "";
            informacion[x][UtilidadesVentas.FECHA_REGISTRO] = lista.get(x).getFecha()+ "";
            informacion[x][UtilidadesVentas.MONTO_TOTAL] = lista.get(x).getMontoTotal()+ "";
            informacion[x][UtilidadesVentas.IGV] = lista.get(x).getIgv()+ "";
            informacion[x][UtilidadesVentas.MONTO_NETO] = lista.get(x).getMontoNeto()+ "";
            informacion[x][UtilidadesVentas.DETALLE_VENTA] = "DETALLE";
            informacion[x][UtilidadesVentas.ELIMINAR] = "ELIMINAR";
        }

        return informacion;
    }

    private void construirTabla(String[] titulos, Object[][] data) {
      
        modelo=new ModeloTablaVentas(data, titulos);
        tabla.setModel(modelo);
        
        filasTabla=tabla.getRowCount();
        columnasTabla=tabla.getColumnCount();
        
        tabla.getColumnModel().getColumn(UtilidadesVentas.MONTO_TOTAL).setCellRenderer(new GestionCeldasVentas("numerico"));
        tabla.getColumnModel().getColumn(UtilidadesVentas.IGV).setCellRenderer(new GestionCeldasVentas("numerico"));
        tabla.getColumnModel().getColumn(UtilidadesVentas.MONTO_NETO).setCellRenderer(new GestionCeldasVentas("numerico"));
        tabla.getColumnModel().getColumn(UtilidadesVentas.DETALLE_VENTA).setCellRenderer(new GestionCeldasVentas("boton"));
        tabla.getColumnModel().getColumn(UtilidadesVentas.ELIMINAR).setCellRenderer(new GestionCeldasVentas("icono"));

        for (int i = 0; i < titulos.length-5; i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(new GestionCeldasVentas("texto"));
        }

        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setRowHeight(25);
        tabla.setGridColor(new java.awt.Color(0, 0, 0)); 
        
        tabla.getColumnModel().getColumn(UtilidadesVentas.NUMERO_TICKET).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(UtilidadesVentas.DNI).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(UtilidadesVentas.FECHA_REGISTRO).setPreferredWidth(180);
        tabla.getColumnModel().getColumn(UtilidadesVentas.MONTO_TOTAL).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(UtilidadesVentas.IGV).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(UtilidadesVentas.MONTO_NETO).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(UtilidadesVentas.DETALLE_VENTA).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(UtilidadesVentas.ELIMINAR).setPreferredWidth(30);

        JTableHeader jtableHeader = tabla.getTableHeader();
        jtableHeader.setDefaultRenderer(new GestionEncabezadoTablaVentas());
        tabla.setTableHeader(jtableHeader);

        scroll.setViewportView(tabla);
     }
    
    public class EventoEliminar extends MouseAdapter{
        
        public void mouseClicked(MouseEvent e){
            int fila=tabla.rowAtPoint(e.getPoint());
            int columna=tabla.columnAtPoint(e.getPoint());
            if(columna==UtilidadesVentas.ELIMINAR){
                EliminarRegistro(fila);
            }else if(columna==UtilidadesVentas.DETALLE_VENTA){
                MostrarDetalleVenta(fila);
            }
        }
        
        public void MostrarDetalleVenta(int fila){
            String ticket;
            ticket=tabla.getValueAt(fila,UtilidadesVentas.NUMERO_TICKET).toString();

            VentanaConsultaDetalle ventana=new VentanaConsultaDetalle(ticket);
            ventana.setVisible(true);
            
        }
        
        public void EliminarRegistro(int fila){
            String numTicket=tabla.getValueAt(fila,UtilidadesVentas.NUMERO_TICKET).toString();
            VentaBEAN venta=new VentaBEAN();
            venta.setNumTicket(numTicket);
            VentaDAO ventaDAO=new VentaDAO();
            int rpta=JOptionPane.showConfirmDialog(null,"Esta seguro que desea eliminar este registro?","Confirmación",JOptionPane.YES_NO_OPTION);
            if(rpta==JOptionPane.YES_OPTION){
                ventaDAO.eliminarVenta(venta);
                capturarListas();
                vaciarComboBox();
                llenarComboBox();
                cbDni.setSelectedIndex(0);
                capturarListaTabla();
                limpiarTabla();
                construirTabla();
            }
        }
    }
    
}


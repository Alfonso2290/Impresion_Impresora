
package InterfaceGraficaConsulta;

import BEAN.ProductoBEAN;
import DAO.ProductoDAO;
import InterfaceGraficaModificacion.VentanaModificacionProducto;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.event.*;
import java.net.URL;
import java.text.DecimalFormat;
import javax.swing.border.*;
import UtilidadesProducto.*;

public class PanelConsultaProductos extends JPanel
{
    private DefaultTableModel modelo;
    private JTable tabla;
    private JScrollPane scroll;
    private JButton btnBuscar,btnAtras;
    private JLabel retornar;
    private JComboBox cbNombre;
    private ArrayList<ProductoBEAN> lista,listaNombres;
    private JLabel mensaje;
    private DecimalFormat formato;
    private int filasTabla;
    private int columnasTabla;
    
    public PanelConsultaProductos()
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

        cbNombre=new JComboBox();
        llenarComboBox();
        cbNombre.setBounds(460, 30, 200, 30);
        cbNombre.setFont(fuenteCampos);
        
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
            
            add(cbNombre);
            add(btnBuscar);
            add(retornar);
            add(scroll);
        }
        else
        {
            mensaje.setText("<< No tienes productos actualmente >>");
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
    
    private void llenarComboBox(){
        cbNombre.addItem("-Seleccionar Producto-");
        for(ProductoBEAN obj: listaNombres)
        {
            cbNombre.addItem(obj.getNombre());
        }
    }
    
    private void capturarListas()
    {
        ProductoDAO productoDAO=new ProductoDAO();
        listaNombres=productoDAO.getListaNombreProductos();
    }
    
    private void capturarListaTabla()
    {
        ProductoDAO productoDAO=new ProductoDAO();
        lista=productoDAO.getListaProductos();
    }
    
    private class filtrar implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String nombre=cbNombre.getSelectedItem().toString();
            
            ProductoBEAN producto=new ProductoBEAN();
            producto.setNombre(nombre);
            ProductoDAO productoD=new ProductoDAO();
            
            if(e.getSource()==btnBuscar)
            {
                if(nombre.equals("-Seleccionar Producto-")==false)
                {
                    capturarListas();
                    vaciarComboBox();
                    llenarComboBox();
                    lista=productoD.listarProductosFiltroNombres(producto);
                    llenarTabla("");
                }
                else
                {
                    lista=productoD.getListaProductos();
                    System.out.println("Cantidad: " + lista.size());
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
                cbNombre.setSelectedIndex(0);
            }
        }
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JComboBox getCbNombre() {
        return cbNombre;
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
        for(int i=0;i<cbNombre.getItemCount();i++){
            cbNombre.removeItemAt(i);
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

        titulosList.add("Código");
        titulosList.add("Nombre");
        titulosList.add("Descripción");
        titulosList.add("Precio Venta");
        titulosList.add("Stock");
        titulosList.add("Estado");
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

            informacion[x][UtilidadesProductos.CODIGO_PRODUCTO] = lista.get(x).getCodProducto()+ "";
            informacion[x][UtilidadesProductos.NOMBRE_PRODUCTO] = lista.get(x).getNombre()+ "";
            informacion[x][UtilidadesProductos.DESCRIPCION_PRODUCTO] = lista.get(x).getDescripcion()+ "";
            informacion[x][UtilidadesProductos.PRECIO_VENTA] = lista.get(x).getPrecioVenta()+ "";
            informacion[x][UtilidadesProductos.CANTIDAD_STOCK] = lista.get(x).getCantidad()+ "";
            informacion[x][UtilidadesProductos.ESTADO] = lista.get(x).getEstado()+ "";
            informacion[x][UtilidadesProductos.MODIFICAR_PRODUCTO] = "MODIFICAR";
            informacion[x][UtilidadesProductos.ELIMINAR_PRODUCTO] = "ELIMINAR";
        }

        return informacion;
    }

    private void construirTabla(String[] titulos, Object[][] data) {
      
        modelo=new ModeloTablaProductos(data, titulos);
        tabla.setModel(modelo);
        
        filasTabla=tabla.getRowCount();
        columnasTabla=tabla.getColumnCount();
        
        tabla.getColumnModel().getColumn(UtilidadesProductos.PRECIO_VENTA).setCellRenderer(new GestionCeldasProductos("numerico"));
        tabla.getColumnModel().getColumn(UtilidadesProductos.CANTIDAD_STOCK).setCellRenderer(new GestionCeldasProductos("numerico"));
        tabla.getColumnModel().getColumn(UtilidadesProductos.ESTADO).setCellRenderer(new GestionCeldasProductos("numerico"));
        tabla.getColumnModel().getColumn(UtilidadesProductos.MODIFICAR_PRODUCTO).setCellRenderer(new GestionCeldasProductos("icono"));
        tabla.getColumnModel().getColumn(UtilidadesProductos.ELIMINAR_PRODUCTO).setCellRenderer(new GestionCeldasProductos("icono"));

        for (int i = 0; i < titulos.length-5; i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(new GestionCeldasProductos("texto"));
        }

        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setRowHeight(25);
        tabla.setGridColor(new java.awt.Color(0, 0, 0)); 
        
        tabla.getColumnModel().getColumn(UtilidadesProductos.CODIGO_PRODUCTO).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(UtilidadesProductos.NOMBRE_PRODUCTO).setPreferredWidth(350);
        tabla.getColumnModel().getColumn(UtilidadesProductos.DESCRIPCION_PRODUCTO).setPreferredWidth(400);
        tabla.getColumnModel().getColumn(UtilidadesProductos.PRECIO_VENTA).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(UtilidadesProductos.CANTIDAD_STOCK).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(UtilidadesProductos.ESTADO).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(UtilidadesProductos.MODIFICAR_PRODUCTO).setPreferredWidth(30);
        tabla.getColumnModel().getColumn(UtilidadesProductos.ELIMINAR_PRODUCTO).setPreferredWidth(30);

        JTableHeader jtableHeader = tabla.getTableHeader();
        jtableHeader.setDefaultRenderer(new GestionEncabezadoTablaProductos());
        tabla.setTableHeader(jtableHeader);

        scroll.setViewportView(tabla);
     }
    
    public class EventoEliminar extends MouseAdapter{
        
        public void mouseClicked(MouseEvent e){
            int fila=tabla.rowAtPoint(e.getPoint());
            int columna=tabla.columnAtPoint(e.getPoint());
            if(columna==UtilidadesProductos.ELIMINAR_PRODUCTO){
                EliminarRegistro(fila);
            }else if(columna==UtilidadesProductos.MODIFICAR_PRODUCTO){
                ModificarRegistro(fila);
            }
        }
        
        public void ModificarRegistro(int fila){
            String cod,nom,des;
            double pv;
            int stock,est;
            cod=tabla.getValueAt(fila,UtilidadesProductos.CODIGO_PRODUCTO).toString();
            nom=tabla.getValueAt(fila,UtilidadesProductos.NOMBRE_PRODUCTO).toString();
            des=tabla.getValueAt(fila,UtilidadesProductos.DESCRIPCION_PRODUCTO).toString();
            pv=Double.parseDouble(tabla.getValueAt(fila,UtilidadesProductos.PRECIO_VENTA).toString());
            stock=Integer.parseInt(tabla.getValueAt(fila,UtilidadesProductos.CANTIDAD_STOCK).toString());
            est=Integer.parseInt(tabla.getValueAt(fila,UtilidadesProductos.ESTADO).toString());
            
            ProductoDAO DAO=new ProductoDAO();
            byte[] imagen=DAO.getImagenProducto(cod);
            
            ProductoBEAN producto=new ProductoBEAN();
            producto.setCodProducto(cod);
            producto.setNombre(nom);
            producto.setDescripcion(des);
            producto.setPrecioVenta(pv);
            producto.setCantidad(stock);
            producto.setEstado(est);
            producto.setImagen(imagen);
            
            VentanaModificacionProducto ventana=new VentanaModificacionProducto(producto);
            ventana.setVisible(true);
            
        }
        
        public void EliminarRegistro(int fila){
            String codigo=tabla.getValueAt(fila,UtilidadesProductos.CODIGO_PRODUCTO).toString();
            ProductoBEAN producto=new ProductoBEAN();
            producto.setCodProducto(codigo);
            ProductoDAO productoDAO=new ProductoDAO();
            int rpta=JOptionPane.showConfirmDialog(null,"Esta seguro que desea eliminar este registro?","Confirmación",JOptionPane.YES_NO_OPTION);
            if(rpta==JOptionPane.YES_OPTION){
                productoDAO.eliminarProducto(producto);
                capturarListas();
                vaciarComboBox();
                llenarComboBox();
                cbNombre.setSelectedIndex(0);
                capturarListaTabla();
                limpiarTabla();
                construirTabla();
            }
        }
    }
    
}

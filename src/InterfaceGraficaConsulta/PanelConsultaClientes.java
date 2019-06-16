
package InterfaceGraficaConsulta;

import BEAN.ClienteBEAN;
import DAO.ClienteDAO;
import InterfaceGraficaModificacion.VentanaModificacionCliente;
import UtilidadesCliente.GestionCeldas;
import UtilidadesCliente.GestionEncabezadoTabla;
import UtilidadesCliente.ModeloTabla;
import UtilidadesCliente.UtilidadesCliente;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class PanelConsultaClientes extends JPanel
{
    private ModeloTabla modelo;
    private JTable tabla;
    private JScrollPane scroll;
    private JButton btnBuscar,btnAtras;
    private JLabel retornar;
    private JComboBox cbNombre;
    private ArrayList<ClienteBEAN> lista,listaNombres;
    private JLabel mensaje;
    private int filasTabla;
    private int columnasTabla;
    
    public PanelConsultaClientes()
    {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        Inicio();
    }

    private void Inicio()
    {
        setLayout(null);
        
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
            mensaje.setText("<< No tienes clientes actualmente >>");
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
        ClienteDAO clienteDAO=new ClienteDAO();
        listaNombres=clienteDAO.getListaNombreClientes();
    }
    
    private void llenarComboBox(){
        cbNombre.addItem("-Seleccionar Nombre-");
        for(ClienteBEAN obj: listaNombres)
        {
            cbNombre.addItem(obj.getNombre());
        }
    }
    
    private void capturarListaTabla()
    {
        ClienteDAO clienteDAO=new ClienteDAO();
        lista=clienteDAO.getListaClientes();
    }
    
    private class filtrar implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String nombre=cbNombre.getSelectedItem().toString();
            
            ClienteBEAN cliente=new ClienteBEAN();
            cliente.setNombre(nombre);
            ClienteDAO clienteD=new ClienteDAO();
            
            if(e.getSource()==btnBuscar)
            {
                if(nombre.equals("-Seleccionar Nombre-")==false)
                {
                    capturarListas();
                    vaciarComboBox();
                    llenarComboBox();
                    lista=clienteD.listarContactosFiltroNombres(cliente);
                    llenarTabla("");
                }
                else
                {
                    lista=clienteD.getListaClientes();
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

        titulosList.add("DNI");
        titulosList.add("Nombre");
        titulosList.add("Fecha Nacimiento");
        titulosList.add("Teléfono");
        titulosList.add("Email");
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

            informacion[x][UtilidadesCliente.DNI] = lista.get(x).getDni()+ "";
            informacion[x][UtilidadesCliente.NOMBRE] = lista.get(x).getNombre()+ "";
            informacion[x][UtilidadesCliente.FECHA] = lista.get(x).getFechaNacimiento()+ "";
            informacion[x][UtilidadesCliente.TELEFONO] = lista.get(x).getCelular()+ "";
            informacion[x][UtilidadesCliente.EMAIL] = lista.get(x).getEmail()+ "";
            informacion[x][UtilidadesCliente.MODIFICAR] = "MODIFICAR";
            informacion[x][UtilidadesCliente.ELIMINAR] = "ELIMINAR";
        }

        return informacion;
    }

    private void construirTabla(String[] titulos, Object[][] data) {
      
        modelo=new ModeloTabla(data, titulos);
        tabla.setModel(modelo);
        
        filasTabla=tabla.getRowCount();
        columnasTabla=tabla.getColumnCount();

        tabla.getColumnModel().getColumn(UtilidadesCliente.MODIFICAR).setCellRenderer(new GestionCeldas("icono"));
        tabla.getColumnModel().getColumn(UtilidadesCliente.ELIMINAR).setCellRenderer(new GestionCeldas("icono"));

        for (int i = 0; i < titulos.length-2; i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(new GestionCeldas("texto"));
        }

        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setRowHeight(25);
        tabla.setGridColor(new java.awt.Color(0, 0, 0)); 
        
        tabla.getColumnModel().getColumn(UtilidadesCliente.DNI).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(UtilidadesCliente.NOMBRE).setPreferredWidth(350);
        tabla.getColumnModel().getColumn(UtilidadesCliente.FECHA).setPreferredWidth(160);
        tabla.getColumnModel().getColumn(UtilidadesCliente.TELEFONO).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(UtilidadesCliente.EMAIL).setPreferredWidth(350);
        tabla.getColumnModel().getColumn(UtilidadesCliente.MODIFICAR).setPreferredWidth(30);
        tabla.getColumnModel().getColumn(UtilidadesCliente.ELIMINAR).setPreferredWidth(30);

        JTableHeader jtableHeader = tabla.getTableHeader();
        jtableHeader.setDefaultRenderer(new GestionEncabezadoTabla());
        tabla.setTableHeader(jtableHeader);

        scroll.setViewportView(tabla);
     }
    
    public class EventoEliminar extends MouseAdapter{
        
        public void mouseClicked(MouseEvent e){
            int fila=tabla.rowAtPoint(e.getPoint());
            int columna=tabla.columnAtPoint(e.getPoint());
            if(columna==UtilidadesCliente.ELIMINAR){
                EliminarRegistro(fila);
            }else if(columna==UtilidadesCliente.MODIFICAR){
                ModificarRegistro(fila);
            }
        }
        
        public void ModificarRegistro(int fila){
            String dni,nom,fecha,tel,email;
            dni=tabla.getValueAt(fila,UtilidadesCliente.DNI).toString();
            nom=tabla.getValueAt(fila,UtilidadesCliente.NOMBRE).toString();
            tel=tabla.getValueAt(fila,UtilidadesCliente.TELEFONO).toString();
            fecha=tabla.getValueAt(fila,UtilidadesCliente.FECHA).toString();
            email=tabla.getValueAt(fila,UtilidadesCliente.EMAIL).toString();
            
            ClienteBEAN cliente=new ClienteBEAN();
            cliente.setDni(dni);
            cliente.setNombre(nom);
            cliente.setCelular(tel);
            cliente.setFechaNacimiento(fecha);
            cliente.setEmail(email);
            
            VentanaModificacionCliente ventana=new VentanaModificacionCliente(cliente);
            ventana.setVisible(true);
            
        }
        
        public void EliminarRegistro(int fila){
            String dni=tabla.getValueAt(fila,UtilidadesCliente.DNI).toString();
            ClienteBEAN cliente=new ClienteBEAN();
            cliente.setDni(dni);
            ClienteDAO clienteDAO=new ClienteDAO();
            int rpta=JOptionPane.showConfirmDialog(null,"Esta seguro que desea eliminar este registro?","Confirmación",JOptionPane.YES_NO_OPTION);
            if(rpta==JOptionPane.YES_OPTION){
                clienteDAO.eliminarCliente(cliente);
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

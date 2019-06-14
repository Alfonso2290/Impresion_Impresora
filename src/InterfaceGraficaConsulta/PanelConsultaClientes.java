
package InterfaceGraficaConsulta;

import BEAN.ClienteBEAN;
import DAO.ClienteDAO;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.event.*;
import java.net.URL;

public class PanelConsultaClientes extends JPanel
{
    private DefaultTableModel modelo;
    private JTable tabla;
    private JScrollPane scroll;
    private JButton btnBuscar,btnAtras;
    private JLabel retornar;
    private JComboBox cbNombre;
    private ArrayList<ClienteBEAN> lista,listaNombres;
    private JLabel mensaje;
    
    public PanelConsultaClientes()
    {
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
        cbNombre.addItem("-Seleccionar Nombre-");
        for(ClienteBEAN obj: listaNombres)
        {
            cbNombre.addItem(obj.getNombre());
        }
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
        
        modelo=new DefaultTableModel();
        tabla=new JTable();
        scroll=new JScrollPane(tabla);
        
        tabla.setFont(new Font("Arial",Font.BOLD,10));
        
        modelo.addColumn("DNI");
        modelo.addColumn("Nombre");
        modelo.addColumn("Fecha Nacimiento");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Email");
        
        tabla.setModel(modelo);
        tabla.setEnabled(false);
        centrarTextoTabla();

        capturarListaTabla();
        
        mensaje=new JLabel();
        mensaje.setBounds(250,150,350,50);
        mensaje.setFont(new Font("Arial",Font.BOLD,18));
        mensaje.setForeground(Color.RED);
        
        if(lista.size()!=0)
        {
            for(ClienteBEAN obj:lista)
            {
                modelo.addRow(new Object[]{obj.getDni(),obj.getNombre(),
                obj.getFechaNacimiento(),obj.getCelular(),obj.getEmail()});
            }

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
    
    private void centrarTextoTabla()
    {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        
        for(int i=0;i<tabla.getColumnCount();i++)
            tabla.getColumnModel().getColumn(i).setCellRenderer(tcr);
    }
    
    private void capturarListas()
    {
        ClienteDAO clienteDAO=new ClienteDAO();
        listaNombres=clienteDAO.getListaNombreClientes();
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
            for(ClienteBEAN obj:lista)
            {
               modelo.addRow(new Object[]{obj.getDni(),obj.getNombre(),
               obj.getFechaNacimiento(),obj.getCelular(),obj.getEmail()});
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
    
    private class AccionMouse extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e)
        {
            if(e.getSource()==retornar)
            {   
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

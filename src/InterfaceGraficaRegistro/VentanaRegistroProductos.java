
package InterfaceGraficaRegistro;

import BEAN.*;
import DAO.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class VentanaRegistroProductos extends JFrame implements ActionListener,FocusListener
{
    private PanelRegistroProductos miPanel;
    
    public VentanaRegistroProductos()
    {
        setTitle("Registrar Producto");
        setSize(500,530);
        setResizable(false);
        setLocationRelativeTo(null);
        Inicio();
    }
    
    private void Inicio()
    {
        miPanel=new PanelRegistroProductos();
        miPanel.setBackground(Color.LIGHT_GRAY.brighter());
        
        miPanel.getBtnGuardar().addActionListener(this);
        miPanel.getBtnCancelar().addActionListener(this);
        miPanel.getTxtCod().addFocusListener(this);
        miPanel.getTxtNom().addFocusListener(this);
        
        miPanel.getBtnCancelar().addKeyListener(new cambioCampo());
        miPanel.getBtnGuardar().addKeyListener(new cambioCampo());
        miPanel.getTxtCod().addKeyListener(new cambioCampo());
        miPanel.getTxtDes().addKeyListener(new cambioCampo());
        miPanel.getTxtPv().addKeyListener(new cambioCampo());
        miPanel.getTxtNom().addKeyListener(new cambioCampo());
        miPanel.getTxtCan().addKeyListener(new cambioCampo());
        miPanel.getTxtEst().addKeyListener(new cambioCampo());
        
        miPanel.getBtnAtras().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
                VentanaRegistros ventana=new VentanaRegistros();
                ventana.setVisible(true);
                ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        
        add(miPanel);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==miPanel.getBtnCancelar())
        {
            eventoCancelar();
        }
        
        if(e.getSource()==miPanel.getBtnGuardar())
        {
            eventoGuardar();
        }
    }
    
    private void eventoGuardar()
    {
        File ruta = new File(miPanel.getRutaImagen());
        String nom,cod,des,can,pv,est;
        cod=miPanel.getTxtCod().getText();
        nom=miPanel.getTxtNom().getText();
        des=miPanel.getTxtDes().getText();
        pv=miPanel.getTxtPv().getText();
        can=miPanel.getTxtCan().getText();
        est=miPanel.getTxtEst().getText();

        if(cod.equals("") || cod.length()<8)
        {
            JOptionPane.showMessageDialog(null, "Ustede debe llenar el campo Código");
            miPanel.getTxtCod().requestFocus();
        }
        else if(nom.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Ustede debe llenar el campo Nombre");
            miPanel.getTxtNom().requestFocus();
        }
        else if(des.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Ustede debe llenar el campo Descripción");
            miPanel.getTxtDes().requestFocus();
        }
        else if(pv.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Ustede debe llenar el campo Precio de Venta");
            miPanel.getTxtPv().requestFocus();
        }
        else if(can.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Ustede debe llenar el campo Cantidad Stock");
            miPanel.getTxtCan().requestFocus();
        }
        else if(est.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Ustede debe llenar el campo Estado");
            miPanel.getTxtEst().requestFocus();
        }
        
        else
        {
            ProductoBEAN producto=new ProductoBEAN();
            producto.setCodProducto(cod);
            producto.setNombre(nom);
            producto.setDescripcion(des);
            producto.setPrecioVenta(Double.parseDouble(pv));
            producto.setCantidad(Integer.parseInt(can));
            producto.setEstado(Integer.parseInt(est));
            
            try{
                byte[] icono = new byte[(int) ruta.length()];
                InputStream input = new FileInputStream(ruta);
                input.read(icono);
                producto.setImagen(icono);
            }catch(Exception ex){
                producto.setImagen(null);
            }
            
            ProductoDAO productoDAO=new ProductoDAO();

            int verificarCodigo=productoDAO.registrarProducto(producto);

            if(verificarCodigo==0)
            {
                JOptionPane.showMessageDialog(null, "Error!!..Verifique los datos ingresados");
                miPanel.getTxtCod().requestFocus();
            }else{
                JOptionPane.showMessageDialog(null,"El producto ha sido registrado correctamente");
                miPanel.limpiarCampos();
            }
            
        }
    }

    
    @Override
    public void focusGained(FocusEvent e)
    {
        if(e.getSource()==miPanel.getTxtCod())
        {
            miPanel.getMensaje().setText("");
        }
        if(e.getSource()==miPanel.getTxtNom())
        {
            miPanel.getMensaje2().setText("");
        }
    }
    
    @Override
    public void focusLost(FocusEvent e)
    {
        if(e.getSource()==miPanel.getTxtCod())
        {
            String cod=miPanel.getTxtCod().getText();
            ProductoBEAN producto=new ProductoBEAN();
            producto.setCodProducto(cod);
            
            ProductoDAO productoDAO=new ProductoDAO();
            int verificacion=productoDAO.verificarProducto(producto);
            if(verificacion>0 && cod.compareTo("")!=0 )
            {
                miPanel.getMensaje().setText("Producto Registrado");
            }
        }
        
        if(e.getSource()==miPanel.getTxtNom())
        {
            String nom=miPanel.getTxtNom().getText();
            ProductoBEAN producto=new ProductoBEAN();
            producto.setNombre(nom);
            
            ProductoDAO productoDAO=new ProductoDAO();
            int verificacion=productoDAO.verificarNombreProducto(producto);
            if(verificacion>0 && nom.compareTo("")!=0 )
            {
                miPanel.getMensaje2().setText("Nombre Registrado");
            }
        }
    }
    
    private void eventoCancelar()
    {
        int rpta=JOptionPane.showConfirmDialog(null,"Esta seguro que desea cancelar?\nLos Datos ingresados en la ventana se perderán","Alerta!!" ,JOptionPane.YES_NO_OPTION);
        if(rpta==JOptionPane.YES_OPTION)
        {
            dispose();
            VentanaRegistros ventana=new VentanaRegistros();
            ventana.setVisible(true);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }
    
    private class cambioCampo extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            if(e.getSource()==miPanel.getTxtCod())
            {
                if(e.VK_ENTER==e.getKeyCode())
                    miPanel.getTxtCod().nextFocus();
            }
            
            if(e.getSource()==miPanel.getTxtNom())
            {
                if(e.VK_ENTER==e.getKeyCode())
                    miPanel.getTxtNom().nextFocus();
            }
            
            if(e.getSource()==miPanel.getTxtDes())
            {
                if(e.VK_ENTER==e.getKeyCode())
                    miPanel.getTxtDes().nextFocus();
            }
            
            if(e.getSource()==miPanel.getTxtPv())
            {
                if(e.VK_ENTER==e.getKeyCode())
                    miPanel.getTxtPv().nextFocus();
            }
            
            if(e.getSource()==miPanel.getTxtCan())
            {
                if(e.VK_ENTER==e.getKeyCode())
                    miPanel.getTxtCan().nextFocus();
            }
            
            if(e.getSource()==miPanel.getTxtEst())
            {
                if(e.VK_ENTER==e.getKeyCode())
                    eventoGuardar(); 
            }
            
            if(e.getSource()==miPanel.getBtnGuardar())
            {
                if(e.VK_ENTER==e.getKeyCode())
                    eventoGuardar();
            }
            
            if(e.getSource()==miPanel.getBtnCancelar())
            {
                if(e.VK_ENTER==e.getKeyCode())
                   eventoCancelar();
            }
                
        }
    }
}

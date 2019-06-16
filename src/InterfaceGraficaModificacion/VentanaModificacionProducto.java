
package InterfaceGraficaModificacion;

import BEAN.*;
import DAO.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class VentanaModificacionProducto extends JFrame implements ActionListener,FocusListener
{
    private PanelModificacionProducto miPanel;
    private ProductoBEAN paramProducto;
    
    public VentanaModificacionProducto(ProductoBEAN paramProducto)
    {
        this.paramProducto=paramProducto;
        setTitle("Modificar Producto");
        setSize(500,530);
        setResizable(false);
        setLocationRelativeTo(null);
        Inicio();
    }
    
    private void Inicio()
    {
        miPanel=new PanelModificacionProducto(paramProducto);
        miPanel.setBackground(Color.LIGHT_GRAY.brighter());
        
        miPanel.getBtnGuardar().addActionListener(this);
        miPanel.getBtnCancelar().addActionListener(this);
        miPanel.getTxtNom().addFocusListener(this);
        
        miPanel.getBtnCancelar().addKeyListener(new cambioCampo());
        miPanel.getBtnGuardar().addKeyListener(new cambioCampo());
        miPanel.getTxtDes().addKeyListener(new cambioCampo());
        miPanel.getTxtPv().addKeyListener(new cambioCampo());
        miPanel.getTxtNom().addKeyListener(new cambioCampo());
        miPanel.getTxtCan().addKeyListener(new cambioCampo());
        miPanel.getTxtEst().addKeyListener(new cambioCampo());
        
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
            eventoModificar();
        }
    }
    
    private void eventoModificar()
    {
        File ruta=null;
        if(miPanel.getRutaImagen()!=null)
            ruta = new File(miPanel.getRutaImagen());
        String nom,cod,des,can,pv,est;
        cod=miPanel.getTxtCod().getText();
        nom=miPanel.getTxtNom().getText();
        des=miPanel.getTxtDes().getText();
        pv=miPanel.getTxtPv().getText();
        can=miPanel.getTxtCan().getText();
        est=miPanel.getTxtEst().getText();

        if(nom.equals(""))
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
                producto.setImagen(paramProducto.getImagen());
            }

            ProductoDAO productoDAO=new ProductoDAO();

            int verificarCodigo=productoDAO.modificarProducto(producto);

            if(verificarCodigo==0)
            {
                JOptionPane.showMessageDialog(null, "Error!!..Verifique los datos ingresados");
                miPanel.getTxtNom().requestFocus();
            }else{
                JOptionPane.showMessageDialog(null,"El producto ha sido modificado correctamente");
                dispose();
            }
            
        }
    }

    
    @Override
    public void focusGained(FocusEvent e)
    {
        if(e.getSource()==miPanel.getTxtNom())
        {
            miPanel.getMensaje2().setText("");
        }
    }
    
    @Override
    public void focusLost(FocusEvent e)
    {
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
        }
    }
    
    private class cambioCampo extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            
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
                    eventoModificar(); 
            }
            
            if(e.getSource()==miPanel.getBtnGuardar())
            {
                if(e.VK_ENTER==e.getKeyCode())
                    eventoModificar();
            }
            
            if(e.getSource()==miPanel.getBtnCancelar())
            {
                if(e.VK_ENTER==e.getKeyCode())
                   eventoCancelar();
            }
                
        }
    }
}

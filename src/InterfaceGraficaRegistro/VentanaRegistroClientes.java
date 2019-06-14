
package InterfaceGraficaRegistro;

import BEAN.*;
import DAO.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

public class VentanaRegistroClientes extends JFrame implements ActionListener,FocusListener
{
    private PanelRegistroClientes miPanel;
    
    public VentanaRegistroClientes()
    {
        setTitle("Registrar Cliente");
        setSize(350,470);
        setResizable(false);
        setLocationRelativeTo(null);
        Inicio();
    }
    
    private void Inicio()
    {
        miPanel=new PanelRegistroClientes();
        miPanel.setBackground(Color.LIGHT_GRAY.brighter());
        
        miPanel.getBtnGuardar().addActionListener(this);
        miPanel.getBtnCancelar().addActionListener(this);
        miPanel.getTxtDni().addFocusListener(this);
        
        miPanel.getBtnCancelar().addKeyListener(new cambioCampo());
        miPanel.getBtnGuardar().addKeyListener(new cambioCampo());
        miPanel.getTxtEmail().addKeyListener(new cambioCampo());
        miPanel.getTxtCel().addKeyListener(new cambioCampo());
        miPanel.getTxtFecha().addKeyListener(new cambioCampo());
        miPanel.getTxtNom().addKeyListener(new cambioCampo());
        miPanel.getTxtDni().addKeyListener(new cambioCampo());
        
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
        String nom,dni,cel,fecha,email;
        dni=miPanel.getTxtDni().getText();
        nom=miPanel.getTxtNom().getText();
        fecha=miPanel.getTxtFecha().getText();
        cel=miPanel.getTxtCel().getText();
        email=miPanel.getTxtEmail().getText();

        if(dni.equals("") || dni.length()<8)
        {
            JOptionPane.showMessageDialog(null, "Ustede debe llenar el campo DNI");
            miPanel.getTxtDni().requestFocus();
        }
        else if(nom.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Ustede debe llenar el campo Nombre");
            miPanel.getTxtNom().requestFocus();
        }
        else if(fecha.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Ustede debe llenar el campo Fecha de Nacimiento");
            miPanel.getTxtFecha().requestFocus();
        }
        else if(cel.equals("") || cel.length()<9)
        {
            JOptionPane.showMessageDialog(null, "Ustede debe llenar el campo Teléfono Celular");
            miPanel.getTxtCel().requestFocus();
        }
        else if(email.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Ustede debe llenar el campo Email");
            miPanel.getTxtEmail().requestFocus();
        }
        else
        {
            ClienteBEAN cliente=new ClienteBEAN();
            cliente.setDni(dni);
            cliente.setNombre(nom);
            cliente.setFechaNacimiento(fecha);
            cliente.setCelular(cel);
            cliente.setEmail(email);
            
            ClienteDAO clienteDAO=new ClienteDAO();

            int verificarDNI=clienteDAO.registrarCliente(cliente);

            if(verificarDNI==0)
            {
                JOptionPane.showMessageDialog(null, "Error!!..Verifique los datos ingresados");
                miPanel.getTxtDni().requestFocus();
            }else{
                JOptionPane.showMessageDialog(null,"El cliente ha sido registrado correctamente");
                miPanel.limpiarCampos();
            }
            
        }
    }

    
    @Override
    public void focusGained(FocusEvent e)
    {
        miPanel.getMensaje().setText("");
    }
    
    @Override
    public void focusLost(FocusEvent e)
    {
        if(e.getSource()==miPanel.getTxtDni())
        {
            String dni=miPanel.getTxtDni().getText();
            ClienteBEAN cliente=new ClienteBEAN();
            cliente.setDni(dni);
            
            ClienteDAO clienteDAO=new ClienteDAO();
            int verificacion=clienteDAO.verificarCliente(cliente);
            if(verificacion>0 && dni.compareTo("")!=0 )
            {
                miPanel.getMensaje().setText("Cliente Registrado");
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
            if(e.getSource()==miPanel.getTxtDni())
            {
                if(e.VK_ENTER==e.getKeyCode())
                    miPanel.getTxtDni().nextFocus();
            }
            
            if(e.getSource()==miPanel.getTxtNom())
            {
                if(e.VK_ENTER==e.getKeyCode())
                    miPanel.getTxtNom().nextFocus();
            }
            
            if(e.getSource()==miPanel.getTxtFecha())
            {
                if(e.VK_ENTER==e.getKeyCode())
                    miPanel.getTxtFecha().nextFocus();
            }
            
            if(e.getSource()==miPanel.getTxtCel())
            {
                if(e.VK_ENTER==e.getKeyCode())
                    miPanel.getTxtCel().nextFocus();
            }
            
            if(e.getSource()==miPanel.getTxtEmail())
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


package InterfaceGraficaModificacion;

import BEAN.*;
import DAO.*;
import InterfaceGraficaConsulta.VentanaConsultaClientes;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

public class VentanaModificacionCliente extends JFrame implements ActionListener
{
    private PanelModificacionCliente miPanel;
    private ClienteBEAN Paramcliente;
    
    public VentanaModificacionCliente(ClienteBEAN Paramcliente)
    {
        this.Paramcliente=Paramcliente;
        setTitle("Modificar Cliente");
        setSize(350,470);
        setResizable(false);
        setLocationRelativeTo(null);
        Inicio();
    }
    
    private void Inicio()
    {
        miPanel=new PanelModificacionCliente(Paramcliente);
        miPanel.setBackground(Color.LIGHT_GRAY.brighter());
        
        miPanel.getBtnGuardar().addActionListener(this);
        miPanel.getBtnCancelar().addActionListener(this);
        
        miPanel.getBtnCancelar().addKeyListener(new cambioCampo());
        miPanel.getBtnGuardar().addKeyListener(new cambioCampo());
        miPanel.getTxtEmail().addKeyListener(new cambioCampo());
        miPanel.getTxtCel().addKeyListener(new cambioCampo());
        miPanel.getTxtFecha().addKeyListener(new cambioCampo());
        miPanel.getTxtNom().addKeyListener(new cambioCampo());
        
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
        String nom,dni,cel,fecha,email;
        dni=miPanel.getTxtDni().getText();
        nom=miPanel.getTxtNom().getText();
        fecha=miPanel.getTxtFecha().getText();
        cel=miPanel.getTxtCel().getText();
        email=miPanel.getTxtEmail().getText();

        if(nom.equals(""))
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

            int verificar=clienteDAO.modificarCliente(cliente);

            if(verificar==0)
            {
                JOptionPane.showMessageDialog(null, "Error!!..Verifique los datos ingresados");
                miPanel.getTxtNom().requestFocus();
            }else{
                JOptionPane.showMessageDialog(null,"El registro del cliente ha sido modificado correctamente");
                dispose();
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



package InterfaceGraficaModificacion;

import BEAN.ClienteBEAN;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class PanelModificacionCliente extends JPanel  
{
    private JLabel titulo,nom,dni,fecha,cel,email,txtDni;
    private JTextField txtNom,txtFecha,txtCel,txtEmail;
    private JButton btnModificar,btnCancelar;
    private JSeparator h1,h2,h3,h4,h5;
    private ClienteBEAN Paramcliente;
    
    public PanelModificacionCliente(ClienteBEAN Paramcliente)
    {
        this.Paramcliente=Paramcliente;
        Inicio();
        txtNom.requestFocus();
    }
    
    private void Inicio()
    {
        setLayout(null);
        
        
        Color ColorFuente=new Color(232,44,12);
        Font fuenteTitulo=new Font("Decker", Font.BOLD, 20);
        Font fuenteCampos=new Font("Decker", Font.PLAIN, 14);
        Font fuenteCamposLabel=new Font("Decker", Font.BOLD, 16);
        Font fuenteMensaje=new Font("Decker",Font.PLAIN,12);
        
        titulo=new JLabel("MODIFICAR CLIENTE");
        titulo.setBounds(80,10,300,30);
        titulo.setFont(fuenteTitulo);
        titulo.setForeground(ColorFuente);
        
        dni=new JLabel("DNI");
        dni.setBounds(50,70,120,20);
        dni.setFont(fuenteCamposLabel);
        dni.setForeground(ColorFuente);
        
        txtDni=new JLabel();
        txtDni.setBounds(50,95,250,20);
        txtDni.setFont(fuenteCampos);
        txtDni.setBorder(null);
        
        h1=new JSeparator();
        h1.setBounds(50,115,250,20);
        h1.setOpaque(false);
        h1.setBackground(Color.gray);
                
        nom=new JLabel("Nombre");
        nom.setBounds(50,130,150,20);
        nom.setFont(fuenteCamposLabel);
        nom.setForeground(ColorFuente);
        
        txtNom=new JTextField();
        txtNom.setBounds(50,155,250,20);
        txtNom.addKeyListener(new validarCampos());
        txtNom.setFont(fuenteCampos);
        txtNom.setBorder(null);
        
        h2=new JSeparator();
        h2.setBounds(50,175,250,20);
        h2.setOpaque(false);
        h2.setBackground(Color.gray);
        
        fecha=new JLabel("Fecha de Nacimiento");
        fecha.setBounds(50,190,200,20);
        fecha.setFont(fuenteCamposLabel);
        fecha.setForeground(ColorFuente);
        
        txtFecha=new JTextField();
        txtFecha.setBounds(50,215,250,20);
        txtFecha.setFont(fuenteCampos);
        txtFecha.setBorder(null);
        
        h3=new JSeparator();
        h3.setBounds(50,235,250,20);
        h3.setOpaque(false);
        h3.setBackground(Color.gray);

        cel=new JLabel("Teléfono celular");
        cel.setBounds(50,250,150,20);
        cel.setFont(fuenteCamposLabel);
        cel.setForeground(ColorFuente);
        
        txtCel=new JTextField();
        txtCel.setBounds(50,275,250,20);
        txtCel.addKeyListener(new validarCampos());
        txtCel.setFont(fuenteCampos);
        txtCel.setBorder(null);
        
        h4=new JSeparator();
        h4.setBounds(50,295,250,20);
        h4.setOpaque(false);
        h4.setBackground(Color.gray);
        
        email=new JLabel("Email");
        email.setBounds(50,310,180,20);
        email.setFont(fuenteCamposLabel);
        email.setForeground(ColorFuente);
        
        txtEmail=new JTextField();
        txtEmail.setBounds(50,335,250,20);
        txtEmail.setFont(fuenteCampos);
        txtEmail.setBorder(null);
        
        h5=new JSeparator();
        h5.setBounds(50,355,250,20);
        h5.setOpaque(false);
        h5.setBackground(Color.gray);
        
        btnModificar=new JButton("Modificar");
        btnModificar.setBounds(50,390,110,30);
        btnModificar.setFont(fuenteCamposLabel);
        btnModificar.setBackground(null);
        btnModificar.setForeground(ColorFuente);
        btnModificar.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,btnModificar));
        
        btnCancelar=new JButton("Cancelar");
        btnCancelar.setBounds(175,390,120,30);
        btnCancelar.setFont(fuenteCamposLabel);
        btnCancelar.setBackground(null);
        btnCancelar.setForeground(ColorFuente);
        btnCancelar.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,btnCancelar));
        
        llenarCampos();
        
        add(titulo);
        add(dni);
        add(nom);
        add(cel);
        add(fecha);
        add(email);
        add(txtDni);
        add(txtNom);
        add(txtCel);
        add(txtFecha);
        add(txtEmail);
        add(btnModificar);
        add(btnCancelar);
        add(h1);
        add(h2);
        add(h3);
        add(h4);
        add(h5);
    }

    public JTextField getTxtNom() {
        return txtNom;
    }

    public JLabel getTxtDni() {
        return txtDni;
    }

    public JTextField getTxtFecha() {
        return txtFecha;
    }

    public JTextField getTxtCel() {
        return txtCel;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public JButton getBtnGuardar() {
        return btnModificar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public void limpiarCampos()
    {
        txtNom.setText("");
        txtCel.setText("");
        txtFecha.setText("");
        txtEmail.setText("");
        txtNom.requestFocus();
    }
    
    private class validarCampos extends KeyAdapter
    {
        @Override
        public void keyTyped(KeyEvent e)
        {
            char teclaPresionada=e.getKeyChar();
            if(e.getSource()==txtCel)
            {
                if(teclaPresionada<'0' || teclaPresionada>'9')
                    e.consume();
            }
            
            if(e.getSource()==txtNom)
            {
                if(((teclaPresionada<'a' || teclaPresionada>'z') && (teclaPresionada<'A' || teclaPresionada>'Z')) && teclaPresionada!=(char)32)
                    e.consume();
            }
            
            if(e.getSource()==txtCel){
                if(txtCel.getText().length()==9)
                    e.consume();
            }
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
    
    public void llenarCampos(){
        
        if(Paramcliente!=null){
            txtDni.setText(Paramcliente.getDni());
            txtNom.setText(Paramcliente.getNombre());
            txtFecha.setText(Paramcliente.getFechaNacimiento());
            txtCel.setText(Paramcliente.getCelular());
            txtEmail.setText(Paramcliente.getEmail());
        }
    }
}



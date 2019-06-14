
package InterfaceGraficaRegistro;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class PanelRegistroProductos extends JPanel  
{
    private JLabel mensaje,titulo,cod,nom,des,pv,can,est;
    private JTextField txtNom,txtCod,txtDes,txtPv,txtCan,txtEst;
    private JButton btnGuardar,btnCancelar,btnAtras;
    private JSeparator h1,h2,h3,h4,h5,h6;
    
    public PanelRegistroProductos()
    {
        Inicio();
    }
    
    private void Inicio()
    {
        setLayout(null);
        Color ColorFuente=new Color(232,44,12);
        Font fuenteTitulo=new Font("Decker", Font.BOLD, 20);
        Font fuenteCampos=new Font("Decker", Font.PLAIN, 14);
        Font fuenteCamposLabel=new Font("Decker", Font.BOLD, 16);
        Font fuenteMensaje=new Font("Decker",Font.PLAIN,12);
        
        titulo=new JLabel("REGISTRAR PRODUCTO");
        titulo.setBounds(70,10,300,30);
        titulo.setFont(fuenteTitulo);
        titulo.setForeground(ColorFuente);
        
        cod=new JLabel("Código");
        cod.setBounds(50,70,200,20);
        cod.setFont(fuenteCamposLabel);
        cod.setForeground(ColorFuente);
        
        txtCod=new JTextField();
        txtCod.setBounds(50,95,250,20);
        txtCod.addKeyListener(new validarCampos());
        txtCod.setFont(fuenteCampos);
        txtCod.setBorder(null);
        
        h1=new JSeparator();
        h1.setBounds(50,115,250,20);
        h1.setOpaque(false);
        h1.setBackground(Color.gray);
        
        mensaje=new JLabel();
        mensaje.setBounds(190,70,120,20);
        mensaje.setForeground(ColorFuente);
        mensaje.setFont(fuenteMensaje);
        
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
        
        des=new JLabel("Descripción");
        des.setBounds(50,190,200,20);
        des.setFont(fuenteCamposLabel);
        des.setForeground(ColorFuente);
        
        txtDes=new JTextField();
        txtDes.setBounds(50,215,250,20);
        txtDes.setFont(fuenteCampos);
        txtDes.setBorder(null);
        
        h3=new JSeparator();
        h3.setBounds(50,235,250,20);
        h3.setOpaque(false);
        h3.setBackground(Color.gray);

        pv=new JLabel("Precio de Venta");
        pv.setBounds(50,250,150,20);
        pv.setFont(fuenteCamposLabel);
        pv.setForeground(ColorFuente);
        
        txtPv=new JTextField();
        txtPv.setBounds(50,275,250,20);
        txtPv.addKeyListener(new validarCampos());
        txtPv.setFont(fuenteCampos);
        txtPv.setBorder(null);
        
        h4=new JSeparator();
        h4.setBounds(50,295,250,20);
        h4.setOpaque(false);
        h4.setBackground(Color.gray);
        
        can=new JLabel("Cantidad Stock");
        can.setBounds(50,310,180,20);
        can.setFont(fuenteCamposLabel);
        can.setForeground(ColorFuente);
        
        txtCan=new JTextField();
        txtCan.setBounds(50,335,250,20);
        txtCan.addKeyListener(new validarCampos());
        txtCan.setFont(fuenteCampos);
        txtCan.setBorder(null);
        
        h5=new JSeparator();
        h5.setBounds(50,355,250,20);
        h5.setOpaque(false);
        h5.setBackground(Color.gray);
        
        est=new JLabel("Estado");
        est.setBounds(50,370,180,20);
        est.setFont(fuenteCamposLabel);
        est.setForeground(ColorFuente);
        
        txtEst=new JTextField();
        txtEst.setBounds(50,395,250,20);
        txtEst.addKeyListener(new validarCampos());
        txtEst.setFont(fuenteCampos);
        txtEst.setBorder(null);
        
        h6=new JSeparator();
        h6.setBounds(50,415,250,20);
        h6.setOpaque(false);
        h6.setBackground(Color.gray);
        
        btnGuardar=new JButton("Guardar");
        btnGuardar.setBounds(50,460,110,30);
        btnGuardar.setFont(fuenteCamposLabel);
        btnGuardar.setBackground(null);
        btnGuardar.setForeground(ColorFuente);
        btnGuardar.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,btnGuardar));
        
        btnCancelar=new JButton("Cancelar");
        btnCancelar.setBounds(175,460,120,30);
        btnCancelar.setFont(fuenteCamposLabel);
        btnCancelar.setBackground(null);
        btnCancelar.setForeground(ColorFuente);
        btnCancelar.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,btnCancelar));
        
        btnAtras=new JButton(new ImageIcon("src/imagenes/atras.png"));
        btnAtras.setBounds(10,460,30,30);
        btnAtras.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,btnAtras));
        btnAtras.setBackground(null);
        btnAtras.setForeground(ColorFuente);
        btnAtras.setFont(fuenteCamposLabel);
        
        add(btnAtras);
        
        add(titulo);
        add(cod);
        add(nom);
        add(est);
        add(pv);
        add(can);
        add(des);
        add(txtCod);
        add(txtNom);
        add(txtDes);
        add(txtPv);
        add(txtCan);
        add(txtEst);
        add(btnGuardar);
        add(btnCancelar);
        add(mensaje);
        add(h1);
        add(h2);
        add(h3);
        add(h4);
        add(h5);
        add(h6);
    }

    public JTextField getTxtNom() {
        return txtNom;
    }

    public JTextField getTxtCod() {
        return txtCod;
    }

    public JTextField getTxtDes() {
        return txtDes;
    }

    public JTextField getTxtPv() {
        return txtPv;
    }

    public JTextField getTxtCan() {
        return txtCan;
    }

    public JTextField getTxtEst() {
        return txtEst;
    }

    public JLabel getMensaje() {
        return mensaje;
    }
    
    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JButton getBtnAtras() {
        return btnAtras;
    }
    
    
    
    public void limpiarCampos()
    {
        txtNom.setText("");
        txtCod.setText("");
        txtDes.setText("");
        txtPv.setText("");
        txtCan.setText("");
        txtEst.setText("");
        txtCod.requestFocus();
    }
    
    private class validarCampos extends KeyAdapter
    {
        @Override
        public void keyTyped(KeyEvent e)
        {
            char teclaPresionada=e.getKeyChar();
            if(e.getSource()==txtCod || e.getSource()==txtCan)
            {
                if(teclaPresionada<'0' || teclaPresionada>'9')
                    e.consume();
            }
            
            if(e.getSource()==txtEst)
            {
                if(teclaPresionada<'0' || teclaPresionada>'1')
                    e.consume();
            }
            
            if(e.getSource()==txtPv)
            {
                if((teclaPresionada<'0' || teclaPresionada>'9') && teclaPresionada!=(char)46)
                    e.consume();
            }
            
            if(e.getSource()==txtNom)
            {
                if(((teclaPresionada<'a' || teclaPresionada>'z') && (teclaPresionada<'A' || teclaPresionada>'Z')) && teclaPresionada!=(char)32)
                    e.consume();
            }
            
            if(e.getSource()==txtCod){
                if(txtCod.getText().length()==8)
                    e.consume();
            }
            
            if(e.getSource()==txtPv){
                
                if(verificarPuntosDecimales(txtPv.getText().toString())==false)
                {
                    if(teclaPresionada<'0' || teclaPresionada>'9')
                    e.consume();
                }
            }
            
            if(e.getSource()==txtEst){
                if(txtEst.getText().length()==1)
                    e.consume();
            }
            
        }
    }
    
    private boolean verificarPuntosDecimales(String cadena){
        int cont=0;
        for(int i=0;i<cadena.length();i++){
            if(cadena.charAt(i)=='.'){
                cont++;
            }
        }
        if(cont<1)
            return true;
        else
            return false;
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



package InterfaceGraficaConsulta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaConsultaProductos extends JFrame
{
    private PanelConsultaProductos miPanel;
    
    public VentanaConsultaProductos()
    {
        setTitle("Registro Productos");
        Dimension tamañoPantalla=Toolkit.getDefaultToolkit().getScreenSize();
        setSize(tamañoPantalla.width*3/5,tamañoPantalla.height/2);
        setLocationRelativeTo(null);
        setResizable(false);
        Inicio();
    }
    
    private void Inicio()
    {
        miPanel=new PanelConsultaProductos();
        miPanel.setBackground(Color.LIGHT_GRAY.brighter());
        
        miPanel.getBtnAtras().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
                VentanaConsultas ventana=new VentanaConsultas();
                ventana.setVisible(true);
                ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        
        
        add(miPanel);
    }

}


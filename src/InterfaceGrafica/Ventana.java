package InterfaceGrafica;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import javax.swing.*;
import BEAN.*;
import java.util.*;

public class Ventana extends JFrame{

    private JButton btnImprimir,btnAtras;
    private Panel miPanel;
    private ArrayList<ProductoBEAN> lista;
    private JPanel lamina;

    public Ventana(ArrayList<ProductoBEAN> lista) {
        this.lista=lista;
        inicioComponentes();
    }

    private void inicioComponentes() {
        setTitle("Imprimir Cupon");
        setSize(240,400);
        setLocationRelativeTo(null);
        
        Color ColorFuente=new Color(232,44,12);
        Font fuenteCamposLabel=new Font("Decker", Font.BOLD, 16);
        
        setLayout(new BorderLayout());

        miPanel=new Panel(this.lista);
        add(miPanel,BorderLayout.CENTER);
        
        lamina=new JPanel();
        lamina.setLayout(new GridLayout(1,2,10,10));
        lamina.setBackground(Color.LIGHT_GRAY.brighter());

        btnImprimir=new JButton("Imprimir");
        btnImprimir.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,btnImprimir));
        btnImprimir.setBackground(null);
        btnImprimir.setForeground(ColorFuente);
        btnImprimir.setFont(fuenteCamposLabel);

        btnImprimir.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                    try {
                            PrinterJob imp=PrinterJob.getPrinterJob();
                            imp.setPrintable(new Printable() {

                                    @Override
                                    public int print(Graphics g, PageFormat pagFor, int index) throws PrinterException {

                                            if(index>0)
                                                    return NO_SUCH_PAGE;

                                            Graphics2D g2=(Graphics2D)g;
                                            g2.translate(pagFor.getImageableX()+8, pagFor.getImageableY()+8);
                                            g2.scale(1.0,1.0);
                                            miPanel.printAll(g);

                                            return PAGE_EXISTS;
                                    }
                            });;
                            boolean top=imp.printDialog();
                            if(top) {
                                    imp.print();
                            }
                    }catch(Exception ex) {
                            ex.printStackTrace();
                    }
            }

        });
        
        btnAtras=new JButton("Atrás",new ImageIcon("src/imagenes/atras.png"));
        btnAtras.addMouseListener(new ColorBotones(ColorFuente,Color.WHITE,btnAtras));
        btnAtras.setBackground(null);
        btnAtras.setForeground(ColorFuente);
        btnAtras.setFont(fuenteCamposLabel);
        btnAtras.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e){
                dispose();
                VentanaConsultaDNI princi=new VentanaConsultaDNI();
                princi.setVisible(true);
                princi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });

        add(lamina,BorderLayout.SOUTH);
        lamina.add(btnAtras);
        lamina.add(btnImprimir);
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
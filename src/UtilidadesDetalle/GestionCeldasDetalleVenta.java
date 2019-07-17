
package UtilidadesDetalle;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
 
public class GestionCeldasDetalleVenta extends DefaultTableCellRenderer{
  
    private String tipo="text";
    private Font normal = new Font( "Verdana",Font.PLAIN ,12 );
    private Font bold = new Font( "Verdana",Font.BOLD ,12 );
    private JLabel label = new JLabel();
    private ImageIcon iconoCompra = new ImageIcon(getClass().getResource("/imagenes/carrito.png"));
    
    public GestionCeldasDetalleVenta(){}

    public GestionCeldasDetalleVenta(String tipo){
        this.tipo=tipo;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {

        Color colorFondo = null;
        Color colorFondoPorDefecto=new Color( 192, 192, 192);
        Color colorFondoSeleccion=new Color( 140, 140 , 140);

        if (selected) {                
           this.setBackground(colorFondoPorDefecto );   
        }
        else
        {
            this.setBackground(Color.white);
        }

        if( tipo.equals("texto"))
        {
            if (focused) {
                colorFondo=colorFondoSeleccion;
            }else{
                colorFondo= colorFondoPorDefecto;
            }
            this.setHorizontalAlignment( JLabel.LEFT );
            this.setText( (String) value );
            this.setBackground( (selected)? colorFondo :Color.WHITE); 
            this.setFont(normal);   
            
            return this;
        }

        if( tipo.equals("icono"))
        {
            if( String.valueOf(value).equals("CARRITO") )
            {
                label.setIcon(iconoCompra);
            }
            label.setHorizontalAlignment( JLabel.CENTER );
            label.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            
            return label;
        }

        if( tipo.equals("numerico"))
        {           
            if (focused) {
                colorFondo=colorFondoSeleccion;
            }else{
                colorFondo=colorFondoPorDefecto;
            }
            
            this.setHorizontalAlignment( JLabel.CENTER );
            this.setText( (String) value );            
            this.setForeground( (selected)? new Color(255,255,255) :new Color(32,117,32) );    
            this.setBackground( (selected)? colorFondo :Color.WHITE);
            this.setFont(bold);
            
            return this;   
        }

        return this; 
    }
}


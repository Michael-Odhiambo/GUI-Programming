
import java.awt.*;
import java.awt.event.*;

public class Awt extends Frame implements ActionListener {

    int x = 100;
    int y = 100;

    public static void main( String[] args ) {
        Frame frame = new Awt();
        frame.setSize( 640, 580 );
        frame.setVisible( true );

    }

    public Awt() {
        setTitle( "AWT Demo" );

        MenuBar mb = new MenuBar();
        setMenuBar( mb );

        Menu menu = new Menu( "File" );
        mb.add( menu );

        MenuItem mi = new MenuItem( "Exit" );
        mi.addActionListener( this );
        menu.add( mi );

        WindowListener l = new WindowAdapter() {
            public void windowClosing( WindowEvent ev ) {
                System.exit( 0 );
            }
        };

        this.addWindowListener( l );

        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked( MouseEvent ev ) {
                x = ev.getX();
                y = ev.getY();
                repaint();
            }
        };

        addMouseListener( mouseListener );
    }

    public void paint( Graphics g ) {
        g.drawOval( x - 50, y - 50, 100, 100 );
    }

    public void actionPerformed( ActionEvent ev ) {
        String command = ev.getActionCommand();
        if ( "Exit".equals( command ) ) {
            System.exit(0);
        }
    }
}

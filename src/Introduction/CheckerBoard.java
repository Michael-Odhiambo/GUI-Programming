package Introduction;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

public class CheckerBoard extends Application {

    Checkerboard board;  // A canvas on which a checkerboard is drawn, defined by a static
                         // nested subclass.
    Button resignButton;
    Button newGameButton;

    Label message;  // A label for displaying messages to the user.

    int clickCount; // Counts how many times the button was clicked.

    /**
     * The start method uses a Pane as a root node of the scene. The prefered size
     * of the Pane is set explicitly to 500 by 420; the stage will take its size from
     * the preferred size of the Pane. Four child nodes are added to the Pane. The
     * location of each child is set, and sizes are set for the two buttons. The
     * buttons are set to be unmanaged to stop the Pane from setting their sizes. ( Without
     * the preferred size for the Pane, it would be just large enough to show the checkerboard and
     * the label, but not the buttons, which it ignores. )
     */
    public void start( Stage stage ) {

        /* Create the child nodes. */
        board = new Checkerboard();
        board.draw();

        newGameButton = new Button( "New Game." );
        newGameButton.setOnAction( e -> doNewGame() );

        resignButton = new Button( "Resign" );
        resignButton.setOnAction( e -> doResign() );

        message = new Label( "Click \"New Game\" to begin." );
        message.setTextFill( Color.rgb( 100, 255, 100 ) );  // Light green.
        message.setFont( Font.font( null, FontWeight.BOLD, 18 ) );

        /* Set the location of each child by calling its relocate() method. */
        board.relocate( 20, 20 );
        newGameButton.relocate( 370, 120 );
        resignButton.relocate( 370, 200 );
        message.relocate( 20, 370 );

        /**
         * Set the sizes of the buttons. For this to have an effect, make
         * the buttons "unmanaged." If they are managed, the Pane will set their
         * sizes.
         */
        resignButton.setManaged( false );
        resignButton.resize( 100, 30 );
        newGameButton.setManaged( false );
        newGameButton.resize( 100, 30 );

        Pane root = new Pane();

        root.setPrefWidth( 500 );
        root.setPrefHeight( 420 );

        /* Add the child nodes to the Pane and set up the rest of the GUI. */
        root.getChildren().addAll( board, newGameButton, resignButton, message );

        Scene scene = new Scene( root );
        stage.setScene( scene );
        stage.setTitle( "Doing my own layout." );
        stage.show();

    }

    /**
     * Method to be called when the user clicks "New Game".
     */
    private void doNewGame() {
        clickCount++;
        if ( clickCount == 1 )
            message.setText( "First Click:  \"New Game\" was clicked." );
        else
            message.setText( "Click no. " + clickCount + ": \"New Game\" was clicked." );
    }

    /**
     * A method to be called when the user clicks "Resign."
     */
    private void doResign() {
        clickCount++;
        if ( clickCount == 1 )
            message.setText( "First click: \"Resign\" was clicked." );
        else
            message.setText( "Click no. " + clickCount + ": \"Resign\" was clicked." );
    }

    public static void main( String[] args ) {
        launch( args );
    }


    /**
     * This canvas displays a 320-by-320 checkerboard pattern with a 2-pixel
     * dark red border. The canvas will be exactly 324 by 324 pixels.
     */
    private static class Checkerboard extends Canvas {

        public Checkerboard() {
            super( 324, 324 );  // Call constructor from canvas to set the size.

        }

        /**
         * Draws the content of the canvas.
         */
        public void draw() {

            GraphicsContext g = getGraphicsContext2D();

            // Draw a 2-pixel dark red border around the edges of the board.
            g.setStroke( Color.DARKRED );
            g.setLineWidth( 2 );
            g.strokeRect( 1, 1, 322, 322 );

            // Draw checkerboard pattern in gray and lightGray.
            for ( int row = 0; row < 8; row++ ) {
                for ( int col = 0; col < 8; col++ ) {
                    if ( row % 2 == col % 2 )
                        g.setFill( Color.LIGHTGRAY );
                    else
                        g.setFill( Color.GRAY );
                    g.fillRect( 2 + col*40, 2 + row*40, 40, 40 );
                }
            }
        }

    }
}

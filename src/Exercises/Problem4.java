package Exercises;

/**
 * Author: Michael Allan Odhiambo.
 * E-mail: michaelallanodhiambo@gmail.com
 */

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;

/**
 * Write a program where the user can select a square by clicking on it. Highlight the selected
 * square by drawing a colored border around it. When the program starts, no square is selected.
 * When the user clicks on a square that is not currently selected, it becomes selected, and the
 * previously selected square, if any, is unselected. If the user clicks the square that is
 * selected, it becomes unselected. Assume that the size of the checkerboard is exactly 400 by
 * 400 pixels, so that each square on the checkerboard is 50 by 50 pixels. 
 */

public class Problem4 extends Application {

    CheckerBoard board;

    // The previously selected row and column.
    int prevRow;
    int prevCol;

    // Is a square currently selected on the board.
    boolean squareHighlighted;

    public static void main( String[] args ) {
        launch( args );
    }

    public void start( Stage stage ) {

        board = new CheckerBoard();
        board.draw();

        squareHighlighted = false;

        board.setOnMouseClicked( e -> mouseClicked( e ) );

        BorderPane root = new BorderPane( board );

        Scene scene = new Scene( root );

        stage.setScene( scene );
        stage.setTitle( "Checker Board" );
        stage.setResizable( false );
        stage.show();



    }

    private void mouseClicked( MouseEvent evt ) {

        // Get the row and column where the user has clicked.
        int col = (int)( evt.getX() / 50 );
        int row = (int) ( evt.getY() / 50 );

        /**
         * If a square is currently highlighted on the board, check if the user has clicked on the currently
         * selected square, if so, remove the highlight from that square. If not, highlight the square.
         */
        if ( squareHighlighted ) {

            if ( prevCol == col && prevRow == row ) {
                board.draw();
                squareHighlighted = false;

            }
            else {
                board.highlightSquare( col, row );
                squareHighlighted = true;
                prevCol = col;
                prevRow = row;
            }

        }
        else {

            board.highlightSquare( col, row );
            squareHighlighted = true;

            prevCol = col;
            prevRow = row;


        }


    }

    /**
     * This nested class represents a Checker board. It is a subclass of Canvas.
     */
    private class CheckerBoard extends Canvas {

        GraphicsContext g = getGraphicsContext2D();

        CheckerBoard() {
            /*
            Call the constructor in the Canvas class to set the width and height of this checkerboard.
             */
            super( 400, 400 );
        }

        /**
         * This method draws the checkerboard pattern on the canvas.
         */
        void draw() {

            /**
             * Draw a black 2-pixel border around the edges of the board.
             */
            g.setStroke( Color.BLACK );
            g.setLineWidth( 2 );
            g.strokeRect( 1, 1, 398, 398 );

            for ( int row = 0; row < 8; row++ ) {
                for ( int col = 0; col < 8; col++ ) {
                    if ( row % 2 == col % 2 ) {
                        g.setFill( Color.RED );
                    }
                    else {
                        g.setFill( Color.BLACK );
                    }
                    g.fillRect( 2 + row*50, 2 + col*50, 50, 50 );
                }
            }

        }

        /**
         * This method highlights a square on the canvas.
         */
        void highlightSquare( int row, int col ) {

            /**
             * Re-draw the board to get rid of any previously selected squares.
             */
            draw();

            g.setStroke( Color.YELLOW );
            g.setLineWidth( 3 );
            g.strokeRect( 3 + row*50, 3 + col*50, 47, 47 );
        }
    }
}

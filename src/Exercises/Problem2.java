package Exercises;

/**
 * Author: Michael Allan Odhiambo.
 * E-mail: michaelallanodhiambo@gmail.com
 */

/**
 * Write a program that shows a small red square and a small blue square. The user should be
 * able to drag either square with the mouse. ( You'll need an instance variable to remember
 * which square the user is dragging. ) The user can drag the square out of the window if she
 * wants, and it will disappear. To allow the user to get the squares back into the window, add
 * a KeyPressed event handler that will restore the squares to their original positions when the
 * user presses the Escape key.
 */

import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;

public class Problem2 extends Application {

    private final int SQUARE_WIDTH = 70, SQUARE_HEIGHT = 70;

    Canvas canvas;  // Canvas where drawing is done.
    GraphicsContext g;

    Square redSquare, blueSquare;  // The red and green squares.

    Square squareBeingDragged;

    boolean isDragging;  // Set to true if the user is performing a drag operation.

    double offsetX, offsetY;


    public static void main( String[] args ) {
        launch( args );

    }

    public void start( Stage stage ) {

        isDragging = false;  // False by default.

        canvas = new Canvas( 700, 700 );
        g = canvas.getGraphicsContext2D();

        redSquare = new Square();
        redSquare.setSquareColor( Color.RED );
        redSquare.drawSquare( g, canvas.getWidth()/2, canvas.getHeight()/2 );

        blueSquare = new Square();
        blueSquare.setSquareColor( Color.BLUE );
        blueSquare.drawSquare( g, ( canvas.getWidth()/2 ) - 105, canvas.getHeight()/2 );

        canvas.setOnMousePressed( e -> mousePressed( e ) );
        canvas.setOnMouseDragged( e -> mouseDragged( e ) );
        canvas.setOnMouseReleased( e -> mouseReleased() );

        BorderPane root = new BorderPane( canvas );

        Scene scene = new Scene( root );
        scene.setOnKeyPressed( e -> keyPressed( e ) );

        stage.setScene( scene );
        stage.setTitle( "Problem 2" );
        stage.show();

    }

    /**
     * This method is called when the user presses the escape key.
     */
    private void keyPressed( KeyEvent evt ) {
            if ( evt.getCode() == KeyCode.ESCAPE ) {
                System.out.println( "Escape." );

                g.setFill( Color.WHITE );
                g.fillRect( 0, 0, canvas.getWidth(), canvas.getHeight() );

                redSquare.drawSquare( g, canvas.getWidth()/2, canvas.getHeight()/2 );
                blueSquare.drawSquare( g, ( canvas.getWidth()/2 ) - 105, canvas.getHeight()/2 );

            }

    }

    /**
     * This method is called when the user clicks the mouse on the canvas. It determines if a
     * square was clicked.
     */
    private void mousePressed( MouseEvent evt ) {

        // Exit if a drag is already in progress.
        if ( isDragging )
            return;

        isDragging = true;

        if ( redSquare.hasBeenClicked( evt.getX(), evt.getY() ) ) {
            // Distance from the corner of the red square.
            offsetX = evt.getX() - redSquare.xPos;
            offsetY = evt.getY() - redSquare.yPos;
            squareBeingDragged = redSquare;
        }
        else if ( blueSquare.hasBeenClicked( evt.getX(), evt.getY() ) ) {
            offsetX = evt.getX() - blueSquare.xPos;
            offsetY = evt.getY() - blueSquare.yPos;
            squareBeingDragged = blueSquare;
        }
    }

    /**
     * This method is called when the user drags the mouse over the canvas. If one of the
     * two squares is currently selected, it is dragged.
     */
    private void mouseDragged( MouseEvent evt ) {

        if ( isDragging ) {

            g.setFill(Color.WHITE);
            g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            if ( squareBeingDragged == redSquare ) {
                g.setFill(Color.BLUE);
                blueSquare.drawSquare( g, blueSquare.xPos, blueSquare.yPos );
            } else {
                g.setFill(Color.RED);
                redSquare.drawSquare( g, redSquare.xPos, redSquare.yPos );
            }


            double xPos = evt.getX() - offsetX;
            double yPos = evt.getY() - offsetY;

            squareBeingDragged.drawSquare( g, xPos, yPos );

        }
    }

    /**
     * This method is called when the user releases the mouse button on the canvas.
     * It simply sets isDragging = false.
     */
    private void mouseReleased() {
        isDragging = false;

    }

    /**
     * This nested class represents a square.
     */
    private class Square {

        double xPos;
        double yPos;

        int squareWidth = 100;
        int squareHeight = 100;

        Color squareColor;

        void setSquareColor( Color color ) {
            squareColor = color;

        }

        void drawSquare( GraphicsContext g, double xPosition, double yPosition ) {

            xPos = xPosition;
            yPos = yPosition;

            g.setFill( squareColor );
            g.fillRect( xPos, yPos, squareWidth, squareHeight );

            g.setStroke( Color.BLACK );
            g.strokeRect( xPos, yPos, squareWidth, squareHeight );

        }

        boolean hasBeenClicked( double xPosition, double yPosition ) {

            if ( xPosition >= xPos && xPosition <= xPos + squareWidth && yPosition >= yPos
                    && yPosition <= yPos + squareHeight ) {
                return true;
            }
            return false;
        }

    }
}

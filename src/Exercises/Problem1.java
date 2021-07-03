package Exercises;

/**
 * Author: Michael Allan Odhiambo.
 * E-mail: michaelallanodhiambo@gmail.com
 */

/**
 * Write a complete program that will continue to draw figures if the user drags the mouse. That is,
 * the mouse will leave a trail of figures as the user drags. However, if the user right-clicks the
 * canvas, then the canvas should simply be cleared and no figures should be drawn even if the user
 * drags the mouse after right clicking.
 *
 * Note that a black border has been added around each shape to make them more distinct. To make the
 * problem a little more challenging, when drawing shapes during a drag operation, make sure that the
 * shapes that are drawn are at least, say 5 pixels apart. To implement this, you have to keep track of
 * the position where the previous shape was drawn.
 */

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Problem1 extends Application {

    // Figure size.
    private final int FIGURE_WIDTH = 70, FIGURE_HEIGHT = 70;

    // Canvas where drawing is done.
    Canvas canvas;

    // The canvas' graphics context.
    GraphicsContext g;

    // Previous mouse location;
    private double prevX, prevY;

    // Set to true if the user is perfoming a drag operation. It's set to false by default.
    private boolean isDragging = false;

    private double spaceBetweenFigures = 5;  // Space between each drawing.




    public static void main( String[] args ) {
        launch( args );

    }

    public void start( Stage stage ) {

        // Create the canvas.
        canvas = new Canvas( 700, 700 );
        g = canvas.getGraphicsContext2D();

        // Install a handler on the canvas to respond to MousePressed events.
        canvas.setOnMousePressed( e -> mousePressed( e ) );

        // Install a handler on the canvas to respond to MouseDragged events.
        canvas.setOnMouseDragged( e -> mouseDragged( e ) );

        // Install a handler on the canvas to respond to MouseReleased events.
        canvas.setOnMouseReleased( e -> mouseReleased( e ) );


        // The root of the program is a BorderPane.
        BorderPane root = new BorderPane( canvas );

        Scene scene = new Scene( root );

        stage.setScene( scene );
        stage.setTitle( "Problem 1" );
        stage.show();

    }

    /**
     * This method simply draws a figure.
     * @param evt
     */
    private void draw( MouseEvent evt ) {

        isDragging = true;
        double xPos = evt.getX() - 35;
        double yPos = evt.getY() - 35;

        /**
         * If the shift key is down, draw a blue oval. If not, draw a red rectangle.
         */
        if ( evt.isShiftDown() ) {
            g.setFill( Color.BLUE );
            g.fillOval( xPos, yPos, FIGURE_WIDTH, FIGURE_HEIGHT );

            // Draw a black outline around the figure.
            g.setStroke( Color.BLACK );
            g.strokeOval( xPos, yPos, FIGURE_WIDTH, FIGURE_HEIGHT );
        }
        else {
            g.setFill( Color.RED );
            g.fillRect( xPos, yPos, FIGURE_WIDTH, FIGURE_HEIGHT );

            g.setStroke( Color.BLACK );
            g.strokeRect( xPos, yPos, FIGURE_WIDTH, FIGURE_HEIGHT );
        }

    }

    /**
     * This method is called when the user clicks on the canvas.
     */
    private void mousePressed( MouseEvent evt ) {

        /**
         * If the user right-clicks on the canvas, clear the drawing area.
         */
        if ( evt.isSecondaryButtonDown() ) {
            clear();
            return;
        }

        isDragging = true;
        draw( evt );
        prevX = evt.getX();
        prevY = evt.getY();
    }

    /**
     * This method is called when the user right clicks on the canvas. It simple clears the
     * drawing area.
     */
    private void clear() {

        g.setFill( Color.WHITE );
        g.fillRect( 0, 0, canvas.getWidth(), canvas.getHeight() );
    }

    /**
     * This method is called when the user drags the mouse over the canvas.
     */
    private void mouseDragged( MouseEvent evt ) {

        // Make sure the user is dragging.
        if ( isDragging ) {
            System.out.println( "Is Dragging." );
            if ( Math.abs( evt.getX() - prevX ) > spaceBetweenFigures && Math.abs( evt.getY() - prevY ) > spaceBetweenFigures ) {
                System.out.println( "True" );
                draw( evt );

            }
            prevX = evt.getX();
            prevY = evt.getY();


        }
    }

    /**
     * This method is called when the user releases the mouse on the canvas. It simply sets isDragging = false.
     */
    private void mouseReleased( MouseEvent evt ) {
        isDragging = false;
    }

}

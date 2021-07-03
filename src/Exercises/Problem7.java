package Exercises;

/**
 * Author: Michael Allan Odhiambo
 * E-mail: michaelallanodhiambo@gmail.com
 */

/**
 * A polygon is a geometric figure made up of a sequence of connected line segments. The points
 * where the line segments meet are called the vertices of the polygon. Java has a list of shape-drawing
 * methods in a GraphicsContext. Among them are methods for stroking and for filling polygons:
 * g.strokePolygon( xcoords, ycoords, n ) and g.fillPolygon( xcoords, ycoords, n ). For these commands,
 * the coordinates of the vertices of the polygon are stored in arrays xcoords and ycoords of type
 * double[], and the number of vertices of the polygon is given by the third parameter, n. Note that it
 * is OK for the sides of a polygon to cross each other, but the interior of a polygon with self-intersections
 * might not be exactly what you expect.
 *
 * Write a program that lets the user draw polygons. As the user clicks a sequence of points in a Canvas,
 * count the points and store their x- and y- coordinates in two arrays. These points will be the vertices
 * of the polygon. As the user is creating the polygon, you should just connect all the points with line
 * segments. When the user clicks near the starting point, draw the complete polygon. Draw it with a red
 * interior and a black border. Once the user has completed a polygon, the next click should clear the data
 * and start a new polygon from scratch.
 */

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

public class Problem7 extends Application {

    private Canvas canvas;
    private GraphicsContext g;

    private int points;  // The number of vertices of the polygon.

    private ArrayList<Double> xCoordinates;
    private ArrayList<Double> yCoordinates;

    /**
     * Two arrays to store the x and y coordinates of the polygon. These are needed because the
     * function to draw the complete polygons accepts only arrays.
     */
    private double[] xCoordsArray;
    private double[] yCoordsArray;

    // These variables store the starting point of the polygon.
    private double startX;
    private double startY;

    // Set to true if the polygon has been drawn. Its set to false by default.
    private boolean polygonDrawn = false;


    public static void main( String[] args ) {
        launch( args );

    }

    public void start( Stage stage ) {

        canvas = new Canvas( 800, 800 );
        g = canvas.getGraphicsContext2D();

        xCoordinates = new ArrayList<>();
        yCoordinates = new ArrayList<>();

        canvas.setOnMouseClicked( e -> {
            mouseClicked( e );

        } );

        BorderPane root = new BorderPane( canvas );

        Scene scene = new Scene( root );
        stage.setScene( scene );
        stage.setTitle( "Paint Version 2.1" );
        stage.show();

    }

    private void getCoords( MouseEvent evt ) {
        double x = evt.getX();
        double y = evt.getY();

        xCoordinates.add( x );
        yCoordinates.add( y );

    }

    private void mouseClicked( MouseEvent evt ) {

        if ( polygonDrawn ) {
            reInitialize();
        }

        getCoords( evt );
        points++;

        System.out.println( points );

        if ( drawPolygon( evt ) ) {

            xCoordsArray = new double[points];
            yCoordsArray = new double[points];

            // Delete the last point.
            xCoordinates.remove( points - 1 );
            yCoordinates.remove( points - 1 );

            xCoordinates.add( startX );
            yCoordinates.add( startY );

            for ( int i = 0; i < xCoordinates.size(); i++ ) {
                xCoordsArray[i] = xCoordinates.get(i);
                yCoordsArray[i] = yCoordinates.get(i);
            }

            fillPolygon();
        }

        g.setStroke( Color.BLACK );

        // If this is the first point, initialize the variables appropriately.
        if ( points == 1 ) {
            startX = evt.getX();
            startY = evt.getY();
        }

        if ( points > 1 ) {
            g.strokeLine( xCoordinates.get( points - 2 ), yCoordinates.get( points - 2 ), xCoordinates.get( points - 1 ), yCoordinates.get( points - 1 ) );
        }
    }

    /**
     * This function draws the whole polygon and fills it with red.
     */
    private void fillPolygon() {
        polygonDrawn = true;
        g.setStroke( Color.BLACK );
        g.strokePolygon( xCoordsArray, yCoordsArray, points );

        g.setFill( Color.RED );
        g.fillPolygon( xCoordsArray, yCoordsArray, points );

    }

    /**
     * This method determines if the user has clicked close enough to the starting point
     * of the polygon. If true, the whole polygon is drawn again and filled with red.
     */
    private boolean drawPolygon( MouseEvent evt ) {
        if ( Math.abs( evt.getX() - startX ) < 5 && Math.abs( evt.getY() - startY ) < 5 )
            return true;

        return false;

    }

    /**
     * Re-initializes the variables after the polygon is drawn.
     */
    private void reInitialize() {
        polygonDrawn = false;
        g.setFill( Color.WHITE );
        g.fillRect( 0, 0, canvas.getWidth(), canvas.getHeight() );

        points = 0;
        xCoordinates.clear();
        yCoordinates.clear();

    }

}

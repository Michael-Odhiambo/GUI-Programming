package Exercises;

/**
 * Author: Michael Allan Odhiambo.
 * E-mail: michaelallanodhiambo@gmail.com
 */

/**
 * Write a program that shows a pair of dice. The dice are drawn on a Canvas. You can
 * assume that the size of the canvas is 100 by 100 pixels. When the user clicks on the
 * canvas, the dice should be rolled ( that is, the dice should be assigned newly computed
 * random values. ) Each dice should be drawn as a square showing from 1 to 6 dots.
 */

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.geometry.Pos;

public class Problem3 extends Application {

    private Canvas canvas;
    GraphicsContext g;
    int frameNumber;

    // Two dice to be used in the program.
    private Die die1, die2;

    public static void main( String[] args ) {
        launch( args );

    }

    public void start( Stage stage ) {

        canvas = new Canvas( 300, 300 );
        g = canvas.getGraphicsContext2D();
        g.setFill( Color.LIGHTBLUE );
        g.fillRect( 0, 0, canvas.getWidth(), canvas.getHeight() );

        draw();

        Button roll = new Button( "Roll" );
        roll.setOnAction( e -> timer.start() );

        BorderPane root = new BorderPane( canvas );
        root.setBottom( roll );
        BorderPane.setAlignment( roll, Pos.CENTER );

        Scene scene = new Scene( root );
        stage.setScene( scene );
        stage.setTitle( "Problem 3 " );
        stage.show();

        timer.start();

    }

    /**
     * This method draws the two dice.
     */
    private void draw() {

        // Create the dice.
        die1 = new Die();
        die2 = new Die();

        die1.drawDie( g, canvas.getWidth() - 120, 10 );
        die2.drawDie( g, 10, canvas.getHeight() - 120 );
    }

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            draw();
            frameNumber++;
            if ( frameNumber == 60 ) {
                timer.stop();
                frameNumber = 0;
            }

        }
    };

    /**
     * This nested class represents a die.
     */
    private class Die {

        private final int DIE_WIDTH = 100, DIE_HEIGHT = 100;
        private int value;  // The value of the die. A number between 1 - 6.
        private double xPos, yPos;  // The die's x and y position.

        /**
         * The constructor. When the die is constructed, it is given a random face value
         * between 1 to 6.
         */
        Die() {
            value = (int)( Math.random() * 6 ) + 1;
        }

        public int getValue() {
            return value;
        }

        /**
         * This method draws the die.
         */
        public void drawDie( GraphicsContext g, double x, double y ) {

            xPos = x;
            yPos = y;

            g.setFill( Color.WHITE );
            g.fillRect( xPos, yPos, DIE_WIDTH, DIE_HEIGHT );

            g.setStroke( Color.BLACK );
            g.strokeRect( x, y, DIE_WIDTH, DIE_HEIGHT );

            double originX = xPos - 5;
            double originY = yPos - 5;

            g.setFill( Color.BLACK );

            // Draw the dots.
            switch ( value ) {

                case 1:
                    g.fillOval( originX + DIE_WIDTH/2, originY + DIE_HEIGHT/2, 10, 10 );
                    break;

                case 2:
                    g.fillOval( originX + ( DIE_WIDTH - 85 ), originY + ( DIE_HEIGHT - 15 ), 10, 10 );
                    g.fillOval( originX + ( DIE_WIDTH - 15 ), originY + ( DIE_HEIGHT - 85 ), 10, 10 );
                    break;

                case 3:
                    g.fillOval( originX + DIE_WIDTH/2, originY + DIE_HEIGHT/2, 10, 10 );
                    g.fillOval( originX + ( DIE_WIDTH - 85 ), originY + ( DIE_HEIGHT - 15 ), 10, 10 );
                    g.fillOval( originX + ( DIE_WIDTH - 15 ), originY + ( DIE_HEIGHT - 85 ), 10, 10 );
                    break;

                case 4:
                    g.fillOval( originX + ( DIE_WIDTH - 85 ), originY + ( DIE_HEIGHT - 15 ), 10, 10 );
                    g.fillOval( originX + ( DIE_WIDTH - 15 ), originY + ( DIE_HEIGHT - 15 ), 10, 10 );
                    g.fillOval( originX + ( DIE_WIDTH - 15 ), originY + ( DIE_HEIGHT - 85 ), 10, 10 );
                    g.fillOval( originX + ( DIE_WIDTH - 85 ), originY + ( DIE_HEIGHT - 85 ), 10, 10 );
                    break;
                case 5:
                    g.fillOval( originX + DIE_WIDTH/2, originY + DIE_HEIGHT/2, 10, 10 );
                    g.fillOval( originX + ( DIE_WIDTH - 85 ), originY + ( DIE_HEIGHT - 15 ), 10, 10 );
                    g.fillOval( originX + ( DIE_WIDTH - 15 ), originY + ( DIE_HEIGHT - 15 ), 10, 10 );
                    g.fillOval( originX + ( DIE_WIDTH - 15 ), originY + ( DIE_HEIGHT - 85 ), 10, 10 );
                    g.fillOval( originX + ( DIE_WIDTH - 85 ), originY + ( DIE_HEIGHT - 85 ), 10, 10 );
                    break;

                case 6:
                    g.fillOval( originX + ( DIE_WIDTH - 85 ), originY + ( DIE_HEIGHT - 15 ), 10, 10 );
                    g.fillOval( originX + ( DIE_WIDTH - 85 ), originY + ( DIE_HEIGHT - 50 ), 10, 10 );
                    g.fillOval( originX + ( DIE_WIDTH - 15 ), originY + ( DIE_HEIGHT - 85 ), 10, 10 );
                    g.fillOval( originX + ( DIE_WIDTH - 85 ), originY + ( DIE_HEIGHT - 85 ), 10, 10 );
                    g.fillOval( originX + ( DIE_WIDTH - 15 ), originY + ( DIE_HEIGHT - 50 ), 10, 10 );
                    g.fillOval( originX + ( DIE_WIDTH - 15 ), originY + ( DIE_HEIGHT - 15 ), 10, 10 );
                    break;

            }

        }


    }
}

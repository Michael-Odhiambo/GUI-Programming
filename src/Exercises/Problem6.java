package Exercises;

/**
 * Author: Michael Allan Odhiambo.
 * E-mail: michaelallanodhiambo@gmail.com
 */

/**
 * Write a GUI program that uses the StatCalc class to compute and display statistics of numbers entered by the
 * user. The program will have an instance variable of type StatCalc that does the computations. The program
 * should include a TextField where the user enters a number. It should have four labels that display four
 * statistics for the numbers that have been entered: the number of, the sum, the mean, and the standard
 * deviation. Every time the user enters a new number, the statistics displayed on the labels should change.
 * The user enters a number by typing it into the TextField and then either clicking an "Enter" button or
 * pressing the Return ( or Enter ) key. There should be a "Clear" button that clears out all the data. This
 * means creating a new StatCalc object and changing the text that is displayed on the labels
 */

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.control.Label;

public class Problem6 extends Application {

    private StatCalc calculator;
    Label message;

    public void start( Stage stage ) {

        calculator = new StatCalc();
        message = new Label( "Enter a number, click enter." );
        message.setStyle( "-fx-background-color: white" );

        TilePane root = new TilePane();
        root.setPrefColumns( 3 );
        root.setStyle( "-fx-background-color: black" );
        root.getChildren().add( message );

        Scene scene = new Scene( root );

        stage.setScene( scene );
        stage.setTitle( "Simple Calc GUI" );
        stage.show();

    }

    /**
     * An object of class StatCalc can be used to compute several simple statistics for a set of
     * numbers. Numbers are entered into the dataset using the enter( double ) method. Methods are
     * provided to return the following statistics for the set of numbers that have been entered:
     * The number of items, the su of the items, the average, and the standard deviation.
     */
    private class StatCalc {

        int count;  // Number of numbers that have been entered.
        double sum;  // The sum of all the items that have been entered.
        double squareSum;  // The sum of the squares of all the items.

        /**
         * Add a number to the dataset. The statistics will be computed for all the numbers
         * that have been added to the dataset using this method.
         */
        void enter( double num ) {
            count++;
            sum += num;
            squareSum += num*num;
        }

        /**
         * Return the number of items that have been entered into the dataset.
         */
        int getCount() {
            return count;
        }

        /**
         * Return the sum of all the numbers that have been entered.
         */
        double getSum() {
            return sum;
        }

        /**
         * Return the average of all the items that have been entered. The return value
         * is Double.NaN if no numbers have been entered.
         */
        public double getMean() {
            return sum / count;
        }

        /**
         * Return the standard deviation of all the items that have been entered. The return value
         * is Double.NaN if no numbers have been entered.
         */
        public double getStandardDeviation() {
            double mean = getMean();
            return Math.sqrt( squareSum / count - mean*mean );

        }

    }
}

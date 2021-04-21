package HighLow;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This class implements a simple card game. The user sees a card and
 * tries to predict whether the next card will be higher or lower. Aces
 * are the lowest-valued cards. If the user makes three correct predictions,
 * the user wins. If not, the user loses.
 *
 * This class depends on several additional source code files:
 * Card.java, Hand.java, Deck.java and cards.png.
 */

public class HighLowGUI extends Application {

    private Canvas board;  // The canvas on which the cards and message are drawn.
    private Image cardImages;   // A single image contains the images of every card.

    private Deck deck;  // A deck of cards to be used in the game.
    private Hand hand;  // The cards that have been dealt so far.
    private String message;  // A message drawn on the canvas, which changes to reflect the state of the game.

    private boolean gameInProgress;  // Set to true when a game begins and to false when the game ends.

    public static void main( String[] args ) {
        launch( args );
    }

    public void start( Stage stage ) {

        cardImages = new Image("cards.jpg" );

        board = new Canvas( 4*99 + 20, 123 + 80 );  // Space for 4 cards, with 20-pixel border
                                                                 // and space on the bottom for a message.

        Button higher = new Button( "Higher" );
        Button lower = new Button( "Lower" );
        Button newGame = new Button( "New Game" );

        HBox buttonBar = new HBox( higher, lower, newGame );

        BorderPane root = new BorderPane();
        root.setCenter( board );
        root.setBottom( buttonBar );

        Scene scene = new Scene( root );
        stage.setScene( scene );
        stage.setTitle( "High/Low Game." );
        stage.setResizable( false );
        stage.show();

    }
}

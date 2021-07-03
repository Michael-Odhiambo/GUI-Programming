package HighLowGUI;

/**
 * Author: Michael Allan Odhiambo.
 * E-mail: michaelallanodhiambo@gmail.com
 */

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
 * This class implements a simple card game. The user sees a card and tries to predict
 * whether the next card will be higher or lower. Aces are the lowest-valued cards. If the
 * user makes three correct predictions, the user wins. If not, the user loses.
 *
 * This class depends on several additional source code files: Card.java, Hand.java, and
 * cards.png.
 */

public class HighLow extends Application {

    private Canvas board;  // The canvas on which cards and message are drawn.
    private Image cardImages;  // A single image contains the images of every card.
    private Deck deck;  // A deck of cards to be used in the game.
    private Hand hand;  // The cards that have been dealt so far.
    private String message;  // A message drawn on the canvas, which changes to reflect the state of the game.
    private boolean gameInProgress;  // Set to true when a game begins and to false when the game ends.

    public static void main( String[] args ) {
        launch( args );

    }

    /**
     * The start() method sets up the GUI and event handling. The root pane is a BorderPane. A canvas
     * where cards are displayed occupies the center position of the BorderPane. On the bottom is a
     * HBox that holds three buttons. ActionEvent handlers are set up to call methods defined elsewhere
     * in this class when the user clicks a button.
     */
    public void start( Stage stage ) {

        cardImages = new Image( "HighLowGUI/cards.png" );

        board = new Canvas( 4*99 + 20, 123 + 80 ); // Space for 4 cards, with 20-pixel border
                                                                // and space on the bottom for a message.

        Button higher = new Button( "Higher" );
        higher.setOnAction( e -> doHigher() );
        Button lower = new Button( "Lower" );
        lower.setOnAction( e -> doLower() );
        Button newGame = new Button( "New Game" );
        newGame.setOnAction( e -> doNewGame() );

        HBox buttonBar = new HBox( higher, lower, newGame );

        /**
         * Improve the layout of the buttonBar. Without adjustment the buttons will be
         * grouped at the left end of the HBox. My solution is to set their preferred
         * widths to make them all the same size and make them fill the entire HBox.
         */
        higher.setPrefWidth( board.getWidth()/3.0 );
        lower.setPrefWidth( board.getWidth()/3.0 );
        newGame.setPrefWidth( board.getWidth()/3.0 );

        BorderPane root = new BorderPane();
        root.setCenter( board );
        root.setBottom( buttonBar );

        doNewGame();  // Set up for the first game.

        Scene scene = new Scene( root );
        stage.setScene( scene );
        stage.setTitle( "High/Low Game" );
        stage.setResizable( false );
        stage.show();

    }

    /**
     * Called by an event handler when the user clicks "Higher" button. Check the user's
     * prediction. Game ends if user guessed wrong or if the user has made three correct
     * predictions.
     */
    private void doHigher() {

        if ( gameInProgress == false ) {
            // If the game has ended, it was an error to click "Higher", so set up an error
            // message and abort processing.
            message = " Click \"New Game\" to start a new game!";
            drawBoard();
            return;

        }
        hand.addCard( deck.dealCard() );  // Deal a card to the hand.
        int cardCount = hand.getCardCount();
        Card thisCard = hand.getCard( cardCount - 1 );  // The Card just dealt.
        Card prevCard = hand.getCard( cardCount - 2 );  // The previous card.

        if ( thisCard.getValue() < prevCard.getValue() ) {
            gameInProgress = false;
            message = "Too bad! You lose.";

        }
        else if ( thisCard.getValue() == prevCard.getValue() ) {
            gameInProgress = false;
            message = "Too bad! You lose on ties.";

        }
        else if ( cardCount == 4 ) {
            gameInProgress = false;
            message = "You win! You made three correct guesses.";

        }
        else {
            message = "Got it right! Try for " + cardCount + ".";

        }
        drawBoard();

    }

    /**
     * Called by an event handler when the user clicks "Lower" button. Check the user's
     * prediction. Game ends if the user guessed wrong or if the user has made three
     * correct predictions.
     */
    private void doLower() {

        if ( gameInProgress == false ) {
            // If the game has ended, it was an error to click "Lower", so
            // set up an error message and abort processing.
            message = "Click \"New Game\" to start a new game!";
            drawBoard();
            return;

        }
        hand.addCard( deck.dealCard() );  // Deal a card to the hand.
        int cardCount = hand.getCardCount();
        Card thisCard = hand.getCard( cardCount - 1 );  // Card just dealt.
        Card prevCard = hand.getCard( cardCount - 2 );  // The previous card.

        if ( thisCard.getValue() > prevCard.getValue() ) {
            gameInProgress = false;
            message = "Too bad! You lose.";

        }
        else if ( thisCard.getValue() == prevCard.getValue() ) {
            gameInProgress = false;
            message = "Too bad! You lose on ties.";

        }
        else if ( cardCount == 4 ) {
            gameInProgress = false;
            message = "You win! You made three correct guesses.";

        }
        else {
            message = "Got it right! Try for " + cardCount + ".";

        }
        drawBoard();

    }

    /**
     * Called by the start() method, and called by an event handler if the user clicks the
     * the "New Game" button. Start a new game.
     */
    private void doNewGame() {

        if ( gameInProgress ) {
            // If the current game is not over, it is an error to try to start a new game.
            message = "You still have to finish this game!";
            drawBoard();
            return;

        }
        deck = new Deck();  // Create the deck and hand to use for this game.
        hand = new Hand();
        deck.shuffle();
        hand.addCard( deck.dealCard() );  // Deal the first card into the hand.

        message = "Is the next card higher or lower?";
        gameInProgress = true;
        drawBoard();

    }

    /**
     * This method draws the message at the bottom of the canvas, and it draws all of the dealt
     * cards spread out across the canvas. If the game is in progress, an extra card is drawn face
     * down representing the card to be dealt.
     */
    private void drawBoard() {

        GraphicsContext g = board.getGraphicsContext2D();
        g.setFill( Color.DARKGREEN );
        g.fillRect( 0, 0, board.getWidth(), board.getHeight() );
        g.setFill( Color.rgb( 220, 255, 220 ) );
        g.setFont( Font.font( 16 ) );
        g.fillText( message, 20, 180 );

        int cardCount = hand.getCardCount();
        for ( int i = 0; i < cardCount; i++ )
            drawCard( g, hand.getCard(i), 20 + i * 99, 20 );

        if ( gameInProgress )
            drawCard( g, null, 20 + cardCount * 99, 20 );

    }

    /**
     * Draws a card with top-left corner at ( x, y ). If card is null, then a face-down
     * card is drawn. The card images are from the file cards.png; this program will fail without
     * it.
     */
    private void drawCard( GraphicsContext g, Card card, int x, int y ) {

        int cardRow, cardCol;

        if ( card == null ) {
            cardRow = 4;  // row and column of a face down card.
            cardCol = 2;

        }
        else {
            cardRow = 3 - card.getSuit();
            cardCol = card.getValue() - 1;

        }
        double sx, sy;  // top left corner of the source rectangle for card in cardImages.
        sx = 79 * cardCol;
        sy = 123 * cardRow;
        g.drawImage( cardImages, sx, sy, 79, 123, x, y, 79, 123 );

    }
}

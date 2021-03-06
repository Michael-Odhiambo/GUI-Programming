package Exercises;

/**
 * Author: Michael Allan Odhiambo.
 * E-mail: michaelallanodhiambo@gmail.com
 */

/**
 * Write a GUI Blackjack program that lets the user play a game of Blackjack, with the computer
 * as the dealer. The program should raw the user's cards and the dealer's cards. Add the following
 * rule to the description:
 *
 * If a player takes five cards without going over 21, that player wins immediately. This rule is used
 * in some casinos. For your program, it means that you only have to allow room for five cards. You should
 * make the canvas just wide enough to show five cards, and tall enough to show both the user's hand and
 * the dealer's hand.
 *
 * Note that the design of the GUI Blackjack game is very different from the design of the text-oriented
 * program that you wrote previously. The user should play the game by clicking on "Hit" and "Stand"
 * buttons. There should be a "New Game" button that can be used to start another game after one game ends.
 * You have to decide what happens when each of these buttons is pressed. You don't have much chance of
 * getting this right unless you think in terms of the states that the game can be in and how the state can
 * change.
 *
 * Your program will need the classes defined in Card.java, Hand.java, Deck.java and BlackjackHand.java. It
 * will also need the images file cards.png, which contains pictures of the cards.
 */

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

public class Problem8 extends Application {

    private Canvas canvas;
    private GraphicsContext g;
    private Image cardImages;

    private Button hit;
    private Button stand;
    private Button newGame;
    private TextField yourBet;

    private Label bet;
    private HBox buttonBar;
    private BorderPane root;

    private Deck deck;
    private BlackjackHand dealer;
    private BlackjackHand player;
    private Hand winner;

    private boolean gameInProgress;
    private boolean hasStood;  // The user has pressed the stand button.

    public static void main( String[] args ) {
        launch( args );
    }

    public void start( Stage stage ) {
        createLayout();
        initialize();
        draw();

        gameInProgress = true;

        newGame.setOnAction( e -> doNewGame() );
        hit.setOnAction( e -> doHit() );
        stand.setOnAction( e -> doStand() );

        Scene scene = new Scene( root );
        stage.setScene( scene );
        stage.setTitle( "Black Jack" );
        stage.show();

    }

    private void doNewGame() {
        initialize();
        draw();

    }

    /**
     * This method creates the layout and buttons.
     */
    private void createLayout() {

        canvas = new Canvas( 800, 600 );
        g = canvas.getGraphicsContext2D();

        cardImages = new Image( "Exercises/cards.png" );

        hit = new Button( "Hit!" );
        stand = new Button( "Stand!" );
        newGame = new Button( "New Game" );
        bet = new Label( "Your bet: " );
        yourBet = new TextField();

        buttonBar = new HBox( hit, stand, newGame, bet, yourBet );
        buttonBar.setAlignment( Pos.CENTER );

        root = new BorderPane( canvas );
        root.setBottom( buttonBar );
        buttonBar.setSpacing( 10 );
        buttonBar.setPrefHeight( 40 );


    }

    /**
     * Initializes all the variables.
     */
    private void initialize() {

        gameInProgress = true;
        hasStood = false;

        dealer = new BlackjackHand();
        player = new BlackjackHand();

        deck = new Deck();
        deck.shuffle();

        dealFirstTwoCards();
        newGame.setDisable( true );
        hit.setDisable( false );
        stand.setDisable( false );

    }

    private void winnerExists() {

        gameInProgress = false;
        newGame.setDisable(false);
        hit.setDisable(true);
        stand.setDisable(true);

    }

    /**
     * This method deals the first two cards to each player. First, tow cards are dealt into each player's
     * hand. If the dealer's hand has a value of 21 at this point, then the dealer wins. Otherwise, if the
     * user has 21, then the user wins. ( This is called a "Blackjack". ) Note that the dealer wins on a tie,
     * so if both players have Blackjack, then the dealer wins.
     */
    private void dealFirstTwoCards() {
        /**
         * Deal two cards to each player.
         */
        for (int i = 0; i < 2; i++) {
            dealer.addCard(deck.dealCard());
            player.addCard(deck.dealCard());

        }

        // If the dealer has won, take the necessary action.
        if ( hasWon( dealer ) ) {
            winnerExists();
            draw();


        } else if ( hasWon( player ) ) {
            winnerExists();
            draw();

        }

    }

    /**
     * Draws a card with top-left corner at ( x, y ). If card is null, then a face-down
     * card is drawn. The card images are from the file cards.png; this program will fail without
     * it.
     */
    private void drawCard( GraphicsContext g, Exercises.Card card, int x, int y ) {

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

    /**
     * This method draws the cards in each of the player's hand.
     */
    private void draw() {

        g.setFill( Color.GREEN );
        g.fillRect( 0, 0, canvas.getWidth(), canvas.getHeight() );

        g.setStroke( Color.WHITE );
        g.strokeText( "Dealer's Cards:", 35, 50 );
        g.strokeText( "Your Cards:", 35, 300 );

        if ( gameInProgress )
            g.strokeText( "Game in progress..", 35, 580 );
        else {
            if ( winner == dealer )
                g.strokeText( "The Dealer wins!!!", 35, 580 );

            else
                g.strokeText( "You win!!!", 35, 580 );
        }

        for ( int i = 0; i < dealer.getCardCount(); i++ ) {
            drawCard( g, dealer.getCard(i), 35 + 123 * i, 80 );

            if ( i == 0 ) {
                if ( !hasStood ) {
                    drawCard(g, null, 35 + 123 * i, 80);
                }
            }

        }

        for ( int i = 0; i < player.getCardCount(); i++ ) {
            drawCard( g, player.getCard(i), 35 + 123 * i, 360 );
        }
    }

    /**
     * This method determines if either player has won the game.
     */
    private boolean hasWon( BlackjackHand hand ) {
        if ( hand.getBlackjackValue() == 21 ) {
            winner = hand;

            return true;
        }

        return false;
    }

    /**
     * Now if the game has not ended, the user gets a chance to add some cards to his/her hand.
     * In this phase, the user sees her own cards and sees one of the dealer's two cards. ( In a
     * casino, the dealer deals himself one card face up and one card face down. ) All the user's
     * cards are dealt face up. ) The user makes a decision whether to "Hit", which means to add
     * another card to her hand, or to "Stand", which means to stop taking cards.
     *
     * If the user Hits, there is a possibility that the user will go over 21. In that case, the game
     * is over and the user loses. If not, then the process continues. The user gets to decide again
     * whether to Hit or Stand.
     */
    private void doHit() {

        player.addCard( deck.dealCard() );
        draw();

        if ( hasWon( player ) ) {

            winnerExists();
            draw();

        }
        else if ( player.getBlackjackValue() > 21 ) {
            winner = dealer;
            winnerExists();
            draw();

        }

    }

    /**
     * If the user Stands, the game will end, but first the dealer gets a chance to draw cards. The dealer
     * only follows rules, without any choice. The rule is that, as long as the value of the dealer's hand
     * is less than or equal to 16, the dealer Hits ( that is, takes another card ). The user should see all
     * the dealers cards at this point. Now, the winner can be determined: if the dealer has gone over 21, the
     * user wins. Otherwise, if the dealer's total is greater than or equal to the user's total, then the dealer
     * wins. Otherwise, the user wins.
     */
    private void doStand() {
        hasStood = true;

        while ( dealer.getBlackjackValue() < 16 ) {
            dealer.addCard( deck.dealCard() );
            draw();
        }
        if ( dealer.getBlackjackValue() > 21 ) {
            winner = player;
            winnerExists();

        }
        else if ( dealer.getBlackjackValue() >= player.getBlackjackValue() ) {
            winner = dealer;
            winnerExists();
        }
        else {
            winner = player;
            winnerExists();
        }
        draw();

    }
}

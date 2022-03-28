/*
Jose M. Aguilar (jaguil61)
MainActivity.java
*/

package edu.jaguil61.project4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TextView> boardPositions;
    private PlayerInfo playerX;
    private PlayerInfo playerO;
    public static final int PLAYER_X = -1;
    public static final int PLAYER_O = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.startButton);
        textViewSetup();

        startButton.setOnClickListener((v) -> startGame());
    }

    //This creates the array that holds all the views
    private void textViewSetup ()
    {
        boardPositions = new ArrayList<>();

        TextView position0 = findViewById(R.id.position0);
        boardPositions.add(position0);

        TextView position1 = findViewById(R.id.position1);
        boardPositions.add(position1);

        TextView position2 = findViewById(R.id.position2);
        boardPositions.add(position2);

        TextView position3 = findViewById(R.id.position3);
        boardPositions.add(position3);

        TextView position4 = findViewById(R.id.position4);
        boardPositions.add(position4);

        TextView position5 = findViewById(R.id.position5);
        boardPositions.add(position5);

        TextView position6 = findViewById(R.id.position6);
        boardPositions.add(position6);

        TextView position7 = findViewById(R.id.position7);
        boardPositions.add(position7);

        TextView position8 = findViewById(R.id.position8);
        boardPositions.add(position8);
    }

    //This method clears the board
    private void clearBoard() {
        TextView currentPosition;

        for (int i = 0; i < boardPositions.size(); i++) {
            currentPosition = boardPositions.get(i);
            currentPosition.setText("");
        }
    }

    //This will start the threads that start the game
    private void startGame() {
        clearBoard();

        playerX = new PlayerInfo("X");
        playerO = new PlayerInfo("O");

        PlayerInfo currentPlayer = playerX;

        for (int i = 0; i < 9; i++) {
            Log.i("PLAYER", "Current Player: " + currentPlayer.getSymbol());

            if (currentPlayer.getSymbol().equals("X")) {
                Thread threadX = new Thread(new PlayerThread(playerX, boardPositions));
                threadX.start();

                //switch player
                currentPlayer = playerO;
            }

            else {
                Thread threadO = new Thread(new PlayerThread(playerO, boardPositions));
                threadO.start();

                currentPlayer = playerX;
            }
        }

        /*
        TODO: Create another thread that will check if the board is full and if so it
         will display a message saying who won
        */
    }

    //This handler is used to communicate to the main
    private final Handler mainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            int playerSymbol = msg.what;
            int positionNum = msg.arg1;

            switch (playerSymbol) {
                case PLAYER_X:
                    boardPositions.get(positionNum).setText("X");
                    playerX.setFinishedTurn(Boolean.TRUE); //player x finished
                    playerO.setFinishedTurn(Boolean.FALSE); //player o is ready to go
                    break;
                case PLAYER_O:
                    boardPositions.get(positionNum).setText("O");
                    playerO.setFinishedTurn(Boolean.TRUE); //player o finished
                    playerX.setFinishedTurn(Boolean.FALSE); //player x is ready to go
            }
        }
    };

    class PlayerThread implements Runnable {
        private PlayerInfo currentPlayer;
        private ArrayList<TextView> boardInfo;

        public PlayerThread (PlayerInfo thePlayer, ArrayList<TextView> theBoard) {
           currentPlayer = thePlayer;
           boardInfo = theBoard;
        }

        //choose the initial first move
        @Override
        public void run() {
            Log.i("THREAD", "Current Thread: " + currentPlayer.getSymbol());

           chooseMoves();
        }

        synchronized void chooseMoves() {
            int positionNum = -1;
            Message msg;

            if (currentPlayer.getSymbol().equals("X")) {
                positionNum = currentPlayer.chooseXMove();

                msg = mainHandler.obtainMessage(PLAYER_X);
            }

            else {
                positionNum = currentPlayer.chooseOMove(boardPositions);

                msg = mainHandler.obtainMessage(PLAYER_O);
            }

            //check if that spot is already taken
            for(int i = 0; i < boardInfo.size(); i++)
            {
                if (boardInfo.get(positionNum).equals("X") || boardInfo.get(positionNum).equals("O")) //spot is already taken
                    chooseMoves(); //choose another spot

                else
                    break;
            }

            msg.arg1 = positionNum;

            mainHandler.sendMessage(msg);
        }

    }

}
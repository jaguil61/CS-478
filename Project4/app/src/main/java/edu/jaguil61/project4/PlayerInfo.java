/*
Jose M. Aguilar (jaguil61)
PlayerInfo.java
 */

package edu.jaguil61.project4;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

//This stores all the info of the player
public class PlayerInfo {

    private String playerSymbol; //Either X or 0
    private Boolean finishedTurn; //Did the player finish their turn?

    public PlayerInfo(String theSymbol) {
        playerSymbol = theSymbol;
        finishedTurn = false;
    }

    public String getSymbol() {
        return playerSymbol;
    }

    public void setFinishedTurn (Boolean finished) {
        finishedTurn = finished;
    }

    public boolean getFinishedTurn() {
        return finishedTurn;
    }

    //chooses the move for the X player
    public int chooseXMove() {
        int positionNum = -1;

        Random rand = new Random();
        positionNum = rand.nextInt(9); //random number from 0-8

        return positionNum;
    }

    //chooses the move for the O player
    public int chooseOMove(ArrayList<TextView> currentPositions) {
        int positionNum = -1;

        //look for an empty spot on the board
        for (int i = 0; i < currentPositions.size(); i++) {
            if (currentPositions.get(i).getText() == ""){
                positionNum = i;
                return positionNum;
            }

            //else keep going
        }

        return positionNum; //no more spots available
    }
}

package com.example.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //enum helps us make our own data type (like int,float..etc.)
    enum Player {
         ONE, TWO
    }

    Player playerPlaying = Player.ONE;
    Player[] playerChoice = new Player[9];

    int[][] whoWon = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {6,4,2}};
    String winner;

    private boolean isGameOver;
    Button resetBtn;

    private GridLayout gridLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void imageViewIsTapped(View imageView){
        //This is casting, which says that we are sure that the tapped component is an ImageView.
        ImageView tappedImage = (ImageView) imageView;



        //We are using the image tags instead of index, to use this later on as cell numbers to decide the winner
        int imageTag = Integer.parseInt(tappedImage.getTag().toString());

        //Checking if the cell has already been clicked or if the game is finished
        if(playerChoice[imageTag] != null || isGameOver){ return; }

        playerChoice[imageTag] = playerPlaying;

        //The image will be outside of the screen until the box is clicked
        tappedImage.setTranslationX(-2000);


        if(playerPlaying == Player.ONE) {
            tappedImage.setImageResource(R.drawable.xforgame);
            playerPlaying = Player.TWO;
        } else {
            tappedImage.setImageResource(R.drawable.oforgame);
            playerPlaying = Player.ONE;
        }
        tappedImage.animate().translationXBy(2000).alpha(1).rotation(3600).setDuration(500);



        //This will check the cells every time one of them is clicked, to see if there is a winner
        for (int[] column: whoWon) {
            if(playerChoice[column[0]]!= null
                    && playerChoice[column[0]] == playerChoice[column[1]]
                    && playerChoice[column[1]]==playerChoice[column[2]]){

                if(playerPlaying == Player.ONE) {
                    winner = "Player Two WINS !!";
                }else if(playerPlaying == Player.TWO){
                    winner = "Player One WINS !!";
                }

                isGameOver = true;
                Toast.makeText(this, winner + "", Toast.LENGTH_LONG).show();


                resetBtn = findViewById(R.id.resetBtn);
                gridLayout = findViewById(R.id.thegrid);
                resetBtn.setVisibility(View.VISIBLE);
                resetBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resetIt();
                    }
                });

            }
        } Tie();





    }


    public void resetIt(){
        //Resetting the game, setting everything back to null
        for (int index = 0; index < gridLayout.getChildCount(); index++){
            ImageView imageView = (ImageView) gridLayout.getChildAt(index);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.5f);
            playerChoice[index] = null;
            resetBtn.setVisibility(View.GONE);
            isGameOver=false;
            playerPlaying = Player.ONE;

        }
    }

    public void Tie(){
        //Checks if all the cells have been chosen or not
        for(int i = 0; i < playerChoice.length; i++) {
            if (playerChoice[i] == null || isGameOver) {
                return;
            }
        }
        //If they were all chosen and game was not over yet, it will show that it is a Tie
            isGameOver = true;
            Toast.makeText(this, "It Is A Tie", Toast.LENGTH_LONG).show();


            resetBtn = findViewById(R.id.resetBtn);
            gridLayout = findViewById(R.id.thegrid);
            resetBtn.setVisibility(View.VISIBLE);
            resetBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetIt();
                }
            });
    }

}


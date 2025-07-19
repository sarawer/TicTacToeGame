package com.example.tictacgame;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    boolean gameActive = true;
    //player representation
    //0 - X
    //1 - O
    int activePlayer = 0;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    // 0 - X, 1 - O, 2 - Null

    int[][] winPositions = {
            {0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}
    };

    public void taptap(View view) {
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());

        if (!gameActive) {
            gameReset(view);
        }

        if (gameState[tappedImage] == 2 && gameActive) {
            gameState[tappedImage] = activePlayer;
            img.setAlpha(0f);
            img.setImageResource(activePlayer == 0 ? R.drawable.x : R.drawable.zero);
            img.animate().alpha(1f).setDuration(150);

            activePlayer = (activePlayer == 0) ? 1 : 0;
            TextView textView2 = findViewById(R.id.textView2);
            textView2.setText(activePlayer == 0 ? "X's Turn - Tap to play" : "O's Turn - Tap to play");
        }


        // Check for winner
        for (int[] winPosition : winPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != 2) {

                // Someone has won
                gameActive = false;
                String winnerStr = (gameState[winPosition[0]] == 0) ? "X has won" : "O has won";

                TextView textView2 = findViewById(R.id.textView2);
                textView2.setText(winnerStr);

                new android.os.Handler().postDelayed(() -> gameReset(null), 1500);
            }
        }
        boolean draw = true;
        for (int state : gameState) {
            if (state == 2) {
                draw = false;
                break;
            }
        }

        if (draw) {
            gameActive = false;
            TextView textView2 = findViewById(R.id.textView2);
            textView2.setText("It's a Draw!");
            new android.os.Handler().postDelayed(() -> gameReset(null), 1500);
        }
    }

    public void gameReset(View view) {
        gameActive = true;
        activePlayer = 0;

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        ((ImageView) findViewById(R.id.imageView0)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView8)).setImageResource(0);

        TextView textView2 = findViewById(R.id.textView2);
        textView2.setText("X's Turn - Tap to play");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}

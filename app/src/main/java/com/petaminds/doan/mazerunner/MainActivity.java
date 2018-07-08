package com.petaminds.doan.mazerunner;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.petaminds.doan.mazerunner.game.GameActionListener;
import com.petaminds.doan.mazerunner.game.GamePanelView;
import com.petaminds.doan.mazerunner.utils.Constants;
import com.petaminds.doan.mazerunner.widget.GameOverDialog;
import com.petaminds.doan.mazerunner.widget.StartDialog;

public class MainActivity extends AppCompatActivity implements Runnable, GameActionListener {

    private GamePanelView game;
    private TextView textView;
    private boolean playing;
    private StartDialog startDialog;
    private GameOverDialog goDialog;

    private Handler gameHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            showGameOverDialog();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Constants.SCREEN_WIDTH = displayMetrics.widthPixels;
        Constants.SCREEN_HEIGHT = displayMetrics.heightPixels;

        game = findViewById(R.id.game);
        textView = findViewById(R.id.score);
        Typeface maze_font = Typeface.createFromAsset(getAssets(), "fonts/zorque.ttf");
        textView.setTypeface(maze_font);
        //textView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void start() {
        playing = true;
        textView.setVisibility(View.VISIBLE);
        new Thread(this).start();
        game.start();
    }

    public GamePanelView getGame() {
        return game;
    }

    @Override
    public void stop() {
        game.stop();
        playing = false;
        showGameOverDialog();
    }


    @Override
    protected void onResume() {
        super.onResume();
        showStartDialog();
    }

    private void showStartDialog() {
         if(startDialog == null){
             startDialog = new StartDialog(this);
         }

         if(!startDialog.isShowing())

         {
             startDialog.show();
         }
    }

    private void showGameOverDialog() {
         if(goDialog == null){
            goDialog = new GameOverDialog(this);
        }

        if(!goDialog.isShowing())

        {
            goDialog.show();
        }
    }


    @Override
    public void run() {
        while (playing) {
            synchronized (game) {
                try {
                    game.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (game != null) {
                String score = "SCORE: " + game.getScore();
                textView.setText(score);
            }

            if (game.isGameover()) {
                gameHandler.sendEmptyMessage(0);
            }

        }

    }

    @Override
    public void onBackPressed() {
        stop();
    }
}

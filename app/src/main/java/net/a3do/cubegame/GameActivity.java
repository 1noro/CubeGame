package net.a3do.cubegame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import net.a3do.cubegame.controller.UserPreferences;
import net.a3do.cubegame.view.MySurfaceView;

import java.util.Objects;

public class GameActivity extends AppCompatActivity {

    public static final String LEVEL_TAG = "level";

    private int level;

    private MySurfaceView mySurfaceView;
    private TextView startText;
    private LinearLayout deadTextLayout;
    private TextView deadText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Intent intent = getIntent();
        level = intent.getIntExtra(LEVEL_TAG, 1);

        setContentView(R.layout.activity_game);
        this.mySurfaceView = findViewById(R.id.my_surface_view);

        this.startText = findViewById(R.id.start_text);
        this.deadTextLayout = findViewById(R.id.dead_text_layout);
        this.deadText2 = findViewById(R.id.dead_text2);

        this.mySurfaceView.setOnClickListener(v -> {
            if (!this.mySurfaceView.getMainLoop().isPlaying()) {
                MySurfaceView mySurfaceView = (MySurfaceView) v;
                // si pongo * 10 el nivel 3 hace que se pase de largo al chocar
                mySurfaceView.setPixelInc(level * 2); // (recomendado 2)
                // con 60 bolas peta
                mySurfaceView.setBallNumber(level * 2); // (recomendado 2)
                if (startText.getVisibility() == View.VISIBLE) {
                    startText.setVisibility(View.GONE);
                }
                if (deadTextLayout.getVisibility() == View.VISIBLE) {
                    deadTextLayout.setVisibility(View.GONE);
                }
                mySurfaceView.startGame();
                // sonido de inicio
//                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.arcade_bonus_229);
//                mediaPlayer.start();
            }
        });

    }

    public void showDeadText(int new_score) {
        if (deadTextLayout.getVisibility() == View.GONE) {
            int best_score = UserPreferences.getInstance().getBestScore(this, level);
            String text;
            if (best_score >= new_score) {
                text = getString(R.string.dead_text2_part1) + new_score + getString(R.string.dead_text2_part2) + best_score;
            } else {
                text = getString(R.string.dead_text2_new_record) + new_score;
            }
            runOnUiThread(() -> {
                deadText2.setText(text);
                deadTextLayout.setVisibility(View.VISIBLE);
            });
            // hago esto aquí para que no tarde nada en mostrarse el mensaje (puede que no sea necesario)
            if (new_score > best_score) {
                UserPreferences.getInstance().setNewScore(this, level, new_score);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mySurfaceView.getMainLoop().setPlaying(false);
    }
}
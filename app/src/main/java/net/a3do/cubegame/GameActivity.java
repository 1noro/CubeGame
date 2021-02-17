package net.a3do.cubegame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import net.a3do.cubegame.view.MySurfaceView;

import java.util.Objects;

public class GameActivity extends AppCompatActivity {

    public static final String LEVEL_TAG = "level";
    private MySurfaceView mySurfaceView;
    private TextView textInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Intent intent = getIntent();
        int level = intent.getIntExtra(LEVEL_TAG, 1);

        setContentView(R.layout.activity_game);
        this.mySurfaceView = findViewById(R.id.my_surface_view);
        // this.mySurfaceView.setPixelInc(level * 2); // (2) si pongo * 10 el nivel 3 hace que se pase de largo al chocar

        this.textInfo = findViewById(R.id.text_info);

        this.mySurfaceView.setOnClickListener(v -> {
            if (!this.mySurfaceView.getMainLoop().isPlaying()) {
                MySurfaceView mySurfaceView = (MySurfaceView) v;
                // si pongo * 10 el nivel 3 hace que se pase de largo al chocar
                mySurfaceView.setPixelInc(level * 2); // (recomendado 2)
                // con 60 bolas peta
                mySurfaceView.setBallNumber(level * 2); // (recomendado 2)
                textInfo.setVisibility(View.GONE);
                mySurfaceView.startGame();
            }
        });

//        Toast.makeText(this, "click to start", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mySurfaceView.getMainLoop().setPlaying(false);
    }
}
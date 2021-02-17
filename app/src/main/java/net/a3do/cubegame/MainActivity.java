package net.a3do.cubegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        Button buttonEasy = findViewById(R.id.button_easy);
        Button buttonMedium = findViewById(R.id.button_medium);
        Button buttonHard = findViewById(R.id.button_hard);

        addClickListener(buttonEasy,1);
        addClickListener(buttonMedium,2);
        addClickListener(buttonHard,3);
    }

    public void addClickListener(final View v, final int level) {
        v.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
            intent.putExtra(GameActivity.LEVEL_TAG, level);
            startActivity(intent);
        });
    }

}
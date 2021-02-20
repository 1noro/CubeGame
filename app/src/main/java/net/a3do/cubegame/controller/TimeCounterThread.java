package net.a3do.cubegame.controller;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

// TimeCounterThread, hilo de ejecución que se encarga de actulizar el contador de segundos
// durante el juego
public class TimeCounterThread extends Thread {

    private MainLoop mainLoop;
    private int seconds;

    public TimeCounterThread(MainLoop mainLoop) {
        this.mainLoop = mainLoop;
        this.seconds = 0;
    }

    public int getSeconds() {
        return seconds;
    }

    @Override
    public void run() {
        try {
            while(mainLoop.isPlaying()) {
                Thread.sleep(1000);
                seconds++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            // Si el thread se interrumpe, se para de contar
            Log.d("TimeCounterThread", "Interrupted");
        }
    }
}


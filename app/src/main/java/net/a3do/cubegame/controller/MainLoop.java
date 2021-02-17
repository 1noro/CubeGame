package net.a3do.cubegame.controller;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import net.a3do.cubegame.view.MySurfaceView;

public class MainLoop extends Thread {

    private boolean isPlaying = false;
    private final SurfaceHolder surfaceHolder;
    private final MySurfaceView mySurfaceView;

    public MainLoop(SurfaceHolder surfaceHolder, MySurfaceView mySurfaceView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.mySurfaceView = mySurfaceView;
    }

    public void setPlaying(boolean playing) {
        this.isPlaying = playing;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    @Override
    public void run() {
        Canvas canvas = null;
        while (isPlaying) {
            try {
                //Obtener el canvas de la vista
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.mySurfaceView.update();
                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}

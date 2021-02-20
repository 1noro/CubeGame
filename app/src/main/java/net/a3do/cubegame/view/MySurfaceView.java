package net.a3do.cubegame.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import net.a3do.cubegame.GameActivity;
import net.a3do.cubegame.controller.MainLoop;
import net.a3do.cubegame.controller.TimeCounterThread;
import net.a3do.cubegame.model.AnimatedRectangle;
import net.a3do.cubegame.model.Direction;
import net.a3do.cubegame.model.Point;
import net.a3do.cubegame.model.Rectangle;
import net.a3do.cubegame.model.Square;

import java.util.ArrayList;
import java.util.List;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

     private final Context context;

    private int squareSize; // tamaño del lado de cada cuadrado (50)
    private final int canvasSquareWidth = 15; // 25
    private int canvasSquareHeight;

    private static final long FPS = 35; // 1, 15, 35 (recomendado), 60
    private static final long MILLIS_PER_SECOND = 1000; // valor de un segundo em milisegundos
    private long nextFrameTime; // control pausing between updates

    private Integer pixelInc = 0; // pixelIncrement (Velocity) (no debería ser superior a 6 u 8)
    private int ballNumber = 2;

    private MainLoop mainLoop;
    private TimeCounterThread timeCounterThread;

    // Elementos del tablero
    private final List<AnimatedRectangle> ballList;
    private final List<Rectangle> wallList;
    private Rectangle player;

    private boolean isDead = false;

    // ---------------------------------------------------------------------------------------------

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this); // asignamos esta misma clase para el callback
        this.context = context;
        mainLoop = new MainLoop(getHolder(), this);
        wallList = new ArrayList<>();
        ballList = new ArrayList<>();
    }

    public MainLoop getMainLoop() {
        return mainLoop;
    }

    public void setPixelInc(int pixelInc) {
        this.pixelInc = pixelInc;
    }

    public Integer getPixelInc() {
        return pixelInc;
    }

    public void setBallNumber(int ballNumber) {
        this.ballNumber = ballNumber;
    }

    // ---------------------------------------------------------------------------------------------

    public Point getRandomPointNotBorderWall() {
        int margin = 10;

        int minX = squareSize + margin;
        int maxX = canvasSquareWidth *  squareSize - squareSize - margin;

        int minY = squareSize + margin;
        int maxY = canvasSquareHeight * squareSize - squareSize - margin;

        int x = minX + (int) (Math.random() * ((maxX - minX) + 1));
        int y = minY + (int) (Math.random() * ((maxY - minY) + 1));

        // comprobamos si está en el medio
        int centerWidth = (canvasSquareWidth * squareSize) / 2;
//        int centerHeight = (canvasSquareHeight * squareSize) / 2;

        if (x >= centerWidth - squareSize * 2 && x < centerWidth + squareSize * 2) {
            if (Math.random() > 0.5) {
                x += squareSize * 2.5;
            } else {
                x -= squareSize * 2.5;
            }
        }

        return new Point(x, y);
    }

    // ---------------------------------------------------------------------------------------------

    public void setupNewGame(){

        isDead = false;

        wallList.clear();
        ballList.clear();

        // creamos el muro horizontal superior
        for (int i = 0; i < canvasSquareWidth; i++) {
            int aX = i * squareSize;
            int aY = -squareSize;
            int bX = aX + squareSize;
            int bY = 0;
            Rectangle wall = new Rectangle(aX, aY, bX, bY, "topWall" + i);
            wall.setColor(Color.BLACK);
            wallList.add(wall);
        }

        // creamos el muro horizontal inferior
        for (int i = 0; i < canvasSquareWidth; i++) {
            int aX = i * squareSize;
            int aY = canvasSquareHeight * squareSize;
            int bX = aX + squareSize;
            int bY = canvasSquareHeight * squareSize + squareSize;
            Rectangle wall = new Rectangle(aX, aY, bX, bY, "bottomWall" + i);
            wall.setColor(Color.BLACK);
            wallList.add(wall);
        }

        // creamos el muro vertical izquierdo
        for (int i = 0; i < canvasSquareHeight; i++) {
            int aX = - squareSize;
            int aY = i * squareSize;
            int bX = 0;
            int bY = aY + squareSize;
            Rectangle wall = new Rectangle(aX, aY, bX, bY, "leftWall" + i);
            wall.setColor(Color.BLACK);
            wallList.add(wall);
        }

        // creamos el muro vertical derecho
        for (int i = 0; i < canvasSquareHeight; i++) {
            int aX = canvasSquareWidth * squareSize;
            int aY = i * squareSize;
            int bX = canvasSquareWidth * squareSize + squareSize;
            int bY = aY + squareSize;
            Rectangle wall = new Rectangle(aX, aY, bX, bY, "leftWall" + i);
            wall.setColor(Color.BLACK);
            wallList.add(wall);
        }

        // creamos el muro vertical central
        int centralWallSquareHeight = 15;
        for (int i = 0; i < centralWallSquareHeight - 1; i++) {
            int aX = (canvasSquareWidth * squareSize) / 2 - squareSize / 2;
            int aY = (canvasSquareHeight * squareSize) / 2 - (centralWallSquareHeight * squareSize) / 2 + i * squareSize;
            int bX = (canvasSquareWidth * squareSize) / 2 + squareSize / 2;
            int bY = aY + squareSize;
            Rectangle wall = new Rectangle(aX, aY, bX, bY, "centerWall" + i);
            wall.setColor(Color.BLUE);
            wallList.add(wall);
        }

        // Creamos la pelota
        for (int i = 0; i < ballNumber; i++) {
            Point randomPoint = getRandomPointNotBorderWall();
            AnimatedRectangle ball = new AnimatedRectangle(new Square(randomPoint.getX(), randomPoint.getY(), squareSize, "ball" + i), this);
            ball.setDirection(Direction.getRandomDirection());
            ball.setColor(Color.RED);
            ballList.add(ball);
        }

        // Creamos al jugador
        Point randomPoint = getRandomPointNotBorderWall();
        player = new Rectangle(new Square(randomPoint.getX(), randomPoint.getY(), squareSize, "player"));
        player.setColor(Color.GREEN);
    }

    public void startGame() {
        // iniciamos el juego
        setupNewGame();
        nextFrameTime = System.currentTimeMillis();
        mainLoop = new MainLoop(getHolder(), this);
        timeCounterThread = new TimeCounterThread(mainLoop);
        mainLoop.setPlaying(true);
        mainLoop.start();
        timeCounterThread.start();
    }

    // ---------------------------------------------------------------------------------------------

    public boolean updateTime() {
        // CAPANDO LA ACTUALIZACIÓN SEGÚN LOS FPSs
        if (nextFrameTime <= System.currentTimeMillis()) {
            nextFrameTime = System.currentTimeMillis() + MILLIS_PER_SECOND / FPS;
            return true;
        }
        return false;
    }

    public void calculateNextMovement() {
        // detectamos las colisiones
        for (AnimatedRectangle ball : ballList) {
            Direction newDirection = null;
            // colisiones con los muros
            for (Rectangle wall : wallList) {
                newDirection = ball.ifIsGoingToCollideGetNewDirection(wall);
                if (newDirection != null) {
                    if (wall.getName().startsWith("centerWall")) {
                        isDead = true;
                    }
                    break;
                }
            }

            // si no hay colisión con los muros busco colisiones con el resto de pelotas
            if (newDirection == null) {
                for (AnimatedRectangle otherBall : ballList) {
                    if (!ball.equals(otherBall)) {
                        newDirection = ball.ifIsGoingToCollideGetNewDirection(otherBall);
                        if (newDirection != null) {
                            break;
                        }
                    }
                }
            }

            // si no hay colisión con los muros ni con otras pelotas busco colisiones con el player
            if (newDirection == null) {
//                Log.d("CHECK COLISION", ball.toString() + " <> " + player.toString());
                newDirection = ball.ifIsGoingToCollideGetNewDirection(player);
            }

            // si no hay colisiones no cambio la dirección
            if (newDirection != null) {
                ball.setDirection(newDirection);
            }
        }

        // movemos laas pelotas
        for (AnimatedRectangle ball : ballList) {
            ball.move();
        }
    }

    private boolean detectDeath(){
        // comprobaciones de muerte
        return isDead;
    }

    public void update() {

        if (updateTime()) {
            calculateNextMovement();
            invalidate(); // se actualiza la interfaz
        }

        // detectamos si el juego acaba
        if (detectDeath()) {
            mainLoop.setPlaying(false);
            ((GameActivity) context).showDeadText(timeCounterThread.getSeconds());
        }/* else {
            ((GameActivity) context).hideDeadText();
        }*/
    }

    // ---------------------------------------------------------------------------------------------

    // se ejecuta con el invalidate()
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // pintamos el fondo negro cada vez que refrescamos
        // (al ser negro no es obligatorio ponerlo, pero lo hago para no olvidarme)
        canvas.drawRGB(0, 0, 0);

        if (mainLoop.isPlaying()) {
            // pintamos los muros
            for (Rectangle wall : wallList) {
                wall.draw(canvas);
            }
            // pintamos la bola
            for (AnimatedRectangle ball : ballList) {
                ball.draw(canvas);
            }
            // pintamos el player
            player.draw(canvas);
//            Log.d("PLAYER_DRAW", player.toString());
        }
    }

    // ---------------------------------------------------------------------------------------------

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        setWillNotDraw(false);

        // obtenemos el canvas
        Canvas canvas = getHolder().lockCanvas();
        draw(canvas);
        getHolder().unlockCanvasAndPost(canvas);

        // calculamos los tamaños
        int canvasWidth = getWidth(); // in pixels
        int canvasHeight = getHeight(); // in pixels

        squareSize = canvasWidth / canvasSquareWidth;
        canvasSquareHeight = canvasHeight / squareSize;
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {}

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {}

    // ---------------------------------------------------------------------------------------------

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float fX = e.getX();
        float fY = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
//                Log.d("ACTION_MOVE || ACTION_DOWN", "(" + fX + ", " + fY + ") ");
                if (mainLoop.isPlaying()) {
                    player.moveCenterDirectly((int) fX, (int) fY);
                }
                break;
            case MotionEvent.ACTION_DOWN:
                // realizamos un click sobre la View, porque sino no funciona el click to start o reastart
                performClick();
                break;
        }

        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}

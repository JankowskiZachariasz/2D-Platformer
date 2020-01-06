package org.VehicleWar.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


import org.TMH_engine.Controlls.Clicker;
import org.VehicleWar.Main;

public class LoadingTest implements Screen, InputProcessor {

    public static Game game=null;
    SpriteBatch batch;
    SpriteBatch txt;
    Texture img;
    BitmapFont font = null;
    Camera camera=null;
    Camera TextCam=null;
    ShapeRenderer shape=null;
    ShapeRenderer touch=null;
    public int counter = 2;
    public static org.TMH_engine.Controlls.Screen Digitizer=null;
    int w = Gdx.graphics.getWidth();
    int h = Gdx.graphics.getHeight();
    Clicker a=null;


    public LoadingTest(final Game game){
        this.game=game;
        batch = new SpriteBatch();
        txt = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        font = new BitmapFont(Gdx.files.internal("big.fnt"));
        camera = new OrthographicCamera(1600, 900);
        TextCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shape = new ShapeRenderer();
        touch=new ShapeRenderer();
        Gdx.input.setInputProcessor(this);
        Digitizer=new org.TMH_engine.Controlls.Screen();

        a = new Clicker(w*0.4f, h*0.4f, w/5, h/5, new Runnable() {
            @Override
            public void run() {
                counter++;
                game.setScreen(Main.b);
            }
        });

        Digitizer.AddClicker(a);
    }


    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(this);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.position.set(0,0,0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //batch.draw(img, 0, 0);
        batch.end();


        shape.setProjectionMatrix(camera.combined);
        shape.setColor(0.3f,0.6f,0.3f,1);
        shape.begin(ShapeRenderer.ShapeType.Filled );
        shape.rect(-750,-400,1500,50);
        shape.setColor(0,1,0,0.6f);
        shape.rect(-745,-395,1490*counter/100,40);
        shape.end();


        //touch.begin(ShapeRenderer.ShapeType.Filled);
        touch.setProjectionMatrix(TextCam.combined);
        a.draw(touch,w,h);


        txt.setProjectionMatrix(TextCam.combined);
        TextCam.update();
        txt.begin();
        font.draw(txt, "     Loading screen    value: "+Integer.toString(counter), -Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        font.draw(txt, "Start!", a.x1-w/2, a.y1-h/2);
        txt.end();
    }

    @Override
    public void resize(int width, int height) {
        TextCam = new OrthographicCamera(width, height);
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Digitizer.T_down(screenX,h-screenY,pointer);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Digitizer.T_up(screenX,h-screenY,pointer);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Digitizer.T_dragged(screenX,h-screenY,pointer);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

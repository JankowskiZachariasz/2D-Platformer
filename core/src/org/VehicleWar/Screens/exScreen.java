package org.VehicleWar.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;


import org.TMH_engine.Controlls.Clicker;
import org.TMH_engine.Objects.JSONreader;
import org.VehicleWar.Main;

public class exScreen implements Screen, InputProcessor {

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
    Clicker b;
    World world;
    Body boxBody;
    Body groundBody;
    Body ST;
    Sprite sprite;


    public exScreen(final Game game){

        this.game=game;
        batch = new SpriteBatch();
        txt = new SpriteBatch();
        img = new Texture("space/space.png");
        font = new BitmapFont(Gdx.files.internal("big.fnt"));
        camera = new OrthographicCamera(50, 28);
        TextCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shape = new ShapeRenderer();
        touch=new ShapeRenderer();
        Gdx.input.setInputProcessor(this);
        Digitizer=new org.TMH_engine.Controlls.Screen();
        world=new World(new Vector2(0,-1), false);
        sprite = new Sprite(img);
        sprite.setSize(28,28);



        JSONreader Space_terrein = new JSONreader("space/space.json",world);
       ST=Space_terrein.getBody(-25,-14);

        CircleShape ballShape = new CircleShape();
        ballShape.setRadius(2);
        FixtureDef def = new FixtureDef();
        def.restitution = 0.9f;
        def.friction = 0.7f;
        def.shape = ballShape;
        def.density = 1f;
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = BodyDef.BodyType.DynamicBody;
        boxBodyDef.position.x = 0;
        boxBodyDef.position.y = 0;
        boxBody = world.createBody(boxBodyDef);
        boxBody.createFixture(def);



        float halfWidth = 25;
        ChainShape chainShape = new ChainShape();
        chainShape.createLoop(new Vector2[] {
                new Vector2(-halfWidth, -14f),
                new Vector2(halfWidth, -14f),
                new Vector2(halfWidth, 14),
                new Vector2(-halfWidth, 14) });
        BodyDef chainBodyDef = new BodyDef();
        chainBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBody = world.createBody(chainBodyDef);
        groundBody.createFixture(chainShape, 0);
        chainShape.dispose();




        a = new Clicker(w*0.4f, h*0.7f, w/5, h/5, new Runnable() {
            @Override
            public void run() {
                counter++;
                game.setScreen(Main.c);
            }
        });


        b = new Clicker(w*0.75f, h*0.4f, w/5, h/5, new Runnable() {
            @Override
            public void run() {
                boxBody.setAngularVelocity(20);
            }
        });
        Digitizer.AddClicker(a);
        Digitizer.AddClicker(b);
    }


    @Override
    public void render(float delta) {
        world.step(0.1f,1,1);
        Gdx.input.setInputProcessor(this);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.position.set(0,0,0);
        camera.update();

        {
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            float x = ST.getPosition().x;
            float y = ST.getPosition().y;
            sprite.setPosition(x, y);
            sprite.setOrigin(0, 0);
            sprite.setRotation((float) Math.toDegrees(ST.getAngle()));
            sprite.draw(batch, 1f);
            batch.end();
        }
        shape.setProjectionMatrix(camera.combined);
        Array<Body> balls = new Array<Body>(world.getBodyCount());
        world.getBodies(balls);
        for(int i =0; i<balls.size; i++) {
            Body x = balls.get(i);
            draw_geometry(shape, x);
        }



        //touch.begin(ShapeRenderer.ShapeType.Filled);
        touch.setProjectionMatrix(TextCam.combined);
        a.draw(touch,w,h);
        b.draw(touch,w,h);


        txt.setProjectionMatrix(TextCam.combined);
        TextCam.update();
        txt.begin();
        font.draw(txt, "     Another Screen", -Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        font.draw(txt, "to LevelTest!", a.x1-w/2, a.y1-h/2);
        font.draw(txt, "Torque!", b.x1-w/2, b.y1-h/2);
        txt.end();
    }

    public void draw_geometry(ShapeRenderer shapeRenderer, Body l){
        Array<Fixture> fixs=l.getFixtureList();
        for(int ix=0; ix<fixs.size;ix++ ){
            Fixture f=fixs.get(ix);
            Shape r = f.getShape();
            Shape.Type zz=r.getType();
            if(zz==Shape.Type.Polygon){

                PolygonShape w = (PolygonShape) f.getShape();
                float vrx[]= new float[(w.getVertexCount()*2)];
                for(int i=0;i<w.getVertexCount();i++)
                {
                    Transform T = l.getTransform();
                    Vector2 u = new Vector2();
                    w.getVertex(i,u);
                    T.mul(u);
                    vrx[i*2] = u.x;
                    vrx[(i*2)+1] = u.y;


                }
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.BLACK);
                shapeRenderer.polygon(vrx);
                shapeRenderer.end();
            }
            else if(zz==Shape.Type.Chain){

                ChainShape w = (ChainShape) f.getShape();
                float vrx[]= new float[(w.getVertexCount()*2)];
                for(int i=0;i<w.getVertexCount();i++)
                {
                    Transform T = l.getTransform();
                    Vector2 u = new Vector2();
                    w.getVertex(i,u);
                    T.mul(u);
                    vrx[i*2] = u.x;
                    vrx[(i*2)+1] = u.y;


                }
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.BLACK);
                shapeRenderer.polygon(vrx);
                shapeRenderer.end();
            }
            else if(zz==Shape.Type.Circle){
                CircleShape a = (CircleShape)f.getShape();
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.BLACK);
                shapeRenderer.circle(l.getPosition().x,l.getPosition().y,a.getRadius(),100);
                shapeRenderer.set(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.BLACK);
                shapeRenderer.line(l.getPosition().x,l.getPosition().y,l.getPosition().x+(float)(Math.cos(l.getAngle())*a.getRadius()),l.getPosition().y+(float)(Math.sin(l.getAngle())*a.getRadius()));
                shapeRenderer.end();
            }

        }
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

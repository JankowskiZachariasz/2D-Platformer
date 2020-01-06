package org.VehicleWar.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import org.TMH_engine.Controlls.Clicker;
import org.TMH_engine.Controlls.steeringWheel;
import org.VehicleWar.Cars.Car;
import org.VehicleWar.Levels.Level;
import org.VehicleWar.Levels.Test.TestLevel;
import org.VehicleWar.Main;

public class LevelTest implements Screen, InputProcessor {

    public static Game game=null;
    SpriteBatch batch;
    SpriteBatch txt;
    BitmapFont font = null;
    OrthographicCamera camera=null;
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
    Level level;
    public static String msg="";
    Car car=null;
    float physicsTimeLeft=0;
    steeringWheel controll=null;
    org.VehicleWar.Camera.Camera camHandler=null;


    public LevelTest(final Game game) {

        LevelTest.game = game;
        batch = new SpriteBatch();
        txt = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("big.fnt"));
        camera = new OrthographicCamera(50, 28);
        TextCam = new PerspectiveCamera();
        shape = new ShapeRenderer();
        touch = new ShapeRenderer();
        Gdx.input.setInputProcessor(this);
        Digitizer = new org.TMH_engine.Controlls.Screen();
        world = new World(new Vector2(0, -1), false);
        car=new Car(world,0,0);



        a = new Clicker(w * 0.4f, h * 0.7f, w / 5, h / 5, new Runnable() {
            @Override
            public void run() {
                counter++;
                game.setScreen(Main.b);
            }
        });

        Digitizer.AddClicker(a);
        this.level = new TestLevel();
        level.initialize(world);
        msg="curved text";
        controll = new steeringWheel(Digitizer,car,w,h);
        camHandler = new org.VehicleWar.Camera.Camera(camera,car);


    }


    @Override
    public void render(float delta) {


        boolean stepped = fixedStep(Gdx.graphics.getDeltaTime());
        Gdx.input.setInputProcessor(this);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);




        camHandler.handle();
        shape.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);
        level.display(shape,batch,camera);
        car.display(batch,camera,shape);



        msg=Float.toString(level.chunks[0].objs[0].body.getPosition().x);
        touch.setProjectionMatrix(TextCam.combined);
        a.draw(touch,w,h);
        TextCam.position.set(0,0,0);
        TextCam.update();
        txt.setProjectionMatrix(TextCam.combined);
        txt.begin();
        font.draw(txt,"A", -Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        TextCam.position.set(0,50,0);
        TextCam.update();
        txt.setProjectionMatrix(TextCam.combined);
        font.draw(txt,"B", -Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

        font.draw(txt, "Back!", a.x1-w/2, a.y1-h/2);
        txt.end();




    }



    private boolean fixedStep(float delta) {
        physicsTimeLeft += delta;
        if (physicsTimeLeft > 1f/45)
            physicsTimeLeft = 1/45f;

        boolean stepped = false;
        while (physicsTimeLeft >= 1f/60) {
            world.step(0.1f, 1, 1);
            controll.physics();
            physicsTimeLeft -= 1/60f;
            stepped = true;
        }
        return stepped;
    }

    public static void draw_geometry(ShapeRenderer shapeRenderer, Body l){
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

package org.VehicleWar.Cars;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import org.TMH_engine.Objects.JSONreader;


public class Car {

    private Body body;
    private Sprite bodySprite;
    public Body marker;
    private Body wheel1;
    private Body wheel2;
    private Body wheel3;
    RevoluteJoint x=null;

    public Car(World world,int x, int y){
        {

            JSONreader Space_Car = new JSONreader("Shepherd/sh_body.json", world);
            body = Space_Car.getBody(x, y);
            body.setType(BodyDef.BodyType.DynamicBody);
            Texture img = new Texture("Shepherd/sh_body.png");
            bodySprite = new Sprite(img);
            bodySprite.setSize(28, 28);
        }
        {
            CircleShape ballShape = new CircleShape();
            ballShape.setRadius(2);
            FixtureDef def = new FixtureDef();
            def.restitution = 0.9f;
            def.friction = 1f;
            def.shape = ballShape;
            def.density = 1f;
            BodyDef boxBodyDef = new BodyDef();
            boxBodyDef.type = BodyDef.BodyType.DynamicBody;
            boxBodyDef.position.x = 3.36f;
            boxBodyDef.position.y = 0;
            wheel1 = world.createBody(boxBodyDef);
            wheel1.createFixture(def);

            RevoluteJointDef w = new RevoluteJointDef();
            w.initialize(body,wheel1,new Vector2(3.36f,0));
            world.createJoint(w);
        }
        {
            CircleShape ballShape = new CircleShape();
            ballShape.setRadius(2);
            FixtureDef def = new FixtureDef();
            def.restitution = 0.9f;
            def.friction = 1f;
            def.shape = ballShape;
            def.density = 1f;
            BodyDef boxBodyDef = new BodyDef();
            boxBodyDef.type = BodyDef.BodyType.DynamicBody;
            boxBodyDef.position.x = 18.256f;
            boxBodyDef.position.y = 0;
            wheel2 = world.createBody(boxBodyDef);
            wheel2.createFixture(def);

            RevoluteJointDef w = new RevoluteJointDef();
            w.initialize(body,wheel2,new Vector2(18.256f,0));
            world.createJoint(w);
        }

        {
            CircleShape ballShape = new CircleShape();
            ballShape.setRadius(0.2f);
            FixtureDef def = new FixtureDef();
            def.restitution = 0.9f;
            def.friction = 1f;
            def.shape = ballShape;
            def.density = 0.1f;
            BodyDef boxBodyDef = new BodyDef();
            boxBodyDef.type = BodyDef.BodyType.DynamicBody;
            boxBodyDef.position.x = 9.8f;
            boxBodyDef.position.y = 3.92f;
            marker = world.createBody(boxBodyDef);
            marker.createFixture(def);

            RevoluteJointDef w = new RevoluteJointDef();
            w.initialize(body,marker,new Vector2(9.8f,3.92f));
            world.createJoint(w);
        }


    }
    public void display(Batch batch, Camera camera, ShapeRenderer shape){
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        float x = body.getPosition().x;
        float y = body.getPosition().y;
        bodySprite.setPosition(x, y);
        bodySprite.setOrigin(0, 0);
        bodySprite.setRotation((float) Math.toDegrees(body.getAngle()));
        bodySprite.draw(batch, 1f);
        batch.end();

        draw_geometry(shape,wheel1);
        draw_geometry(shape,wheel2);
    }
    private void draw_geometry(ShapeRenderer shapeRenderer, Body l){
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
    public void move(int key){
        switch(key){
            /*/
            0-forward
            1-backwards
            2-?
            3-?
             */

            case(0):{
                wheel1.setAngularVelocity(-1.5f);
                wheel2.setAngularVelocity(-1.5f);
                body.setAngularVelocity(0.1f);
                break;}
            case(1):{
                wheel1.setAngularVelocity(1.5f);
                wheel2.setAngularVelocity(1.5f);
                body.setAngularVelocity(-0.11f);
                break;}
            case(2):{break;}
            case(3):{break;}
        }
    }
}

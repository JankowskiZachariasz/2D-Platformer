package org.VehicleWar.Levels.Chunks;


import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.utils.Array;

import org.VehicleWar.Objects.ObjectB2;
import org.VehicleWar.Objects.Terrain;
import org.VehicleWar.Screens.LevelTest;

public class Chunk {

    public Chunk(){}
    public ObjectB2[] objs;

    public void display(ShapeRenderer shape, Batch batch,Camera camera){
        for(int i=0;i<objs.length;i++){

            switch(objs[i].category){
                case(1):{//terrain


            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            float x = objs[i].body.getPosition().x;
            float y = objs[i].body.getPosition().y;
            objs[i].sprite.setPosition(x, y);
            objs[i].sprite.setOrigin(0, 0);
            objs[i].sprite.setRotation((float) Math.toDegrees(objs[i].body.getAngle()));
            objs[i].sprite.draw(batch, 1f);
            batch.end();


                    draw_geometry(shape, objs[i].body);
                    break;}

            }

        }
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
}

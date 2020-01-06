package org.TMH_engine.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;

/**
 * Created by Zacki on 20.05.2017.
 */

public class JSONreader {

    String source="SPBS";
    public Body body;
    Triangle initial=null;
    Triangle last=null;
    World world;
    int counter=0;


    public JSONreader(String filename, World worlder){

        world=worlder;
        FileHandle file = Gdx.files.internal(filename);
        source =file.readString();
        source=source.substring(source.indexOf("polygons"),source.indexOf("circles"));
        devide();


    }
    void addTRIAN(Triangle add){
        if(initial==null){initial=add;}
        else if(last==null){initial.next=add;last=add;}
        else{last.next=add;last=add;}
    }

    void devide(){
        boolean stay=true;
        String keep=source;

        while(stay){
            int a=999,b=999;
            if(source.contains("x")){a=source.indexOf("x");}
            else {stay=false;}
            if(source.contains("]")){b=source.indexOf("]");}

            if(b>a){source=source.substring(source.indexOf("x")+3,source.length());}
            else if (a>b){
                int where=keep.length()-(source.length()-b);
                Triangle just = new Triangle(keep.substring(0,where));
                addTRIAN(just);
                counter++;
                keep=keep.substring(where,keep.length());
                source=source.substring(b+1,source.length());

            }


        }
    }

    public Body getBody(float x, float y){
        BodyDef bd = new BodyDef();
        bd.position.set(x,y);
        bd.angle = 0.0f;
        Body body = world.createBody(bd);

        for(Triangle t=initial;t!=null;t=t.next){
            Fixture f = body.createFixture(t.piece,4f);
            f.setDensity(0.1f);
            f.setFriction(0.5f);

        }


        body.setType(BodyDef.BodyType.StaticBody);
        body.setUserData(null);
        this.body=body;

        return body;


    }

}

class Triangle{
    Triangle next;
    String partly;
    Vector2 vertices[];
    int numbbersAperiod=5;
    PolygonShape piece;

    public Triangle(String sub){
        partly=sub;
        vertices=new Vector2[vertexCount()];
        calculate();
        piece = new PolygonShape();
        piece.set(vertices);

    }

    Integer vertexCount(){
        int i=0;
        boolean stay=true;
        String safely = partly;
        while(stay){
            if(safely.contains("x")){safely=safely.substring(safely.indexOf("x")+3,safely.length());i++;}
            else{stay=false;}
        }
        return  i;
    }

    void calculate(){

        boolean stay=true;
        String s = partly;
        int loops=0;

        while(stay){
            if(s.contains("x")){
                s=s.substring(s.indexOf("x")+3,s.length());
                float x=Character.getNumericValue(s.toCharArray()[0]);
                for(int w=2;w<numbbersAperiod;w++)
                {

                    int add = Character.getNumericValue(s.toCharArray()[w]);
                    if(add>=0&&add<=9) {
                        x = x + (float) (add / Math.pow(10, w - 1));
                    }
                    else{break;}
                }

                s=s.substring(s.indexOf("y")+3,s.length());
                float y=Character.getNumericValue(s.toCharArray()[0]);
                for(int w=2;w<numbbersAperiod;w++)
                {
                    int add = Character.getNumericValue(s.toCharArray()[w]);
                    if(add>=0&&add<=9) {
                        y = y + (float) (add / Math.pow(10, w - 1));
                    }
                    else{break;}
                }

                vertices[loops] =  new Vector2(x*28, y*28);
                loops++;

            }
            else{stay=false;}

        }


    }

}

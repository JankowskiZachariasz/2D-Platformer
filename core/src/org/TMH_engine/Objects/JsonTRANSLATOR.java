package org.TMH_engine.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by Zacki on 20.05.2017.
 */

public class JsonTRANSLATOR {
    String alerts = "Just created";
    PolygonShape triangle1=null;
    int numbbersAperiod=3;
    public static Vector2[] vertices=null;


    public JsonTRANSLATOR(String name){



        triangle1 = new PolygonShape();
        int loops=0;
        FileHandle file = Gdx.files.internal(name);
        String kk =file.readString();
        alerts="File read";

        if(kk.contains("vertices")){
            kk=kk.substring(kk.indexOf("vertices"),kk.length());

            String keep=kk;
            boolean endloop = true;

            while(endloop){
                endloop=true;
                if(kk.contains("x")){kk=kk.substring(kk.indexOf("x")+3,kk.length());loops++;}
                else{endloop=false;}

            }

            alerts="estimated number of vertexes: "+Integer.toString(loops);
            vertices=new Vector2[loops];
            loops=0;
            endloop=true;
            kk=keep;
            while(endloop){
                endloop=true;
                if(kk.contains("x")){
                    kk=kk.substring(kk.indexOf("x")+3,kk.length());
                    float x=Character.getNumericValue(kk.toCharArray()[0]);
                    for(int w=2;w<numbbersAperiod;w++)
                    {

                        int add = Character.getNumericValue(kk.toCharArray()[w]);
                        if(add>=0&&add<=9) {
                            x = x + (float) (add / Math.pow(10, w - 1));
                        }
                        else{break;}
                    }

                    kk=kk.substring(kk.indexOf("y")+3,kk.length());
                    float y=Character.getNumericValue(kk.toCharArray()[0]);
                    for(int w=2;w<numbbersAperiod;w++)
                    {
                        int add = Character.getNumericValue(kk.toCharArray()[w]);
                        if(add>=0&&add<=9) {
                            y = y + (float) (add / Math.pow(10, w - 1));
                        }
                        else{break;}
                    }

                    vertices[loops] =  new Vector2(x*100, y*30);
                    loops++;

                }
                else{alerts="No more x'es, ended with: "+Integer.toString(loops)+" Arrays.";endloop=false;}

            }


        }
        else{alerts="File read error";}

    }
}

package org.TMH_engine.Controlls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


/**
 * Created by Zacki on 17.04.2017.
 */




public class Clicker {


    Clicker next=null;
    Runnable effect=null;
    boolean tapped=false;
    boolean OlejProduct=false;
    public boolean tapping(){
        if(one.product&&one.touching||two.product&&two.touching||three.product&&three.touching)
            return true;
        else
        return false;
    }
    String name = null;
    Progx first_x;
    Progx last_x;
    Progy first_y;
    Progy last_y;

    TouchMemory one=null;
    TouchMemory two=null;
    TouchMemory three=null;



    public float x1;
    float x2;
    public float y1;
    float y2;

    public Clicker(float xstart, float ystart, float w, float h,Runnable exe, String name, boolean olej){

        x1= xstart;
        y1=ystart;
        x2=xstart+w;
        y2=ystart+h;
        effect=exe;
        this.name=name;
        OlejProduct=olej;

    }

    public Clicker(float xstart, float ystart, float w, float h,Runnable exe, String name){

        x1= xstart;
        y1=ystart;
        x2=xstart+w;
        y2=ystart+h;
        effect=exe;
        this.name=name;

    }

    public Clicker(float xstart, float ystart, float w, float h,Runnable exe){

        x1= xstart;
        y1=ystart;
        x2=xstart+w;
        y2=ystart+h;
        effect=exe;

    }

    boolean check(float gx,float gy){

            boolean clicked=false;
            if(gy>y1&&gy<y2){
                if(gx>x1&&gx<x2){clicked=true;}
                else if(gx<x1&&gx>x2){clicked=true;}}
            else if(gy<y1&&gy>y2){
                if(gx>x1&&gx<x2){clicked=true;}
                else if(gx<x1&&gx>x2){clicked=true;}}

        return clicked;
    }

    public void draw(ShapeRenderer shapeRenderer){

        boolean glow = one.onTile&&one.product&&one.touching||two.onTile&&two.product&&two.touching||three.onTile&&three.product&&three.touching;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if(name=="Slider"){Color a= Color.RED;shapeRenderer.setColor(a);}
        else if(name=="Slider2"){shapeRenderer.setColor(Color.GREEN);}
        else{shapeRenderer.setColor(Color.BLUE);}
        if(glow){shapeRenderer.setColor(Color.CYAN);}
        shapeRenderer.rect(x1, y2,x2-x1,y2-y1);
        shapeRenderer.end();


    }
    public void draw(ShapeRenderer shapeRenderer,int w, int h){

        boolean glow = one.onTile&&one.product&&one.touching||two.onTile&&two.product&&two.touching||three.onTile&&three.product&&three.touching;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        if(name=="Slider"){Color a= Color.RED;shapeRenderer.setColor(a);}
        else if(name=="Slider2"){shapeRenderer.setColor(Color.GREEN);}
        else{shapeRenderer.setColor(Color.BLUE);}
        if(glow){shapeRenderer.setColor(Color.CYAN);}
        shapeRenderer.rect(x1-w/2, y1-h/2,x2-x1,y2-y1);
        shapeRenderer.end();


    }

    public void checkProgi_x(float xtilt){
        boolean koniec=false;
        if(first_x!=null){
        for(Progx a=first_x;a!=null;a=a.next)
        {
            if(a.oddol&&koniec==false){
                if(a.ile<xtilt){if(!a.resetTODO){a.effect.run();koniec=true;}RESETtodo_EXCEPT(a,null);koniec=true;}
            }
            else if(a.oddol==false&&koniec==false){
                if(a.ile>xtilt){if(!a.resetTODO){a.effect.run();koniec=true;}RESETtodo_EXCEPT(a,null);koniec=true;}
            }
        }
    }}

    public void checkProgi_y(float ytilt){
        boolean koniec=false;
        if(first_y!=null){
        for(Progy a=first_y;a!=null;a=a.next)
        {

            if(a.oddol&&koniec==false){
                if(a.ile<ytilt){if(!a.resetTODO){a.effect.run();koniec=true;}RESETtodo_EXCEPT(null,a);koniec=true;}
            }
            else if(a.oddol==false&&koniec==false){
                if(a.ile>ytilt){if(!a.resetTODO){a.effect.run();koniec=true;}RESETtodo_EXCEPT(null,a);koniec=true;}
            }
        }
    }}

    public void AddMemoryRefrence(TouchMemory vv){
        if(one==null){one=vv;}
        else if(two==null){two=vv;}
        else if(three==null){three=vv;}
    }

    public void addProg_x(Progx x){
        if (first_x==null){first_x=x;}
        else if(last_x==null){first_x.next=x;last_x=x;}
        else{last_x.next=x;last_x=x;}
    }

    public void addProg_y(Progy y){
        if (first_y==null){first_y=y;}
        else if(last_y==null){first_y.next=y;last_y=y;}
        else{last_y.next=y;last_y=y;}
    }

    public void RESETtodo(){
        for(Progx a=first_x;a!=null;a=a.next)
        {
            a.resetTODO=false;
        }
        for(Progy a=first_y;a!=null;a=a.next)
        {
            a.resetTODO=false;
        }
    }

    public void RESETtodo_EXCEPT(Progx xx, Progy yy){
        for(Progx a=first_x;a!=null;a=a.next)
        {
            if(a!=xx){
            a.resetTODO=false;}
        }
        for(Progy a=first_y;a!=null;a=a.next)
        {
            if(a!=yy){
            a.resetTODO=false;}
        }
    }


}

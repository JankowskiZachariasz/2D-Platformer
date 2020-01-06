package org.TMH_engine.Controlls;


/**
 * Created by Zacki on 17.04.2017.
 */

public class Screen {

    Finger one = new Finger();
    Finger two= new Finger();
    Finger three= new Finger();

    public Screen(){}

    public void AddClicker(Clicker x){
        one.AddClicker(x);
        two.AddClicker(x);
        three.AddClicker(x);
    }

    public void T_down(float x,float y,int pointer){
        if(pointer==0){one.T_down(x,y);}
        if(pointer==1){two.T_down(x,y);}
        if(pointer==2){three.T_down(x,y);}
    }

    public void T_up(float x,float y,int pointer){
        if(pointer==0){one.T_up(x,y);}
        if(pointer==1){two.T_up(x,y);}
        if(pointer==2){three.T_up(x,y);}
    }

    public void T_dragged(float x,float y,int pointer){
        if(pointer==0){one.T_dragged(x,y);}
        if(pointer==1){two.T_dragged(x,y);}
        if(pointer==1){three.T_dragged(x,y);}

    }
}

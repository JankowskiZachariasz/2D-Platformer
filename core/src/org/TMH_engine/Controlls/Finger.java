package org.TMH_engine.Controlls;


/**
 * Created by Zacki on 17.04.2017.
 */

public class Finger {
    Clicker A=null;
    Clicker B_last=null;
    TouchMemory Cell=null;
    TouchMemory Cell_last=null;


    public Finger(){}

    void AddClicker(Clicker x){if(A==null){A=x;B_last=x; Cell=new TouchMemory(x);x.AddMemoryRefrence(Cell);Cell_last=Cell;}
    else{B_last.next=x;B_last=x;
        TouchMemory temp = new TouchMemory(x); x.AddMemoryRefrence(temp); Cell_last.next=temp;Cell_last=temp;}
    }

    void T_down(float x,float y){


        for(TouchMemory i = Cell;i!=null;i=i.next) {
            i.onTile= i.Assigned.check(x,y);
            i.product= i.Assigned.check(x,y);
            i.whileSliding=i.Assigned.check(x,y);
            i.x_initial=x;
            i.y_initial=y;
            i.touching=true;


        }



    }

    void T_up(float x,float y){

        for(TouchMemory i = Cell;i!=null;i=i.next) {
            if(i.product&&i.Assigned.check(x,y)){i.Assigned.effect.run();i.Assigned.tapped=false;}
            boolean logic=i.Assigned.check(x,y);
            if(i.product){i.Assigned.RESETtodo(); }
            i.touching=false; i.whileSliding=false;
            i.onTile= i.Assigned.check(x,y);
            i.y_tilt=0;
            i.x_tilt=0;

        }


    }

    void T_dragged(float x,float y){
        for(TouchMemory i = Cell;i!=null;i=i.next) {
            i.touching=true;
            i.onTile= i.Assigned.check(x,y);
            i.y_tilt=-y+i.y_initial;
            i.x_tilt=x-i.x_initial;
            if(i.whileSliding) {
                i.Assigned.checkProgi_x(i.x_tilt);
                i.Assigned.checkProgi_y(i.y_tilt);

            }
            if(i.Assigned.OlejProduct) {
                i.Assigned.checkProgi_x(i.x_tilt);
                i.Assigned.checkProgi_y(i.y_tilt);

            }




    }
    }
}

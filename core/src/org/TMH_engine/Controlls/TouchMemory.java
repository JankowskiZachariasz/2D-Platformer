package org.TMH_engine.Controlls;

/**
 * Created by Zacki on 17.04.2017.
 */

public class TouchMemory {

    boolean product=false;
    boolean touching=false;
    boolean onTile=false;
    boolean whileSliding = false;
    Clicker Assigned=null;
    TouchMemory next=null;
    float x_initial;
    float y_initial;
    float x_tilt;
    float y_tilt;
    public TouchMemory(Clicker w){Assigned=w;}
}

package org.TMH_engine.Controlls;

/**
 * Created by Zacki on 22.08.2017.
 */

public class Progx{
    Clicker assig=null;
    boolean resetTODO=false;
    float ile=0f;
    boolean oddol=false;
    Progx next=null;
    Runnable effect=null;
    public Progx(float wartosc,Runnable outcome,boolean odDolu){
        ile=wartosc;
        effect=outcome;
        oddol=odDolu;
    }
    void add_prog(Progx nachst){next=nachst;}

}
package org.TMH_engine.Controlls;

import org.VehicleWar.Cars.Car;
import org.VehicleWar.Main;

public class steeringWheel {

    Clicker gas;
    Clicker breaking;
    public Car car;

    public steeringWheel(Screen digitizer, Car card,int w,int h){
        this.car=card;
        gas = new Clicker(w/2, 0, w / 2, h / 2, new Runnable() {
            @Override
            public void run() {

            }
        });
        breaking = new Clicker(0, 0, w / 2, h / 2, new Runnable() {
            @Override
            public void run() {

            }
        });
        digitizer.AddClicker(gas);
        digitizer.AddClicker(breaking);



    }
    public void physics(){

        if(gas.tapping()){car.move(0);}
        if(breaking.tapping()){car.move(1);}
    }
}

package domain.utils;

import domain.lugares.Viaje;

import java.util.Timer;
import java.util.TimerTask;

public class Cronometro {
    private Timer timer;
    private Integer segundos = 0;

    public void iniciar(){
        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                segundos++;
            }
        }, 1000, 1000);
        return;
    }

    public void detener(){
        this.timer.cancel();
        return;
    }

    public void reiniciar(){
        this.segundos = 0;
        return;
    }

    public void seAlcanzoDemoraMax(Viaje unViaje){
        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(segundos > unViaje.getDemoraAprox())
                    unViaje.alertarPeligro();
            }
        }, 1000, 1000);
        return;
    }
}

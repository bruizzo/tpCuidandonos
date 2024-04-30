package domain.notifaciones;

import domain.personas.Persona;

public class Notificador {

    private EstrategiaNotifacion estrategia;

    public void notificar(Persona unaPersona){
        this.estrategia.notificar(unaPersona);
        return;
    }

    public void cambiarEstrategia(EstrategiaNotifacion unaEstrategia){
        this.estrategia = unaEstrategia;
        return;
    }

}

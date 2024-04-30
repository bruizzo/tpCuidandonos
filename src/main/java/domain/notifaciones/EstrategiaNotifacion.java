package domain.notifaciones;

import domain.personas.Persona;

public abstract class EstrategiaNotifacion {

    protected String mensaje;

    public abstract void notificar(Persona unaPersona);
}

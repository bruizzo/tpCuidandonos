package domain.personas;

import domain.alertas.IModoAlerta;
import domain.lugares.Ubicacion;
import domain.lugares.Viaje;

public class Persona {

    private String nombre;
    private String apellido;
    private Ubicacion direccion;
    private Integer edad;
    private Character sexo;
    private ModoUsuario modoUsuario;
    private IModoAlerta modoAlerta;

    public IModoAlerta getModoAlerta() {
        return modoAlerta;
    }

    public void aceptarCuidado(Viaje unViaje){
        unViaje.agregarCuidadorAceptado(this);
        unViaje.eliminarCuidador(this, unViaje.getCuidadoresSolicitados());
        return;
    }

    public void rechazarCuidado(Viaje unViaje){
        unViaje.eliminarCuidador(this, unViaje.getCuidadoresSolicitados());
        return;
    }

    public void cambiarModoAlerta(IModoAlerta unModo){
        this.modoAlerta = unModo;
        return;
    }

    public void cambiarModoUsuario(ModoUsuario unModo){
        this.modoUsuario = unModo;
        return;
    }
}

package domain.lugares;

public abstract class EstadoViaje {

    protected Viaje viaje;

    public abstract void comenzar(Ubicacion desde, Parada hasta);

}

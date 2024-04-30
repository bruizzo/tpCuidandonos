package domain.lugares;

public class ViajeFinalizado extends EstadoViaje{

    public ViajeFinalizado(Viaje unViaje) {
        this.viaje = unViaje;
    }
    @Override
    public void comenzar(Ubicacion desde, Parada hasta) {
        return;
    }
}

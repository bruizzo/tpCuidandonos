package domain.lugares;

public class ViajeEnCurso extends EstadoViaje{

    public ViajeEnCurso(Viaje unViaje) {
        this.viaje = unViaje;
    }

    @Override
    public void comenzar(Ubicacion desde, Parada hasta) {
        viaje.getCronometro().iniciar(); // Iniciamos el cronometro
        viaje.setDemoraAprox(viaje.getAdapterDistancia().calcularDemoraAprox(desde, hasta) + hasta.getDemora()); // Calculamos la demora aproximada con la API de Google + la demora adicional (si es que hay)
        viaje.setDistancia(viaje.getAdapterDistancia().calcularDistancia(desde, hasta)); // Calculamos la distancia con la API de Google
        viaje.getCronometro().seAlcanzoDemoraMax(viaje); // Empezamos a controlar que no superemos la demora calculada
    }
}

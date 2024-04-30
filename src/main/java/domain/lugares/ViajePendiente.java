package domain.lugares;

import domain.notifaciones.NotificacionInicioViaje;
import domain.personas.ModoUsuario;

public class ViajePendiente extends EstadoViaje{

    public ViajePendiente(Viaje unViaje) {
        this.viaje = unViaje;
    }

    @Override
    public void comenzar(Ubicacion desde, Parada hasta) {
        super.viaje.cambiarEstado(new ViajeEnCurso(viaje)); // En nuestro diseño, los viajes no iniciados tienen estado PENDIENTE
        viaje.getNotificador().cambiarEstrategia(new NotificacionInicioViaje()); // Seteamos al notificador en modo inicio de viaje
        viaje.getCuidadoresAceptados().forEach(c -> viaje.getNotificador().notificar(c)); // Notificamos a los cuidadores del inicio
        viaje.getTranseunte().cambiarModoUsuario(ModoUsuario.ACTIVO);  // Pasamos al usuario al modo ACTIVO
        viaje.setParadaActual(viaje.getDestino().get(0)); // Seteamos como paradaActual el primer elemento de nuestra lista de destinos
        viaje.getCronometro().iniciar(); // Iniciamos el cronometro
        viaje.setDemoraAprox(viaje.getAdapterDistancia().calcularDemoraAprox(desde, hasta) + hasta.getDemora()); // Calculamos la demora aproximada con la API de Google + la demora adicional (si es que hay)
        viaje.setDistancia(viaje.getAdapterDistancia().calcularDistancia(desde, hasta)); // Calculamos la distancia con la API de Google
        viaje.getCronometro().seAlcanzoDemoraMax(viaje); // Empezamos a controlar que no superemos la demora calculada
        return;
    }
}

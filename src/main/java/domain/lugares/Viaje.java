package domain.lugares;

import domain.adapters.IAdapterDistancia;
import domain.notifaciones.NotificacionFinViaje;
import domain.notifaciones.NotificacionInicioViaje;
import domain.notifaciones.Notificador;
import domain.personas.ModoUsuario;
import domain.personas.Persona;
import domain.utils.Cronometro;

import java.util.List;

public class Viaje {

    private Parada origen;
    private List<Parada> destino;
    private Parada paradaActual;
    // private Parada paradaDesde;
    // private Parada paradaHasta;
    private Persona transeunte;
    private List<Persona> cuidadoresSolicitados;
    private List<Persona> cuidadoresAceptados;
    private EstadoViaje estado;
    private Cronometro cronometro;
    private IAdapterDistancia adapterDistancia;
    private Integer demoraAprox;
    private Integer distancia;
    private Notificador notificador;



    public Integer getDemoraAprox() {
        return demoraAprox;
    }

    public List<Persona> getCuidadoresSolicitados() {
        return cuidadoresSolicitados;
    }

    public List<Persona> getCuidadoresAceptados() {
        return cuidadoresAceptados;
    }

    public void cambiarEstado(EstadoViaje unEstado){
        this.estado = unEstado;
        return;
    }

    public void agregarCuidadorSolicitado(Persona unCuidador){
        this.cuidadoresSolicitados.add(unCuidador);
        return;
    }

    public void agregarCuidadorAceptado(Persona unCuidador){
        this.cuidadoresAceptados.add(unCuidador);
        return;
    }

    public void eliminarCuidador(Persona unCuidador, List<Persona> unaLista){
        unaLista.remove(unCuidador);
        return;
    }

    public void comenzar(Ubicacion desde, Ubicacion hasta){

        if(this.estado == EstadoViaje.PENDIENTE) {
            this.cambiarEstado(EstadoViaje.EN_CURSO); // En nuestro diseño, los viajes no iniciados tienen estado PENDIENTE
            this.notificador.cambiarEstrategia(new NotificacionInicioViaje()); // Seteamos al notificador en modo inicio de viaje
            this.cuidadoresAceptados.forEach(c -> this.notificador.notificar(c)); // Notificamos a los cuidadores del inicio
            this.transeunte.cambiarModoUsuario(ModoUsuario.ACTIVO);  // Pasamos al usuario al modo ACTIVO

            // this.paradaDesde = this.origen;
            // this.paradaHasta = this.destino.get(0);
            // this.paradaActual = this.destino.get(0);
        }

        if(this.tieneParadas()){
            this.realizarViajeConParadas();
        } else {

        }
        this.cronometro.iniciar(); // Iniciamos el cronometro
        this.demoraAprox = this.adapterDistancia.calcularDemoraAprox(desde, hasta); // Calculamos la demora aproximada con la API de Google
        this.distancia = this.adapterDistancia.calcularDistancia(desde, hasta); // Calculamos la distancia con la API de Google
        this.cronometro.seAlcanzoDemoraMax(this); // Empezamos a controlar que no superemos la demora calculada
        return;
    }

    public Boolean tieneParadas(){
        //TODO
        return true;
    }

    public void realizarViajeConParadas(){
        //TODO
        return;
    }

    public void finalizar(){

        // this.paradaActual = ALGO

        if(!this.esUltimaParada(this.paradaActual)) {
            this.cronometro.reiniciar();
            this.comenzar(this.paradaActual, this.destino.get(this.destino.indexOf(paradaActual) + 1));
        }
        // [0 ; 1 ; 2]
        else{
            this.estado = EstadoViaje.FINALIZADO; // Seteamos el estado del viaje a FINALIZADO
            this.cronometro.detener(); // Detenemos el cronometro
            this.transeunte.cambiarModoUsuario(ModoUsuario.PASIVO); // Pasamos al usuario al modo PASIVO
            this.notificador.cambiarEstrategia(new NotificacionFinViaje()); // Seteamos al notificador en modo fin de viaje
            this.cuidadoresAceptados.forEach(c -> this.notificador.notificar(c)); // Notificamos a los cuidadores del fin
        }
        return;
    }

    public void alertarPeligro(){
        this.transeunte.getModoAlerta().alertarPeligro();
        return;
    }

    public Boolean esUltimaParada(Parada unaParada){
        return (unaParada == this.destino.get(this.destino.size() - 1));
    }
}

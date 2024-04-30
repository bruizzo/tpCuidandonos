package domain.lugares;

import domain.adapters.IAdapterDistancia;
import domain.notifaciones.NotificacionFinViaje;
import domain.notifaciones.NotificacionInicioViaje;
import domain.notifaciones.NotificacionSolicitudViaje;
import domain.notifaciones.Notificador;
import domain.personas.ModoUsuario;
import domain.personas.Persona;
import domain.utils.Cronometro;

import java.util.List;

public class Viaje {

    private Ubicacion origen;
    private List<Parada> destino;
    private Parada paradaActual;
    private Persona transeunte;
    private List<Persona> cuidadoresSolicitados;
    private List<Persona> cuidadoresAceptados;
    private EstadoViaje estado;
    private Cronometro cronometro;
    private IAdapterDistancia adapterDistancia;
    private Integer demoraAprox;
    private Integer distancia;
    private Notificador notificador;

    public Ubicacion getOrigen() {
        return origen;
    }

    public void setOrigen(Ubicacion origen) {
        this.origen = origen;
    }

    public List<Parada> getDestino() {
        return destino;
    }

    public void setDestino(List<Parada> destino) {
        this.destino = destino;
    }

    public Parada getParadaActual() {
        return paradaActual;
    }

    public void setParadaActual(Parada paradaActual) {
        this.paradaActual = paradaActual;
    }

    public Persona getTranseunte() {
        return transeunte;
    }

    public void setTranseunte(Persona transeunte) {
        this.transeunte = transeunte;
    }

    public void setCuidadoresSolicitados(List<Persona> cuidadoresSolicitados) {
        this.cuidadoresSolicitados = cuidadoresSolicitados;
    }

    public void setCuidadoresAceptados(List<Persona> cuidadoresAceptados) {
        this.cuidadoresAceptados = cuidadoresAceptados;
    }

    public EstadoViaje getEstado() {
        return estado;
    }

    public void setEstado(EstadoViaje estado) {
        this.estado = estado;
    }

    public Cronometro getCronometro() {
        return cronometro;
    }

    public void setCronometro(Cronometro cronometro) {
        this.cronometro = cronometro;
    }

    public IAdapterDistancia getAdapterDistancia() {
        return adapterDistancia;
    }

    public void setAdapterDistancia(IAdapterDistancia adapterDistancia) {
        this.adapterDistancia = adapterDistancia;
    }

    public void setDemoraAprox(Integer demoraAprox) {
        this.demoraAprox = demoraAprox;
    }

    public Integer getDistancia() {
        return distancia;
    }

    public void setDistancia(Integer distancia) {
        this.distancia = distancia;
    }

    public Notificador getNotificador() {
        return notificador;
    }

    public void setNotificador(Notificador notificador) {
        this.notificador = notificador;
    }

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
        this.notificador.cambiarEstrategia(new NotificacionSolicitudViaje());
        this.notificador.notificar(unCuidador);
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

    public void comenzar(Ubicacion desde, Parada hasta){

        this.estado.comenzar(desde, hasta);

        /*
        if(this.estado == EstadoViaje.PENDIENTE) {
            this.cambiarEstado(EstadoViaje.EN_CURSO); // En nuestro diseño, los viajes no iniciados tienen estado PENDIENTE
            this.notificador.cambiarEstrategia(new NotificacionInicioViaje()); // Seteamos al notificador en modo inicio de viaje
            this.cuidadoresAceptados.forEach(c -> this.notificador.notificar(c)); // Notificamos a los cuidadores del inicio
            this.transeunte.cambiarModoUsuario(ModoUsuario.ACTIVO);  // Pasamos al usuario al modo ACTIVO
            this.paradaActual = this.destino.get(0);
        }
        this.cronometro.iniciar(); // Iniciamos el cronometro
        this.demoraAprox = this.adapterDistancia.calcularDemoraAprox(desde, hasta); // Calculamos la demora aproximada con la API de Google
        this.distancia = this.adapterDistancia.calcularDistancia(desde, hasta); // Calculamos la distancia con la API de Google
        this.cronometro.seAlcanzoDemoraMax(this); // Empezamos a controlar que no superemos la demora calculada
         */

        return;
    }

    public void finalizar(){
        this.notificador.cambiarEstrategia(new NotificacionFinViaje()); // Seteamos al notificador en modo fin de viaje
        this.cuidadoresAceptados.forEach(c -> this.notificador.notificar(c)); // Notificamos a los cuidadores del fin
        this.cronometro.detener(); // Detenemos el cronometro

        if(!this.esUltimaParada(this.paradaActual)) {
            this.cronometro.reiniciar(); // Reiniciamos el cronometro a cero
            this.comenzar(this.paradaActual, this.destino.get(this.destino.indexOf(paradaActual) + 1)); // Iniciamos el viaje hacia la proxima parada
        }
        else{
            this.cambiarEstado(new ViajeFinalizado(this));// Seteamos el estado del viaje a FINALIZADO
            this.transeunte.cambiarModoUsuario(ModoUsuario.PASIVO); // Pasamos al usuario al modo PASIVO
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

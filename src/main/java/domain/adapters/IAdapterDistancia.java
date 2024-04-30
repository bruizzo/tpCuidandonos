package domain.adapters;

import domain.lugares.Ubicacion;

public interface IAdapterDistancia {
    public abstract Integer calcularDistancia(Ubicacion desde, Ubicacion hasta);
    public abstract Integer calcularDemoraAprox(Ubicacion desde, Ubicacion hasta);
}

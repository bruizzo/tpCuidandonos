package domain.adapters;

import domain.lugares.Ubicacion;

public class AdapterDistanceMatrixAPI implements IAdapterDistancia{

    private AdaptadaDistanceMatrixAPI adaptada;

    @Override
    public Integer calcularDistancia(Ubicacion desde, Ubicacion hasta) {
        return this.adaptada.calcularRealDistancia();
    }

    @Override
    public Integer calcularDemoraAprox(Ubicacion desde, Ubicacion hasta) {
        return this.adaptada.calcularRealDemoraAprox();
    }
}

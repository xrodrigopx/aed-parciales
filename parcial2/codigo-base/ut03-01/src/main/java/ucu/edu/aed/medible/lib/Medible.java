package ucu.edu.aed.medible.lib;

import java.io.Serializable;

public abstract class Medible<Data> implements Serializable {

    public Medicion medir(int repeticiones, Data data) {
        long init = System.nanoTime();
        ejecutar(repeticiones, data);
        long fin = System.nanoTime();
        return new Medicion(this.getClass().getSimpleName(), ObjectSizeFetcher.getObjectSize(getObjetoAMedirMemoria()), fin - init);
    }

    abstract public void ejecutar(int repeticiones, Data data);

    abstract public Object getObjetoAMedirMemoria();

}

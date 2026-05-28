package uy.edu.ucu.aed.parcial2;

import uy.edu.ucu.aed.*;

@SuppressWarnings({"rawtypes", "unchecked"})
public class TVerticeDeLaRed extends TVertice<TNodoDeLaRed> {

    private static TNodoDeLaRed crearNodo(String unaEtiqueta, String tipoDeVertice) {
        String[] parts = unaEtiqueta.split("_");
        int id = Integer.parseInt(parts[1]);

        TipoDeNodo tipo = TipoDeNodo.fromInt(Integer.parseInt(tipoDeVertice));

        return new TNodoDeLaRed(id, unaEtiqueta, tipo);
    }

    public TVerticeDeLaRed(Object... args) { 
        super((String) args[0]);
    
        this.datos = crearNodo((String) args[0], (String) args[1]);
    }
}

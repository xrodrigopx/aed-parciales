package uy.edu.ucu.aed;


/*
 * NO LICENCE 
 * Author: Ing. Agustin Paredes
 */

import java.util.Collection;


/**
 *
 * @author agustinp
 */
public class TGrafoRedDatos /*extends TGrafo*/ implements ITGrafoRedDatos
{

    public TGrafoRedDatos(Collection<TVertice> vertices, Collection<TArista> aristas)
    {
        // Descomentar la siguiente línea luego de elegir una superclase
        //super(vertices, aristas);
    }

    @Override
    public boolean conectados(Comparable a, Comparable b)
    {
        throw new UnsupportedOperationException("Método no implementado :(");
    }


}

package uy.edu.ucu.aed.parcial2;

enum TipoDeEstacion {
    TREN(1),
    AUTOBUSES(2);

    private final int value;

    TipoDeEstacion(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TipoDeEstacion fromInt(int i) {
        for (TipoDeEstacion type : TipoDeEstacion.values()) {
            if (type.getValue() == i) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid value for TipoEstacion: " + i);
    }
}

public class TEstacionDeLaRed {

    public TEstacionDeLaRed(int id, String nombre, TipoDeEstacion tipo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    private int id;
    private String nombre;
    private TipoDeEstacion tipo;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoDeEstacion getTipo() {
        return tipo;
    }
}

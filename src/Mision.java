import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

class Mision {
    private String nombre;
    private Date fechaLanzamiento;
    private String objetivos;
    private Nave nave;
    private ArrayList<Tripulante> tripulacion;

    public Mision(String nombre, Date fechaLanzamiento, String objetivos, Nave nave) {
        this.nombre = nombre;
        this.fechaLanzamiento = fechaLanzamiento;
        this.objetivos = objetivos;
        this.nave = nave;
        this.tripulacion = new ArrayList<>();
    }

    public void agregarTripulante(Tripulante tripulante) {
        tripulacion.add(tripulante);
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public Nave getNave() {
        return nave;
    }

    public ArrayList<Tripulante> getTripulacion() {
        return tripulacion;
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String fechaStr = sdf.format(fechaLanzamiento);
        return "Misión: " + nombre + "\nFecha de lanzamiento: " + fechaStr + "\nObjetivos: " + objetivos + "\nNave: " + nave.getNombre() + "\nTripulación: " + tripulacion;
    }
}

class Nave {
    private String nombre;
    private String modelo;

    public Nave(String nombre, String modelo) {
        this.nombre = nombre;
        this.modelo = modelo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getModelo() {
        return modelo;
    }

    public String toString() {
        return nombre + " (" + modelo + ")";
    }
}

class Tripulante {
    private String nombre;
    private String rol;

    public Tripulante(String nombre, String rol) {
        this.nombre = nombre;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRol() {
        return rol;
    }

    public String toString() {
        return nombre + " - " + rol;
    }
}


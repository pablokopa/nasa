import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataHandler {
    public static void guardarMision(Mision mision, String archivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String fechaStr = sdf.format(mision.getFechaLanzamiento());
            writer.write(mision.getNombre() + "\n");
            writer.write(fechaStr + "\n");
            writer.write(mision.getObjetivos() + "\n");
            writer.write(mision.getNave().getNombre() + "\n");
            writer.write(mision.getNave().getModelo() + "\n");
            for (Tripulante tripulante : mision.getTripulacion()) {
                writer.write(tripulante.getNombre() + "," + tripulante.getRol() + "\n");
            }
        }
    }

    public static Mision cargarMision(String archivo) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String nombre = reader.readLine();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date fechaLanzamiento = sdf.parse(reader.readLine());
            String objetivos = reader.readLine();
            String nombreNave = reader.readLine();
            String modeloNave = reader.readLine();
            Nave nave = new Nave(nombreNave, modeloNave);
            Mision mision = new Mision(nombre, fechaLanzamiento, objetivos, nave);
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datosTripulante = linea.split(",");
                mision.agregarTripulante(new Tripulante(datosTripulante[0], datosTripulante[1]));
            }
            return mision;
        } catch (ParseException e) {
            throw new IOException("Error al parsear la fecha", e);
        }
    }
}

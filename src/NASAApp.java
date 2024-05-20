import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NASAApp extends JFrame {
    private JTextField nombreMisionField;
    private JTextField fechaField;
    private JTextArea objetivosArea;
    private JTextField nombreNaveField;
    private JTextField modeloNaveField;
    private JTextField nombreTripulanteField;
    private JTextField rolTripulanteField;
    private JTextArea resultadoArea;
    private JComboBox<Mision> misionComboBox;
    private TrajectoryPanel trajectoryPanel;

    private ArrayList<Mision> misiones;

    public NASAApp() {
        misiones = new ArrayList<>();

        setTitle("NASA Mission Planner");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(12, 2));

        inputPanel.add(new JLabel("Nombre de la Misión:"));
        nombreMisionField = new JTextField();
        inputPanel.add(nombreMisionField);

        inputPanel.add(new JLabel("Fecha de Lanzamiento (dd-MM-yyyy):"));
        fechaField = new JTextField();
        inputPanel.add(fechaField);

        inputPanel.add(new JLabel("Objetivos de la Misión:"));
        objetivosArea = new JTextArea();
        inputPanel.add(new JScrollPane(objetivosArea));

        inputPanel.add(new JLabel("Nombre de la Nave:"));
        nombreNaveField = new JTextField();
        inputPanel.add(nombreNaveField);

        inputPanel.add(new JLabel("Modelo de la Nave:"));
        modeloNaveField = new JTextField();
        inputPanel.add(modeloNaveField);

        inputPanel.add(new JLabel("Nombre del Tripulante:"));
        nombreTripulanteField = new JTextField();
        inputPanel.add(nombreTripulanteField);

        inputPanel.add(new JLabel("Rol del Tripulante:"));
        rolTripulanteField = new JTextField();
        inputPanel.add(rolTripulanteField);

        JButton agregarTripulanteButton = new JButton("Agregar Tripulante");
        agregarTripulanteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarTripulante();
            }
        });
        inputPanel.add(agregarTripulanteButton);

        JButton crearMisionButton = new JButton("Crear Misión");
        crearMisionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearMision();
            }
        });
        inputPanel.add(crearMisionButton);

        JButton guardarMisionButton = new JButton("Guardar Misión");
        guardarMisionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarMision();
            }
        });
        inputPanel.add(guardarMisionButton);

        JButton cargarMisionButton = new JButton("Cargar Misión");
        cargarMisionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarMision();
            }
        });
        inputPanel.add(cargarMisionButton);

        JButton verTrayectoriaButton = new JButton("Ver Trayectoria");
        verTrayectoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verTrayectoria();
            }
        });
        inputPanel.add(verTrayectoriaButton);

        add(inputPanel, BorderLayout.WEST);

        resultadoArea = new JTextArea();
        add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        misionComboBox = new JComboBox<>();
        inputPanel.add(new JLabel("Misiones:"));
        inputPanel.add(misionComboBox);

        trajectoryPanel = new TrajectoryPanel(null);
        add(trajectoryPanel, BorderLayout.EAST);
    }

    private void agregarTripulante() {
        String nombre = nombreTripulanteField.getText();
        String rol = rolTripulanteField.getText();

        if (nombre.isEmpty() || rol.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el nombre y rol del tripulante.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Tripulante tripulante = new Tripulante(nombre, rol);
        Mision misionSeleccionada = (Mision) misionComboBox.getSelectedItem();
        if (misionSeleccionada != null) {
            misionSeleccionada.agregarTripulante(tripulante);
            resultadoArea.append("Tripulante agregado: " + tripulante + "\n");
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una misión para agregar tripulantes.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crearMision() {
        String nombre = nombreMisionField.getText();
        String fechaStr = fechaField.getText();
        String objetivos = objetivosArea.getText();
        String nombreNave = nombreNaveField.getText();
        String modeloNave = modeloNaveField.getText();

        if (nombre.isEmpty() || fechaStr.isEmpty() || objetivos.isEmpty() || nombreNave.isEmpty() || modeloNave.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaLanzamiento;
        try {
            fechaLanzamiento = sdf.parse(fechaStr);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Fecha de lanzamiento no válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Nave nave = new Nave(nombreNave, modeloNave);
        Mision mision = new Mision(nombre, fechaLanzamiento, objetivos, nave);
        misiones.add(mision);
        misionComboBox.addItem(mision);
        resultadoArea.append("Misión creada: " + mision + "\n");
    }

    private void guardarMision() {
        Mision misionSeleccionada = (Mision) misionComboBox.getSelectedItem();
        if (misionSeleccionada != null) {
            try {
                DataHandler.guardarMision(misionSeleccionada, misionSeleccionada.getNombre() + ".txt");
                resultadoArea.append("Misión guardada: " + misionSeleccionada.getNombre() + "\n");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar la misión.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una misión para guardar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarMision() {
        String nombreArchivo = JOptionPane.showInputDialog(this, "Ingrese el nombre del archivo de la misión (sin extensión):");
        if (nombreArchivo != null && !nombreArchivo.trim().isEmpty()) {
            try {
                Mision mision = DataHandler.cargarMision(nombreArchivo + ".txt");
                misiones.add(mision);
                misionComboBox.addItem(mision);
                resultadoArea.append("Misión cargada: " + mision + "\n");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al cargar la misión.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe ingresar un nombre de archivo válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verTrayectoria() {
        Mision misionSeleccionada = (Mision) misionComboBox.getSelectedItem();
        if (misionSeleccionada != null) {
            trajectoryPanel = new TrajectoryPanel(misionSeleccionada);
            add(trajectoryPanel, BorderLayout.EAST);
            revalidate();
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una misión para ver la trayectoria.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NASAApp().setVisible(true);
            }
        });
    }
}

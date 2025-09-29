package app.objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Date;

public class Appoint {
    public SimpleIntegerProperty numCita;
    public SimpleIntegerProperty idPaciente;
    public ObjectProperty<Date> fecha;
    public SimpleIntegerProperty idEspecialidad;

    public Appoint(int numCita, int idPaciente, int idEspecialidad, Date fecha){
        this.numCita = new SimpleIntegerProperty(numCita);
        this.idPaciente = new SimpleIntegerProperty(idPaciente);
        this.idEspecialidad = new SimpleIntegerProperty(idEspecialidad);
        this.fecha = new SimpleObjectProperty<>(fecha);

    }
}

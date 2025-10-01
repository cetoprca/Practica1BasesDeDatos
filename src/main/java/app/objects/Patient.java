package app.objects;

public class Patient {
    public int id;
    public String nombre;
    public String direccion;
    public String telefono;

    public Patient(int id, String nombre, String direccion, String telefono){
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }
}

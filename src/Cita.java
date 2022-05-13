public class Cita {

    private Integer id;
    private static String nombreCita;
    private String fecha;
    private Medico medico;
    private Paciente paciente;

    public Cita(String medico, String paciente, String toString, String motivo) {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static String getNombreCita() {
        return nombreCita;
    }

    public void setNombreCita(String nombreCita) {
        this.nombreCita = nombreCita;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

}


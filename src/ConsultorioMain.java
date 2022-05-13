import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConsultorioMain {
    private static ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
    public static HashMap<Integer, Medico> listaMedicos = new HashMap<Integer, Medico>();
    public static HashMap<Integer, Paciente> listaPacientes = new HashMap<Integer, Paciente>();
    public static HashMap<Integer, Cita> listaCitas = new HashMap<Integer, Cita>();
    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {

        crearCatalogos(listaMedicos, listaPacientes);

        if (validarAcceso()) {
            System.out.println("Usuario autorizado");
            menu();
        }
        else
            System.out.println("\nUsuario no autorizado.");
        menu();

        System.out.println("Usuario no autorizado");

    }

    public static void crearCatalogos(HashMap listaMedicos, HashMap listaPacientes) {

        String inputFilenameMedicos = "src/db/medicos.txt";
        String inputFilenamePacientes = "src/db/pacientes.txt";
        BufferedReader bufferedReader = null;
        String Nombre = "";
        String Especialidad = "";
        try {
            bufferedReader = new BufferedReader(new FileReader(inputFilenameMedicos));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                int coma = line.indexOf(',');
                Nombre = line.substring(0, coma);
                Especialidad = line.substring(coma+1, line.length());
                int id = listaMedicos.size();
                listaMedicos.put(id+1, new Medico(Nombre,Especialidad));
            }
        } catch(IOException e) {
            System.out.println("IOException catched while reading: " + e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }

        try {
            bufferedReader = new BufferedReader(new FileReader(inputFilenamePacientes));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Nombre = line;
                int id = listaPacientes.size();
                listaPacientes.put(id+1, new Paciente(line));
            }
        } catch(IOException e) {
            System.out.println("IOException catched while reading: " + e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }

    }


    private static boolean validarAcceso( ) {

        usuarios.add(new Usuario("admin", "admin"));
        usuarios.add(new Usuario("yetla", "1234"));

        System.out.println("**BIENVENIDO AL SISTEMA DE CITAS MEDICAS**");
        System.out.print("Usuario: ");
        String nombre = teclado.nextLine();
        System.out.print("Password: ");
        String password = teclado.nextLine();

        Usuario admin = new Usuario(nombre, password);

        return usuarios.contains(admin);

    }

    private static void menu(){
        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion;

        while (!salir) {
            System.out.println("\n Seleccione la Opcion deseada");
            System.out.println("1. Dar de alta un doctor");
            System.out.println("2. Dar de alta un paciente");
            System.out.println("3. Generar una cita");
            System.out.println("4. Ver las citas de todos los medicos");
            System.out.println("5. Ver las citas por nombre del medico");
            System.out.println("6. Ver las citas por nombre del paciente");
            System.out.println("7. Ver todas las citas");
            System.out.println("8. Salir");
            try {
                opcion = sn.nextInt();
                int id = 0;
                String nombre = "";
                switch (opcion) {
                    case 1:
                        String especialidad = "";
                        System.out.print("Nombre del doctor: ");
                        nombre = teclado.nextLine();
                        System.out.print("Especialidad: ");
                        especialidad = teclado.nextLine();
                        id = listaMedicos.size();
                        listaMedicos.put(id + 1, new Medico(nombre, especialidad));
                        break;
                    case 2:
                        System.out.print("Nombre del paciente: ");
                        nombre = teclado.nextLine();
                        id = listaPacientes.size();
                        listaPacientes.put(id + 1, new Paciente(nombre));
                        break;
                    case 3:
                        int medico;
                        int paciente;
                        String fecha;
                        String motivo;
                        boolean valid = false;
                        do {
                            System.out.println("Verificar cita");
                            for (Iterator<Map.Entry<Integer, Medico>> entries = listaMedicos.entrySet().iterator(); entries.hasNext(); ) {
                                Map.Entry<Integer, Medico> entry = entries.next();
                                String output = String.format("%s. %s", entry.getKey(), entry.getValue());
                                System.out.println(output);
                            }
                            System.out.print("Seleccione un doctor calificado: ");
                            medico = Integer.parseInt(teclado.nextLine());
                            valid = listaMedicos.containsKey(medico);
                        } while (valid != true);
                        valid = false;
                        do {
                            for (Iterator<Map.Entry<Integer, Paciente>> entries = listaPacientes.entrySet().iterator(); entries.hasNext(); ) {
                                Map.Entry<Integer, Paciente> entry = entries.next();
                                String output = String.format("%s. %s", entry.getKey(), entry.getValue());
                                System.out.println(output);
                            }
                            System.out.print("Seleccione un paciente registrado: ");
                            paciente = Integer.parseInt(teclado.nextLine());
                            valid = listaPacientes.containsKey(paciente);
                        } while (valid != true);
                        valid = false;
                        Date testDate = null;
                        do {
                            System.out.println("Fecha de la cita con formato aaaa-MM-ddHH:mm:ss");
                            Scanner sc = new Scanner(System.in);

                            fecha = sc.nextLine();
                            SimpleDateFormat df = new SimpleDateFormat("aaaa-MM-ddHH:mm:ss");
                            String date = fecha;
                            try {
                                testDate = df.parse(date);
                                valid = true;
                            } catch (Exception e) {
                                System.out.println("Ingrese la fecha en un formato valido");
                            }

                            if (df != null) {
                                if (!df.format(testDate).equals(date)) {
                                    System.out.println("Ingrese la fecha en un formato valido");
                                } else {
                                    valid = true;
                                }
                            }
                        } while (valid != true);
                        System.out.print("Motivo de la cita: ");
                        motivo = teclado.nextLine();
                        id = listaCitas.size();
                        String Medico = listaMedicos.get(medico).toString();
                        int coma = Medico.indexOf(':');
                        Medico = Medico.substring(coma + 2, Medico.length()).toString();
                        coma = Medico.indexOf(':');
                        Medico = Medico.substring(0, coma).toString();
                        Medico = Medico.substring(0, Medico.length() - 13);
                        String Paciente = listaPacientes.get(paciente).toString();
                        coma = Paciente.indexOf(':');
                        Paciente = Paciente.substring(coma + 2, Paciente.length()).toString();
                        listaCitas.put(id + 1, new Cita(Medico, Paciente, testDate.toString(), motivo));
                        System.out.print(listaCitas.get(id + 1));
                        break;
                    case 4:
                        Cita[] date = new Cita[0];
                        for (Cita cita : date) {
                            System.out.println("---------------------------------------------------");
                            System.out.println("Nombre medico:" + cita.getMedico().getNombre());
                            System.out.println("Nombre paciente:" + cita.getPaciente().getNombre());
                            System.out.println("Nombre cita:" + Cita.getNombreCita());
                        }
                        break;
                    case 5:
                        Cita[] dait = new Cita[0];
                        for (Cita cita : dait) {
                            System.out.println("---------------------------------------------------");
                            System.out.println("Nombre medico:" + cita.getMedico().getNombre());
                            System.out.println("Nombre paciente:" + cita.getPaciente().getNombre());
                            System.out.println("Nombre cita:" + Cita.getNombreCita());
                        }
                        break;
                    case 6:
                        Cita[] citas = new Cita[0];
                        for (Cita cita : citas) {
                            System.out.println("---------------------------------------------------");
                            System.out.println("Nombre paciente:" + cita.getPaciente().getNombre());
                            System.out.println("Nombre cita:" + Cita.getNombreCita());
                            System.out.println("Nombre medico:" + cita.getMedico().getNombre());
                        }
                        break;
                    case 7:
                        citas = new Cita[0];
                        for (Cita cita : citas) {
                            System.out.println("---------------------------------------------------");
                            System.out.println("Nombre cita:" + Cita.getNombreCita());
                            System.out.println("Nombre paciente:" + cita.getPaciente().getNombre());
                            System.out.println("Nombre medico:" + cita.getMedico().getNombre());
                        }
                        break;
                    case 8:
                        salir = true;
                        break;
                    default:
                        System.out.println("Ingrese una opcion válida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ingrese una opcion válida");
                sn.next();
            }
        }
    }
}

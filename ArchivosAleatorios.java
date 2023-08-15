

// Importamos las clases del paquete java.io
// donde encontramos la clase RandomAccessFile
import java.io.*;
import java.util.*;

public class ArchivosAleatorios {

    // Creamos una variable para leer los datos que se piden por teclado
    private static Scanner lectura = new Scanner(System.in);
    
    /* Variables para almacenar los datos de cada persona que se introduce
    por teclado. El sexo será 'M' para masculino y 'F' para femenino. Además
    los datos, tendrán una longitud fija máxima*/
    
    private static String idPersona=""; // Almacena un id unico de la persona
    private static int id=0; // 4 bytes
    private static String dniPersona=""; // 9 por 2 bytes = 18 bytes
    private static String nombrePersona=""; // 20 por 2 bytes = 40 bytes
    private static String apellidoPersona=""; // 20 por 2 bytes = 40 bytes
    private static String edadPersona=""; // Contendrá la edad en texto.
    private static int edadEntera=0; // 4 bytes
    private static char sexoPersona='M'; // 1 caracter x 2 bytes = 2 bytes
    // Almacena los bytes que ocupa un registro como máximo.
    private static int longitudRegistro =110;
    
    // Variable objeto de tipo Persona que almacena un nuevo objeto Persona.
   public static Persona nuevaPersona = new Persona();
    
    // Creamos una variable objeto de tipo RandomAccessFile.
    private static RandomAccessFile archivo=null;
    
    /* Variables necesarias para serializar los objetos de tipo Persona
    
    Varaibles para poder escribir en el fichero*/
    private static byte []array=null;
    private static ByteArrayOutputStream escribir=null;
    private static ObjectOutputStream salida=null;
    
    // Variables para poder leer el fichero
    private static ByteArrayInputStream leer=null;
    private static ObjectInputStream entrada = null;
    
    // Metodo que pide los datos de la persona por teclado.
    private static boolean pedirDatos(){
        
        // Pedimos al usuario cada uno de los datos de la persona
        do{
            System.out.println("Introduce el ID de la persona. Este ID "
                    + "debe ser mayor que cero: ");
            idPersona=lectura.nextLine();
            
            // Intentamos pasarlo a número
            try {
                id=Integer.parseInt(idPersona);
            } catch (NumberFormatException e) {
                System.out.println("Debes introducir un ID mayor que cero");
            }
        }while(idPersona.isEmpty() || id<=0);
        
        do{
            System.out.println("Introduce el DNI de la persona: ");
            dniPersona=lectura.nextLine();
        }while(dniPersona.isEmpty() || dniPersona.length() !=9);
        
        do{
            System.out.println("Introduce el nombre de la persona: ");
            nombrePersona=lectura.nextLine();
        }while(nombrePersona.isEmpty() || nombrePersona.length()>20);
        
        do{
            System.out.println("Introduce un apellido de la persona: ");
            apellidoPersona=lectura.nextLine();
        }while(apellidoPersona.isEmpty() || apellidoPersona.length()>20);
        
        do{
            System.out.println("Introduce la edad de la persona: ");
            edadPersona=lectura.nextLine();
            try {
                edadEntera=Integer.parseInt(edadPersona);
            } catch (NumberFormatException e) {
                System.out.println("Debes introducir un número entero");
            }
        }while(edadPersona.isEmpty() || edadEntera<=0);
        
        do{
            System.out.println("Introduce el sexo de la persona. 'M' para "
                    + "masculino o 'F' para femenino. (Sin las comillas): ");
            sexoPersona=lectura.next().charAt(0);
        }while(sexoPersona !='M' && sexoPersona !='F');
        
        // Pasamos los datos al objeto Persona
        nuevaPersona.setId(id);
        nuevaPersona.setDni(dniPersona);
        nuevaPersona.setNombre(nombrePersona);
        nuevaPersona.setApellidos(apellidoPersona);
        nuevaPersona.setEdad(edadEntera);
        nuevaPersona.setSexo(sexoPersona);
        // Devolvemos true
        return true;
    }
    
    /* Método para escribir en un archivo de acceso aleatorio con la clase
    RandomAccessFile.*/
    
    private static void escribirEnArchivoAleatorio(){
        try {
            /* Creamos o abrimos un nuevo archivo. En este caso:
            El primer parámetro hace referencia a la ruta del archivo.
            El segundo parametro es el siguiente:
            - r - read. Solo lectura.
            - rw - read/wirte. Lectura y escritura */
            archivo = new RandomAccessFile("./Registrar_personas.txt", "rw");
            
            // Ponemos el puntero al final del archivo
            archivo.seek(archivo.length());
            
            // Serializamos el objeto Persona
            // Lo convertimos en una secuencia de bytees.
            escribir= new ByteArrayOutputStream();
            salida = new ObjectOutputStream(escribir);
            salida.writeObject(nuevaPersona.toString());
            
            // Cerramos el objeto.
            salida.close();
            
            // obtenemos los bytes del libro serializado
            array = escribir.toByteArray();
            
            // Escribimos los bytes en el archivo.
            archivo.write(array);
            
            // Cerramos el archivo
            archivo.close();
        } catch (Exception e) {
            System.out.println("No se puede escribir en el archivo" 
            + e.getMessage());
        }
    }
    
    // Metodo para leer el archivo de acceso aleatorio
    private static void leerArchivoAleatorio(){
        try {
            /* Creamos o abrimos un nuevo archivo. Este archivo lo crearemos
            dentro de la carpeta src de nuestro proyecto. Además debemos tener
            en cuenta que el constructor de la clase RandomAccessFile recibe
            2 parámetros:
            El primero hace referencia a la ruta del archivo.
            El segundo hace referencia al modo de apertura del archivo:
            - r - read. Solo lectura.
            - rw - read/wirte. Lectura y escritura */
            archivo = new RandomAccessFile("./Registrar_personas.txt", "r");
            
            // Nos posicionamos al principio del fichero
            archivo.seek(0);
            
            // Almacenamos los bytes del fichero en un array de bytes
            array = new byte[(int)archivo.length()];
            
            // Leemos todos los bytes del fichero
            archivo.readFully(array);
            
            // Convertimos ese array de bytes en un objeto.
            leer = new ByteArrayInputStream(array);
            entrada = new ObjectInputStream(leer);
            
            /* Hacemos una conversion de lo que lee el ObjectInputStream
            a un objeto de tipo Persona y lo almacenamos
            en la variable objeto nuevaPersona*/
            nuevaPersona=(Persona) entrada.readObject();
            System.out.println(nuevaPersona);
            // Cerramos el objeto ObjectInputStream
            entrada.close();
            
            
        } catch (Exception e) {
            System.out.println("No se puede leer el archivo" 
            + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        
        /*Si a la hora de pedir los datos devuelve true, es que los datos
        se han introducido de forma correcta.*/
        if(pedirDatos()){
            
            // Si los datos son correctos, intentamos...
            try {
                escribirEnArchivoAleatorio();
                // Escribimos en el archivo.
                //escribirEnArchivoAleatorio(nuevaPersona);
            } catch (Exception e) {
                System.out.println("No se ha podido registrar la persona.");
            }
        }
        
        /* Si se quiere leer el archivo,
        solo debemos llamarlo desde este método, de la siguiente manera*/
        leerArchivoAleatorio();
    }
}

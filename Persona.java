

import java.io.Serializable;

public class Persona implements Serializable {
    /* Declaramos los atributos o características de nuestro objeto persona:
    DNI, nombre, apellidos, edad y sexo*/
    private int id;
    private String dni;
    private String nombre;
    private String apellidos;
    private int edad;
    private char sexo;
    
    Persona(){
        
    }
    
    // Creamos los métos setter (para establecer valores al objeto
    // y getter que devolverá el valor de cada uno de los atributos del objeto

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    public void setDni(String dni) {
        this.dni = dni;
    }
    
    public String getDni() {
        return dni;
    }

    public void setNombre(String nombre) {
            this.nombre = nombre;   
    }

    public String getNombre() {
        return nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
   
    public String getApellidos() {
        return apellidos;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getEdad() {
        return edad;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }
 
    public char getSexo() {
        return sexo;
    }
    
    /* Sobreescribimos el método toString() de la clase Object. Esto lo hacemos
    para devolver todos los datos de la persona en cuestión.*/

    @Override
    public String toString() {
        return this.getDni() + ", " + this.getNombre() + ", " 
                + this.getApellidos () + ", " + this.getEdad()+ ", " 
                + this.getSexo() + ".\n";
    } 
}

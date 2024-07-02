package Modelo;


public class Administradores {
    private String idadministrador;
    private String nombre;
    private String password;
    
    // Getters y setters
    public String getIdAdministrador(){ 
        return this.idadministrador; 
    }
    
    public void setIdAdministrador(String idadministrador){ 
        this.idadministrador = idadministrador; 
    }
    
    public String getNombre(){ 
        return this.nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
}

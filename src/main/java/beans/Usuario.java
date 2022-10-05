package beans;

public class Usuario {
    private String username;
    private String clave;
    private String nombre;
    private String apellido;
    private String email;
    private double saldo;
    private boolean premium;

    public Usuario(String username, String clave, String nombre, String apellido, String email, double saldo, boolean premium) {
        this.username = username;
        this.clave = clave;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.saldo = saldo;
        this.premium = premium;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    @Override
    public String toString() {
        return "Usuario{" + "username=" + username + ", clave=" + clave + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", saldo=" + saldo + ", premium=" + premium + '}';
    }
    
    
}

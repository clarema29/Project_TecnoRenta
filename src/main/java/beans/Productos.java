package beans;

public class Productos {
    private int id;
    private String tipo;
    private String referencia;
    private String marca;
    private int cantidad;
    private boolean novedad;

    public Productos(int id, String tipo, String referencia, String marca, int cantidad, boolean novedad) {
        this.id = id;
        this.tipo = tipo;
        this.referencia = referencia;
        this.marca = marca;
        this.cantidad = cantidad;
        this.novedad = novedad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isNovedad() {
        return novedad;
    }

    public void setNovedad(boolean novedad) {
        this.novedad = novedad;
    }

    @Override
    public String toString() {
        return "Productos{" + "id=" + id + ", tipo=" + tipo + ", referencia=" + referencia + ", marca=" + marca + ", cantidad=" + cantidad + ", novedad=" + novedad + '}';
    }
    
    
}

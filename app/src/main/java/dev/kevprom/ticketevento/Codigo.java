package dev.kevprom.ticketevento;

public class Codigo {
    String codigo;
    int estado;
    String tipo;
    String precio;
    String usuario;

    public Codigo(){};

    public Codigo(String codigo, int estado, String tipo, String precio, String usuario) {
        this.codigo = codigo;
        this.estado = estado;
        this.tipo = tipo;
        this.precio = precio;
        this.usuario = usuario;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Codigo{" +
                "codigo='" + codigo + '\'' +
                ", estado=" + estado +
                ", tipo='" + tipo + '\'' +
                ", precio='" + precio + '\'' +
                ", usuario='" + usuario + '\'' +
                '}';
    }
}

package com.platinum.model;

public class Transaccion {
    private long id;
    private String rutCliente;
    private String rutDueno;
    private long idCuenta;
    private double montoTransferencia;
    private String cuentaTransferencia;
    private String tipoCuenta;

    // Constructor completo
    public Transaccion(long id, String rutCliente, String rutDueno, long idCuenta,
                       double montoTransferencia, String cuentaTransferencia, String tipoCuenta) {
        this.id = id;
        this.rutCliente = rutCliente;
        this.rutDueno = rutDueno;
        this.idCuenta = idCuenta;
        this.montoTransferencia = montoTransferencia;
        this.cuentaTransferencia = cuentaTransferencia;
        this.tipoCuenta = tipoCuenta;
    }

    // Getters y setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getRutCliente() { return rutCliente; }
    public void setRutCliente(String rutCliente) { this.rutCliente = rutCliente; }

    public String getRutDueno() { return rutDueno; }
    public void setRutDueno(String rutDueno) { this.rutDueno = rutDueno; }

    public long getIdCuenta() { return idCuenta; }
    public void setIdCuenta(long idCuenta) { this.idCuenta = idCuenta; }

    public double getMontoTransferencia() { return montoTransferencia; }
    public void setMontoTransferencia(double montoTransferencia) { this.montoTransferencia = montoTransferencia; }

    public String getCuentaTransferencia() { return cuentaTransferencia; }
    public void setCuentaTransferencia(String cuentaTransferencia) { this.cuentaTransferencia = cuentaTransferencia; }

    public String getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; }
}

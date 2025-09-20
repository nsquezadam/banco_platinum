package com.platinum.model;

public class CtaCorriente {
    private int idCuenta;
    private String rutCliente;
    private double monto;
    private String ejecutivo;

    public CtaCorriente() {}

    public CtaCorriente(int idCuenta, String rutCliente, double monto, String ejecutivo) {
        this.idCuenta = idCuenta;
        this.rutCliente = rutCliente;
        this.monto = monto;
        this.ejecutivo = ejecutivo;
    }

    public int getIdCuenta() { return idCuenta; }
    public void setIdCuenta(int idCuenta) { this.idCuenta = idCuenta; }

    public String getRutCliente() { return rutCliente; }
    public void setRutCliente(String rutCliente) { this.rutCliente = rutCliente; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getEjecutivo() { return ejecutivo; }
    public void setEjecutivo(String ejecutivo) { this.ejecutivo = ejecutivo; }
}

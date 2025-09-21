# language: es
Característica: Transferencias bancarias
  Escenario: Registrar una transferencia exitosa
    Dado que la cuenta con id 1 tiene un saldo disponible
    Cuando se realiza una transferencia de 100.0 pesos
    Entonces el saldo final debe ser menor en 100.0 pesos

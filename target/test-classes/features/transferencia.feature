Feature: Registro de transferencias

  Scenario: Registrar una nueva transferencia
    Given el usuario abre la página de transferencia
    When completa el formulario con rutCliente "12345678-9", rutDueno "98765432-1", idCuenta "1", monto "50000", cuenta "123-456789", tipo "Corriente"
    Then debería ver el mensaje "Transferencia registrada con éxito"

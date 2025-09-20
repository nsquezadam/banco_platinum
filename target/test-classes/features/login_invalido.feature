Feature: Login inválido de ejecutivos

  Scenario: Ingreso con credenciales erróneas
    Given el usuario abre la página de login
    When ingresa rut "00000000-0" y departamento "Falso"
    Then debería ver el mensaje "Credenciales inválidas"

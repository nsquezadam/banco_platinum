Feature: Login de ejecutivos

  Scenario: Ingreso con credenciales válidas
    Given el usuario abre la página de login
    When ingresa rut "12345678-9" y departamento "Ventas"
    Then debería ver el mensaje "Bienvenido"

  Scenario: Ingreso con credenciales inválidas
    Given el usuario abre la página de login
    When ingresa rut "00000000-0" y departamento "Fake"
    Then debería ver el mensaje "Credenciales inválidas"

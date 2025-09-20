package com.platinum.steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransferenciaSteps {

    private WebDriver driver;

    @Given("el usuario abre la página de transferencia")
    public void abrir_pagina_transferencia() {
        io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:8081/CtaCorriente/transferencia.jsp");
    }

    @When("completa el formulario con rutCliente {string}, rutDueno {string}, idCuenta {string}, monto {string}, cuenta {string}, tipo {string}")
    public void completar_formulario(String rutCliente, String rutDueno, String idCuenta, String monto, String cuenta, String tipo) {
        driver.findElement(By.name("rutCliente")).sendKeys(rutCliente);
        driver.findElement(By.name("rutDueno")).sendKeys(rutDueno);
        driver.findElement(By.name("idCuenta")).sendKeys(idCuenta);
        driver.findElement(By.name("montoTransferencia")).sendKeys(monto);
        driver.findElement(By.name("cuentaTransferencia")).sendKeys(cuenta);
        driver.findElement(By.name("tipoCuenta")).sendKeys(tipo);

        driver.findElement(By.tagName("button")).click();
    }

    @Then("debería ver el mensaje {string}")
    public void verificar_mensaje(String mensaje) {
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains(mensaje));
        driver.quit();
    }
}

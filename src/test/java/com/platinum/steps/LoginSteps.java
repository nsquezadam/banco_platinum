package com.platinum.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        // Inicializa el driver de Chrome
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Dado("el usuario abre la página de login")
    public void el_usuario_abre_la_pagina_de_login() {
        driver.get("http://localhost:8081/CtaCorriente/login.jsp");
    }

    @Cuando("ingresa rut {string} y departamento {string}")
    public void ingresa_rut_y_departamento(String rut, String departamento) {
        WebElement campoRut = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("rut")));
        WebElement campoDepto = driver.findElement(By.name("departamento"));

        campoRut.clear();
        campoRut.sendKeys(rut);
        campoDepto.clear();
        campoDepto.sendKeys(departamento);

        WebElement boton = driver.findElement(By.cssSelector("button[type='submit']"));
        boton.click();
    }

    @Entonces("debería ver el mensaje de bienvenida {string}")
    public void deberia_ver_el_mensaje_de_bienvenida(String mensajeEsperado) {
        WebElement mensaje = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mensajeBienvenida")));
        assertTrue(mensaje.getText().contains(mensajeEsperado));
    }

    @Entonces("debería ver el mensaje de error {string}")
    public void deberia_ver_el_mensaje_de_error(String mensajeEsperado) {
        WebElement mensaje = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mensajeError")));
        assertTrue(mensaje.getText().contains(mensajeEsperado));
    }
}

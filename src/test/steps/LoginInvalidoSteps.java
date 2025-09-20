package com.platinum.steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginInvalidoSteps {

    private WebDriver driver;

    @Given("el usuario abre la página de login")
    public void abrir_pagina_login() {
        io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:8081/CtaCorriente/login.jsp");
    }

    @When("ingresa rut {string} y departamento {string}")
    public void ingresar_credenciales_invalidas(String rut, String departamento) {
        WebElement rutInput = driver.findElement(By.name("rut"));
        rutInput.clear();
        rutInput.sendKeys(rut);

        WebElement deptoInput = driver.findElement(By.name("departamento"));
        deptoInput.clear();
        deptoInput.sendKeys(departamento);

        driver.findElement(By.tagName("button")).click();
    }

    @Then("debería ver el mensaje {string}")
    public void verificar_mensaje_error(String mensaje) {
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains(mensaje), "No se encontró el mensaje esperado en la página");
        driver.quit();
    }
}

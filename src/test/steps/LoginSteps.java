package steps;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginSteps {
    private static WebDriver driver;

    @Given("el usuario abre la página de login")
    public void abrirPaginaLogin() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:8081/CtaCorriente/login.jsp");
    }

    @When("ingresa rut {string} y departamento {string}")
    public void ingresarCredenciales(String rut, String depto) {
        driver.findElement(By.name("rut")).sendKeys(rut);
        driver.findElement(By.name("departamento")).sendKeys(depto);
    }

    @When("hace clic en {string}")
    public void hacerClick(String boton) {
        driver.findElement(By.xpath("//button[text()='" + boton + "']")).click();
    }

    @Then("debería ver el mensaje {string}")
    public void verificarMensaje(String mensaje) {
        assertTrue(driver.getPageSource().contains(mensaje));
        driver.quit();
    }
}

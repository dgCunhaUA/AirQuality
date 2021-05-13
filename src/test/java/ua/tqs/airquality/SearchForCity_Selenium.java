package ua.tqs.airquality;

import io.cucumber.java.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SearchForCity_Selenium {

    WebDriver driver;

    @BeforeEach
    public void setup(){
        //use FF Driver
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    void searchForCityName() {

        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1299, 741));

        assertThat(driver.findElement(By.cssSelector(".title")).getText(), is("AirQuality WebPage"));
        assertThat(driver.findElement(By.cssSelector(".search-text")).getText(), is("Procurar por Cidade"));

        driver.findElement(By.id("cityName")).click();
        driver.findElement(By.id("cityName")).sendKeys("Viseu");

        driver.findElement(By.linkText("Pesquisar")).click();


        assertThat(driver.findElement(By.cssSelector(".title-container > h1")).getText(), is("Resultados"));
        assertThat(driver.findElement(By.cssSelector(".city-name > h1")).getText(), is("\"Viseu\""));
        assertThat(driver.findElement(By.cssSelector(".city-information > .row:nth-child(2) > .col-lg-6:nth-child(1) > p")).getText(), is("Pais: \"PT\""));
        assertThat(driver.findElement(By.cssSelector(".city-information > .row:nth-child(2) > .col-lg-6:nth-child(2) > p")).getText(), is("Codigo Postal: \"3500-004\""));
        assertThat(driver.findElement(By.cssSelector(".city-information > .row:nth-child(3) > .col-lg-6:nth-child(1) > p")).getText(), is("Latitude: 40.661"));
        assertThat(driver.findElement(By.cssSelector(".city-information > .row:nth-child(3) > .col-lg-6:nth-child(2) > p")).getText(), is("Longitude: -7.9097"));

        driver.findElement(By.linkText("Voltar")).click();
        assertThat(driver.findElement(By.cssSelector(".title")).getText(), is("AirQuality WebPage"));
        assertThat(driver.findElement(By.cssSelector(".search-text")).getText(), is("Procurar por Cidade"));
    }

    @Test
    void searchForWrongCityName() {

        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1299, 741));

        assertThat(driver.findElement(By.cssSelector(".title")).getText(), is("AirQuality WebPage"));
        assertThat(driver.findElement(By.cssSelector(".search-text")).getText(), is("Procurar por Cidade"));

        driver.findElement(By.id("cityName")).click();
        driver.findElement(By.id("cityName")).sendKeys("xyzwklxzzzzz");

        driver.findElement(By.linkText("Pesquisar")).click();


        assertThat(driver.findElement(By.cssSelector(".title-container > h1")).getText(), is("Resultados"));
        assertThat(driver.findElement(By.cssSelector(".city-name > h1")).getText(), is("City Not Found"));
        assertThat(driver.findElement(By.cssSelector(".city-information > .row:nth-child(2) > .col-lg-6:nth-child(1) > p")).getText(), is("Pais: -"));
        assertThat(driver.findElement(By.cssSelector(".city-information > .row:nth-child(2) > .col-lg-6:nth-child(2) > p")).getText(), is("Codigo Postal: -"));
        assertThat(driver.findElement(By.cssSelector(".city-information > .row:nth-child(3) > .col-lg-6:nth-child(1) > p")).getText(), is("Latitude: -"));
        assertThat(driver.findElement(By.cssSelector(".city-information > .row:nth-child(3) > .col-lg-6:nth-child(2) > p")).getText(), is("Longitude: -"));

        driver.findElement(By.linkText("Voltar")).click();
        assertThat(driver.findElement(By.cssSelector(".title")).getText(), is("AirQuality WebPage"));
        assertThat(driver.findElement(By.cssSelector("form:nth-child(1) > .search-text")).getText(), is("Procurar por Cidade"));
        assertThat(driver.findElement(By.cssSelector("form:nth-child(3) > .search-text")).getText(), is("Procurar por Lat e Lng"));
    }


    @Test
    void searchForCityLatLng() {
        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1299, 741));

        assertThat(driver.findElement(By.cssSelector(".title")).getText(), is("AirQuality WebPage"));
        assertThat(driver.findElement(By.cssSelector("form:nth-child(1) > .search-text")).getText(), is("Procurar por Cidade"));
        assertThat(driver.findElement(By.cssSelector("form:nth-child(3) > .search-text")).getText(), is("Procurar por Lat e Lng"));

        driver.findElement(By.id("cityLat")).click();
        driver.findElement(By.id("cityLat")).sendKeys("40.661");
        driver.findElement(By.id("cityLng")).sendKeys("-7.9097");
        driver.findElement(By.cssSelector("#searchBtn1 > .btn")).click();


        assertThat(driver.findElement(By.cssSelector(".title-container > h1")).getText(), is("Resultados"));
        assertThat(driver.findElement(By.cssSelector(".city-name > h1")).getText(), is("\"Viseu\""));
        assertThat(driver.findElement(By.cssSelector(".city-information > .row:nth-child(2) > .col-lg-6:nth-child(1) > p")).getText(), is("Pais: \"PT\""));
        assertThat(driver.findElement(By.cssSelector(".city-information > .row:nth-child(2) > .col-lg-6:nth-child(2) > p")).getText(), is("Codigo Postal: \"3500-004\""));
        assertThat(driver.findElement(By.cssSelector(".city-information > .row:nth-child(3) > .col-lg-6:nth-child(1) > p")).getText(), is("Latitude: 40.661"));
        assertThat(driver.findElement(By.cssSelector(".city-information > .row:nth-child(3) > .col-lg-6:nth-child(2) > p")).getText(), is("Longitude: -7.9097"));

        driver.findElement(By.linkText("Voltar")).click();
        assertThat(driver.findElement(By.cssSelector(".title")).getText(), is("AirQuality WebPage"));
        assertThat(driver.findElement(By.cssSelector("form:nth-child(1) > .search-text")).getText(), is("Procurar por Cidade"));
        assertThat(driver.findElement(By.cssSelector("form:nth-child(3) > .search-text")).getText(), is("Procurar por Lat e Lng"));
    }


    @Test
    void searchForWrongCityLatLng() {

        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(1299, 741));

        assertThat(driver.findElement(By.cssSelector(".title")).getText(), is("AirQuality WebPage"));
        assertThat(driver.findElement(By.cssSelector("form:nth-child(1) > .search-text")).getText(), is("Procurar por Cidade"));
        assertThat(driver.findElement(By.cssSelector("form:nth-child(3) > .search-text")).getText(), is("Procurar por Lat e Lng"));

        driver.findElement(By.id("cityLat")).click();
        driver.findElement(By.id("cityLat")).sendKeys("1234");
        driver.findElement(By.id("cityLng")).sendKeys("1234");
        driver.findElement(By.cssSelector("#searchBtn1 > .btn")).click();


        assertThat(driver.findElement(By.cssSelector(".title-container > h1")).getText(), is("Resultados"));
        assertThat(driver.findElement(By.cssSelector(".city-name > h1")).getText(), is("City Not Found"));
        assertThat(driver.findElement(By.cssSelector(".city-information > .row:nth-child(2) > .col-lg-6:nth-child(1) > p")).getText(), is("Pais: -"));
        assertThat(driver.findElement(By.cssSelector(".city-information > .row:nth-child(2) > .col-lg-6:nth-child(2) > p")).getText(), is("Codigo Postal: -"));
        assertThat(driver.findElement(By.cssSelector(".city-information > .row:nth-child(3) > .col-lg-6:nth-child(1) > p")).getText(), is("Latitude: -"));
        assertThat(driver.findElement(By.cssSelector(".city-information > .row:nth-child(3) > .col-lg-6:nth-child(2) > p")).getText(), is("Longitude: -"));

        driver.findElement(By.linkText("Voltar")).click();
        assertThat(driver.findElement(By.cssSelector(".title")).getText(), is("AirQuality WebPage"));
        assertThat(driver.findElement(By.cssSelector("form:nth-child(1) > .search-text")).getText(), is("Procurar por Cidade"));
        assertThat(driver.findElement(By.cssSelector("form:nth-child(3) > .search-text")).getText(), is("Procurar por Lat e Lng"));
    }
}

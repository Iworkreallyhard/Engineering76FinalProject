package com.sparta.greg.pom.pagesTest.components;

import com.sparta.greg.pom.pages.components.PropertyLoader;
import com.sparta.greg.pom.pages.trainee.HomeTrainee;
import com.sparta.greg.pom.pages.trainer.HomeTrainer;
import com.sparta.greg.pom.pages.components.Login;
import com.sparta.greg.pom.webDriverFactory.WebDriverFactory;
import com.sparta.greg.pom.webDriverFactory.WebDriverType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class LoginTest {

    private static Login login;
    private static WebDriver webDriver;
    private static String trainerUsername;
    private static String trainerPassword;
    private static String traineeUsername;
    private static String traineePassword;

    @BeforeEach
    void setup() {
        webDriver = WebDriverFactory.getWebDriver(WebDriverType.CHROME);
        webDriver.get("http://localhost:8080");
        login = new Login(webDriver);

        PropertyLoader.loadProperties();
        trainerUsername = PropertyLoader.properties.getProperty("trainerUsername");
        trainerPassword = PropertyLoader.properties.getProperty("trainerPassword");
        traineeUsername = PropertyLoader.properties.getProperty("traineeUsername");
        traineePassword = PropertyLoader.properties.getProperty("traineePassword");
    }

    @Test
    void checkLoginPageLoaded() {
        Assertions.assertEquals("http://localhost:8080/login", webDriver.getCurrentUrl());
        webDriver.close();
    }

    @Test
    void canSignInAsTrainer() {
        HomeTrainer homeTrainer = login.logInAsTrainer(trainerUsername, trainerPassword);
        Assertions.assertEquals(HomeTrainer.class, homeTrainer.getClass());
        webDriver.close();
    }

    @Test
    void canSignInAsTrainee() {
        HomeTrainee homeTrainee = login.logInAsTrainee(traineeUsername, traineePassword);
        Assertions.assertEquals(HomeTrainee.class, homeTrainee.getClass());
        webDriver.close();
    }

    @Test
    void canSignInAsTraineeFromLogout() {
        webDriver.get("http://localhost:8080/login?logout");
        HomeTrainee homeTrainee = login.logInAsTrainee(traineeUsername, traineePassword);
        Assertions.assertEquals(HomeTrainee.class, homeTrainee.getClass());
        webDriver.close();
    }

    @Test
    void canSignInAsTrainerFromLogout() {
        webDriver.get("http://localhost:8080/login?logout");
        HomeTrainer homeTrainer = login.logInAsTrainer(trainerUsername, trainerPassword);
        Assertions.assertEquals(HomeTrainer.class, homeTrainer.getClass());
        webDriver.close();
    }

    @Test
    void canSignInAsTraineeFromError() {
        webDriver.get("http://localhost:8080/login?error");
        HomeTrainee homeTrainee = login.logInAsTrainee(traineeUsername, traineePassword);
        Assertions.assertEquals(HomeTrainee.class, homeTrainee.getClass());
        webDriver.close();
    }

    @Test
    void canSignInAsTrainerFromError() {
        webDriver.get("http://localhost:8080/login?error");
        HomeTrainer homeTrainer = login.logInAsTrainer(trainerUsername, trainerPassword);
        Assertions.assertEquals(HomeTrainer.class, homeTrainer.getClass());
        webDriver.close();
    }

}

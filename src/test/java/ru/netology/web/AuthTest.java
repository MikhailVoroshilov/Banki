package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.cssSelector;

public class AuthTest {
    private SelenideElement form = $("[action]");

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLogInIfUserIsValid() {
        Registration user = Generation.generateNewActiveUser("active");

        form.$(cssSelector("[name=login]")).sendKeys(user.getLogin());
        form.$(cssSelector("[name=password]")).sendKeys(user.getPassword());
        form.$(cssSelector("[type=button]")).click();
        $(byText("Личный кабинет")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldNotLogInIfBlockedUser() {
        Registration user = Generation.generateNewActiveUser("blocked");

        form.$(cssSelector("[name=login]")).sendKeys(user.getLogin());
        form.$(cssSelector("[name=password]")).sendKeys(user.getPassword());
        form.$(cssSelector("[type=button]")).click();
        $(byText("Пользователь заблокирован")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldNotLogInIfInvalidLogin() {
        Registration user = Generation.generateNewActiveUserInvalidLogin();
        form.$(cssSelector("[name=login]")).sendKeys(user.getLogin());
        form.$(cssSelector("[name=password]")).sendKeys(user.getPassword());
        form.$(cssSelector("[type=button]")).click();
        $(byText("Неверно указан логин или пароль")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldNotLogInIfInvalidPassword() {
        Registration user = Generation.generateNewActiveInvalidPassword();
        form.$(cssSelector("[name=login]")).sendKeys(user.getLogin());
        form.$(cssSelector("[name=password]")).sendKeys(user.getPassword());
        form.$(cssSelector("[type=button]")).click();
        $(byText("Неверно указан логин или пароль")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}

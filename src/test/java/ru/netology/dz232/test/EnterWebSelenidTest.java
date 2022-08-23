package ru.netology.dz232.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.dz232.utility.DataGeneration;

import static com.codeborne.selenide.Selenide.*;

public class EnterWebSelenidTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
        Configuration.holdBrowserOpen = true;
    }

    @AfterEach
    void memoryClear() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }

    @Test
    void shouldActiveUser() {
        var validUser = DataGeneration.Registration.getRegisteredUser("active");
        $("[data-test-id=login] .input__box .input__control").val(validUser.getLogin());
        $("[data-test-id=password] .input__box .input__control").val(validUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("h2").shouldHave(Condition.exactText("  Личный кабинет"));

    }

    @Test
    void shouldBlockedUser() {
        var validUser = DataGeneration.Registration.getRegisteredUser("blocked");
        $("[data-test-id=login] .input__box .input__control").val(validUser.getLogin());
        $("[data-test-id=password] .input__box .input__control").val(validUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Пользователь заблокирован"));
    }

    @Test
    void shouldUserRandomLoginUnregistered() {
        var invalidUser = DataGeneration.Registration.getUser("blocked");
        $("[data-test-id=login] .input__box .input__control").val(DataGeneration.Registration.getRandomLogin());
        $("[data-test-id=password] .input__box .input__control").val(invalidUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    void shouldUserRandomPasswordUnregistered() {
        var invalidUser = DataGeneration.Registration.getUser("blocked");
        $("[data-test-id=login] .input__box .input__control").val(invalidUser.getLogin());
        $("[data-test-id=password] .input__box .input__control").val(DataGeneration.Registration.getRandomPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    void shouldUserRandomLoginRegistered() {
        var validUser = DataGeneration.Registration.getRegisteredUser("blocked");
        $("[data-test-id=login] .input__box .input__control").val(DataGeneration.Registration.getRandomLogin());
        $("[data-test-id=password] .input__box .input__control").val(validUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    void shouldUserRandomPasswordRegistered() {
        var validUser = DataGeneration.Registration.getRegisteredUser("blocked");
        $("[data-test-id=login] .input__box .input__control").val(validUser.getLogin());
        $("[data-test-id=password] .input__box .input__control").val(DataGeneration.Registration.getRandomPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    void shouldLoginNotification() {
        var validUser = DataGeneration.Registration.getRegisteredUser("active");
        $("[data-test-id=login] .input__box .input__control").val();
        $("[data-test-id=password] .input__box .input__control").val(validUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=login].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldPassNotification() {
        var validUser = DataGeneration.Registration.getRegisteredUser("active");
        $("[data-test-id=login] .input__box .input__control").val(validUser.getLogin());
        $("[data-test-id=password] .input__box .input__control").val();
        $("[data-test-id=action-login]").click();
        $("[data-test-id=password].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
}

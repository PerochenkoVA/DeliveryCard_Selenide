package ru.netology.auto;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCard {

    LocalDate date = LocalDate.now().plusDays(5);
    LocalDate date1 = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Test
    void taskOne() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Петропавловск-Камчатский"); //город
        $("[data-test-id=date] input").doubleClick(); //очитка поля дата
        $("[data-test-id=date] input").sendKeys(" "); //дата
        $("[data-test-id=date] input").sendKeys(formatter.format(date)); //дата
        $("[data-test-id=name] input").setValue("Иванов Владимир"); // ФИО
        $("[data-test-id=phone] input").setValue("+79163334455"); // telefon number
        $("[data-test-id=agreement]").click(); //согласие
        $("[class='button__content']").click(); //Продолжить
        $(withText("Успешно")).shouldBe(visible, Duration.ofSeconds(50));

    }

    @Test
    void taskTwo() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Петропавловск-Камчатский"); //город
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE); //очитка поля дата
        $("[data-test-id=date] input").setValue(formatter.format(date1)); //дата
        $("[data-test-id=name] input").setValue("Иванов Владимир"); // ФИО
        $("[data-test-id=phone] input").setValue("+79163334455"); // telefon number
        $("[data-test-id=agreement]").click(); //согласие
        $("[class='button__content']").click(); //Продолжить
        $(withText("Заказ на выбранную дату невозможен")).shouldBe(visible);

    }

    String generateDate(int days, String datePattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(datePattern));
    }

    @Test
    void shouldSendValidForm() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Петропавловск-Камчатский");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Иванов Владимир");
        $("[data-test-id=phone] input").setValue("+79163334455");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldBe(visible)
                .shouldHave(exactText("Встреча успешно забронирована на " + planningDate));
    }

}
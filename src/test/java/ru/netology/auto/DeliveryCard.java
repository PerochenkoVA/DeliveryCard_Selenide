package ru.netology.auto;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DeliveryCard {

    @Test
    void taskOne() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Петропавловск-Камчатский"); //город
        $("[data-test-id=date] input").setValue(formatDate); //дата
        $("[data-test-id=name] input").setValue("Иванов Владимир"); // ФИО
        $("[data-test-id=phone] input").setValue("+79163334455"); // telefon number
        $("[data-test-id=agreement]").click(); //согласие
        $("[class='button__content']").click(); //Продолжить

        $(withText("Успешно")).shouldBe(visible, Duration.ofSeconds(50));

    }


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String date = LocalDate.now().plusWeeks(2).toString();
    String formatDate = String.format(date, formatter);



}
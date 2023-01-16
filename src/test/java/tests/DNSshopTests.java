package tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.CollectionCondition.textsInAnyOrder;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;

public class DNSshopTests extends TestBase {

    //consts
    SelenideElement addressInput = $x("//input[@type='text']");

    @Test
    @Disabled //Для нормальной работы остальных тестов нужно чтобы шаги этого теста прогонялись BeforeEach
    @Tag("dnsshop")
    @DisplayName("Закрываем popup выбора города")
    void closeCitySelectPopup() {
        step("Проверяем что popup отображается", () -> {
            $(".v-confirm-city_XmH").shouldBe(visible);
        });
        step("Нажимаем на кнопку Всё верно", () -> {
            $x("//*[text()[contains(.,'Всё верно')]]").click();
        });
    }

    @Test
    @Tag("dnsshop")
    @DisplayName("Проверяем субкатегории в Бытовой технике")
    void checkSubcategory() {
        $(".header-bottom__catalog-spoiler").click();
        $x("//a[contains(text(),'Бытовая техника')]").click();
        $$(".subcategory__title").shouldHave(textsInAnyOrder("Встраиваемая техника", "Техника для кухни", "Техника для дома"));
    }

    @Test
    @Tag("dnsshop")
    @DisplayName("Проверяем отображение модального окна регистрации/входа по нажатию на Войти в блоке Личный кабинет")
    void checkLogin() {
        $(".personal-block-desktop").shouldBe(visible);
        $(".personal-block-desktop__buttons").$("button").click();
        $(".form-entry-or-registry").shouldBe(visible);
    }

    @Test
    @Tag("dnsshop")
    @DisplayName("Проверяем поиск")
    void searchCheck() {
        SelenideElement searchInput = $("[name=q]");
        searchInput.click();
        searchInput.sendKeys("samsung");
        $x("//span[contains(text(),'- Смартфоны')]").shouldBe(visible);
    }

    @Test
    @Tag("dnsshop")
    @DisplayName("Проверяем карту магазинов и переход к первому магазину в списке")
    void mapCheck() {
        $x("//a[contains(text(),'Магазины')]").click();
        $(".city-shops-page").shouldBe(visible);
        $(".shop-list-item__shop-link").shouldBe(visible).click();
        $(".shop-page-content").shouldBe(visible);
    }

    @Test
    @Tag("dnsshop")
    @DisplayName("Добавляем товар в корзину и проверяем что он там есть")
    void cartCheck() {
        Selenide.clipboard().setText("samsung s22");
        SelenideElement searchInput = $("[name=q]");
        searchInput.click();
        searchInput.sendKeys(Keys.CONTROL + "v");
        $x("//span[contains(text(),'- Смартфоны')]").click();
        $x("//button[contains(text(),'Купить')]").click();
        $x("//button[contains(text(),'В корзине')]").click();
        $(".cart-title").shouldHave(text("Корзина"));
        $(".cart-items__product-name").shouldHave(text("Samsung Galaxy S22"));
    }
}
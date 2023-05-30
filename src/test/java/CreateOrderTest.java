
import Courier.ScooterRentSpec;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import Order.Order;
import Order.OrderAPI;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;



@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final List<String> color;
    OrderAPI orderApi = new OrderAPI();

    public CreateOrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "{index}: Заказ с цветом: {0}")
    public static Object[][] checkCreateOrderWithChoiceColor() {
        return new Object[][]{
                {List.of("GREY")},
                {List.of("BLACK")},
                {List.of("GREY", "BLACK")},
                {List.of()},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = ScooterRentSpec.BASE_URI;
    }

    @Test
    @DisplayName("Создание заказа с выбором цвета")
    @Description("Проверка, что можно создать заказ на: серый цвет, черный цвет, выбор двух цветов, без выбора цвета")
    public void paramCreateOrderTest() {
        Order order = new Order("Александра", "Зеленина", "Советская 35", "1", "8921999999", 1, "2023-05-05", "нет комментариев", color);
        ValidatableResponse response = orderApi.createOrder(order);
        response.assertThat().statusCode(SC_CREATED).and().body("track", notNullValue());
    }
}


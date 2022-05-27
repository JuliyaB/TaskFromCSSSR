package testApi.superheroes;

import io.qameta.allure.Description;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Superhero;
import pojo.SuperheroGenerator;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.Connection.BASE_URL;
import static utils.Connection.SUPERHEROES_BASE_PATH;

public class SuperheroTestsApi {

    private static RequestSpecification spec;

    @BeforeAll
    public static void initSpec() {
        spec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath(SUPERHEROES_BASE_PATH)
                .setContentType(ContentType.JSON)
                .build()
                .header("Content-Type", "application/json");
    }

    @Test
    @DisplayName("Get all superheroes")
    public void getSuperheroes() {
        given().spec(spec)
                .get()
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Post a superhero 200")
    @Description("Проверяет отправку запроса на создлание супергероя и что в системе создался супергерой")
    public void postUser() {
        Superhero superhero = given().spec(spec).body(SuperheroGenerator.getSimpleSuperhero())
                .post()
                .then()
                .statusCode(200)
                .extract().as(Superhero.class);
        assertThat(superhero).extracting(Superhero::getFullName).isEqualTo(superhero.getFullName());
        assertThat(superhero).extracting(Superhero::getBirthDate).isEqualTo(superhero.getBirthDate());
        assertThat(superhero).extracting(Superhero::getCity).isEqualTo(superhero.getCity());
        assertThat(superhero).extracting(Superhero::getMainSkill).isEqualTo(superhero.getMainSkill());
        assertThat(superhero).extracting(Superhero::getGender).isEqualTo(superhero.getGender());
    }

    @Test
    @DisplayName("Post a superhero 403")
    @Description("Проверяет отправку запроса на создлание супергероя с City = null")
    public void postUserWithOutCity() {
        given().spec(spec).body(SuperheroGenerator.getSimpleSuperhero(null))
                .post()
                .then()
                .statusCode(403);
    }

    @Test
    @DisplayName("Get superhero by id")
    public void getSuperheroesById() {
        Superhero getSuperhero = given().spec(spec).get()
                .jsonPath().getList("$", Superhero.class)
                .stream().findFirst().get();
        Superhero getByIdSuperhero = given().spec(spec).get("/{id}", getSuperhero.getId())
                .then().statusCode(200).extract().as(Superhero.class);
        Assertions.assertEquals(getByIdSuperhero.getId(), getSuperhero.getId());
        Assertions.assertEquals(getByIdSuperhero.getFullName(), getSuperhero.getFullName());
        Assertions.assertEquals(getByIdSuperhero.getBirthDate(), getSuperhero.getBirthDate());
        Assertions.assertEquals(getByIdSuperhero.getCity(), getSuperhero.getCity());
        Assertions.assertEquals(getByIdSuperhero.getMainSkill(), getSuperhero.getMainSkill());
        Assertions.assertEquals(getByIdSuperhero.getGender(), getSuperhero.getGender());
        Assertions.assertEquals(getByIdSuperhero.getPhone(), getSuperhero.getPhone());
    }

    @Test
    @DisplayName("Get superhero by id 400")
    public void getSuperheroesByWrongId() {
        given().spec(spec).get("/{id}", -1).then().statusCode(400);
    }

    @Test
    @DisplayName("Put of superhero")
    @Description("Проверяет отправку запроса на изменение супергероя и что в системе изменился супергерой")
    public void putSuperhero() {
        Superhero getSuperhero = given().spec(spec).get().jsonPath()
                .getList("$", Superhero.class).stream().findFirst().get();
        given().spec(spec)
                .body(SuperheroGenerator.getSimpleSuperhero("Моряк Южный", "Сочи",
                        getSuperhero.getMainSkill(), getSuperhero.getGender()))
                .put("/{id}", getSuperhero.getId())
                .then()
                .header("content-length", "0")
                .header("x-frame-options", "SAMEORIGIN")
                .statusCode(200);
        Superhero getByIdSuperhero = given().spec(spec).get("/{id}", getSuperhero.getId())
                .then().statusCode(200).extract().as(Superhero.class);
        Assertions.assertNotEquals(getByIdSuperhero.getFullName(), getSuperhero.getFullName());
        Assertions.assertNotEquals(getByIdSuperhero.getBirthDate(), getSuperhero.getBirthDate());
        Assertions.assertNotEquals(getByIdSuperhero.getCity(), getSuperhero.getCity());
        Assertions.assertEquals(getByIdSuperhero.getMainSkill(), getSuperhero.getMainSkill());
        Assertions.assertEquals(getByIdSuperhero.getGender(), getSuperhero.getGender());
        Assertions.assertEquals(getByIdSuperhero.getPhone(), getSuperhero.getPhone());
    }

    @Test
    @DisplayName("Put of superhero 400")
    public void putSuperheroWrongId() {
        given().spec(spec).body(SuperheroGenerator.getSimpleSuperhero())
                .put("/{id}", 1)
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Put of superhero 403")
    public void putSuperheroWithOutCity() {
        int id = given().spec(spec).get().jsonPath().getList("$", Superhero.class).stream().findFirst().get().getId();
        given().spec(spec).body(SuperheroGenerator.getSimpleSuperhero(null))
                .put("/{id}", id)
                .then()
                .statusCode(403);
    }

    @Test
    @DisplayName("Put of superhero 405")
    public void putSuperheroWrongUrl() {
        given().spec(spec).body(SuperheroGenerator.getSimpleSuperhero())
                .put("/?id={id}", 81)
                .then()
                .statusCode(405);
    }

    @Test
    @DisplayName("Delete superhero by id")
    public void deleteSuperheroesById() {
        int id = given().spec(spec).get().jsonPath().getList("$", Superhero.class).stream().findFirst().get().getId();
        given().spec(spec)
                .delete("/{id}", id)
                .then()
                .statusCode(200);
        given().spec(spec).get("/{id}", id).then().statusCode(400);
    }

    @Test
    @DisplayName("Delete superhero by id 405")
    public void deleteSuperheroesByIdWrongUrl() {
        given().spec(spec)
                .delete("/?id={id}", 81)
                .then()
                .statusCode(405);
    }

    @Test
    @DisplayName("End to end")
    public void liveSuperhero() {
        given().spec(spec).get().then().statusCode(200);
        Superhero superhero = given().spec(spec).body(SuperheroGenerator.getSimpleSuperhero())
                .post().then().statusCode(200).extract().as(Superhero.class);
        given().spec(spec).get("/{id}", superhero.getId()).then().statusCode(200);
        given().spec(spec).body(SuperheroGenerator.getSimpleSuperhero("London"))
                .put("/{id}", superhero.getId()).then().statusCode(200);
        given().spec(spec).delete("/{id}", superhero.getId()).then().statusCode(200);
        given().spec(spec).get("/{id}", superhero.getId()).then().statusCode(400);
    }
}

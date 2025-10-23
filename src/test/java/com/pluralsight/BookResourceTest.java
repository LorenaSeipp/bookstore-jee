package com.pluralsight;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class BookResourceTest {

    @Test
    public void shouldGetAllBooks() {
        given()
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON).
            when()
            .get("/api/books").
            then()
            .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    public void shouldGetABook() {
        given()
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
            .pathParam("id", 1005).
            when()
            .get("/api/books/{id}").
            then()
            .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    public void shouldCountAllBooks() {
        given()
            .header(HttpHeaders.ACCEPT, MediaType.TEXT_PLAIN).
            when()
            .get("/api/books/count").
            then()
            .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    public void shouldCreateABook() {
        Book book = new Book();
        given()
            .body(book)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).
            when()
            .post("/api/books").
            then()
            .statusCode(Response.Status.CREATED.getStatusCode())
            .body("id", is(6666));
    }

    @Test
    public void shouldDeleteABook() {
        given()
            .pathParam("id", 1008).
            when()
            .delete("/api/books/{id}").
            then()
            .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }
}

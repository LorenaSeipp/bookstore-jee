package com.pluralsight;

import jakarta.inject.Inject;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/api/books")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class BookResource {

    @Inject
    BookService bookService;

    @Inject
    UriInfo uriInfo;

    @GET
    @Operation(
            summary = "Liest alle Bücher aus",
            description = "Gibt alle Bücher aus der Datenbank zurück. Wenn keine Bücher vorhanden sind, wird HTTP 204 zurückgegeben."
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Liste aller Bücher",
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            schema = @Schema(implementation = Book.class)
                    )
            ),
            @APIResponse(responseCode = "204", description = "Keine Bücher vorhanden")
    })
    public Response getBooks() {
        List<Book> books = bookService.findAll();

        if (books.isEmpty())
            return Response.noContent().build();

        return Response.ok(books).build();
    }

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Liest ein einzelnes Buch aus",
            description = "Gibt ein Buch anhand der ID zurück. Wenn das Buch nicht existiert, wird HTTP 404 zurückgegeben."
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Buch wurde gefunden",
                    content = @Content(schema = @Schema(implementation = Book.class))
            ),
            @APIResponse(responseCode = "404", description = "Kein Buch mit dieser ID gefunden")
    })
    public Response getBook(@PathParam("id") @Min(1) Long id) {
        Book book = bookService.find(id);
        if (book == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(book).build();
    }

    @GET
    @Path("/count")
    @Produces(TEXT_PLAIN)
    @Operation(
            summary = "Zählt alle Bücher",
            description = "Gibt die Gesamtanzahl der vorhandenen Bücher zurück."
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Anzahl der Bücher (Plain Text)"
            ),
            @APIResponse(responseCode = "204", description = "Keine Bücher vorhanden")
    })
    public Response countBooks() {
        Long nbOfBooks = bookService.countAll();
        if (nbOfBooks == 0)
            return Response.noContent().build();

        return Response.ok(nbOfBooks).build();
    }

    @POST
    @Operation(
            summary = "Erstellt ein neues Buch",
            description = "Speichert ein neues Buch in der Datenbank und gibt das erstellte Objekt inklusive Location-Header zurück."
    )
    @RequestBody(
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Book.class),
                    examples = @ExampleObject(
                            name = "Beispielbuch",
                            value = """
                                    {
                                      "title": "Die Geheimnisse von Arcadia",
                                      "description": "Ein Fantasyroman voller Abenteuer.",
                                      "price": 19.99,
                                      "publicationDate": "2023-09-15",
                                      "nbOfPages": 428,
                                      "imageURL": "https://example.com/cover.jpg"
                                    }"""
                    )
            )
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "201",
                    description = "Buch erfolgreich erstellt",
                    content = @Content(schema = @Schema(implementation = Book.class))
            ),
            @APIResponse(responseCode = "400", description = "Ungültige Daten im Request Body")
    })
    public Response createBook(Book book) throws URISyntaxException {
        book = bookService.create(book);
        URI createdURI = uriInfo.getAbsolutePathBuilder().path(book.getId().toString()).build();
        return Response.created(createdURI).entity(book).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(
            summary = "Löscht ein Buch",
            description = "Löscht ein Buch anhand seiner ID. Existiert kein Buch, folgt dennoch ein 204."
    )
    @APIResponse(responseCode = "204", description = "Buch gelöscht oder nicht vorhanden")
    public Response deleteBook(@PathParam("id") @Min(1) Long id) {
        bookService.delete(id);
        return Response.noContent().build();
    }
}

package com.pluralsight;

import jakarta.inject.Inject;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

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
    public Response getBooks() {
        List<Book> books = bookService.findAll(); // Get all the books from the database

        if (books.isEmpty())
            return Response.noContent().build();

        return Response.ok(books).build();
    }

    @GET
    @Path("/{id}")
    public Response getBook(@PathParam("id") @Min(1) Long id) {
        Book book = bookService.find(id);
        if (book == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(book).build();
    }

    @GET
    @Path("/count")
    @Produces(TEXT_PLAIN)
    public Response countBooks() {
        Long nbOfBooks = bookService.countAll();
        if (nbOfBooks == 0)
            return Response.noContent().build();

        return Response.ok(nbOfBooks).build();
    }

    @POST
    public Response createBook(Book book) throws URISyntaxException {
        book = bookService.create(book);
        URI createdURI = uriInfo.getAbsolutePathBuilder().path(book.getId().toString()).build();
        return Response.created(createdURI).entity(book).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") @Min(1) Long id) {
        bookService.delete(id);
        return Response.noContent().build();
    }
}

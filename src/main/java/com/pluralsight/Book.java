package com.pluralsight;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.json.bind.annotation.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "T_BOOK")
@Schema(name = "Book", description = "POJO, das ein Buch in der Bibliothek repräsentiert")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Eindeutige ID des Buches", example = "1", readOnly = true)
    private Long id;

    @Column(length = 50)
    @Schema(
            description = "ISBN des Buches",
            example = "978-3-86680-192-9"
    )
    private String isbn;

    @Column(length = 100)
    @NotNull
    @Size(min = 1, max = 100)
    @JsonbProperty()
    @Schema(
            description = "Titel des Buches",
            example = "Die Geheimnisse von Arcadia",
            required = true
    )
    private String title;

    @Column(length = 10000)
    @Size(min = 10, max = 10000)
    @Schema(
            description = "Beschreibung oder Zusammenfassung des Buchinhalts",
            example = "Ein spannender Fantasyroman über eine junge Heldin, die das Schicksal ihres Königreichs in den Händen hält."
    )
    private String description;

    @NotNull
    @Min(1)
    @JsonbNumberFormat(locale = "de_DE", value = "#0.00€")
    @Schema(
            description = "Preis des Buches in Euro",
            example = "19.99",
            required = true,
            minimum = "1"
    )
    private BigDecimal price;

    @Column(name = "publication_date")
    @Past
    @JsonbProperty("publication-date")
    @Schema(
            description = "Veröffentlichungsdatum des Buches (muss in der Vergangenheit liegen)",
            example = "2023-09-15",
            format = "date"
    )
    private LocalDate publicationDate;

    @Column(name = "nb_of_pages")
    @Min(40)
    @JsonbProperty("nb-of-pages")
    @Schema(
            description = "Anzahl der Seiten (mindestens 40 Seiten)",
            example = "428",
            minimum = "40"
    )
    private Integer nbOfPages;

    @Column(name = "image_url")
    @JsonbTransient
    @Schema(hidden = true)
    private String imageURL;

    // ---- GETTER & SETTER ---- //
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public LocalDate getPublicationDate() { return publicationDate; }
    public void setPublicationDate(LocalDate publicationDate) { this.publicationDate = publicationDate; }

    public Integer getNbOfPages() { return nbOfPages; }
    public void setNbOfPages(Integer nbOfPages) { this.nbOfPages = nbOfPages; }

    public String getImageURL() { return imageURL; }
    public void setImageURL(String imageURL) { this.imageURL = imageURL; }
}

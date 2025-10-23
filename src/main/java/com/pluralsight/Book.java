package com.pluralsight;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "T_BOOK")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String isbn;

    @Column(length = 100)
    @NotNull
    @Size(min = 1, max = 100)
    private String title;

    @Column(length = 10000)
    @Size(min = 10, max = 10000)
    private String description;

    @NotNull
    @Min(1)
    private BigDecimal price;

    @Column(name = "publication_date")
    @Past
    private LocalDate publicationDate;

    @Column(name = "nb_of_pages")
    @Min(40)
    private Integer nbOfPages;

    @Column(name = "image_url")
    private String imageURL;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getNbOfPages() {
        return nbOfPages;
    }

    public void setNbOfPages(Integer nbOfPages) {
        this.nbOfPages = nbOfPages;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}

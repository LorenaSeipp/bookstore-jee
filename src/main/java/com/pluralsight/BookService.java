package com.pluralsight;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

import static jakarta.transaction.Transactional.TxType.REQUIRED;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;

@Named
@ApplicationScoped
public class BookService {

    @Inject
    EntityManager em;

    @Inject
    IsbnGenerator isbnGenerator;

    @Transactional(SUPPORTS)
    public Book find(Long id) {
        return em.find(Book.class, id);
    }

    @Transactional(SUPPORTS)
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b ORDER BY b.title DESC", Book.class);
        return query.getResultList();
    }

    public Long countAll() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(b.id) FROM Book b", Long.class);
        return query.getSingleResult();
    }

    @Transactional(REQUIRED)
    public Book create(Book book) {
        book.setIsbn(isbnGenerator.generateIsbn());
        em.persist(book);
        return book;
    }

    @Transactional(REQUIRED)
    public void delete(Long id) {
        em.remove(em.getReference(Book.class, id));
    }
}

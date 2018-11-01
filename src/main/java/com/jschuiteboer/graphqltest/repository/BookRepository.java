package com.jschuiteboer.graphqltest.repository;

import com.jschuiteboer.graphqltest.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends CrudRepository<Book, UUID> {
    Iterable<Book> findAllByTitleContaining(String title);
}

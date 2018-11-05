package com.jschuiteboer.graphqltest.author;

import com.jschuiteboer.graphqltest.author.Author;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorRepository extends CrudRepository<Author, UUID>, JpaSpecificationExecutor<Author> {
    Iterable<Author> findByNameLike(String authorName);
}

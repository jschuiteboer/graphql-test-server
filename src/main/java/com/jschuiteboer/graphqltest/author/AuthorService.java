package com.jschuiteboer.graphqltest.author;

import com.jschuiteboer.graphqltest.author.Author;
import com.jschuiteboer.graphqltest.author.AuthorFilter;
import com.jschuiteboer.graphqltest.author.AuthorRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import org.springframework.stereotype.Service;

import java.util.UUID;

@GraphQLApi
@Service
public class AuthorService {
    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    @GraphQLQuery(name = "authors")
    public Iterable<Author> getAllAuthors(@GraphQLArgument(name = "filter") AuthorFilter filter) {
        return repository.findAll(filter);
    }

    @GraphQLQuery(name = "author")
    public Author getAuthorById(@GraphQLArgument(name="id") UUID id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("invalid author id"));
    }

    public Iterable<Author> authorsByName(@GraphQLArgument(name="name") String name) {
        return this.repository.findByNameLike(name);
    }

    @GraphQLMutation
    public Author createAuthor(@GraphQLArgument(name="name") String name) {
        Author author = new Author();
        author.setName(name);

        return this.repository.save(author);
    }
}

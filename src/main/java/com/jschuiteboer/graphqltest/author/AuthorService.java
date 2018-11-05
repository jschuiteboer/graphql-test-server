package com.jschuiteboer.graphqltest.author;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@GraphQLApi
@Service
public class AuthorService {
    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    @GraphQLQuery(name = "authors")
    public List<Author> getAuthors(@GraphQLArgument(name = "filter") AuthorFilter filter) {
        return repository.findAll(filter);
    }

    @GraphQLQuery(name = "author")
    public Author getAuthorById(@GraphQLArgument(name="id") UUID id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("invalid author id"));
    }

    @GraphQLMutation
    public Author createAuthor(@GraphQLArgument(name="name") String name) {
        Author author = new Author();
        author.setName(name);

        return this.repository.save(author);
    }
}

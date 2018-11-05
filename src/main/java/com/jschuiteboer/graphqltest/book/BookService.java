package com.jschuiteboer.graphqltest.book;

import com.jschuiteboer.graphqltest.author.Author;
import com.jschuiteboer.graphqltest.author.AuthorService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@GraphQLApi
@Service
public class BookService {
    private final BookRepository bookRepository;

    private final AuthorService authorService;

    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    @GraphQLQuery(name = "books")
    public List<Book> getAllBooks(@GraphQLArgument(name="filter") BookFilter filter) {
        Sort sort = filter != null && filter.getSort() != null ? filter.getSort() : Sort.unsorted();

        return bookRepository.findAll(filter, sort);
    }

    @GraphQLQuery(name = "book")
    public Book getBookById(@GraphQLArgument(name="id") UUID id) {
        return this.bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("invalid book id"));
    }

    @GraphQLMutation(name = "createBook")
    public Book saveBook(@GraphQLArgument(name="book") Book book) {
        //TODO: remove id from BookInput type
        book.setId(null);

        Author author = this.authorService.getAuthorById(book.getAuthor().getId());
        book.setAuthor(author);

        //TODO: validation
        return bookRepository.save(book);
    }
}

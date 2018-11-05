package com.jschuiteboer.graphqltest.service;

import com.jschuiteboer.graphqltest.model.Author;
import com.jschuiteboer.graphqltest.model.Book;
import com.jschuiteboer.graphqltest.model.BookFilter;
import com.jschuiteboer.graphqltest.repository.BookRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@GraphQLApi
@Service
public class BookService {
    private final BookRepository bookRepository;

    private final AuthorService authorService;

    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    @GraphQLQuery(name = "booksByTitle")
    public Iterable<Book> getAllBooksByTitle(@GraphQLArgument(name="title") String title) {
        return bookRepository.findAllByTitleContaining(title);
    }

    @GraphQLQuery(name = "books")
    public List<Book> getAllBooks(
            @GraphQLArgument(name="title") String title,
            @GraphQLArgument(name="authorId") UUID authorId,
            @GraphQLArgument(name="authorName") String authorName,
            @GraphQLArgument(name="series") String series
    ) {
        BookFilter bookFilter = new BookFilter(title, authorId, authorName, series, null, null);

        return getAllBooks(bookFilter);
    }

    @GraphQLQuery(name = "filterBooks")
    public List<Book> getAllBooks(@GraphQLArgument(name="filter") BookFilter filter) {
        Sort sort = filter.getSort();
        if(sort != null) {
            return bookRepository.findAll(filter, sort);
        } else {
            return bookRepository.findAll(filter);
        }
    }

    @GraphQLMutation(name = "createBook")
    public Book createBook(
            @GraphQLArgument(name="title") String title,
            @GraphQLArgument(name="authorId") UUID authorId
    ) {
        Author author = authorService.getAuthorById(authorId);

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);

        return this.saveBook(book);
    }

    public Book saveBook(Book book) {
        //TODO: validation
        return bookRepository.save(book);
    }
}

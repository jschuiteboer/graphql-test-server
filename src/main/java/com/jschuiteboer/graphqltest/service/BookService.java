package com.jschuiteboer.graphqltest.service;

import com.jschuiteboer.graphqltest.model.Author;
import com.jschuiteboer.graphqltest.model.Book;
import com.jschuiteboer.graphqltest.model.BookFilter;
import com.jschuiteboer.graphqltest.repository.BookRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @GraphQLQuery(name = "books")
    public List<Book> getAllBooks(
        @GraphQLArgument(name="title") String title,
        @GraphQLArgument(name="authorId") UUID authorId,
        @GraphQLArgument(name="authorName") String authorName
    ) {
        BookFilter bookFilter = new BookFilter(title, authorId, authorName);
        return bookRepository.findAll(bookFilter);
    }

    @GraphQLQuery(name = "filterBooks")
    public List<Book> filterBooks(@GraphQLArgument(name="filter") BookFilter filter) {
        return bookRepository.findAll(filter);
    }

    @GraphQLQuery(name = "booksByTitle")
    public Iterable<Book> getAllBooksByTitle(@GraphQLArgument(name="title") String title) {
        return bookRepository.findAllByTitleContaining(title);
    }

    @GraphQLQuery(name = "booksByAuthorId")
    public Iterable<Book> getAllBooksByAuthorId(@GraphQLArgument(name="authorId") UUID authorId) {
        Author author = authorService.authorById(authorId);

        return author.getBooks();
    }

    @GraphQLQuery(name = "booksByAuthorName")
    public Iterable<Book> getAllBooksByAuthorName(@GraphQLArgument(name="authorName") String authorName) {
        Iterable<Author> authors = authorService.authorsByName(authorName);

        Set<Book> result = new HashSet<>();

        for(Author author : authors) {
            result.addAll(author.getBooks());
        }

        return result;
    }

    @GraphQLMutation(name = "createBook")
    public Book createBook(
            @GraphQLArgument(name="title") String title,
            @GraphQLArgument(name="authorId") UUID authorId
    ) {
        Author author = authorService.authorById(authorId);

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);

        return bookRepository.save(book);
    }
}

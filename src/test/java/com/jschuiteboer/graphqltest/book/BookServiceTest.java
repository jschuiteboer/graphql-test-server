package com.jschuiteboer.graphqltest.book;

import com.jschuiteboer.graphqltest.author.AuthorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.util.*;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;

public class BookServiceTest {
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepositoryMock;

    @Mock
    private AuthorService authorServiceMock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getBooks_withHappyFlow() {
        // given
        List<Book> bookList = Arrays.asList(new Book(), new Book(), new Book());
        BookFilter bookFilter = new BookFilter();

        given(bookRepositoryMock.findAll(eq(bookFilter), any(Sort.class))).willReturn(bookList);

        // when
        List<Book> result = bookService.getBooks(bookFilter);

        // then
        then(result).isEqualTo(bookList);
    }

    @Test
    public void getBooks_withNullFilterArgument() {
        // given
        List<Book> bookList = Arrays.asList(new Book(), new Book(), new Book());

        given(bookRepositoryMock.findAll(isNull(), any(Sort.class))).willReturn(bookList);

        // when
        List<Book> result = bookService.getBooks(null);

        // then
        then(result).isEqualTo(bookList);
    }

    @Test
    public void getBookById_withNullIdArgument() {
        // when
        try {
            Book ignoredResult = bookService.getBookById(null);
        } catch (IllegalArgumentException ex) {
            // then
            then(ex.getMessage()).isEqualTo("invalid book id");
        }
    }

    @Test
    public void getBookById_withValidId() {
        // given
        UUID id = UUID.randomUUID();
        Book book = new Book();
        given(bookRepositoryMock.findById(id)).willReturn(Optional.of(book));

        // when
        Book result = bookService.getBookById(id);

        // then
        then(result).isEqualTo(book);
    }

    @Test
    public void getBookById_withEmptyResult() {
        // given
        UUID id = UUID.randomUUID();
        given(bookRepositoryMock.findById(id)).willReturn(Optional.empty());

        // when
        try {
            Book ignoredResult = bookService.getBookById(id);
        } catch (IllegalArgumentException ex) {
            // then
            then(ex.getMessage()).isEqualTo("invalid book id");
        }
    }
}
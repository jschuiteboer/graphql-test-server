package com.jschuiteboer.graphqltest.repository;

import com.jschuiteboer.graphqltest.model.Book;
import com.jschuiteboer.graphqltest.service.BookService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookServiceIT {
    @Autowired
    private BookService testSubject;

    @Autowired
    private BookRepository bookRepository;

    private Iterable<Book> booksResult;

    @Before
    public void setup() {

    }

    @After
    public void tearDown() {
        bookRepository.deleteAll();
    }

    @Test
    public void test() {
        givenTheRepositoryHasABook();

        whenTheAllBooksMethodIsCalled();

        thenTheResultShouldBeAListOfBooks(3);
    }

    private void givenTheRepositoryHasABook() {
    }

    private void whenTheAllBooksMethodIsCalled() {
        this.booksResult = testSubject.getAllBooks();
    }

    private void thenTheResultShouldBeAListOfBooks(int listSize) {
        //TODO:
        //Assert.assertEquals(listSize, booksResult.);
    }
}

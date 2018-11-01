package com.jschuiteboer.graphqltest;

import com.jschuiteboer.graphqltest.model.Author;
import com.jschuiteboer.graphqltest.service.AuthorService;
import com.jschuiteboer.graphqltest.service.BookService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitTestData {
    @Bean
    public ApplicationRunner init(AuthorService authorService, BookService bookService) {
        return args -> {
            createBookSeries(bookService, authorService.createAuthor("J.K. Rowling"),
                    "The Philosopher's Stone",
                    "The Chamber of Secrets",
                    "The Prisoner of Azkaban",
                    "The Goblet of Fire",
                    "The Order of the Phoenix",
                    "The Half-Blood Prince",
                    "The Deathly Hallows"
            );

            createBookSeries(bookService, authorService.createAuthor("Suzanne Collins"),
                    "The Hunger Games",
                    "Catching Fire",
                    "Mockingjay"
            );

            createBookSeries(bookService, authorService.createAuthor("C. S. Lewis"),
                    "The Lion, the Witch and the Wardrobe" ,
                    "Prince Caspian" ,
                    "The Voyage of the Dawn Treader",
                    "The Silver Chair",
                    "The Horse and His Boy",
                    "The Magician's Nephew",
                    "The Last Battle"
            );
        };
    }

    private void createBookSeries(BookService bookService, Author author, String...bookTitles) {
        for(String title : bookTitles) {
            bookService.createBook(title, author.getId());
        }
    }
}

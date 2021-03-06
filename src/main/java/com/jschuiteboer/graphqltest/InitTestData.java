package com.jschuiteboer.graphqltest;

import com.jschuiteboer.graphqltest.author.Author;
import com.jschuiteboer.graphqltest.author.AuthorFilter;
import com.jschuiteboer.graphqltest.author.AuthorService;
import com.jschuiteboer.graphqltest.book.BookInput;
import com.jschuiteboer.graphqltest.book.BookService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitTestData {
    @Bean
    public ApplicationRunner init(AuthorService authorService, BookService bookService) {
        return args -> {
            Author jkRowling = authorService.createAuthor("J.K. Rowling");
            Author suzanneCollins = authorService.createAuthor("Suzanne Collins");
            Author csLewis = authorService.createAuthor("C. S. Lewis");

            createBookSeries(bookService,"Harry Potter", jkRowling,
                    "The Philosopher's Stone",
                    "The Chamber of Secrets",
                    "The Prisoner of Azkaban",
                    "The Goblet of Fire",
                    "The Order of the Phoenix",
                    "The Half-Blood Prince",
                    "The Deathly Hallows"
            );

            createBookSeries(bookService, "The Hunger Games", suzanneCollins,
                    "The Hunger Games",
                    "Catching Fire",
                    "Mockingjay"
            );

            createBookSeries(bookService, "The Chronicles of Narnia", csLewis,
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

    private void createBookSeries(BookService bookService, String series, Author author, String...bookTitles) {
        for(String title : bookTitles) {
            BookInput book = new BookInput();
            book.setTitle(title);
            book.setAuthor(new AuthorFilter(author.getId(), author.getName()));
            book.setSeries(series);
            bookService.saveBook(book);
        }
    }
}

package com.jschuiteboer.graphqltest.author;

import com.jschuiteboer.graphqltest.book.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @OneToMany(mappedBy="author")
    private Collection<Book> books;
}

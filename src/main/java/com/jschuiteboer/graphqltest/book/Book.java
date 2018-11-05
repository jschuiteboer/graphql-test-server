package com.jschuiteboer.graphqltest.book;

import com.jschuiteboer.graphqltest.author.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    private String series;

    private LocalDate publicationDate;

    @ManyToOne
    private Author author;
}

package com.jschuiteboer.graphqltest.book;

import com.jschuiteboer.graphqltest.author.AuthorFilter;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@GraphQLType(name = "Book")
public class BookInput {
    private String title;

    private String series;

    private LocalDate publicationDate;

    private AuthorFilter author;
}

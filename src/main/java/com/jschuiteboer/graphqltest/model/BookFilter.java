package com.jschuiteboer.graphqltest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookFilter implements Specification<Book> {
    private String title;

    private UUID authorId;

    private String authorName;

    private String series;

    private String sortProperty;

    private Sort.Direction sortDirection;

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Predicate filter = builder.conjunction();

        if(title != null) {
            filter = builder.and(filter, builder.like(
                    builder.lower(root.get("title")),
                    "%" + title.toLowerCase() + "%"
            ));
        }

        if(authorId != null) {
            filter = builder.and(filter, builder.equal(
                    root.join("author").get("id"),
                    authorId
            ));
        }

        if(authorName != null) {
            filter = builder.and(filter, builder.like(
                    builder.lower(root.join("author").get("name")),
                    "%" + authorName.toLowerCase() + "%"
            ));
        }

        if(series != null) {
            filter = builder.and(filter, builder.like(
                    builder.lower(root.get("series")),
                    "%" + series.toLowerCase() + "%"
            ));
        }

        return filter;
    }

    public Sort getSort() {
        if(sortDirection == null || sortProperty == null) return null;
        return new Sort(sortDirection, sortProperty);
    }
}

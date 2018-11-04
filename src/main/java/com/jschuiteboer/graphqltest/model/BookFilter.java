package com.jschuiteboer.graphqltest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookFilter implements Specification<Book> {
    private String title;

    private UUID authorId;

    private String authorName;

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> filters = new ArrayList<>();

        if(title != null) {
            filters.add(builder.like(root.get("title"), "%" + title + "%"));
        }

        if(authorId != null) {
            filters.add(builder.equal(root.join("author").get("id"), authorId));
        }

        if(authorName != null) {
            filters.add(builder.like(root.join("author").get("name"), "%" + authorName + "%"));
        }

        return builder.and(filters.toArray(new Predicate[0]));
    }
}

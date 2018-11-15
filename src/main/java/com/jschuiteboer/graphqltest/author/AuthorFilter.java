package com.jschuiteboer.graphqltest.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorFilter implements Specification<Author> {

    private UUID id;

    private String name;

    @Override
    public Predicate toPredicate(Root<Author> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Predicate filter = builder.conjunction();

        if(id != null) {
            filter = builder.and(filter, builder.equal(
                    root.get("id"),
                    id
            ));
        }

        if(name != null) {
            filter = builder.and(filter, builder.like(
                    builder.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%"
            ));
        }

        return filter;
    }
}

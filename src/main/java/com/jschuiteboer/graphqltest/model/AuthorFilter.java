package com.jschuiteboer.graphqltest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorFilter implements Specification<Author> {

    private String name;

    @Override
    public Predicate toPredicate(Root<Author> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Predicate filter = builder.conjunction();

        if(name != null) {
            filter = builder.and(filter, builder.like(
                    builder.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%"
            ));
        }

        return filter;
    }
}

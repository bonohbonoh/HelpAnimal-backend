package com.jeonggolee.helpanimal.domain.crew.query;

import com.jeonggolee.helpanimal.domain.crew.domain.Crews;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class CrewSpecification {

    //크루 ID로 조회
    public Specification<Crews> searchWithId(Long id){
        return (root, query, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            predicateList.add(this.withNotDeleted(root, builder));
            predicateList.add(this.withId(id, root, builder));

            return builder.and(predicateList.toArray(new  Predicate[0]));
        };
    }

    //크루 이름으로 조회
    public Specification<Crews> searchWithName(String name){
        return (root, query, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            predicateList.add(this.withNotDeleted(root, builder));
            predicateList.add(this.withName(name, root, builder));

            return builder.and(predicateList.toArray(new  Predicate[0]));
        };
    }

    //삭제되지 않은 조건 쿼리
    private Predicate withNotDeleted(Root<Crews> root, CriteriaBuilder builder){
        return builder.isNull(root.get("deletedAt"));
    }

    //크루아이디 조건 쿼리
    private Predicate withId(Long id, Root<Crews> root, CriteriaBuilder builder){
        return builder.equal(root.get("id"), id);
    }

    //크루이름 조건 쿼리
    private Predicate withName(String name, Root<Crews> root, CriteriaBuilder builder){
        return builder.like(root.get("name"), "%" + name + "%");
    }
}

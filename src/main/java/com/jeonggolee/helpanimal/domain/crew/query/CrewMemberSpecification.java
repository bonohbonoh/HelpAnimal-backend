package com.jeonggolee.helpanimal.domain.crew.query;

import com.jeonggolee.helpanimal.domain.crew.domain.CrewMembers;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class CrewMemberSpecification {

    //크루맴버 ID로 조회
    public Specification<CrewMembers> searchWithId(Long id){
        return (root, query, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            predicateList.add(this.withNotDeleted(root, builder));
            predicateList.add(this.withId(id, root, builder));

            return builder.and(predicateList.toArray(new  Predicate[0]));
        };
    }

    //삭제되지 않은 조건 쿼리
    private Predicate withNotDeleted(Root<CrewMembers> root, CriteriaBuilder builder){
        return builder.isNull(root.get("deletedAt"));
    }

    //크루맴버아이디 조건 쿼리
    private Predicate withId(Long id, Root<CrewMembers> root, CriteriaBuilder builder){
        return builder.equal(root.get("id"), id);
    }
}

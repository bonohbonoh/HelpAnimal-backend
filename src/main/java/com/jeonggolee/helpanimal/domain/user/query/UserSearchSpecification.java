package com.jeonggolee.helpanimal.domain.user.query;

import com.jeonggolee.helpanimal.domain.user.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserSearchSpecification {
    //유저 ID로 조회
    public Specification<User> searchWithId(Long userId) {
        return (root, query, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            predicateList.add(this.withNotDeleted(root, builder));
            predicateList.add(this.withUserId(userId, root, builder));

            return builder.and(predicateList.toArray(new Predicate[0]));
        };
    }

    //유저 닉네임으로 조회
    public Specification<User> searchWithNickname(String nickname) {
        return (root, query, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            predicateList.add(this.withNotDeleted(root, builder));
            predicateList.add(this.withNickname(nickname, root, builder));

            return builder.and(predicateList.toArray(new Predicate[0]));
        };
    }

    //유저 이메일로 조회
    public Specification<User> searchWithEmail(String email) {
        return (root, query, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            predicateList.add(this.withNotDeleted(root, builder));
            predicateList.add(this.withEmail(email, root, builder));

            return builder.and(predicateList.toArray(new Predicate[0]));
        };
    }

    //유저 이메일로 조회
    public Specification<User> searchWithEmailEqual(String email) {
        return (root, query, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            predicateList.add(this.withNotDeleted(root, builder));
            predicateList.add(this.withEmailEqual(email, root, builder));

            return builder.and(predicateList.toArray(new Predicate[0]));
        };
    }

    //삭제되지 않은 조건 쿼리
    private Predicate withNotDeleted(Root<User> root, CriteriaBuilder builder) {
        return builder.isNull(root.get("deletedAt"));
    }

    //유저아이디 조건 쿼리
    private Predicate withUserId(Long userId, Root<User> root, CriteriaBuilder builder) {
        return builder.equal(root.get("user_id"), userId);
    }

    //유저 닉네임 조건 쿼리
    private Predicate withNickname(String nickname, Root<User> root, CriteriaBuilder builder) {
        return builder.like(root.get("nickname"), nickname + "%");
    }

    //유저 이메일 조건 쿼리
    private Predicate withEmail(String email, Root<User> root, CriteriaBuilder builder) {
        return builder.like(root.get("email"), email + "%");
    }

    //유저 이메일 일치 쿼리
    private Predicate withEmailEqual(String email, Root<User> root, CriteriaBuilder builder) {
        return builder.equal(root.get("email"), email);
    }
}

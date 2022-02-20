package com.jeonggolee.helpanimal.domain.recruitment.repository;

import com.jeonggolee.helpanimal.domain.recruitment.dto.response.RecruitmentApplicationDetailDto;
import com.jeonggolee.helpanimal.domain.recruitment.entity.RecruitmentApplication;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecruitmentApplicationRepository extends JpaRepository<RecruitmentApplication, Long> {
    @Query(
            "SELECT ra " +
                    "FROM RecruitmentApplication ra " +
                    "INNER JOIN ra.user u " +
                    "     ON ra.deletedAt IS NULL " +
                    "    AND u.deletedAt IS NULL " +
                    "WHERE ra.id = :raId " +
                    "AND   u.userId = :userId "
    )
    List<RecruitmentApplication> findByHistory(@Param("raId") Long raId, @Param("userId") Long userId);

    Optional<RecruitmentApplication> findByIdAndUserAndDeletedAtIsNull(Long id, User user);

    Optional<RecruitmentApplication> findByIdAndDeletedAtIsNull(Long id);

    @Query(value =
            "SELECT new com.jeonggolee.helpanimal.domain.recruitment.dto.response.RecruitmentApplicationDetailDto( " +
                    "ra.id, " +
                    "r.id, " +
                    "r.name, " +
                    "u.email, " +
                    "ra.comment, " +
                    "ra.status" +
                    ") " +
                    "FROM RecruitmentApplication ra " +
                    "INNER JOIN User u " +
                    "       ON ra.user.userId = u.userId " +
                    "       AND u.deletedAt IS NULL " +
                    "INNER JOIN Recruitment r " +
                    "       ON r.id = ra.recruitment.id " +
                    "       AND r.deletedAt IS NULL " +
                    "WHERE r.id = :recruitmentId " +
                    "AND   ra.deletedAt IS NULL ",
            countQuery =
                    "SELECT count(ra) " +
                            "FROM RecruitmentApplication ra " +
                            "INNER JOIN User u " +
                            "     ON ra.user.userId = u.userId " +
                            "     AND u.deletedAt IS NULL " +
                            "INNER JOIN Recruitment r " +
                            "     ON r.id = ra.recruitment.id " +
                            "     AND r.deletedAt IS NULL " +
                            "WHERE  r.id = :recruitmentId " +
                            "AND    ra.deletedAt IS NULL")
    Page<RecruitmentApplicationDetailDto> findRecruitmentApplicationByRecruitment(Pageable pageable, @Param("recruitmentId") Long id);

    @Query(value =
            "SELECT new com.jeonggolee.helpanimal.domain.recruitment.dto.response.RecruitmentApplicationDetailDto( " +
                    "ra.id, " +
                    "r.id, " +
                    "r.name, " +
                    "u.email, " +
                    "ra.comment, " +
                    "ra.status" +
                    ") " +
                    "FROM RecruitmentApplication ra " +
                    "INNER JOIN User u " +
                    "       ON ra.user.userId = u.userId " +
                    "       AND u.deletedAt IS NULL " +
                    "INNER JOIN Recruitment r " +
                    "       ON r.id = ra.recruitment.id " +
                    "       AND r.deletedAt IS NULL " +
                    "WHERE u.userId = :userId " +
                    "AND   ra.deletedAt IS NULL ",
            countQuery =
                    "SELECT count(ra) " +
                            "FROM RecruitmentApplication ra " +
                            "INNER JOIN User u " +
                            "     ON ra.user.userId = u.userId " +
                            "     AND u.deletedAt IS NULL " +
                            "INNER JOIN Recruitment r " +
                            "     ON r.id = ra.recruitment.id " +
                            "     AND r.deletedAt IS NULL " +
                            "WHERE u.userId = :userId " +
                            "AND   ra.deletedAt IS NULL ")
    Page<RecruitmentApplicationDetailDto> findRecruitmentApplicationByUserId(Pageable pageable, @Param("userId") Long id);
}

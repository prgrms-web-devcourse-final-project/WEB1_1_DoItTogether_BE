package com.doittogether.platform.infrastructure.persistence.housework;

import com.doittogether.platform.domain.entity.Housework;
import com.doittogether.platform.domain.enumeration.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HouseworkRepository extends JpaRepository<Housework, Long> {
    @Query(value = "select h from Housework h where "
            + "h.channel.channelId = :channelId "
            + "and h.startDate = :startDate "
            + "order by "
            + "case "
            + "when h.status = 'UN_COMPLETE' then 0 "
            + "when h.status = 'COMPLETE' then 1 "
            + "else 3 "
            + "end asc, "
            + "case "
            + "when h.assignee.assigneeId = :userId then 0 "
            + "else 1 "
            + "end asc,"
            + "h.startTime asc")
    Slice<Housework> findAllByChannelIdAndTargetDate(@Param("channelId") final Long channelId,
                                                     @Param("userId") final Long userId,
                                                     final
                                                     Pageable pageable,
                                                     @Param("startDate") final LocalDate startDate);

    @Query(value = "select h from Housework h where "
            + "h.channel.channelId = :channelId "
            + "and h.assignee.assigneeId = :assigneeId "
            + "and h.startDate = :startDate "
            + "order by "
            + "case "
            + "when h.status = 'UN_COMPLETE' then 0 "
            + "when h.status = 'COMPLETE' then 1 "
            + "else 3 "
            + "end asc, "
            + "h.startTime asc")
    Slice<Housework> findAllByChannelIdAndTargetDateAndAssigneeId(@Param("channelId") final Long channelId,
                                                                  @Param("assigneeId") final Long assigneeId,
                                                                  final
                                                                  Pageable pageable,
                                                                  @Param("startDate") final LocalDate startDate);

    List<Housework> findByChannelChannelIdAndStartDateBetween(Long channelId, LocalDate startOfWeek, LocalDate endOfWeek);

    Optional<Housework> findByChannelChannelIdAndHouseworkId(Long channelId, Long houseworkId);

    @Query("SELECT COUNT(h) FROM Housework h " +
            "WHERE h.channel.channelId = :channelId " +
            "AND h.status = :status " +
            "AND h.startDate BETWEEN :startOfWeek AND :endOfWeek")
    int countByStatusAndDateRange(@Param("channelId") Long channelId,
                                  @Param("status") Status status,
                                  @Param("startOfWeek") LocalDate startOfWeek,
                                  @Param("endOfWeek") LocalDate endOfWeek);

    @Modifying
    @Query("DELETE FROM Housework h WHERE h.assignee.assigneeId = :assigneeId")
    void deleteByAssigneeId(@Param("assigneeId") Long assigneeId);
}

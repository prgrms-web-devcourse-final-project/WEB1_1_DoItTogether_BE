package com.doittogether.platform.infrastructure.persistence.housework;

import com.doittogether.platform.domain.entity.Housework;
import java.time.LocalDateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HouseworkRepository extends JpaRepository<Housework, Long> {
    @Query(value = "select h from Housework h where "
            + "h.channel.channelId = :channelId "
            + "and h.startDateTime BETWEEN :startOfDay AND :endOfDay "
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
            + "h.createdAt desc")
    Slice<Housework> findAllByChannelIdAndTargetDate(@Param("channelId") final Long channelId,
                                                     @Param("userId") final Long userId,
                                                     final
                                                     Pageable pageable,
                                                     @Param("startOfDay") final LocalDateTime startOfDay,
                                                     @Param("endOfDay") final LocalDateTime endOfDay);

    @Query(value = "select h from Housework h where "
            + "h.assignee.assigneeId = :assigneeId "
            + "order by "
            + "case "
            + "when h.status = 'UN_COMPLETE' then 0 "
            + "when h.status = 'COMPLETE' then 1 "
            + "else 3 "
            + "end asc, "
            + "h.createdAt desc")
    Slice<Housework> findAllByChannelIdAndTargetDateAndAssigneeId(@Param("channelId") final Long channelId,
                                                                  @Param("assigneeId") final Long assigneeId,
                                                                  final
                                                                  Pageable pageable,
                                                                  @Param("startOfDay") final LocalDateTime startOfDay,
                                                                  @Param("endOfDay") final LocalDateTime endOfDay);
}

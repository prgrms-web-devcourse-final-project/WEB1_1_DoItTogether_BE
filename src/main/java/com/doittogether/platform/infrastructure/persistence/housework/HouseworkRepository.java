package com.doittogether.platform.infrastructure.persistence.housework;

import com.doittogether.platform.domain.entity.Housework;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HouseworkRepository extends JpaRepository<Housework, Long> {
    @Query(value = "select h from Housework h where "
            + ":lastHouseworkId = 0 OR h.houseworkId < :lastHouseworkId "
            + "and h.channel.channelId = :channelId "
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
    Slice<Housework> findAllByChannelId(@Param("lastHouseworkId") final Long lastHouseworkId,
                                        @Param("channelId") final Long channelId, @Param("userId") final Long userId,
                                        final
                                        Pageable pageable);

    @Query(value = "select h from Housework h where "
            + ":lastHouseworkId = 0 OR h.houseworkId < :lastHouseworkId "
            + "and h.channel.channelId = :channelId "
            + "and h.assignee.assigneeId = :assigneeId "
            + "order by "
            + "case "
            + "when h.status = 'UN_COMPLETE' then 0 "
            + "when h.status = 'COMPLETE' then 1 "
            + "else 3 "
            + "end asc, "
            + "case "
            + "when h.assignee.assigneeId = :assigneeId then 0 "
            + "else 1 "
            + "end asc,"
            + "h.createdAt desc")
    Slice<Housework> findAllByChannelIdAndAssigneeId(final Long channelId,
                                                     final Long assigneeId, final
                                                     Pageable pageable);
}

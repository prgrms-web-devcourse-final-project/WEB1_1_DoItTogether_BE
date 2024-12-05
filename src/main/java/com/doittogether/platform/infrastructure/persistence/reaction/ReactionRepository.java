package com.doittogether.platform.infrastructure.persistence.reaction;

import com.doittogether.platform.domain.entity.Reaction;
import com.doittogether.platform.domain.enumeration.ReactionType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    @Query("SELECT COUNT(r) FROM Reaction r " +
            "WHERE r.channel.channelId = :channelId " +
            "AND r.reactionType = :reactionType " +
            "AND r.reactedAt BETWEEN :startOfWeek AND :endOfWeek")
    int countReactionsByChannelAndTypeAndDateRange(
            @Param("channelId") Long channelId,
            @Param("reactionType") ReactionType reactionType,
            @Param("startOfWeek") LocalDateTime startOfWeek,
            @Param("endOfWeek") LocalDateTime endOfWeek
    );

    @Query("SELECT r.targetUser.userId AS userId, r.targetUser.nickName AS nickName, COUNT(r) AS reactionCount " +
            "FROM Reaction r " +
            "WHERE r.channel.channelId = :channelId " +
            "AND r.reactedAt BETWEEN :startDate AND :endDate " +
            "AND r.reactionType = :reactionType " +
            "GROUP BY r.targetUser.userId, r.targetUser.nickName " +
            "ORDER BY reactionCount DESC")
    List<Object[]> findTopUserByReactionType(
            @Param("channelId") Long channelId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("reactionType") ReactionType reactionType,
            Pageable pageable
    );

}
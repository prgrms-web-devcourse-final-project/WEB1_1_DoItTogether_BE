package com.doittogether.platform.domain.entity;

import com.doittogether.platform.domain.enumeration.ReactionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reaction")
public class Reaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id", nullable = false)
    private User targetUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false)
    private Channel channel;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;

    @Column(nullable = false)
    private LocalDate reactDate;

    public static Reaction of(User user, User targetUser, Channel channel, ReactionType reactionType, LocalDate reactDate) {
        Reaction reaction = new Reaction();
        reaction.user = user;
        reaction.targetUser = targetUser;
        reaction.channel = channel;
        reaction.reactionType = reactionType;
        reaction.reactDate = reactDate;
        return reaction;
    }
}
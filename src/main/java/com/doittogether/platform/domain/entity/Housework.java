package com.doittogether.platform.domain.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import com.doittogether.platform.presentation.dto.housework.HouseworkRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Housework extends BaseEntity {
    @Id
    @Column(name = "housework_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long houseworkId;

    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(name = "task")
    private String task;

    @Column(name = "category")
    @Enumerated(value = EnumType.STRING)
    private HouseworkCategory category;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column(name = "is_all_day")
    private boolean isAllDay;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "assignee_id")
    private Assignee assignee;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    public static Housework of(LocalDateTime startDateTime, String task, HouseworkCategory category, Assignee assignee,
                               Channel channel) {
        final Housework housework = new Housework();
        housework.startDateTime = startDateTime;
        housework.task = task;
        housework.category = category;
        housework.status = Status.UN_COMPLETE;
        housework.assignee = assignee;
        housework.channel = channel;
        return housework;
    }
    }

    public LocalDateTime retrieveStartDateTime() {
        return startDateTime;
    }

    public String retrieveTask() {
        return task;
    }

    public HouseworkCategory retrieveCategory() {
        return category;
    }

    public Status retrieveStatus() {
        return status;
    }

    public boolean isAllDay() {
        return isAllDay;
    }

    public Assignee retrieveAssignee() {
        return assignee;
    }

    public Channel retrieveChannel() {
        return channel;
    }
}

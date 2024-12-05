package com.doittogether.platform.domain.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import com.doittogether.platform.domain.enumeration.HouseworkCategory;
import com.doittogether.platform.domain.enumeration.Status;
import com.doittogether.platform.presentation.dto.housework.HouseworkRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Housework extends BaseEntity {
    @Id
    @Column(name = "housework_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long houseworkId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "start_time")
    private LocalTime startTime;

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

    public static Housework of(LocalDate startDate, LocalTime startTime, String task, HouseworkCategory category, Assignee assignee,
                               Channel channel) {
        final Housework housework = new Housework();
        housework.startDate = startDate;
        housework.startTime = startTime;
        housework.task = task;
        housework.category = category;
        housework.status = Status.UN_COMPLETE;
        housework.assignee = assignee;
        housework.channel = channel;
        return housework;
    }

    public Housework update(HouseworkRequest request, Assignee assignee) {
        this.startDate = request.startDate();
        this.startTime = request.startTime();
        this.task = request.task();
        this.category = HouseworkCategory.parse(request.category());
        this.assignee = assignee;
        return this;
    }

    public void updateStatus() {
        if (this.status == Status.UN_COMPLETE) {
            this.status = Status.COMPLETE;
            return;
        }
        this.status = Status.UN_COMPLETE;
    }

    public Long retrieveHouseworkId() {
        return houseworkId;
    }

    public LocalDate retrieveStartDate() {
        return startDate;
    }

    public LocalTime retrieveStartTime() {
        return startTime;
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


    public Assignee retrieveAssignee() {
        return assignee;
    }

    public Channel retrieveChannel() {
        return channel;
    }

    public boolean isAllDay() {
        return null == startTime;
    }
}

package com.doittogether.platform.domain.entity;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Assignee {
    @Id
    @Column(name = "assignee_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long assigneeId;

    @OneToOne(cascade = ALL, fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static Assignee assignAssignee(User user){
        final Assignee assignee = new Assignee();
        assignee.assigneeId = user.retrieveUserId();
        assignee.user = user;
        return assignee;
    }
    public Long retrieveAssigneeId() {
        return assigneeId;
    }
    public User retrieveUser() {
        return user;
    }
}

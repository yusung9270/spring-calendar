package org.example.calendarproject.core.doamin.entity;


import lombok.*;
import org.example.calendarproject.core.doamin.Event;
import org.example.calendarproject.core.doamin.Notification;
import org.example.calendarproject.core.doamin.ScheduleType;
import org.example.calendarproject.core.doamin.Task;
import org.example.calendarproject.core.util.Period;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class Schedule extends BaseEntity{

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private String title;

    private String description;

    @JoinColumn(name = "writer_id")
    @ManyToOne
    private User writer;

    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType;

    public static Schedule event(String title, String description, LocalDateTime startAt, LocalDateTime endAt, User writer){
        return Schedule.builder()
                .title(title)
                .description(description)
                .startAt(startAt)
                .endAt(endAt)
                .writer(writer)
                .scheduleType(ScheduleType.EVENT)
                .build();
    }

    public static Schedule task(String title, String description, LocalDateTime taskAt, User writer){
        return Schedule.builder()
                .title(title)
                .description(description)
                .startAt(taskAt)
                .writer(writer)
                .scheduleType(ScheduleType.TASK)
                .build();
    }

    public static Schedule task(String title, LocalDateTime notifyAt, User writer){
        return Schedule.builder()
                .title(title)
                .startAt(notifyAt)
                .writer(writer)
                .scheduleType(ScheduleType.NOTIFICATION)
                .build();
    }

    public static Schedule notification(String title, LocalDateTime notifyAt, User writer) {
        return Schedule.builder()
                .startAt(notifyAt)
                .title(title)
                .writer(writer)
                .scheduleType(ScheduleType.NOTIFICATION)
                .build();
    }

    public Task toTask(){
        return new Task(this);
    }
    public Event toEvent(){
        return new Event(this);
    }

    public Notification toNotification(){
        return new Notification(this);
    }

    public boolean isOverlapped(Period period) {
        return Period.of(getStartAt(), getEndAt()).isOverlapped(period);
    }
    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + super.getId() +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", writer=" + writer +
                ", scheduleType=" + scheduleType +
                '}';
    }
}

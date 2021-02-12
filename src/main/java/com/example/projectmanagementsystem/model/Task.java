package com.example.projectmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime beginAt;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime finishAt;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column
    private String detail;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Task(String name) {
        this.name = name;
    }

    public Task(String name, LocalDateTime beginAt, LocalDateTime finishAt, String detail) {
        this.name = name;
        this.beginAt = beginAt;
        this.finishAt = finishAt;
        this.detail = detail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(name, task.name) && Objects.equals(beginAt, task.beginAt) && Objects.equals(finishAt, task.finishAt) && Objects.equals(createAt, task.createAt) && Objects.equals(detail, task.detail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, beginAt, finishAt, createAt, detail);
    }

    @Override
    public String toString() {
        return "Task [beginAt=" + beginAt + ", createAt=" + createAt + ", detail=" + detail + ", finishAt=" + finishAt
                + ", id=" + id + ", name=" + name + "]";
    }

}

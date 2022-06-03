package ru.shmovsky.tasklist.task;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shmovsky.tasklist.step.Step;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "task")
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    @OneToMany(
        mappedBy = "task", 
        cascade = CascadeType.ALL, 
        orphanRemoval = true)
    private List<Step> steps;

    public void addstep(Step step) {
        steps.add(step);
        step.setTask(this);
    }

    public void removestep(Step step) {
        steps.remove(step);
        step.setTask(null);
    }
    
    Task(String body) {
        this.body = body;
    }

}
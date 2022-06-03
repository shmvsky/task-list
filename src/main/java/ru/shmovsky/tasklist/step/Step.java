package ru.shmovsky.tasklist.step;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shmovsky.tasklist.task.Task;

@Entity
@Data
@NoArgsConstructor
@Table(name = "step")
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Task task;

    Step(String body) {
        this.body = body;
    }

}
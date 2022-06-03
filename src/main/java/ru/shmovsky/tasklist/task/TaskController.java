package ru.shmovsky.tasklist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.shmovsky.tasklist.step.Step;
import ru.shmovsky.tasklist.step.StepRepository;

@RestController
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    StepRepository stepRepository;
    
    @PostMapping("/tasks/create")
    public Task createTask(@RequestBody Task task) {
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    @GetMapping("/tasks")
    public Page<Task> getTasks(@RequestParam Integer page) {
        return taskRepository.findAll(PageRequest.of(page, 25));
    }

    @GetMapping("/tasks/{task_id}")
    public Task getTask(@PathVariable(name = "task_id") Long taskId) {
        return taskRepository.findById(taskId).get();
    }

    @PostMapping("/tasks/{task_id}/new_step")
    public Task addStep(@PathVariable(name = "task_id") Long taskId,
                        @RequestBody Step step) {
        Task task = taskRepository.findById(taskId).get();
        task.addstep(step);
        stepRepository.save(step);
        return taskRepository.save(task);
    }

    @GetMapping("/tasks/{task_id}/steps")
    public List<Step> getSteps(@PathVariable(name = "task_id") Long taskId) {
        Task task = taskRepository.findById(taskId).get();
        return task.getSteps();
    }

    @PatchMapping("/tasks/{task_id}/edit")
    public Task addDueDate(@PathVariable(name = "task_id") Long taskId, 
                           @RequestParam(name = "due_date") Optional<String> dueDate,
                           @RequestParam(name = "is_completed") Optional<Boolean> isCompleted) {
        
        Task task = taskRepository.findById(taskId).get();
        
        if (dueDate.isPresent()) {
            task.setDueDate(LocalDateTime.parse(dueDate.get()));
        }

        if (isCompleted.isPresent()) {
            if (isCompleted.get()) {
                task.setIsCompleted(true);
                task.setCompletedAt(LocalDateTime.now());
            } else {
                task.setIsCompleted(false);
                task.setCompletedAt(null);
            }
        }
        
        return taskRepository.save(task);
    
    }

}

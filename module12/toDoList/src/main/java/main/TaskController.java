package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import main.model.Task;
import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

  @Autowired
  private TaskRepository taskRepository;

  @GetMapping("/tasks/")
  public List<Task> list() {
    Iterable<Task> taskIterable = taskRepository.findAll();
    ArrayList<Task> tasks = new ArrayList<>();
    for (Task task : taskIterable) {
      tasks.add(task);
    }
    return tasks;
  }

  @PostMapping("/tasks/")
  public long add(Task task) {
    Task newTask = taskRepository.save(task);
    return newTask.getId();
  }

  @GetMapping("/tasks/{id}")
  public ResponseEntity get(@PathVariable long id) {
    Optional<Task> optionalTask = taskRepository.findById(id);
    if (!optionalTask.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    return new ResponseEntity(optionalTask.get(), HttpStatus.OK);
  }

  @DeleteMapping("/tasks/{id}")
  public ResponseEntity delete(@PathVariable long id) {
    Optional<Task> optionalTask = taskRepository.findById(id);
    if (!optionalTask.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    taskRepository.deleteById(id);
    return new ResponseEntity(optionalTask.get(), HttpStatus.OK);
  }

  @PutMapping("/tasks/{id:\\d+}")
  public ResponseEntity<Task> put(@PathVariable long id, @Valid @RequestBody Task updatedTask) {
    Optional<Task> optionalTask = taskRepository.findById(id);
    if (!optionalTask.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    Task taskToBeUpdated = taskRepository.findById(id).get();
    taskToBeUpdated.setName(updatedTask.getName());
    taskToBeUpdated.setDate(updatedTask.getDate());
    taskRepository.save(taskToBeUpdated);
    return ResponseEntity.ok().body(taskToBeUpdated);
  }
}
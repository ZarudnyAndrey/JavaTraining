package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import main.model.Task;

public class Storage {
  private static long currentId = 1;
  private static HashMap<Long, Task> tasks = new HashMap<>();

  public static List<Task> getAllTask() {
    ArrayList<Task> tasksList = new ArrayList<>();
    tasksList.addAll(tasks.values());
    return tasksList;
  }

  public static long addTask(Task task) {
    long id = currentId++;
    task.setId(id);
    tasks.put(id, task);
    return id;
  }

  public static Task getTask(int taskId) {
    if (tasks.containsKey(taskId)) {
      return tasks.get(taskId);
    }
    return null;
  }

  public static Task changeTask(long id, Task task) {
    if (tasks.containsKey(id)) {
      tasks.replace(id, task);
    }
    return null;
  }

  public static void deleteTask(long taskId) {
    tasks.remove(taskId);
  }
}
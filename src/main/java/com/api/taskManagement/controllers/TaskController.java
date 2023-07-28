package com.api.taskManagement.controllers;


import com.api.taskManagement.data.dto.request.TaskRequest;
import com.api.taskManagement.data.dto.response.TaskResponse;
import com.api.taskManagement.data.models.TaskStatus;
import com.api.taskManagement.exception.TaskNotFoundException;
import com.api.taskManagement.services.TaskService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create_tasks")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
        TaskResponse createdTask = taskService.createTask(taskRequest);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }


    @GetMapping("/get_all_tasks")
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        List<TaskResponse> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }


    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable("taskId") Long taskId) {
        TaskResponse task = taskService.getTaskById(taskId);
        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }


    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable("taskId") Long taskId,
            @RequestBody TaskRequest taskRequest) {
        try {
            TaskResponse updatedTask = taskService.updateTask(taskId, taskRequest);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (TaskNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable("taskId") Long taskId) {
        try {
            taskService.deleteTask(taskId);
            return new ResponseEntity<>("Task deleted successfully", HttpStatus.OK);
        } catch (TaskNotFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filter_tasks")
    public ResponseEntity<List<TaskResponse>> filterTasks(
            @RequestParam(value = "dateTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dueDate,
            @RequestParam(value = "status", required = false) TaskStatus status) {
        List<TaskResponse> filteredTasks = taskService.filterTasks(dueDate, status);
        return new ResponseEntity<>(filteredTasks, HttpStatus.OK);
    }


//    final String clientUrl = Client.clientUrl;
//
//    @Autowired
//    private TaskService service;
//
//    @PostMapping("/createTask")
//    @CrossOrigin(origins = clientUrl)
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<Boolean> createTask(@RequestBody NewTaskDTO newTaskDto) {
//        Task task = new Task();
//        BeanUtils.copyProperties(newTaskDto.getTask(), task);
//        service.createTask(task, newTaskDto.getUser());
//        return new ResponseEntity<Boolean>(true,HttpStatus.OK);
//    }
//
//    @GetMapping("/getAllTaskRecordByUser/{pid}/{uid}")
//    @CrossOrigin(origins = clientUrl)
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<TaskRecordListResource> getAllTaskRecordByUser(@PathVariable("pid") Long pid, @PathVariable("uid") Long uid) {
//        // service call
//        TaskRecordListResource trlr = service.getAllTaskRecordOfUserAndProgram(uid, pid);
//        return new ResponseEntity<TaskRecordListResource>(trlr, HttpStatus.OK);
//    }
//
//    @GetMapping("/getAllTaskByUser/{pid}/{uid}")
//    @CrossOrigin(origins = clientUrl)
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<TaskListResource> getAllTaskByUser(@PathVariable("pid") Long pid, @PathVariable("uid") Long uid) {
//        // service call
//        TaskListResource tlr = service.getAllTaskOfUserAndProgram(uid, pid);
//        return new ResponseEntity<TaskListResource>(tlr, HttpStatus.OK);
//    }
//
//    @GetMapping("/getAllTaskRecord")
//    @CrossOrigin(origins = clientUrl)
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<TaskRecordListResource> getAllTaskRecord() {
//        // service call
//        TaskRecordListResource trlr = service.getAllTaskRecord();
//        return new ResponseEntity<TaskRecordListResource>(trlr, HttpStatus.OK);
//    }
//
//    @GetMapping("/getAllTaskByProgram/{pid}")
//    @CrossOrigin(origins = clientUrl)
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<TaskListResource> getAllTaskByProgram(@PathVariable("pid") Long pid) {
//        // service call
//        TaskListResource tlr = service.getAllTaskByProgram(pid);
//        return new ResponseEntity<TaskListResource>(tlr, HttpStatus.OK);
//    }
//
//    @PostMapping("/updateTaskStatus/{status}")
//    @CrossOrigin(origins = clientUrl)
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<?> updateTaskStatus(@PathVariable("status") String status) {
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @GetMapping("/getTaskCalendar/{pid}/{uid}")
//    @CrossOrigin(origins = clientUrl)
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<CalendarListResource> getTaskCalendar(@PathVariable("pid") Long pid, @PathVariable("uid") Long uid) {
//        // service call
//        TaskListResource tlr =  service.getAllTaskOfUserAndProgram(uid, pid);
//        List<CalendarResource> cl = new ArrayList<CalendarResource>();
//        List<Task> tl = tlr.getTaskList();
//        for(Task t: tl) {
//            CalendarResource cr = new CalendarResource();
//            cr.setTitle(t.getName());
//            cr.setStart(t.getStartTime());
//            cr.setEnd(t.getDeadline());
//            cl.add(cr);
//        }
//        CalendarListResource clr = new CalendarListResource();
//        clr.setEvents(cl);
//
//        return new ResponseEntity<CalendarListResource>(clr, HttpStatus.OK);
//    }
//
//    @PostMapping("/addTaskWork/{pid}/{uid}/{tid}")
//    @CrossOrigin(origins = clientUrl)
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<?> addWork(@PathVariable("pid") Long pid, @PathVariable("uid") Long uid, @PathVariable("tid") Long tid, @RequestBody WorkDTO work) {
//        // service call
//        service.addWork(uid, pid, tid, work.getWork());
//        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
//    }
//
//    @GetMapping("/getTaskWork/{pid}/{uid}/{tid}")
//    @CrossOrigin(origins = clientUrl)
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<WorkDTO> getWork(@PathVariable("pid") Long pid, @PathVariable("uid") Long uid, @PathVariable("tid") Long tid) {
//        // service call
//        WorkDTO response = service.getWork(uid, pid, tid);
//        return new ResponseEntity<WorkDTO>(response, HttpStatus.OK);
//    }

//    private final TaskService taskService;
//
//    @Autowired
//    public TaskController(TaskService taskService) {
//        this.taskService = taskService;
//    }
//
//    @PostMapping("create_tasks")
//    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
//        TaskResponse taskResponse = taskService.createTask(taskRequest);
//        return ResponseEntity.status(HttpStatus.CREATED).body(taskResponse);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<TaskResponse>> getAllTasks() {
//        List<TaskResponse> taskResponses = taskService.getAllTasks();
//        return ResponseEntity.ok(taskResponses);
//    }
//
//    @GetMapping("/{taskId}")
//    public ResponseEntity<TaskResponse> getTask(@PathVariable Long taskId) {
//        TaskResponse taskResponse = taskService.getTask(taskId);
//        if (taskResponse != null) {
//            return ResponseEntity.ok(taskResponse);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
}

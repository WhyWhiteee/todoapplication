package com.example.whywhiteee.Services.Impl;

import com.example.whywhiteee.Models.Tasks;
import com.example.whywhiteee.Repositories.TasksRepository;
import com.example.whywhiteee.Services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TasksRepository tasksRepository;

    @Override
    public List<Tasks> getTasksByCompletedAndArchived(boolean completed) {
        return tasksRepository.findByCompletedAndArchived(completed, false);
    }

    @Override
    public List<Tasks> getTasksByArchived(boolean archived) {
        return tasksRepository.findByArchived(archived);
    }

    @Override
    public Tasks saveTask(Tasks task) {
        return tasksRepository.save(task);
    }

    @Override
    public void deleteTask(Tasks task) {
         tasksRepository.delete(task);
    }
}

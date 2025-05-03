package com.example.whywhiteee.Services;

import com.example.whywhiteee.Models.Tasks;

import java.util.List;


public interface TasksService {

    List<Tasks> getTasksByCompletedAndArchived(boolean completed);
    List<Tasks> getTasksByArchived(boolean archived);
    Tasks saveTask(Tasks task);
    void deleteTask(Tasks task);

}

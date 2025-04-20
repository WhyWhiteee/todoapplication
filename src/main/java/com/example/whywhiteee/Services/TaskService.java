package com.example.whywhiteee.Services;

import com.example.whywhiteee.Models.Tasks;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TaskService {

    List<Tasks> getTasksByCompletedAndArchived(boolean completed);
    List<Tasks> getTasksByArchived(boolean archived);
    Tasks saveTask(Tasks task);
    void deleteTask(Tasks task);

}

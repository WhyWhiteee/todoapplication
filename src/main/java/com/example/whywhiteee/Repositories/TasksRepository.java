package com.example.whywhiteee.Repositories;

import com.example.whywhiteee.Models.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TasksRepository extends JpaRepository<Tasks, Long> {

    List<Tasks> findByCompleted(boolean completed);
}
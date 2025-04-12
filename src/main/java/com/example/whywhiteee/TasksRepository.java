package com.example.whywhiteee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TasksRepository extends JpaRepository<Tasks, Long> {

    List<Tasks> findByCompleted(boolean completed);
}
package com.mxr.to_do.repositories;

import com.mxr.to_do.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserId(Long userId);

    List<Task> findByUserIdAndIsCompleteFalse(Long userId);

    Optional<Task> findByUserIdAndTitle(Long userId, String title);

    List<Task> findByUserIdAndTitleContainingIgnoreCase(Long userId, String title);

    void deleteByUserIdAndTitle(Long userId, String title);

    boolean existsByUserIdAndTitle(Long userId, String title);
}

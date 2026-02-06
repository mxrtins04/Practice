package com.mxr.to_do.repositories;

import com.mxr.to_do.entities.CompletedTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompletedTaskRepository extends JpaRepository<CompletedTask, Long> {

    List<CompletedTask> findByUserId(Long userId);

    Optional<CompletedTask> findByUserIdAndTitle(Long userId, String title);

    void deleteByUserId(Long userId);

    void deleteByUserIdAndTitle(Long userId, String title);

    boolean existsByUserIdAndTitle(Long userId, String title);
}

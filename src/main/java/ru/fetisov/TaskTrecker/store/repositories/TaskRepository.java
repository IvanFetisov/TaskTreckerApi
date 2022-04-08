package ru.fetisov.TaskTrecker.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fetisov.TaskTrecker.store.entities.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity,Long> {
}

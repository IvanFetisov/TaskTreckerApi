package ru.fetisov.TaskTrecker.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fetisov.TaskTrecker.store.entities.TaskStateEntity;

public interface TaskStateRepository extends JpaRepository<TaskStateEntity,Long> {
}

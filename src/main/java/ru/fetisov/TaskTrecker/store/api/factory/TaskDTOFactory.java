package ru.fetisov.TaskTrecker.store.api.factory;

import org.springframework.stereotype.Component;
import ru.fetisov.TaskTrecker.store.api.dto.TaskDTO;
import ru.fetisov.TaskTrecker.store.entities.TaskEntity;

@Component
public class TaskDTOFactory {

public TaskDTO makeTaskDTO(TaskEntity taskEntity){

    return TaskDTO.builder()
            .id(taskEntity.getId())
            .name(taskEntity.getName())
            .createdAt(taskEntity.getCreatedAt())
            .description(taskEntity.getDescription())
            .build();
}

}

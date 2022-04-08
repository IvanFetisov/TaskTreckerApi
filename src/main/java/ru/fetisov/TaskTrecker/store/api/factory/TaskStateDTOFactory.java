package ru.fetisov.TaskTrecker.store.api.factory;

import org.springframework.stereotype.Component;
import ru.fetisov.TaskTrecker.store.api.dto.ProjectDTO;
import ru.fetisov.TaskTrecker.store.api.dto.TaskStateDTO;
import ru.fetisov.TaskTrecker.store.entities.ProjectEntity;
import ru.fetisov.TaskTrecker.store.entities.TaskStateEntity;

@Component
public class TaskStateDTOFactory {

    public TaskStateDTO makeTaskStateDTO (TaskStateEntity taskStateEntity){

        return TaskStateDTO.builder()
                .id(taskStateEntity.getId())
                .name(taskStateEntity.getName())
                .createdAt(taskStateEntity.getCreatedAt())
                .build();
    }
}

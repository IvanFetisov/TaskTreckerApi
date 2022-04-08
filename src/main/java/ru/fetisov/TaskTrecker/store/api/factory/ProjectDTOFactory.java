package ru.fetisov.TaskTrecker.store.api.factory;

import org.springframework.stereotype.Component;
import ru.fetisov.TaskTrecker.store.api.dto.ProjectDTO;
import ru.fetisov.TaskTrecker.store.entities.ProjectEntity;

@Component
public class ProjectDTOFactory {


    public ProjectDTO makeProjectDTO(ProjectEntity entity){

        return ProjectDTO.builder()
            .id(entity.getId())
            .name(entity.getName())
            .createdAt(entity.getCreatedAt())
            .build();
    }

}

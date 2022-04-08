package ru.fetisov.TaskTrecker.store.api.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.fetisov.TaskTrecker.store.api.dto.AskDTO;
import ru.fetisov.TaskTrecker.store.api.dto.ProjectDTO;
import ru.fetisov.TaskTrecker.store.api.exceptions.BadRequestException;
import ru.fetisov.TaskTrecker.store.api.exceptions.NotFoundException;
import ru.fetisov.TaskTrecker.store.api.factory.ProjectDTOFactory;
import ru.fetisov.TaskTrecker.store.entities.ProjectEntity;
import ru.fetisov.TaskTrecker.store.repositories.ProjectRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) //nonstable
@Transactional
@RestController
/**
 * Что должен уметь ?
 * Создавать/редактировать проекты
 * удалять проект
 * Получать
 */
public class ProjectController {

    ProjectDTOFactory projectDTOFactory;
    ProjectRepository projectRepository;

    public static final String FETCH_PROJECT = "/api.projects";
    public static final String DELETE_PROJECT = "/api/projects/{project_id}";
    public static final String CREATE_OR_UPDATE_PROJECT = "/api/project";

    @PutMapping(CREATE_OR_UPDATE_PROJECT)
    public ProjectDTO createOrUpdateProject(
            @RequestParam(value = "project_id",required = false) Optional<Long>optionalProjectId,
            @RequestParam(value = "project_name",required = false) Optional<String> optionalProjectName)
    //Another PARAM
    {
        optionalProjectName=optionalProjectName.filter(projectName -> !projectName.trim().isEmpty());

        boolean isCreate = !optionalProjectId.isPresent();
        ProjectEntity project = optionalProjectId
                .map(this::getProjectOrThrowException)
                .orElseGet(()->ProjectEntity.builder().build());

        if(isCreate && !optionalProjectName.isPresent() ){
            throw  new BadRequestException("Project name can't be empty");

        }

        optionalProjectName
                .ifPresent(projectName ->{
                    projectRepository.findByName(projectName)
                            .filter(anotherProject -> !Objects.equals(anotherProject.getId(), project.getId()))
                            .ifPresent(anotherProject -> {
                                throw new BadRequestException(String.format("Project \"%s\"already exist", projectName));


                            });
                    project.setName(projectName);

                });

        final ProjectEntity savedProject = projectRepository.saveAndFlush(project);

        return projectDTOFactory.makeProjectDTO(savedProject);
    }

    /**
     * FETCH - поиск по критерию
     *
     * @param optionalPrefixName
     * @return
     */

    @PostMapping(FETCH_PROJECT)
    public List<ProjectDTO> fetchProject(
            @RequestParam(value = "prefix_name", required = false)
                    Optional<String> optionalPrefixName) {

        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());

        Stream<ProjectEntity> projectStream = optionalPrefixName
                .map(projectRepository::streamAllByNameStartsWithIgnoreCase)
                .orElseGet(projectRepository::streamAllBy);

        return projectStream
                .map(projectDTOFactory::makeProjectDTO)
                .collect(Collectors.toList());
    }


    /**
     * DELETE PROJECT
     * @param projectId
     * @return
     */

    @DeleteMapping(DELETE_PROJECT)
    public AskDTO deleteProject(@PathVariable("project_id") Long projectId) {

        getProjectOrThrowException(projectId);

        projectRepository.deleteById(projectId);

        return AskDTO.makeDefault(true);
    }

    private ProjectEntity getProjectOrThrowException(@PathVariable("project_id") Long projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(() ->
                        new NotFoundException(
                                String.format(
                                        "Project with \"%s\" doesn't exist",
                                        projectId
                                )
                        )
                );
    }


}

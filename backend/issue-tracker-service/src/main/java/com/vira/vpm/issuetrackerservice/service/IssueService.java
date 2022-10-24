package com.vira.vpm.issuetrackerservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vira.vpm.common.exception.AttributeException;
import com.vira.vpm.common.exception.NotFoundException;
import com.vira.vpm.issuetrackerservice.dto.CreateIssueDto;
import com.vira.vpm.issuetrackerservice.dto.IssueDto;
import com.vira.vpm.issuetrackerservice.dto.PriorityDto;
import com.vira.vpm.issuetrackerservice.dto.ProjectDto;
import com.vira.vpm.issuetrackerservice.dto.UpdateCardDto;
import com.vira.vpm.issuetrackerservice.dto.UserDto;
import com.vira.vpm.issuetrackerservice.entity.Column;
import com.vira.vpm.issuetrackerservice.entity.Issue;
import com.vira.vpm.issuetrackerservice.entity.Priority;
import com.vira.vpm.issuetrackerservice.entity.Project;
import com.vira.vpm.issuetrackerservice.enums.PriorityNameEnum;
import com.vira.vpm.issuetrackerservice.feign.UserFeign;
import com.vira.vpm.issuetrackerservice.repository.ColumnRepository;
import com.vira.vpm.issuetrackerservice.repository.IssueRepository;
import com.vira.vpm.issuetrackerservice.repository.PriorityRepository;
import com.vira.vpm.issuetrackerservice.repository.ProjectRepository;
import com.vira.vpm.issuetrackerservice.util.EnumUtil;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ColumnRepository columnRepository;
    @Autowired
    private PriorityRepository priorityRepository;
    @Autowired
    private UserFeign userFeign;
    @Autowired
    private EnumUtil enumUtil;

    public IssueDto createFromBacklog(CreateIssueDto data) throws NotFoundException, AttributeException {
        Optional<Project> project = projectRepository.findById(data.getProjectId());
        if (!project.isPresent()) {
            throw new NotFoundException("Project with id '" + data.getProjectId() + "' not exists");
        }
        if (issueRepository.findByName(data.getName()).isPresent()) {
            throw new AttributeException("Issue with name '" + data.getName() + "' exists");
        }
        Issue issue = issueRepository.save(Issue.builder().name(data.getName())
                .project(project.get())
                .sprint(null)
                .order(0)
                .build());
        Priority priority = priorityRepository
                .save(Priority.builder().name(PriorityNameEnum.MEDIUM).issues(Arrays.asList(issue)).build());
        Issue created = issueRepository.save(issue.withPriority(priority));
        return IssueDto.builder().id(created.getId()).name(created.getName())
                .creationDate(created.getCreationDate())
                .updateDate(created.getUpdateDate())
                .project(ProjectDto.builder().id(created.getProject().getId()).name(created.getProject().getName())
                        .description(created.getProject().getDescription())
                        .users(userFeign.findAllUsersByIds(created.getProject().getUsers())).build())
                .priority(PriorityDto.builder().id(priority.getId()).name(priority.getName().name()).build())
                .build();
    }

    public IssueDto createFromBoard(CreateIssueDto data) throws NotFoundException, AttributeException {
        Optional<Column> column = columnRepository.findById(data.getColumnId());
        if (!column.isPresent()) {
            throw new NotFoundException("Column with id '" + data.getColumnId() + "' not found");
        }
        if (issueRepository.findByNameAndColumn(data.getName(), column.get()).isPresent()) {
            throw new AttributeException("Card with name '" + data.getName() + "' exists");
        }
        int order = issueRepository.countIssueByColumn(column.get());
        Issue card = issueRepository.save(Issue.builder().name(data.getName())
                .column(column.get())
                .order(order)
                .build());
        Priority priority = priorityRepository
                .save(Priority.builder().name(PriorityNameEnum.MEDIUM).issues(Arrays.asList(card)).build());
        Issue created = issueRepository.save(card.withPriority(priority));
        return IssueDto.builder().id(created.getId()).name(created.getName())
                .columnId(created.getColumn().getId())
                .priority(PriorityDto.builder().id(priority.getId()).name(priority.getName().name()).build())
                .build();
    }

    public IssueDto findById(String id) {
        Optional<Issue> card = issueRepository.findById(id);
        if (card.isPresent()) {
            return IssueDto.builder().id(card.get().getId()).name(card.get().getName())
                    .columnId(card.get().getColumn().getId())
                    .description(card.get().getDescription())
                    .order(card.get().getOrder())
                    .users(userFeign.findAllUsersByIds(card.get().getUsers()))
                    .priority(PriorityDto.builder().id(card.get().getPriority().getId())
                            .name(card.get().getPriority().getName().name()).build())
                    .creationDate(card.get().getCreationDate())
                    .updateDate(card.get().getUpdateDate())
                    .build();
        } else {
            return null;
        }
    }

    public IssueDto update(String id, UpdateCardDto data) {
        Optional<Issue> card = issueRepository.findById(id);
        if (card.isPresent()) {
            Priority priority = priorityRepository.save(
                    card.get().getPriority().withName(enumUtil.parsePriorityStringToEnum(data.getPriorityName())));
            Issue updateCard = issueRepository
                    .save(card.get().withName(data.getName()).withDescription(data.getDescription())
                            .withPriority(priority)
                            .withUsers(data.getUsers().stream().map(UserDto::getId).collect(Collectors.toList())));
            return IssueDto.builder().id(updateCard.getId()).name(updateCard.getName())
                    .columnId(card.get().getColumn().getId())
                    .description(updateCard.getDescription())
                    .order(updateCard.getOrder())
                    .priority(PriorityDto.builder().id(updateCard.getPriority().getId())
                            .name(updateCard.getPriority().getName().name()).build())
                    .users(userFeign.findAllUsersByIds(updateCard.getUsers()))
                    .creationDate(updateCard.getCreationDate())
                    .updateDate(updateCard.getUpdateDate())
                    .build();
        } else {
            return null;
        }
    }

    public IssueDto updateOrder(String id, int order) {
        Optional<Issue> card = issueRepository.findById(id);
        if (card.isPresent()) {
            Issue updateCard = issueRepository.save(card.get().withOrder(order));
            return IssueDto.builder().id(updateCard.getId()).name(updateCard.getName())
                    .columnId(card.get().getColumn().getId())
                    .description(updateCard.getDescription())
                    .order(updateCard.getOrder())
                    .creationDate(updateCard.getCreationDate())
                    .updateDate(updateCard.getUpdateDate())
                    .build();
        } else {
            return null;
        }
    }

    public IssueDto updateColumn(String id, String columnId) {
        Optional<Issue> card = issueRepository.findById(id);
        Optional<Column> column = columnRepository.findById(columnId);
        if (card.isPresent() && column.isPresent()) {
            Issue updateCard = issueRepository.save(card.get().withColumn(column.get()));
            return IssueDto.builder().id(updateCard.getId()).name(updateCard.getName())
                    .columnId(card.get().getColumn().getId())
                    .description(updateCard.getDescription())
                    .order(updateCard.getOrder())
                    .creationDate(updateCard.getCreationDate())
                    .updateDate(updateCard.getUpdateDate())
                    .build();
        } else {
            return null;
        }
    }

    public List<IssueDto> updateOrder(List<IssueDto> data) throws NotFoundException {
        List<IssueDto> result = new ArrayList<>();
        for (IssueDto element : data) {
            Optional<Issue> card = issueRepository.findById(element.getId());
            if (!card.isPresent()) {
                throw new NotFoundException("Card with id '" + element.getId() + "' not found");
            }
            Issue updated = issueRepository.save(card.get().withOrder(element.getOrder()));
            result.add(IssueDto.builder().name(updated.getName())
                    .order(updated.getOrder())
                    .columnId(card.get().getColumn().getId())
                    .creationDate(updated.getCreationDate())
                    .updateDate(updated.getUpdateDate())
                    .build());
        }
        result.sort(Comparator.comparingInt(IssueDto::getOrder));
        return result;
    }

    public List<IssueDto> updateOrderAndColumn(List<IssueDto> data) throws NotFoundException {
        List<IssueDto> result = new ArrayList<>();
        for (IssueDto element : data) {
            Optional<Issue> card = issueRepository.findById(element.getId());
            if (!card.isPresent()) {
                throw new NotFoundException("Card with id '" + element.getId() + "' not found");
            }
            Optional<Column> column = columnRepository.findById(element.getColumnId());
            if (!column.isPresent()) {
                throw new NotFoundException("Column with id '" + element.getColumnId() + "' not found");
            }
            Issue updated = issueRepository.save(card.get().withOrder(element.getOrder()).withColumn(column.get()));
            result.add(IssueDto.builder().name(updated.getName())
                    .order(updated.getOrder())
                    .columnId(card.get().getColumn().getId())
                    .creationDate(updated.getCreationDate())
                    .updateDate(updated.getUpdateDate())
                    .build());
        }
        result.sort(Comparator.comparingInt(IssueDto::getOrder));
        return result;
    }
}

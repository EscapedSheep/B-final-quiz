package com.example.demo.service;

import com.example.demo.domain.Group;
import com.example.demo.domain.Trainee;
import com.example.demo.domain.Trainer;
import com.example.demo.exception.GroupFailedException;
import com.example.demo.exception.GroupNameExistedException;
import com.example.demo.exception.IdNotExistedException;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.TraineeRepository;
import com.example.demo.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GroupService {
    private GroupRepository groupRepository;
    private TrainerRepository trainerRepository;
    private TraineeRepository traineeRepository;
    private AtomicInteger groupCount;
    private static final String DEFAULT_NAME = " 组";

    @Autowired
    public GroupService(GroupRepository groupRepository, TrainerRepository trainerRepository, TraineeRepository traineeRepository) {
        this.groupRepository = groupRepository;
        this.trainerRepository = trainerRepository;
        this.traineeRepository = traineeRepository;
    }

    @PostConstruct
    public void initGroupCount() {
        this.groupCount = new AtomicInteger(1);
    }

    public List<Group> getGroups() {
        List<Group> groups = groupRepository.findAll();
        for(Group group : groups) {
            List<Trainee> trainees = traineeRepository.findAllByTeamGroupId(group.getId());
            List<Trainer> trainers = trainerRepository.findAllByTeamGroupId(group.getId());
            group.setTrainees(trainees);
            group.setTrainers(trainers);
        }
        return groups;
    }

    @Transactional
    public List<Group> autoGrouping() {
        List<Trainer> trainers = trainerRepository.findAll();
        List<Trainee> trainees = traineeRepository.findAll();
        // GTB: - magic number
        if (trainers.size() < 2 || trainees.size() == 0) {
            throw new GroupFailedException();
        }
        initTraineeAndTrainer(trainees, trainers);
        int needGroup = trainers.size()/2;
        List<Group> groups = groupRepository.findAll();
        initGroup(needGroup, groups);
        addTraineeToGroup(trainees, groups, needGroup);
        addTrainerToGroup(trainers, groups, needGroup);
        groups = groupRepository.saveAll(groups);
        trainerRepository.saveAll(trainers);
        traineeRepository.saveAll(trainees);
        return groups;
    }

    private void addTrainerToGroup(List<Trainer> trainers, List<Group> groups, int needGroup) {
        Collections.shuffle(trainers);
        int currentTrainers = 0;
        for(int start=0; start<needGroup; start++) {
            Group group = groups.get(start);
            Trainer firstTrainer = trainers.get(currentTrainers++);
            Trainer secondTrainer = trainers.get(currentTrainers++);
            firstTrainer.setTeamGroup(group);
            secondTrainer.setTeamGroup(group);
            group.addTrainer(firstTrainer);
            group.addTrainer(secondTrainer);
        }
    }

    private void addTraineeToGroup(List<Trainee> trainees, List<Group> groups, int needGroup) {
        Collections.shuffle(trainees);
        int currentGroup = 1;
        for(Trainee trainee : trainees) {
            Group group = groups.get(currentGroup - 1);
            trainee.setTeamGroup(group);
            group.addTrainee(trainee);
            currentGroup = currentGroup == needGroup ? 1 : currentGroup + 1;
        }
    }

    private void initTraineeAndTrainer(List<Trainee> trainees, List<Trainer> trainers) {
        trainees.forEach(trainee -> {
            trainee.setTeamGroup(null);
        });
        trainers.forEach(trainer -> {
            trainer.setTeamGroup(null);
        });

    }

    private void initGroup(int needGroup, List<Group> groups) {
        if(groups.size() < needGroup) {
            for (int start = 0; start < needGroup-groups.size(); start++) {
                groups.add(Group.builder().name(groupCount.getAndIncrement() + DEFAULT_NAME).trainees(new ArrayList<>()).trainers(new ArrayList<>()).build());
            }
        }
        if(groups.size() > needGroup) {
            Iterator<Group> iterator = groups.iterator();
            int size = groups.size();
            while(size > needGroup) {
                iterator.remove();
                size -= 1;
            }
        }
    }

    public Group updateGroupName(Integer group_id, Group group) {
        Group findGroup = groupRepository.findById(group_id).orElseThrow(IdNotExistedException::new);
        List<Group> groups = groupRepository.findAll();
        groups.forEach(g -> {
            if(g.getName().equals(group.getName())) {
                throw new GroupNameExistedException();
            }
        });
        findGroup.setName(group.getName());
        return groupRepository.save(findGroup);
    }
}

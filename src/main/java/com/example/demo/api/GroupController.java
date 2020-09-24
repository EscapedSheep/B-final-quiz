package com.example.demo.api;

import com.example.demo.domain.Group;
import com.example.demo.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/groups")
public class GroupController {
    private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<Group> getGroups() {
        return groupService.getGroups();
    }

    @PostMapping("/auto-grouping")
    public List<Group> autoGrouping() {
        return groupService.autoGrouping();
    }

    @PatchMapping("{group_id}")
    public Group updateGroupName(@PathVariable Integer group_id, @RequestBody @Valid Group group) {
        return groupService.updateGroupName(group_id, group);
    }
}

package com.example.springjparelationships.controller;

import com.example.springjparelationships.entity.Faculty;
import com.example.springjparelationships.entity.Group;
import com.example.springjparelationships.payload.GroupDto;
import com.example.springjparelationships.respository.FacultyRepository;
import com.example.springjparelationships.respository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/group")
public class GroupController {


    @Autowired
    GroupRepository groupRepository;

    @Autowired
    FacultyRepository facultyRepository;

    //READ FOR VAZIRLIK
    @GetMapping
    public List<Group> getGroups(){
        List<Group> groupList = groupRepository.findAll();
        return groupList;
    }

    @GetMapping(value = "/byUniversityId/{universityId}")
    public List<Group> getGroupsByUniversityId(@PathVariable Integer universityId){
       // List<Group> allByFaculty_universityId = groupRepository.findAllByFaculty_UniversityId(universityId);
//        List<Group> groupsByUniversityId = groupRepository.getGroupsByUniversityId(universityId);
        List<Group> groupList = groupRepository.getList(universityId);
        return groupList;
    }

    @PostMapping
    public String addGroup(@RequestBody GroupDto groupDto){

        Group group = new Group();
        group.setName(groupDto.getName());
        Optional<Faculty> faculty = facultyRepository.findById(groupDto.getFacultyId());
        if (!faculty.isPresent()){
            return "such faculty not found";
        }
        group.setFaculty(faculty.get());
        groupRepository.save(group);
        return "Group added";

    }
}

package com.cybertek.controller;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.enums.Status;
import com.cybertek.service.ProjectService;
import com.cybertek.service.UserService;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project")
public class ProjectController {


    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @GetMapping("/create")
    public String createProject(Model model) {

        model.addAttribute("project", new ProjectDTO());
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("managers", userService.findManagers());

        return "/project/create";
    }

    @PostMapping("/create")
    public String insertproject(ProjectDTO project) {

        projectService.save(project);
        project.setProjectStatus(Status.OPEN);

        return "redirect:/project/create";

    }

    @GetMapping("/delete/{projectcode}")
    public String deleteProject(@PathVariable("projectcode") String projectcode) {
        projectService.deleteById(projectcode);

        return "redirect:/project/create";
    }

    @GetMapping("/complete/{projectcode}")
    public String completeProject(@PathVariable("projectcode") String projectcode){
        projectService.complete(projectService.findById(projectcode));

        return "redirect:/project/create";
    }

    @GetMapping("/update/{projectCode}")
    public String editProject(@PathVariable("projectCode") String projectcode,Model model){

        model.addAttribute("project", projectService.findById(projectcode));
        model.addAttribute("projects",projectService.findAll());
        model.addAttribute("managers",userService.findManagers());



        return "/project/update";
    }

    @PostMapping("/update/{projectCode}")
    public String updateProject(@PathVariable("projectCode") String projectcode, ProjectDTO project,Model model){

        projectService.update(project);

        return "redirect:/project/create";
    }

}

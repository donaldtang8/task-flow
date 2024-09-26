package com.dt8.task_flow;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.entity.ProjectStatus;
import com.dt8.task_flow.entity.User;
import com.dt8.task_flow.service.ProjectService;
import com.dt8.task_flow.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class TaskFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskFlowApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserService userService, ProjectService projectService) {
		return runner -> {
			createUser(userService);
			createProject(userService, projectService);
			getProjectsByOwnerId(projectService);
			createNewUserAndAddToProject(userService, projectService);
		};
	}

	public void createUser(UserService userService) {
		User newUser = new User("admin@email.com", "admin", "admin", "admin", "test123", "ADMIN");
		userService.createUser(newUser);
	 }

	public void createProject(UserService userService, ProjectService projectService) {
		Optional<User> projectOwner = userService.getUserByUsername("admin");
		if (projectOwner.isPresent()) {
			User owner = projectOwner.get();
			Project newProject = new Project("Test project", "Test description");
			newProject.setOwner(owner);
			projectService.createProject(newProject);
			Project newProject2 = new Project("Test project 2", "Test description 2");
			newProject2.setOwner(owner);
			projectService.createProject(newProject2);
		}
	}

	public void getProjectsByOwnerId(ProjectService projectService) {
		List<Project> projects = projectService.getProjectsByOwnerId(1);
		for (Project p: projects) {
			System.out.println(p.getTitle());
		}
	}

	private void getProjectsByUserId(UserService userService, long userId) {
		System.out.println("Getting projects for user: " + userId);
		List<Project> projects = userService.getProjectsByUserId(userId);
		for (Project p: projects) {
			System.out.println(p.getTitle());
		}
	}

	private void createNewUserAndAddToProject(UserService userService, ProjectService projectService) {
		User newUser = new User("user1@email.com", "Moo", "Deng", "user1", "test123", "USER");
		userService.createUser(newUser);
		projectService.addUserToProjectById(1, newUser.getId());
		getProjectsByUserId(userService, newUser.getId());
	}
}

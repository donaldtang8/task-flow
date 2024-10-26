package com.dt8.task_flow;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.entity.User;
import com.dt8.task_flow.entity.UserRole;
import com.dt8.task_flow.service.ProjectService;
import com.dt8.task_flow.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class TaskFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskFlowApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserService userService, ProjectService projectService, PasswordEncoder passwordEncoder) {
		return runner -> {
			createUser(userService, passwordEncoder);
			createProject(userService, projectService);
			getProjectsByOwnerId(projectService);
			createNewUserAndAddToProject(userService, projectService);
//			getProjectsByUserId(userService, 2);
		};
	}

	public void createUser(UserService userService, PasswordEncoder passwordEncoder) {
		User newUser = new User("admin@email.com", "admin", "admin", "admin", passwordEncoder.encode("test123"), UserRole.ADMIN);
		userService.createUser(newUser);
	 }

	public void createProject(UserService userService, ProjectService projectService) {
		User projectOwner = userService.getUserByUsername("admin");
		if (projectOwner != null) {
			Project newProject = new Project("Test project", "Test description");
			newProject.setOwner(projectOwner);
			projectService.createProject(newProject);
			Project newProject2 = new Project("Test project 2", "Test description 2");
			newProject2.setOwner(projectOwner);
			projectService.createProject(newProject2);
		}
	}

	public void getProjectsByOwnerId(ProjectService projectService) {
		List<Project> projects = projectService.getProjectsByOwnerId(1);
		for (Project p: projects) {
			System.out.println(p.getTitle());
		}
	}

	private void getProjectsByUserId(ProjectService projectService, long userId) {
		System.out.println("Getting projects for user: " + userId);
		List<Project> projects = projectService.getProjectsByUserId(userId);
		for (Project p: projects) {
			System.out.println(p.getTitle());
		}
	}

	private void createNewUserAndAddToProject(UserService userService, ProjectService projectService) {
		User newUser = new User("user1@email.com", "Moo", "Deng", "user1", "test123", UserRole.USER);
		userService.createUser(newUser);
		projectService.addUserToProjectById(1, newUser.getId());
		projectService.addUserToProjectById(2, newUser.getId());
		Project newProject = new Project("moo deng's project", "moo deng is the best");
		newProject.setOwner(newUser);
		projectService.createProject(newProject);
		getProjectsByUserId(projectService, newUser.getId());
	}

	private void deleteProject(ProjectService projectService) {
		projectService.deleteProjectById(2);
	}
}

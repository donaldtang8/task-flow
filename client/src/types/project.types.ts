import { User } from "./user.types";
import { Task } from "./task.types";

export type Project = {
    id: number,
    title: string,
    description: string,
    status: string,
    createdAt: Date,
    updatedAt: Date,
    owner: User,
    users: User[],
    tasks: Task[]
}

export type ProjectContextType = {
    projects: Project[],
    project: Project | null;
    getProjects: () => Promise<void>;
    getProject: (id: string) => Promise<void>;
}
import { User } from "./user.types";
import { Project } from "./project.types";

export type Task = {
    id: number,
    title: string,
    description: string,
    status: string,
    assignee: User,
    assigner: User,
    project: Project,
    createdBy: User,
    targetDate: Date,
    createdAt: Date,
    updatedAt: Date
}
import { createContext, useContext, useState } from 'react';
import axiosInstance from '@/utils/axiosInstance';
import { Project, ProjectContextType } from '@/types/project.types';

const ProjectContext = createContext<ProjectContextType>({
    projects: [],
    project: null,
    getProjects: async () => { },
    getProject: async () => { }
});

export const ProjectProvider = ({ children }: { children: React.ReactNode }) => {
    const [projects, setProjects] = useState<Project[]>([]);
    const [project, setProject] = useState<Project | null>(null);

    const getProjects = async () => {
        const res = await axiosInstance.get('/projects');
        setProjects(res.data);
    };

    const getProject = async (id: string) => {
        const res = await axiosInstance.get(`/projects/${id}`);
        setProject(res.data);
    };

    return (
        <ProjectContext.Provider
            value={{
                projects,
                project,
                getProjects,
                getProject
            }}
        >
            {children}
        </ProjectContext.Provider>
    );
};

export const useProject = () => useContext(ProjectContext);
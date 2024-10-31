import { Project } from "@/types/project.types";

export enum ProjectActionTypes {
    GET_PROJECTS = 'GET_PROJECTS',
    GET_PROJECT = 'GET_PROJECT',
    ADD_PROJECT = 'ADD_PROJECT',
    UPDATE_PROJECT = 'UPDATE_PROJECT',
    DELETE_PROJECT = 'DELETE_PROJECT',
    PROJECT_LOADING = 'PROJECT_LOADING',
    PROJECT_ERROR = 'PROJECT_ERROR',
}

type GetProjectsAction = {
    type: ProjectActionTypes.GET_PROJECTS;
    payload: {
        projects: Project[]
    }
}

type GetProjectAction = {
    type: ProjectActionTypes.GET_PROJECT;
    payload: {
        project: Project
    }
}

type AddProjectAction = {
    type: ProjectActionTypes.ADD_PROJECT;
    payload: {
        project: Project
    }
}

type UpdateProjectAction = {
    type: ProjectActionTypes.UPDATE_PROJECT;
    payload: {
        project: Project
    }
}

type DeleteProjectAction = {
    type: ProjectActionTypes.DELETE_PROJECT;
    payload: {
        project: Project
    }
}

type ProjectLoadingAction = {
    type: ProjectActionTypes.PROJECT_LOADING;
    payload: {
        loading: boolean
    }
}

type ProjectErrorAction = {
    type: ProjectActionTypes.PROJECT_ERROR;
    payload: {
        error: string
    }
}

export type ProjectActions = GetProjectAction | GetProjectsAction | AddProjectAction | UpdateProjectAction | DeleteProjectAction | ProjectLoadingAction | ProjectErrorAction;

export type ProjectState = {
    projects: Project[],
    project: Project | null,
    error: string,
    loading: boolean
}

export const initialState = {
    projects: [],
    project: null,
    error: '',
    loading: false
}

export function ProjectReducer(state: any, action: any) {
    switch (action.type) {
        case 'GET_PROJECTS': {
            return {
                ...state,
                projects: action.payload.projects,
                error: null,
                loading: false
            }
        }
        case 'GET_PROJECT': {
            return {
                ...state,
                project: action.payload.project,
                error: null,
                loading: false
            }
        }
        case 'ADD_PROJECT': {
            return {
                ...state,
                projects: [...state.projects, action.payload.project],
                error: null,
                loading: false
            }
        }
        case 'UPDATE_PROJECT': {
            return {
                ...state,
                projects: state.projects.map((project: Project) => project.id === action.payload.project.id ? action.payload.project : project),
                error: null,
                loading: false
            }
        }
        case 'DELETE_PROJECT': {
            return {
                ...state,
                projects: state.projects.filter((project: Project) => project.id !== action.payload.project.id),
                error: null,
                loading: false
            }
        }
        case 'PROJECT_LOADING': {
            return {
                ...state,
                loading: action.payload.loading
            }
        }
        case 'PROJECT_ERROR': {
            return {
                ...state,
                error: action.payload.error
            }
        }
        default: {
            return state
        }
    }
}
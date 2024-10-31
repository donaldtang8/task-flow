import { createContext, Dispatch, useContext, useReducer } from 'react';
import { initialState, ProjectReducer, ProjectState, ProjectActions } from '@/reducer/project.reducer';

type ProjectContextType = {
    state: ProjectState,
    dispatch: Dispatch<ProjectActions>
}

const ProjectContext = createContext<ProjectContextType>({
    state: initialState,
    dispatch: () => { }
});

export const ProjectProvider = ({ children }: { children: React.ReactNode }) => {
    const [state, dispatch] = useReducer(ProjectReducer, initialState)

    return (
        <ProjectContext.Provider
            value={{ state, dispatch }}
        >
            {children}
        </ProjectContext.Provider>
    );
};

export const useProject = () => useContext(ProjectContext);
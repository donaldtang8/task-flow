import withProtectedRoute from "@/components/ProtectedRoute/ProtectedRoute";
import React, { useEffect, useState } from "react";
import { useProject } from "@/context/project.context";
import { getOwnedProjects, getMyProjects } from "@/service/project.service";
import { ProjectActionTypes } from "@/reducer/project.reducer";

const Projects = (): React.ReactElement => {
    const { state, dispatch } = useProject();
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchOwnedProjects = getOwnedProjects();
        const fetchMyProjects = getMyProjects();
        Promise.all([fetchOwnedProjects, fetchMyProjects]).then((response) => {
            setLoading(false)
        }).catch((error) => {
            dispatch({
                type: ProjectActionTypes.PROJECT_ERROR,
                payload: {
                    error: 'Error fetching projects'
                }
            });
        })
    }, [getOwnedProjects, getMyProjects]);

    return (
        <div>
            <h1>Projects</h1>
        </div>
    )
}

export default withProtectedRoute(Projects);
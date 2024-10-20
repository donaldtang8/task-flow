import withProtectedRoute from "@/components/ProtectedRoute/ProtectedRoute";
import React, { useEffect } from "react";
import { useProject } from "@/context/project.context";

const Projects = (): React.ReactElement => {
    const { getProjects, projects } = useProject();

    useEffect(() => {
        getProjects();
    }, [getProjects]);

    useEffect(() => {

    })

    return (
        <div>
            <h1>Projects</h1>
        </div>
    )
}

export default withProtectedRoute(Projects);
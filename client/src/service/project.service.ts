import axiosInstance from "@/utils/axiosInstance";

export const getProjectById = async (id: number) => {
    try {
        const res = await axiosInstance.get(`/projects/${id}`);
        return res.data;
    } catch (error) {
        throw error;
    }
}

export const getOwnedProjects = async () => {
    try {
        const res = await axiosInstance.get('/projects/owned');
        return res;
    } catch (error) {
        throw error;
    }
};

export const getMyProjects = async () => {
    try {
        const res = await axiosInstance.get('/projects/user/me');
        return res;
    } catch (error) {
        throw error;
    }
};

export const createProject = async (project: any) => {
    try {
        const res = await axiosInstance.post('/projects', project);
        return res;
    } catch (error) {
        throw error;
    }
};

export const addUserToProject = async (projectId: number, userId: number) => {
    try {
        const res = await axiosInstance.put(`projects/id/${projectId}/add-user/${userId}`);
        return res;
    } catch (error) {
        throw error;
    }
};

export const removeUserFromProject = async (projectId: number, userId: number) => {
    try {
        const res = await axiosInstance.put(`projects/id/${projectId}/remove-user/${userId}`);
        return res;
    } catch (error) {
        throw error;
    }
}

export const updateProjectById = async (id: number, project: any) => {
    try {
        const res = await axiosInstance.put(`projects/id/${id}`, project);
        return res;
    } catch (error) {
        throw error;
    }
}

export const deleteProjectById = async (id: number) => {
    try {
        const res = await axiosInstance.delete(`projects/id/${id}`);
        return res;
    } catch (error) {
        throw error;
    }
}

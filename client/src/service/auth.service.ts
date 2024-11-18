import axiosInstance from "@/utils/axiosInstance";

export const register = async (credentials: any) => {
    try {
        const res: any = await axiosInstance.post(`/auth/signup`, credentials);
        localStorage.setItem('token', res.data.accessToken);
        localStorage.setItem('user', JSON.stringify(res.data.user));
        return res;
    } catch (error: any) {
        throw error;
    }
}

export const login = async (credentials: any) => {
    try {
        const res: any = await axiosInstance.post(`/auth/login`, credentials);
        localStorage.setItem('token', res.data.accessToken);
        localStorage.setItem('user', JSON.stringify(res.data.user));
        return res;
    } catch (error: any) {
        throw error;
    }
};

export const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
};
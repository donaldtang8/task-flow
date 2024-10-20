import { User } from "./user.types";

export interface AuthContextType {
    user: User | null;
    token: string | null;
    isAuthenticated: boolean;
    register: (credentials: any) => Promise<void>;
    login: (credentials: any) => Promise<void>;
    logout: () => void;
}
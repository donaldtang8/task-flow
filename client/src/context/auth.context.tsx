import { createContext, useState, useEffect, useContext } from 'react';
import axios from 'axios';

interface User {
  id: number;
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  role: string;
}

interface AuthContextType {
  user: User | null;
  token: string | null;
  isAuthenticated: boolean;
  register: (credentials: any) => Promise<void>;
  login: (credentials: any) => Promise<void>;
  logout: () => void;
}

const config = {
  headers: {
    'Content-Type': 'application/json',
  },
};

const AuthContext = createContext<AuthContextType>({
  user: null,
  token: null,
  isAuthenticated: false,
  register: async () => {},
  login: async () => {},
  logout: () => {},
});

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  // load user details and token from local storage on initial render
  useEffect(() => {
    const storedToken = localStorage.getItem('token');
    const storedUser = localStorage.getItem('user');

    if (storedToken && storedUser) {
      setToken(storedToken);
      setUser(JSON.parse(storedUser));
      setIsAuthenticated(true);
    }
  }, []);

  const register = async(credentials: any) => {
    try {
      const res: any = await axios.post(`${process.env.NEXT_PUBLIC_API_URL}/auth/register`, credentials, config);
      localStorage.setItem('token', res.data.token);
      localStorage.setItem('user', JSON.stringify(res.data.user));
      setToken(res.data.token);
      setUser(res.data.user);
      setIsAuthenticated(true);
    } catch (error) {
      console.error('Registration error:', error);
    }
  }

  const login = async (credentials: any) => {
    try {
      const res: any = await axios.post(`${process.env.NEXT_PUBLIC_API_URL}/auth/login`, credentials, config);
      localStorage.setItem('token', res.data.token);
      localStorage.setItem('user', JSON.stringify(res.data.user));
      setToken(res.data.token);
      setUser(res.data.user);
      setIsAuthenticated(true);
    } catch (error) {
      console.error('Login error:', error);
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setUser(null);
    setToken(null);
    setIsAuthenticated(false);
  };

  return (
    <AuthContext.Provider
      value={{ user, token, isAuthenticated, register, login, logout }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
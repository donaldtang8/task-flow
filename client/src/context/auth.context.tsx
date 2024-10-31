import { createContext, useEffect, useContext, useReducer, Dispatch, useState } from 'react';
import { AuthReducer, initialState, AuthState, AuthActions, AuthActionTypes } from '@/reducer/auth.reducer';

type AuthContextType = {
  state: AuthState,
  dispatch: Dispatch<AuthActions>
}

const AuthContext = createContext<AuthContextType>({
  state: initialState,
  dispatch: () => { }
});

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [state, dispatch] = useReducer(AuthReducer, initialState);
  const [token, setToken] = useState<string | null>(null);
  const [user, setUser] = useState<string | null>(null);

  // load user details and token from local storage on initial render
  useEffect(() => {
    if (token && user) {
      dispatch({
        type: AuthActionTypes.AUTH_SUCCESS,
        payload: {
          token: token,
          user: user
        }
      });
    } else {
      dispatch({
        type: AuthActionTypes.AUTH_FAIL
      })
    }
  }, [token, user]);

  useEffect(() => {
    const storedToken = localStorage.getItem('token');
    const storedUser = localStorage.getItem('user');
    setToken(storedToken);
    setUser(storedUser);
  }, [])

  useEffect(() => {
    function handleChangeStorage() {
      setToken(JSON.parse(localStorage.getItem('token') || '{}'));
      setUser(JSON.parse(localStorage.getItem('user') || '{}'));
    }
    window.addEventListener('storage', handleChangeStorage);
    return () => {
      window.removeEventListener('storage', handleChangeStorage);
    }
  }, [])

  return (
    <AuthContext.Provider
      value={{ state, dispatch }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
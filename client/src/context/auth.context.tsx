import { createContext, useEffect, useContext, useReducer, Dispatch } from 'react';
import { AuthReducer, initialState, AuthState, AuthActions } from '@/reducer/auth.reducer';

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

  // load user details and token from local storage on initial render
  useEffect(() => {
    const storedToken = localStorage.getItem('token');
    const storedUser = localStorage.getItem('user');

    if (storedToken && storedUser) {
      dispatch({
        type: 'AUTH_SUCCESS',
        payload: {
          token: storedToken,
          user: JSON.parse(storedUser)
        }
      });
      console.log(state)
    }
  }, []);

  return (
    <AuthContext.Provider
      value={{ state, dispatch }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
import { User } from "@/types/user.types";

export enum AuthActionTypes {
    AUTH_SUCCESS = 'AUTH_SUCCESS',
    AUTH_FAIL = 'AUTH_FAIL',
    LOGOUT = 'LOGOUT'
}

type LoginAction = {
    type: AuthActionTypes.AUTH_SUCCESS;
    payload: {
        user: User;
        token: string;
    };
}

type RegisterAction = {
    type: AuthActionTypes.AUTH_SUCCESS;
    payload: {
        user: User;
        token: string;
    };
}

type LogoutAction = {
    type: AuthActionTypes.LOGOUT;
}

export type AuthActions = LoginAction | RegisterAction | LogoutAction;

export type AuthState = {
    isAuthenticated: boolean;
    user: User | null;
    token: string | null;
    error: any;
    loading: boolean;
}

export const initialState = {
    isAuthenticated: false,
    user: null,
    token: null,
    error: null,
    loading: false
}

export function AuthReducer(state: any, action: any) {
    switch (action.type) {
        case 'AUTH_SUCCESS': {
            return {
                ...state,
                isAuthenticated: true,
                user: action.payload.user,
                token: action.payload.token,
                error: null,
                loading: false
            }
        }
        case 'AUTH_FAIL': {
            return {
                ...state,
                isAuthenticated: false,
                user: null,
                token: null,
                error: action.payload,
                loading: false
            }
        }
        case 'LOGOUT': {
            return {
                ...state,
                isAuthenticated: false,
                user: null,
                token: null,
                error: null,
                loading: false
            }
        }
        default:
            return state;
    }
}

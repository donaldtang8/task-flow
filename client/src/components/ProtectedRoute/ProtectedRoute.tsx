import { useAuth } from "@/context/auth.context";
import { useRouter } from "next/router";
import React, { useEffect } from "react";

const withProtectedRoute = (WrappedComponent: React.FC) => {
    return (props: any) => {
        const { state } = useAuth();
        const router = useRouter();

        useEffect(() => {
            if (!state.isAuthenticated) {
                router.push('/login');
            }
        }, [state.isAuthenticated, router]);

        return state.isAuthenticated ? <WrappedComponent {...props} /> : null;
    };
};

export default withProtectedRoute;
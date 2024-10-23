import { useAuth } from "@/context/auth.context";
import { useRouter } from "next/router";
import React, { useEffect } from "react";

const withProtectedRoute = (WrappedComponent: React.FC) => {
    return (props: any) => {
        const { state: { isAuthenticated } } = useAuth();
        const router = useRouter();

        useEffect(() => {
            if (!isAuthenticated) {
                router.push('/login');
            }
        }, [isAuthenticated, router]);

        return isAuthenticated ? <WrappedComponent {...props} /> : null;
    };
};

export default withProtectedRoute;
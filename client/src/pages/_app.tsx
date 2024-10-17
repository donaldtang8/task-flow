import "@/styles/globals.css";
import type { AppProps } from "next/app";
import { AuthProvider } from '../context/auth.context';
import Navbar from "@/components/navbar/navbar";

export default function App({ Component, pageProps }: AppProps) {
  return (
    <AuthProvider>
      <Navbar />
      <Component {...pageProps} />
    </AuthProvider>
  );
}
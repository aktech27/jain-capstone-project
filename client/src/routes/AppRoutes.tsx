import { Routes, Route, useLocation, useNavigate } from "react-router";
import AuthLayout from "./AuthLayout";
import AppLayout from "./AppLayout";
import Login from "@/pages/Login";
import Register from "@/pages/Register";
import { useEffect, useContext } from "react";
import { GlobalContext } from "@/context/GlobalContext";

const AppRoutes = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const {
    globalState: { auth },
  } = useContext(GlobalContext)!;

  useEffect(() => {
    if (!auth?.isLoggedIn && location.pathname?.includes("/app/")) {
      navigate("/auth/login");
    }
    if (auth?.isLoggedIn && location.pathname?.includes("/auth/")) {
      navigate("/app/home");
    }
  }, [location.pathname, auth?.isLoggedIn]);
  return (
    <Routes>
      <Route path="auth" element={<AuthLayout />}>
        <Route path="login" element={<Login />} />
        <Route path="register" element={<Register />} />
        <Route path="*" element={<h1>404</h1>} />
      </Route>
      <Route path="app" element={<AppLayout />}>
        <Route path="*" element={<h1>404</h1>} />
      </Route>
    </Routes>
  );
};

export default AppRoutes;

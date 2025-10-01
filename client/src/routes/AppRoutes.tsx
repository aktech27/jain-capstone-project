import { Routes, Route } from "react-router";
import AuthLayout from "./AuthLayout";
import AppLayout from "./AppLayout";
import Login from "@/pages/Login";
import Register from "@/pages/Register";

const AppRoutes = () => {
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

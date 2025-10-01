import { Container } from "@mui/material";
import { Outlet } from "react-router";

const AuthLayout = () => {
  return (
    <Container
      maxWidth={false}
      sx={{ height: "100vh", display: "flex", alignItems: "center" }}
    >
      <Outlet />
    </Container>
  );
};

export default AuthLayout;

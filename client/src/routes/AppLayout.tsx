import ResponsiveDrawer from "@/components/ResponsiveDrawer";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { useEffect, useState } from "react";
import { Outlet, useLocation, useNavigate } from "react-router";

const AppLayout = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isMobile, setIsMobile] = useState(false);
  useEffect(() => {
    console.log(location.pathname);
    if (!isLoggedIn) {
      navigate("/auth/login");
    }
  }, [location.pathname]);

  useEffect(() => {
    function handleWindowResize() {
      if (window.innerWidth < 768) {
        setIsMobile(true);
      } else {
        setIsMobile(false);
      }
    }
    handleWindowResize();

    window.addEventListener("resize", handleWindowResize);

    return () => window.removeEventListener("resize", handleWindowResize);
  }, []);

  return (
    <Container maxWidth={false}>
      <ResponsiveDrawer drawerWidth={240} isMobile={isMobile} />
      <Box
        component="main"
        sx={{
          ml: "auto",
          width: isMobile ? "100%" : `calc(100% - ${240}px)`,
        }}
      >
        <Typography variant="h2">App Layout</Typography>
        <Outlet />
      </Box>
    </Container>
  );
};

export default AppLayout;

import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Paper from "@mui/material/Paper";

import AdminLoginForm from "@/components/Auth/AdminLoginForm";
import LoginImageSection from "@/components/Auth/LoginImageSection";
import LoginFooter from "@/components/Auth/LoginFooter";

const Login = () => {
  return (
    <Container maxWidth="md">
      <Box display="flex" flexDirection="column" gap={6}>
        <Paper elevation={4} sx={{ overflow: "hidden", borderRadius: "1rem" }}>
          <Grid container>
            <Grid size={{ xs: 12, md: 6 }}>
              <AdminLoginForm />
            </Grid>
            <Grid size={{ xs: 0, md: 6 }}>
              <LoginImageSection />
            </Grid>
          </Grid>
        </Paper>
        <LoginFooter />
      </Box>
    </Container>
  );
};

export default Login;

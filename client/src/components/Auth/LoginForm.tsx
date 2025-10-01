import {
  Box,
  Button,
  Paper,
  Grid,
  Link,
  TextField,
  Typography,
} from "@mui/material";
import LoginImage from "@/assets/login-banner.svg";
import LoginOverlayImage from "@/assets/login-overlay.svg";

export function LoginForm() {
  return (
    <Box display="flex" flexDirection="column" gap={6}>
      <Paper elevation={4} sx={{ overflow: "hidden", borderRadius: "1rem" }}>
        <Grid container>
          <Grid size={{ xs: 12, md: 6 }}>
            <Box
              component="form"
              sx={{
                p: { xs: 3, md: 4 },
                display: "flex",
                flexDirection: "column",
                gap: 3,
              }}
            >
              <Box textAlign="center">
                <Typography variant="h5" fontWeight="bold">
                  Welcome back
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Login to your bank account
                </Typography>
              </Box>

              <Box display="flex" flexDirection="column" gap={1}>
                <Typography variant="body2">Email</Typography>
                <TextField id="email" type="email" required fullWidth />
              </Box>

              <Box display="flex" flexDirection="column" gap={1}>
                <Box display="flex" alignItems="center">
                  <Typography variant="body2">Password</Typography>
                </Box>
                <TextField id="password" type="password" required fullWidth />
                <Link
                  href="#"
                  variant="body2"
                  sx={{ ml: "auto", fontSize: "0.8rem" }}
                  color="secondary"
                >
                  Forgot your password?
                </Link>
              </Box>

              <Button type="submit" fullWidth variant="contained">
                Login
              </Button>

              <Box
                sx={{
                  position: "relative",
                  textAlign: "center",
                  fontSize: "0.8rem",
                  "&::after": {
                    content: '""',
                    position: "absolute",
                    top: "50%",
                    left: 0,
                    right: 0,
                    borderTop: "1px solid",
                    borderColor: "divider",
                    zIndex: 0,
                  },
                }}
              >
                <Typography
                  sx={{
                    position: "relative",
                    zIndex: 1,
                    backgroundColor: "background.paper",
                    px: 1,
                    color: "text.secondary",
                  }}
                >
                  Or continue with
                </Typography>
              </Box>

              <Typography variant="body2" textAlign="center">
                Don&apos;t have an account?&nbsp;
                <Link href="#" underline="hover" color="secondary">
                  Register
                </Link>
              </Typography>
            </Box>
          </Grid>
          <Grid
            size={{ md: 6 }}
            sx={{
              display: { xs: "none", md: "block" },
              position: "relative",
              backgroundColor: "#f2f2ff",
            }}
          >
            <Box
              component="img"
              src={LoginImage}
              alt="Image"
              sx={{
                position: "absolute",
                left: "-20px",
                width: "100%",
                height: "100%",
                objectFit: "contain",
                scale: 1.1,
              }}
            />
            <Box
              component="img"
              src={LoginOverlayImage}
              alt="Image"
              sx={{
                position: "absolute",
                right: "-125px",
                width: "100%",
                height: "100%",
                objectFit: "contain",
                transform: "rotateY(180deg)",
                scale: 0.5,
              }}
            />
          </Grid>
        </Grid>
      </Paper>

      <Typography
        variant="caption"
        textAlign="center"
        color="text.secondary"
        sx={{
          "& a": {
            textDecoration: "underline",
            textUnderlineOffset: "4px",
            "&:hover": { color: "primary.main" },
          },
        }}
      >
        By clicking continue, you agree to our&nbsp;
        <Link href="#" color="secondary">
          Terms of Service
        </Link>
        &nbsp;and&nbsp;
        <Link href="#" color="secondary">
          Privacy Policy
        </Link>
        .
      </Typography>
    </Box>
  );
}

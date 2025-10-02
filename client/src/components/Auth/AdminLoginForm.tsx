import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Link from "@mui/material/Link";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import LoginIcon from "@mui/icons-material/Login";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";

import { adminLoginSchema } from "@/helpers/schema/auth";

import type { AdminLoginSchema } from "@/helpers/schema/auth";

const AdminLoginForm: React.FC = () => {
  const {
    register,
    handleSubmit,
    formState: { errors, isValid, isSubmitting },
  } = useForm({
    mode: "onTouched",
    resolver: zodResolver(adminLoginSchema),
  });

  const onSubmit = (data: AdminLoginSchema) => console.log(data);

  return (
    <Box
      component="form"
      sx={{
        p: { xs: 3, md: 4 },
        display: "flex",
        flexDirection: "column",
        gap: 3,
      }}
      onSubmit={handleSubmit(onSubmit)}
    >
      <Box textAlign="center">
        <LoginIcon color="primary" fontSize="large" sx={{ m: 2 }} />
        <Typography variant="h5" fontWeight="bold">
          Welcome back !
        </Typography>
        <Typography variant="body2" color="text.secondary" gutterBottom>
          Login to your admin account
        </Typography>
      </Box>

      <TextField
        variant="outlined"
        label="Email"
        id="email"
        type="email"
        required
        fullWidth
        margin="normal"
        {...register("email")}
        error={!!errors?.email}
        helperText={errors.email?.message}
      />

      <TextField
        variant="outlined"
        label="Password"
        id="password"
        type="password"
        required
        fullWidth
        margin="normal"
        {...register("password")}
        error={!!errors?.password}
        helperText={errors.password?.message}
      />

      <Link
        href="/auth/forgot-password"
        variant="body2"
        sx={{ ml: "auto", fontSize: "0.8rem" }}
        color="secondary"
      >
        Forgot your password?
      </Link>

      <Button
        type="submit"
        fullWidth
        variant="contained"
        disabled={!isValid || isSubmitting}
      >
        Login
      </Button>

      <Typography variant="body2" textAlign="center">
        Don&apos;t have an account?&nbsp;
        <Link href="/auth/register" underline="hover" color="secondary">
          Register
        </Link>
      </Typography>
    </Box>
  );
};

export default AdminLoginForm;

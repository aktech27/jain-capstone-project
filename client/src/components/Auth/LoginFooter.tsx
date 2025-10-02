import Typography from "@mui/material/Typography";
import Link from "@mui/material/Link";

const LoginFooter = () => {
  return (
    <Typography
      variant="caption"
      textAlign="center"
      color="text.secondary"
      sx={{
        "& a": {
          textDecoration: "underline",
          textUnderlineOffset: "4px",
          "&:hover": { color: "primary.main", cursor: "pointer" },
        },
      }}
    >
      By clicking continue, you agree to our&nbsp;
      <Link color="secondary">Terms of Service</Link>
      &nbsp;and&nbsp;
      <Link color="secondary">Privacy Policy</Link>
    </Typography>
  );
};

export default LoginFooter;

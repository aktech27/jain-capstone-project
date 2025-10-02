import Box from "@mui/material/Box";
import LoginImage from "@/assets/login-banner.svg";
import LoginOverlayImage from "@/assets/login-overlay.svg";

const LoginImageSection = () => {
  return (
    <Box
      width="100%"
      height="100%"
      sx={{ position: "relative", backgroundColor: "#f2f2ff" }}
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
    </Box>
  );
};

export default LoginImageSection;

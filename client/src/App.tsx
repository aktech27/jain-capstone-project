import React from "react";
import CssBaseline from "@mui/material/CssBaseline";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import "./App.css";

function App() {
  return (
    <React.Fragment>
      <CssBaseline />
      <Container maxWidth="md">
        <Typography variant="h2" gutterBottom textAlign={"center"}>
          Banking Web Application
        </Typography>
      </Container>
    </React.Fragment>
  );
}

export default App;

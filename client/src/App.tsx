import React from "react";
import { BrowserRouter } from "react-router";
import AppRoutes from "@/routes/AppRoutes";
import "./App.css";
import AppThemeProvider from "@/context/AppThemeProvider";

function App() {
  return (
    <React.Fragment>
      <BrowserRouter>
        <AppThemeProvider>
          <AppRoutes />
        </AppThemeProvider>
      </BrowserRouter>
    </React.Fragment>
  );
}

export default App;

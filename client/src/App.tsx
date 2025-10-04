import React from "react";
import { BrowserRouter } from "react-router";
import { QueryClientProvider, QueryClient } from "@tanstack/react-query";
import { SnackbarProvider } from "notistack";

import AppRoutes from "@/routes/AppRoutes";
import AppThemeProvider from "@/context/AppThemeProvider";
import GlobalContextProvider from "@/context/GlobalContext";

import "./App.css";

const queryClient = new QueryClient();

function App() {
  return (
    <React.Fragment>
      <BrowserRouter>
        <QueryClientProvider client={queryClient}>
          <AppThemeProvider>
            <GlobalContextProvider>
              <SnackbarProvider
                anchorOrigin={{
                  horizontal: "right",
                  vertical: "top",
                }}
                autoHideDuration={3000}
              >
                <AppRoutes />
              </SnackbarProvider>
            </GlobalContextProvider>
          </AppThemeProvider>
        </QueryClientProvider>
      </BrowserRouter>
    </React.Fragment>
  );
}

export default App;

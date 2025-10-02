import React from "react";
import { BrowserRouter } from "react-router";
import { QueryClientProvider, QueryClient } from "@tanstack/react-query";

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
              <AppRoutes />
            </GlobalContextProvider>
          </AppThemeProvider>
        </QueryClientProvider>
      </BrowserRouter>
    </React.Fragment>
  );
}

export default App;

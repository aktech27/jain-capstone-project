import { createContext, useMemo, useState } from "react";
import { ThemeProvider } from "@mui/material";
import CssBaseline from "@mui/material/CssBaseline";
import { maintheme, mainthemeDark } from "@/utils/theme";

import type { PropsWithChildren } from "react";

export type ThemeName = "default" | "default-dark";

export interface IAppThemeContextProps {
  theme: ThemeName;
  setTheme: React.Dispatch<React.SetStateAction<ThemeName>>;
}

export const AppThemeContext = createContext<IAppThemeContextProps>({
  theme: "default",
  setTheme: () => {},
});

const AppThemeProvider: React.FC<PropsWithChildren> = ({ children }) => {
  const [themeName, setThemeName] = useState<ThemeName>("default");

  const theme = useMemo(() => {
    if (themeName == "default") {
      return maintheme;
    } else {
      return mainthemeDark;
    }
  }, [themeName]);

  return (
    <AppThemeContext value={{ theme: themeName, setTheme: setThemeName }}>
      <ThemeProvider theme={theme}>
        <CssBaseline />
        {children}
      </ThemeProvider>
    </AppThemeContext>
  );
};

export default AppThemeProvider;

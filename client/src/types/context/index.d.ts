export type ThemeName = "default" | "default-dark";

export interface IAppThemeContextProps {
  theme: ThemeName;
  setTheme: React.Dispatch<React.SetStateAction<ThemeName>>;
}

export interface IGlobalState {
  auth: Record<string, any>;
  dashboard: Record<string, any>;
  transaction: Record<string, any>;
  error: string | null;
  loading: boolean;
}

export type GlobalStateAction =
  | { type: "SET_AUTH"; payload: Record<string, any> }
  | { type: "SET_DASHBOARD"; payload: Record<string, any> }
  | { type: "SET_TRANSACTION"; payload: Record<string, any> }
  | { type: "SET_ERROR"; payload: string | null }
  | { type: "SET_LOADING"; payload: boolean };

export interface IGlobalContextProps {
  globalState: IGlobalState;
  dispatch: React.Dispatch<IGlobalStateAction>;
}

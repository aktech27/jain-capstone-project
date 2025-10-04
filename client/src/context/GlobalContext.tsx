import { createContext, useReducer } from "react";

import globalReducer from "@/reducers/globalReducer";

import type { PropsWithChildren } from "react";
import type { IGlobalState, IGlobalContextProps } from "@/types/context";

export const DEFAULT_GLOBAL_STATE: IGlobalState = {
  auth: {
    isLoggedIn: false,
  },
  dashboard: {},
  transaction: {},
  error: null,
  loading: false,
};

export const GlobalContext = createContext<IGlobalContextProps | null>(null);

const GlobalContextProvider: React.FC<PropsWithChildren> = ({ children }) => {
  const [state, dispatch] = useReducer(globalReducer, DEFAULT_GLOBAL_STATE);

  return (
    <GlobalContext value={{ globalState: state, dispatch }}>
      {children}
    </GlobalContext>
  );
};

export default GlobalContextProvider;

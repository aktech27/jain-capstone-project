import type { GlobalStateAction, IGlobalState } from "@/types/context";

function globalReducer(
  state: IGlobalState,
  action: GlobalStateAction
): IGlobalState {
  switch (action.type) {
    case "ADMIN_LOGIN":
      return {
        ...state,
        auth: {
          ...state.auth,
          isLoggedIn: true,
          role: "Admin",
          name: action.payload.name,
          email: action.payload.email,
          username: action.payload.username,
        },
      };
    default:
      return state;
  }
}

export default globalReducer;

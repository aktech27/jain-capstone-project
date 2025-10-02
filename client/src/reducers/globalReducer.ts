import type { GlobalStateAction, IGlobalState } from "@/types/context";

function globalReducer(state: IGlobalState, action: GlobalStateAction) {
  switch (action.type) {
    default:
      return state;
  }
}

export default globalReducer;

import { PAD_BUTTON } from "../commons";
import activate from "./activate-player";
import { createDisplayMenu } from "./menu";

function activateShoot(state, event) {
  console.log("shoot");
  return {
    ...state,
    activate,
  };
}

export default activateShoot;

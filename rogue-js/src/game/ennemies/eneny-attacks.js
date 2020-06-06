import { one6SidesDice } from "../commons/dices";
import { meleeVersus } from "../fight";

const ATTACKS = {
  nibbles: {
    desc: "mordillement",
    getDamages: one6SidesDice,
    versus: meleeVersus,
  },
  bite: {
    desc: "morsure",
    getDamages: () => one6SidesDice() + 1,
    versus: meleeVersus,
  },
};

export default ATTACKS;

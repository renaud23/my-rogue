import { one6SidesDice, one4SidesDice } from "../commons/dices";
import { meleeVersus } from "../fight";

const ATTACKS = {
  nibbles: {
    desc: "mordillement",
    getDamages: one4SidesDice,
    versus: meleeVersus,
  },
  bite: {
    desc: "morsure",
    getDamages: one6SidesDice,
    versus: meleeVersus,
  },
};

export default ATTACKS;

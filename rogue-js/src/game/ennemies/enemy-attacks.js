import { one6SidesDice, one4SidesDice } from "../commons/dices";
import { meleeVersus, distanceVersus } from "../fight";

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
  woodenBow: {
    code: 1003,
    desc: "un arc en bois",
    range: 4,
    getDamages: one4SidesDice,
    versus: distanceVersus,
  },
};

export default ATTACKS;

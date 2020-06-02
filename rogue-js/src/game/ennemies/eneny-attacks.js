import { one6SidesDice } from "../commons/dices";

const ATTACKS = {
  nibbles: { desc: "mordillement", getDamages: one6SidesDice },
  bite: { desc: "morsure", getDamages: () => one6SidesDice() + 1 },
};

export default ATTACKS;

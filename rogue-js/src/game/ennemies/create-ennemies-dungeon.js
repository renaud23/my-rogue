import { createRatsDungeon } from "./rat";

function create(state) {
  return [...createRatsDungeon(state)];
}

export default create;

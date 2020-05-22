// import { consumeMove } from "../../player";

function takeObject(state) {
  return state;
}

export default (name) => [{ desc: `prendre ${name}`, todo: takeObject }];

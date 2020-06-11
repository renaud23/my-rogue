import getVisibles from "./get-visibles";

function update(state) {
  const { player } = state;
  return { ...state, player: { ...player, visibles: getVisibles(state) } };
}

export default update;

import activate from "../activate-player";

function todo(state, door) {
  const { player } = state;
  return { ...state, activate, player: { ...player, action: undefined } };
}

export default todo;

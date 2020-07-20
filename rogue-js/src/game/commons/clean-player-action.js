import activate from "../activate-player";

function cleanPlayerAction(state) {
  const { player } = state;
  return { ...state, activate, player: { ...player, action: undefined } };
}

export default cleanPlayerAction;

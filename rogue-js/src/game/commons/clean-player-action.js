function cleanPlayerAction(state) {
  const { player } = state;
  return { ...state, player: { ...player, action: undefined } };
}

export default cleanPlayerAction;

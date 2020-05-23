function cleanPlayerAction(state) {
  const { player } = state;
  return { ...state, player: { ...player, action: null } };
}

export default cleanPlayerAction;

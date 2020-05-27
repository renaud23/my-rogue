function shootTodo(state) {
  const { player } = state;
  const { action } = player;
  const { weapon } = action;
  console.log("shoot todo", weapon);
  return { ...state, player: { ...player, action: null } };
}

export default shootTodo;

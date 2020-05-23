import activate from "../../activate-player";

export default {
  desc: "exit",
  todo: function (state) {
    const { player } = state;
    return { ...state, player: { ...player, action: null }, activate };
  },
};

function createEquipWeapon(weapon) {
  return function (state) {
    const { player } = state;
    console.log("equip", weapon);
    return { ...state, player: { ...player, action: null } };
  };
}

export default createEquipWeapon;

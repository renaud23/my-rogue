import activateWait from "../../activate-wait";
import { PLAYER_ACTIONS } from "../../../commons";

function doIt(state, enemy) {
  const { player, messages } = state;
  const { weapon } = enemy;
  const { versus } = weapon;
  const [nextEnemy, nextPlayer, newMessage] = versus(
    enemy,
    player,
    weapon,
    state
  );
  return [
    {
      ...state,
      activate: activateWait,
      player: {
        ...nextPlayer,
        action: {
          type: PLAYER_ACTIONS.menu,
          header: ["Appuyer sur le bouton A"],
        },
      },
      messages: [...messages, ...newMessage],
    },
    nextEnemy,
  ];
}

function attack(state, enemy) {
  const { attack, attackLimite } = enemy;
  if (attack === undefined) {
    return doIt(state, enemy);
  }
  if (attack < attackLimite) {
    return doIt(state, { ...enemy, attack: attack + 1 });
  }
  return [state, { ...enemy, attack: 0 }];
}

export default attack;

import activate from "../activate-player";
import { removeObjectFromState } from "../objects";

function computeStatsHealing(stats, potion) {
  const { life, maxLife } = stats;
  const { how } = potion;
  return { ...stats, life: Math.min(how * maxLife + life, maxLife) };
}

function consumePotion(state, potion) {
  const next = removeObjectFromState(state, potion);
  const { player } = next;
  const { stats } = player;
  return {
    ...next,
    player: {
      ...player,
      stats: computeStatsHealing(stats, potion),
      action: undefined,
    },
    activate,
  };
}

export default consumePotion;

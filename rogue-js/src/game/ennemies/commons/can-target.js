import { distanceEucl2, antecedentPoint } from "../../../commons";

function canShoot(state, enemy) {
  const { player, dungeon } = state;
  const { position: pp, currentLevel } = player;
  const width = dungeon.getWidth(currentLevel);
  const { position: pe, weapon } = enemy;
  const { range = 1 } = weapon;

  const distance = distanceEucl2(
    antecedentPoint(pe, width),
    antecedentPoint(pp, width)
  );
  if (distance <= range * range) {
    return true;
  }

  return false;
}

export default canShoot;

import { createRat } from "./enemy/enemy-rat";
import { levelForXp } from "../fight/table-xp";
import createWolf from "./enemy/enemy-wolf";
import createBowman from "./enemy/enemy-bowman";
import { randomInt } from "../../commons";

function randomise(...tables) {
  const witch = randomInt(tables.length);
  const [create, level] = tables[witch];
  return create(level);
}

function createEnemy(sumXp) {
  const level = Math.max(1, levelForXp(sumXp));
  if (level < 3) {
    return createBowman(level); //createRat(level);
  }
  if (level < 6) {
    return randomise([createRat, level], [createWolf, level - 1]);
  }

  return createWolf(level);
}

export default createEnemy;

import canSeePlayer from "../commons/can-see-player";
import canTarget from "../commons/can-target";
import canBite from "../commons/can-bite";
import attack from "../commons/attack";
import { TYPE_OBJECT } from "../../objects";
import { removeObject } from "../../player/inventory";
import { computePath, consumePath, isPath } from "./commons-path";

function canAttack(state, enemy) {
  const { weapon, ammo } = enemy;
  const { type } = weapon;
  if (type === TYPE_OBJECT.distanceWeapon && ammo) {
    return canTarget(state, enemy);
  }
  if (type === TYPE_OBJECT.meleeWeapon) {
    return canBite(state, enemy);
  }

  return false;
}

function needSwapWeapon(state, enemy) {
  const { weapon, ammo } = enemy;
  const { type } = weapon;
  if (type === TYPE_OBJECT.distanceWeapon && !ammo) {
    return true;
  }
  return false;
}

function swapWeapon(state, enemy) {
  const { inventory, weapon } = enemy;
  const { objects } = inventory;
  const newWeapon = objects.find(function (o) {
    const { type } = o;
    return type === TYPE_OBJECT.meleeWeapon;
  });
  if (newWeapon) {
    const newInventory = removeObject(inventory, weapon);
    return [state, { ...enemy, weapon: newWeapon, inventory: newInventory }];
  }
  return [state, enemy];
}

function activate(state, enemy) {
  if (canSeePlayer(state, enemy)) {
    if (canAttack(state, enemy)) {
      const [nextState, nextEnemy] = attack(state, enemy);
      return [nextState, { ...nextEnemy, path: undefined }];
    }
    if (needSwapWeapon(state, enemy)) {
      return swapWeapon(state, enemy);
    }

    if (isPath(enemy)) {
      return consumePath(state, enemy);
    }
    return computePath(state, enemy);
  }
  if (isPath(enemy)) {
    return consumePath(state, enemy);
  }
  return [state, enemy];
}

export default activate;

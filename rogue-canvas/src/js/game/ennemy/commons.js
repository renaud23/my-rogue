import { createChrono } from "../common-tools";
import * as berserk from "./berserk";
import * as worm from "./worm";

export const STATE = {
  wait: "ennemy/waiting",
  move: "ennemy/move"
};

export const activate = e => game => {
  switch (e.type) {
    case berserk.TYPE_BERSERK:
      return berserk.activate(e)(game);
    case worm.TYPE_WORM:
      return worm.activate(e)(game);
    default:
      return game;
  }
};

export const isAtPos = e => pos => {
  switch (e.type) {
    case berserk.TYPE_BERSERK:
      return berserk.isAtPos(e)(pos);
    case worm.TYPE_WORM:
      return worm.isAtPos(e)(pos);
    default:
      return false;
  }
};

export const getTileAt = e => pos => {
  switch (e.type) {
    case berserk.TYPE_BERSERK:
      return berserk.getTileAt(e)(pos);
    case worm.TYPE_WORM:
      return worm.getTileAt(e)(pos);
    default:
      return null;
  }
};

export const checkTimer = speed => e => ({
  ...e,
  timer: e.timer || createChrono(speed)
});

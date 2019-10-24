import * as berserk from "./berserk";

export const activate = e => game => {
  switch (e.type) {
    case berserk.TYPE_BERSERK:
      return berserk.activate(e)(game);
    default:
      return game;
  }
};

export const isVisibleAt = e => pos => {
  switch (e.type) {
    case berserk.TYPE_BERSERK:
      return berserk.isVisibleAt(e)(pos);
    default:
      return false;
  }
};

export const getTileAt = e => pos => {
  switch (e.type) {
    case berserk.TYPE_BERSERK:
      return berserk.getTileAt(e)(pos);
    default:
      return null;
  }
};

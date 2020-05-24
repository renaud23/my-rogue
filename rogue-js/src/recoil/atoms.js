import { atom } from "recoil";

export const dungeonState = atom({
  key: "dungeonState",
  default: null,
});

export const playerState = atom({
  key: "playerState",
  default: {
    position: null,
    action: null,
    fov: null,
  },
});

export const activateState = atom({
  key: "activateState",
  default: null,
});

export const ennemiesState = atom({
  key: "ennemiesState",
  default: [],
});

export const objectsState = atom({
  key: "objectsState",
  default: [],
});

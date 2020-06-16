import { atom } from "recoil";

export const dungeonState = atom({
  key: "dungeonState",
  default: undefined,
});

export const playerState = atom({
  key: "playerState",
  default: {
    position: undefined,
    action: undefined,
    fov: undefined,
  },
});

export const activateState = atom({
  key: "activateState",
  default: undefined,
});

export const ennemiesState = atom({
  key: "ennemiesState",
  default: [],
});

export const objectsState = atom({
  key: "objectsState",
  default: [],
});

export const messagesState = atom({
  key: "messagesState",
  default: [],
});

export const miscellaneousState = atom({
  key: "miscellaneousState",
  default: { effects: [] },
});

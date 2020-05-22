import typeObject from "./type-object";
// import { takeObject } from "../game/todo";

const SIMPLE = [
  { code: 1, desc: "du fil", size: 1, type: typeObject.simple, todo: [] },
  {
    code: 2,
    desc: "un morceau de pain",
    size: 1,
    type: typeObject.simple,
    todo: [],
  },
  { code: 3, desc: "un briquet", size: 1, type: typeObject.simple, todo: [] },
  { code: 4, desc: "des os", size: 2, type: typeObject.simple, todo: [] },
  {
    code: 4,
    desc: "une grosse pierre",
    size: 2,
    type: typeObject.simple,
    todo: [],
  },
  {
    code: 4,
    desc: "un vulgaire cailloux",
    size: 1,
    type: typeObject.simple,
    todo: [],
  },
];

export default SIMPLE;

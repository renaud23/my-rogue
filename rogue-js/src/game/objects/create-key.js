import typeObject from "./type-object";

let INDEX = 0;

const PROTO = {
  code: "key",
  size: 0,
  takeable: true,
  type: typeObject.key,
};

function create(position, level, targets, desc = "une clef") {
  const { kind } = targets[0];
  const lockedId = targets.map(({ lockedId }) => lockedId);
  return {
    ...PROTO,
    id: `KEY-${INDEX++}`,
    position,
    level,
    kind,
    targets: lockedId,
    desc,
    todo: [],
  };
}

export default create;

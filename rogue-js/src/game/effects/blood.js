export const BLOOD_EFFECT = "effect/blood";

function createBlood(position, level) {
  return { type: BLOOD_EFFECT, position, level };
}

export default createBlood;

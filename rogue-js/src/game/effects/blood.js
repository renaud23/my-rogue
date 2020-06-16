export const BLOOD_EFFECT = "effect/blood";

function createTempo(interval) {
  const date = new Date();
  let last = date.getTime();
  return function () {
    const next = new Date().getTime();
    if (interval <= next - last) {
      last = next;
      return true;
    }

    return false;
  };
}

function createActivate() {
  const tempo = createTempo(100);
  const seq = ["/", "-", "_"];
  let index = 0;

  return function activate(effect) {
    if (tempo()) {
      if (index <= seq.length - 1) {
        return [{ ...effect, char: seq[index++] }, true];
      }

      return [{ ...effect, char: undefined, activate: undefined }, true];
    }
    return [effect, false];
  };
}

function createBlood(position, level) {
  return {
    type: BLOOD_EFFECT,
    position,
    level,
    char: "|",
    activate: createActivate(),
  };
}

export default createBlood;

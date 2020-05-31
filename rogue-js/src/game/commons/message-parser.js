function findInPath(path = [], object = {}) {
  if (!path.length) {
    return object;
  }
  const [entry, ...rest] = path;
  if (entry in object) {
    return findInPath(rest, object[entry]);
  }
  return undefined;
}

function parseOne(match, params) {
  const length = match[0].length;
  const index = match.index;
  const path = match[1];
  const value = findInPath(path.split("."), params);
  return [index, length, value === undefined ? "[?]" : value];
}

export function fillMessage(message, params) {
  const regexp = /\$\[(.*?)\]/g;
  const matches = [...message.matchAll(regexp)];
  const operations = matches.map(function (match) {
    return parseOne(match, params);
  });

  const [transformation] = operations.reduce(
    function ([stack, pos], transfo, i) {
      const [index, length, value] = transfo;
      const next = index + length;
      const entries = [message.substr(pos, index - pos), value];
      if (i === operations.length - 1 && next < message.length - 1) {
        return [[...stack, ...entries, message.substr(next)]];
      }
      return [[...stack, ...entries], next];
    },
    [[], 0]
  );

  return transformation.join("");
}

/**
 *
 * @param {*} state
 * @param {*} message
 */
export function appendMessages(state, message) {
  if (!message) {
    return state;
  }
  const { messages } = state;
  return { ...state, messages: [...messages, message] };
}

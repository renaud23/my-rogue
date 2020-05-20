export const logCave = (cave, width, height) =>
  console.log(
    new Array(width * height)
      .fill(":")
      .map((c, i) => (cave.indexOf(i) !== -1 ? "X" : "."))
      .reduce(
        (a, c, i) => ((i + 1) % width === 0 ? `${a}${c}\n\r` : `${a}${c}`),
        ""
      )
  );

const getChar = (code) => {
  switch (code) {
    case 1:
      return ".";
    case 0:
      return " ";
    default:
      return `${code}`;
  }
};

export const logData = (data, width) =>
  console.log(
    data.reduce(
      (a, c, i) =>
        (i + 1) % width === 0 ? `${a}${getChar(c)}\n\r` : `${a}${getChar(c)}`,
      ""
    )
  );

/* */
const findCave = ({ data, width }, i, cave = []) => {
  data[i] = 1;

  const left = data[i - 1] === 0 ? findCave({ data, width }, i - 1, cave) : [];
  const right = data[i + 1] === 0 ? findCave({ data, width }, i + 1, cave) : [];
  const top =
    data[i - width] === 0 ? findCave({ data, width }, i - width, cave) : [];
  const bottom =
    data[i + width] === 0 ? findCave({ data, width }, i + width, cave) : [];

  return [...cave, ...left, ...right, ...top, ...bottom, i];
};

const findFillCave = ({ data, caves, width }, i) => {
  return { data, caves: [...caves, findCave({ data, width }, i)], width };
};

const findCaves = (initial, width) =>
  initial.reduce(
    ({ data, caves }, curr, i) => {
      return data[i] === 0
        ? { ...findFillCave({ data, caves, width }, i) }
        : { data, caves };
    },
    { data: [...initial], caves: [] }
  ).caves;

/* */
const distance = ({ a, b }) => Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2);

const FindNearestBeetween2Caves = (width) => (caveA, caveB) => {
  const [pos, ...caveOfRest] = caveB;
  const a = { x: pos % width, y: Math.trunc(pos / width) };
  const currCand = caveA.reduce(
    (acc, point) => {
      const b = { x: point % width, y: Math.trunc(point / width) };
      if (distance({ a, b }) < distance(acc)) return { a, b };
      return acc;
    },
    {
      a: { x: 0, y: 0 },
      b: { x: 99999999, y: 99999999 },
    }
  );
  if (caveOfRest.length > 0) {
    const nextCand = FindNearestBeetween2Caves(width)(caveA, caveOfRest);
    if (distance(currCand) > distance(nextCand)) return nextCand;
  }

  return currCand;
};

const findNearest = (width) => (cave, otherCaves) => {
  const [next, ...other] = otherCaves;
  const result = FindNearestBeetween2Caves(width)(cave, next);
  if (other.length) {
    const nextResult = FindNearestBeetween2Caves(width)(cave, other);
    if (distance(result) > distance(nextResult)) return nextResult;
  }
  return result;
};

const joinPoints = ({ data, width }) => (a, b) => {
  const next = [...data];
  next[a.x + a.y * width] = 0;

  if (a.x !== b.x || a.y !== b.y) {
    const { dx, dy } =
      Math.abs(a.x - b.x) < Math.abs(a.y - b.y)
        ? { dx: 0, dy: (b.y - a.y) / Math.abs(b.y - a.y) }
        : { dx: (b.x - a.x) / Math.abs(b.x - a.x), dy: 0 };
    return joinPoints({ data: next, width })({ x: a.x + dx, y: a.y + dy }, b);
  }

  return next;
};

const joinThem = ({ caves, data, width }) => {
  const [cave, ...rest] = caves;
  if (rest.length > 1) {
    const { a, b } = findNearest(width)(cave, rest);
    const next = joinPoints({ data, width })(a, b);
    return joinThem({ caves: rest, data: next, width });
  }
  return data;
};

/* */
const removeLittleCaves = (limite) => (caves) =>
  caves.filter((c) => c.length > limite);

const fillSmallest = (limite) => ({ caves, data }) =>
  caves
    .filter((c) => c.length <= limite)
    .reduce((a, cave) => {
      cave.forEach((c) => (a[c] = 1));
      return a;
    }, data);

/* */
export default ({ data, height, width }) => {
  const limite = 10;
  const caves = findCaves(data, width);
  const joined = joinThem({
    caves: removeLittleCaves(limite)(caves),
    data,
    width,
  });
  const finalData = fillSmallest(limite)({ data: joined, caves });
  return { data: finalData, height, width };
};

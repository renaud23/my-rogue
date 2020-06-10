import { randomInt } from "../../commons";

function split(rect, iteration, curr = 0) {
  const [x, y, width, height] = rect;
  const lim = 0.3;
  if (curr < iteration) {
    if (randomInt(2) === 0) {
      const wi = Math.trunc(
        lim * width + (1 - 2 * lim) * width * Math.random()
      );
      return [
        rect,
        split([x, y, wi, height], iteration, curr + 1),
        split([x + wi, y, width - wi, height], iteration, curr + 1),
      ];
    }

    const hi = Math.trunc(
      lim * height + (1 - 2 * lim) * height * Math.random()
    );
    return [
      rect,
      split([x, y, width, hi], iteration, curr + 1),
      split([x, y + hi, width, height - hi], iteration, curr + 1),
    ];
  }

  return [rect];
}

function printRoom(rect, room) {
  const w = rect[2];

  const [rows] = room.reduce(
    function ([r, curr], t, i) {
      if (i % w === w - 1) {
        return [[...r, `${curr}${t}`], ""];
      }
      return [r, `${curr}${t}`];
    },
    [[], ""]
  );
  console.log(rect);
  rows.forEach(function (row, i) {
    console.log(row, i);
  });
}

function carveRoom(rect) {
  const room = new Array(rect[2] * rect[3]).fill(1);
  return [rect, room];
}

function isInRect(x, y, rect) {
  const [rx, ry, w, h] = rect;
  if (x >= rx && x < rx + w && y >= ry && y < ry + h) {
    return true;
  }
  return false;
}

function mergeRooms(left, right, i) {
  const [rectLeft, roomLeft] = left;
  const [rectRight, roomRight] = right;
  const [xl, yl, wl, hl] = rectLeft;
  const [xr, yr, wr, hr] = rectRight;

  const minX = Math.min(xl, xr);
  const minY = Math.min(yl, yr);
  const maxX = Math.max(xl + wl, xr + wr);
  const maxY = Math.max(yl + hl, yr + hr);
  const width = maxX - minX;
  const height = maxY - minY;

  const room = new Array(width * height).fill(0).map(function (_, i) {
    const px = (i % width) + minX;
    const py = Math.trunc(i / width) + minY;

    if (isInRect(px, py, rectLeft)) {
      return roomLeft[px - xl + (py - yl) * wl];
    }

    return roomRight[px - xr + (py - yr) * wr];
  });

  return [[minX, minY, width, height], room];
}

function crawlTree([rect, left, right], i = 0) {
  const [x, y, w, h] = rect;

  if (!left && !right) {
    return [carveRoom(rect)];
  }
  const [childLeft] = crawlTree(left, i + 1);
  const [childRight] = crawlTree(right, i + 1);
  const merged = mergeRooms(childLeft, childRight);

  return [merged];
}

function createFactory(width = 30, height = 30) {
  const tree = split([0, 0, width, height], 4);

  const what = crawlTree(tree);
  console.log(what);

  const tab = new Array(width * height).fill(0);

  // rooms.reduce(function(){},[])

  return tab;
}

export default createFactory;

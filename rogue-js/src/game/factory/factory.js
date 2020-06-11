import { randomInt } from "../../commons";
import carveCorridor from "./carve-corridor";

function isInRect(x, y, rect) {
  const [rx, ry, w, h] = rect;
  if (x >= rx && x < rx + w && y >= ry && y < ry + h) {
    return true;
  }
  return false;
}

function splitHor(rect, limite, already = false) {
  const [x, y, w, h] = rect;
  if (w >= 2 * limite) {
    const wi = Math.trunc(limite + Math.random() * (w - 2 * limite));
    return [
      rect,
      split([x, y, wi, h], limite),
      split([x + wi, y, w - wi, h], limite),
    ];
  }
  if (!already) {
    return splitVer(rect, limite, true);
  }
  return [rect];
}
function splitVer(rect, limite, already = false) {
  const [x, y, w, h] = rect;
  if (h >= 2 * limite) {
    const hi = Math.trunc(limite + Math.random() * (h - 2 * limite));

    return [
      rect,
      split([x, y, w, hi], limite),
      split([x, y + hi, w, h - hi], limite),
    ];
  }
  if (!already) {
    return splitHor(rect, limite, true);
  }
  return [rect];
}

function split(rect, limite) {
  if (randomInt(2) === 0) {
    return splitHor(rect, limite);
  }
  return splitVer(rect, limite);
}

// function printRoom(rect, room) {
//   const w = rect[2];

//   const [rows] = room.reduce(
//     function ([r, curr], t, i) {
//       if (i % w === w - 1) {
//         return [[...r, `${curr}${t}`], ""];
//       }
//       return [r, `${curr}${t}`];
//     },
//     [[], ""]
//   );
//   console.log(rect);
//   rows.forEach(function (row, i) {
//     console.log(row, i);
//   });
// }

function carveRoom(rect) {
  const [x, y, w, h] = rect;
  const width = Math.trunc(0.4 * w * (1 + Math.random()));
  const height = Math.trunc(0.4 * h * (1 + Math.random()));
  const xi = Math.trunc((w - width) / 2);
  const yi = Math.trunc((h - height) / 2);
  const zone = [xi, yi, width, height];

  const room = new Array(w * h).fill(1).map(function (_, i) {
    const px = i % w;
    const py = Math.trunc(i / w);

    if (isInRect(px, py, zone)) {
      return 0;
    }

    return 1;
  });
  return [rect, [room, [[x + xi, y + yi, width, height]]]];
}

function mergeRooms(rect, left, right) {
  const [rectLeft, [roomLeft, zonesLeft]] = left;
  const [rectRight, [roomRight, zonesRight]] = right;
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

  const joinedRoom = carveCorridor(rect, room, left, right);

  return [
    [minX, minY, width, height],
    [joinedRoom, [...zonesLeft, ...zonesRight]],
  ];
}

function crawlTree([rect, left, right]) {
  if (!left && !right) {
    return carveRoom(rect);
  }
  const childLeft = crawlTree(left);
  const childRight = crawlTree(right);

  return mergeRooms(rect, childLeft, childRight);
}

function createFactory(width = 30, height = 30) {
  const limite = Math.trunc(Math.min(width, height) * 0.2);
  const tree = split([0, 0, width, height], limite);
  const [rect, [room, zones]] = crawlTree(tree);

  return room;
}

export default createFactory;

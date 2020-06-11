import { randomInt } from "../../../commons";
import { isInRect } from "./utils";
import carveCorridor from "./carve-corridor";
import findEmptyTiles from "./find-empty-tiles";
import carveRoom from "./carve-room";

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
  const limite = Math.trunc(Math.min(width, height) * 0.4);
  const tree = split([0, 0, width, height], limite);
  const [rect, [room, zones]] = crawlTree(tree);

  return { data: room, width, height, emptyTiles: findEmptyTiles(room) };
}

export default createFactory;

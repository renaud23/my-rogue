import { removeObject } from "../player/inventory";

function putInMapLevel(mapLevel, object) {
  const { level } = object;
  const current = mapLevel[level] || [];
  return { ...mapLevel, [level]: [...current, object] };
}

function removeInMapId(mapId, object) {
  const { id } = object;
  return Object.entries(mapId).reduce(function (next, [idCurr, curr]) {
    if (id === idCurr) {
      return next;
    }
    return { ...next, [idCurr]: curr };
  }, {});
}

function removeInMapLevel(mapLevel, object) {
  const { id, level } = object;
  const nextMapLevel = { ...mapLevel };
  if (level !== undefined) {
    nextMapLevel[level] = mapLevel[level].reduce(function (next, o) {
      const { id: currId } = o;
      if (currId === id) {
        return next;
      }
      return [...next, o];
    }, []);

    return nextMapLevel;
  }
  return mapLevel;
}

function putInMapId(mapId, object) {
  const { id } = object;
  return { ...mapId, [id]: object };
}

/** */

export function createDungeonObjects(...objects) {
  return objects.reduce(
    function ({ mapId, mapLevel }, object) {
      return {
        mapId: putInMapId(mapId, object),
        mapLevel: putInMapLevel(mapLevel, object),
      };
    },
    { mapId: {}, mapLevel: {} }
  );
}

export function getObjects(dungeonObjects, level) {
  const { mapLevel } = dungeonObjects;
  if (level in mapLevel) {
    return mapLevel[level];
  }
  return [];
}

export function getObjectsAt(dungeonObjects, level, position) {
  return getObjects(dungeonObjects, level).reduce(function (stack, o) {
    const { position: posObject } = o;
    if (posObject === position) {
      return [...stack, o];
    }
    return stack;
  }, []);
}

export function putObjects(dungeonObjects, ...objects) {
  return objects.reduce(
    function ({ mapId, mapLevel }, object) {
      return {
        mapId: putInMapId(mapId, object),
        mapLevel: putInMapLevel(mapLevel, object),
      };
    },
    { ...dungeonObjects }
  );
}

export function removeObjects(dungeonObjects, ...objects) {
  return objects.reduce(
    function ({ mapId, mapLevel }, object) {
      return {
        mapId: removeInMapId(mapId, object),
        mapLevel: removeInMapLevel(mapLevel, object),
      };
    },
    { ...dungeonObjects }
  );
}

export function isInDungeon(dungeonObjects, object) {
  if (object) {
    const { id } = object;
    const { mapId } = dungeonObjects;
    return id in mapId;
  }
  return false;
}

export function replaceObject(dungeonObjects, witch, by) {
  return putObjects(removeObjects(dungeonObjects, witch), by);
}

export default createDungeonObjects;

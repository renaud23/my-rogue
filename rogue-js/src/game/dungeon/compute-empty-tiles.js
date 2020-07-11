function compute(dungeon) {
  const { data, doors } = dungeon;
  const emptyTiles = data.reduce(function (a, val, i) {
    if (val === 0 && doors.indexOf(i) === -1) {
      return [...a, i];
    }
    return a;
  }, []);

  return { ...dungeon, data, emptyTiles };
}

export default compute;

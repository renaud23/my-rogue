function update(state) {
  const { player, dungeon } = state;
  const { currentLevel, memory, visibles } = player;
  const data = dungeon.getData(currentLevel);

  const nextMemory = visibles.reduce(
    function (m, pos) {
      if (currentLevel in m) {
        return {
          ...m,
          [currentLevel]: { ...m[currentLevel], [pos]: data[pos] },
        };
      }
      return { ...m, [currentLevel]: { [pos]: data[pos] } };
    },
    { ...memory }
  );

  return { ...player, memory: nextMemory };
}

export default update;

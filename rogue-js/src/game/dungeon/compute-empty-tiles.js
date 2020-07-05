function compute({ data, ...other }) {
  const emptyTiles = data.reduce(function (a, val, i) {
    if (val === 0) {
      return [...a, i];
    }
    return a;
  }, []);

  return { data, emptyTiles, ...other };
}

export default compute;

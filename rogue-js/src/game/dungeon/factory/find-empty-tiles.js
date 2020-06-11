function find(room) {
  return room.reduce(function (a, tile, i) {
    if (tile === 0) {
      return [...a, i];
    }
    return a;
  }, []);
}

export default find;

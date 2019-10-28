export const createChrono = laps => {
  let start = Date.now();
  return () => {
    const current = Date.now();
    const ellapsed = current - start;
    if (ellapsed > laps) {
      start = current;
      return true;
    }
    return false;
  };
};

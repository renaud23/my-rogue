export function aggregateObjects(from, to) {
  const how = from.how + to.how;
  const desc = `${how} ${from.originalDesc}`;
  return { ...from, how, desc };
}

export default function combine(...callbacks) {
  return callbacks.reverse().reduce(
    (a, call) => {
      return function (tiles, state, rect) {
        return a(call(tiles, state, rect), state, rect);
      };
    },
    (state) => state
  );
}

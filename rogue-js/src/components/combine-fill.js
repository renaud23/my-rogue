export default function combine(...callbacks) {
  return callbacks.reverse().reduce(
    (a, call) => {
      return function (state, ...rest) {
        return a(call(state, ...rest), ...rest);
      };
    },
    (state) => state
  );
}

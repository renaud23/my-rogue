export const compseRenderer = (...renderer) =>
  renderer.reverse().reduce(
    (a, r) => (...args) => game => {
      a(...args)(game);
      r(...args)(game);
    },
    () => () => null
  );

export { default as createMapRenderer } from "./map-rendering";
export { default as createRenderer } from "./console-memory-rendering";

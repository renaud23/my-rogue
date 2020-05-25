// import { canSee, isEmptyPosition } from "../commons";
// import { astarPath } from "./path-finding";
// import { checkTimer, STATE } from "./commons";

// export const TYPE_WORM = "ennemy/worm";
// const WORM_SPEED = 250;

// const wait = e => game => {
//   const { position } = e;
//   return canSee(game)(position, game.player.position)
//     ? { game, e: { ...e, state: STATE.move } }
//     : { game, e };
// };

// const findPlayer = game => {
//   return false;
// };

// const wormMove = next => e => {
//   const { positions, length } = e;
//   if (positions.length < length) {
//     return { ...e, positon: next, positions: [next, ...positions] };
//   }
//   const rest = positions.slice(0, positions.length - 1);
//   return { ...e, position: next, positions: [next, ...rest] };
// };

// const followPath = game => e => {
//   const { player } = game;
//   const { path } = e;
//   if (!path || path.length === 0) {
//     return {
//       ...e,
//       state: STATE.wait,
//       path: undefined,
//       lastSee: undefined,
//       timer: undefined
//     };
//   }
//   const [next, ...rest] = path;
//   return isEmptyPosition(game)(next) && next !== player.position
//     ? { ...wormMove(next)(e), path: rest }
//     : { ...e, path: undefined };
// };

// const move = e => game => {
//   const { player } = game;
//   const { position, timer, lastSee } = e;
//   if (timer()) {
//     if (canSee(game)(position, game.player.position)) {
//       return {
//         game,
//         e:
//           lastSee === player.position
//             ? followPath(game)(e)
//             : followPath(game)({
//                 ...e,
//                 path: astarPath(game)(position, player.position)
//               })
//       };
//     }
//     return { game, e: followPath(game)(e) };
//   }

//   return { game, e };
// };

// export const activate = e => game => {
//   if (findPlayer(game)) return { game, e };
//   const { state } = e;
//   switch (state) {
//     case STATE.wait:
//       return wait(e)(game);
//     case STATE.move:
//       return move(checkTimer(WORM_SPEED)(e))(game);
//     default:
//       return { game, e };
//   }
// };

// export const getTileAt = ({ positions, tile }) => pos =>
//   positions.indexOf(pos) !== -1 ? tile : undefined;

// let I = 0;
// export const createWorm = position => ({
//   state: STATE.wait,
//   type: TYPE_WORM,
//   timer: undefined,
//   lastSee: undefined,
//   path: undefined,
//   position,
//   positions: [position],
//   tile: Math.min(I++, 9),
//   length: 5
// });

// export const isAtPos = ({ positions }) => pos =>
//   positions && positions.indexOf(pos) !== -1;

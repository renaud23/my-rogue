export const PAD_EVENT = "action/pad-event";
export function padEvent(button) {
  return { type: PAD_EVENT, payload: { button } };
}
export const CLICK_TILE_EVENT = "action/click-tile-event";
export function tileClick(position) {
  return { type: CLICK_TILE_EVENT, payload: { position } };
}

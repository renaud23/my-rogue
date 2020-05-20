export const PAD_EVENT = "action/pad-event";
export function padEvent(button) {
  return { type: PAD_EVENT, payload: { button } };
}

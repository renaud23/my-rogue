import updateMemory from "./update-memory";
import updateVisibles from "./update-visibles";

function updatePlayerView(state) {
  return updateMemory(updateVisibles(state));
}

export default updatePlayerView;

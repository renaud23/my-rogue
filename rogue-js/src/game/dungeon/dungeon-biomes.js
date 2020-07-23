export const BIOMES = {
  corridor: "biome/corridor",
  standardRoom: "biome/standard-room",
};

function affectLevelBiome(level) {
  const { regions } = level;
  const { zones } = regions;

  const nextZones = zones.map(function (zone) {
    const { roomIndex } = zone;
    if (roomIndex >= 0) {
      return { ...zone, biome: BIOMES.standardRoom };
    }
    return { ...zone, biome: BIOMES.corridor };
  });

  return { ...level, regions: { ...regions, zones: nextZones } };
}

export function affectDungeonBiome(dungeon) {
  return dungeon.map(function (level) {
    return affectLevelBiome(level);
  });
}

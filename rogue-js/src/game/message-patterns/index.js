const colorPart = (color) => (text) => `<${color}>${text}</${color}>`;
const yellow = colorPart("yellow");
const red = colorPart("red");
const mediumOrchid = colorPart("MediumOrchid");
const chartreuse = colorPart("Chartreuse");

function mergeParts(...args) {
  return args.reduce(function (a, p) {
    return `${a}${p}`;
  }, "");
}

export default {
  attackSuccess:
    "<yellow>Attaque réussie, </yellow><OrangeRed>${att.desc}</OrangeRed><yellow> sur </yellow><MediumTurquoise>${deff.desc} level ${deff.stats.level}</MediumTurquoise>.",
  attackFailure:
    "<yellow>Attaque échec, </yellow><OrangeRed>${att.desc}</OrangeRed><yellow> sur </yellow><MediumTurquoise>${deff.desc} level ${deff.stats.level}</MediumTurquoise>.",
  damages:
    "<yellow>${att.desc} inflige </yellow><red>${how}</red><yellow> a ${deff.desc}</yellow>",
  nextTurn: mergeParts(
    yellow("Tour suivant : "),
    mediumOrchid("${turn.turnPlay}")
  ),
  takeObject: mergeParts(
    yellow("vous ramassez "),
    chartreuse("${object.desc}")
  ),
  throwObject: mergeParts(yellow("vous posez "), chartreuse("${object.desc}")),
  inventoryFull: yellow("Votre inventaire est plein."),
};

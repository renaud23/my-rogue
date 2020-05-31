const colorPart = (color) => (text) => `<${color}>${text}</${color}>`;
const yellow = colorPart("yellow");
// const red = colorPart("red");
const mediumOrchid = colorPart("MediumOrchid");
const chartreuse = colorPart("Chartreuse");

function mergeParts(...args) {
  return args.reduce(function (a, p) {
    return `${a}${p}`;
  }, "");
}

function printStats(name, color) {
  return `<${color}>($[${name}.stats.strength],$[${name}.stats.agility],$[${name}.stats.luck],$[${name}.stats.endurance])</${color}>`;
}

function printPlayer(name, colorA = "yellow", colorB = "DodgerBlue") {
  return `<${colorA}>$[${name}.desc]</${colorA}>${printStats(name, colorB)}`;
}

export default {
  attack: mergeParts(
    printPlayer("att"),
    yellow(" attaque "),
    printPlayer("deff")
  ),
  attackSuccess: mergeParts(
    printPlayer("att"),
    yellow(" réussi son attaque sur "),
    printPlayer("deff")
  ),
  attackFailure: mergeParts(
    printPlayer("att"),
    yellow(" rate son attaque sur "),
    printPlayer("deff")
  ),
  damages:
    "<yellow>$[att.desc] inflige </yellow><red>$[how]</red><yellow> a $[deff.desc]</yellow>",
  nextTurn: mergeParts(
    yellow("Tour suivant : "),
    mediumOrchid("$[turn.turnPlay]")
  ),
  takeObject: mergeParts(
    yellow("vous ramassez "),
    chartreuse("$[object.desc]")
  ),
  throwObject: mergeParts(yellow("vous posez "), chartreuse("$[object.desc]")),
  inventoryFull: yellow("Votre inventaire est plein."),
};

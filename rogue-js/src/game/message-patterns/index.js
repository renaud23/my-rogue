const colorPart = (color) => (text) => `<${color}>${text}</${color}>`;
const yellow = colorPart("yellow");
const red = colorPart("red");
const mediumOrchid = colorPart("MediumOrchid");
const springGreen = colorPart("SpringGreen");
const chartreuse = colorPart("Chartreuse");
const snow = colorPart("Snow");

function mergeParts(...args) {
  return args.reduce(function (a, p) {
    return `${a}${p}`;
  }, "");
}

function printStats(name, color) {
  return `<${color}>($[${name}.stats.strength],$[${name}.stats.agility],$[${name}.stats.luck],$[${name}.stats.endurance])</${color}>`;
}

function printPlayer(name, colorA = "yellow", colorB = "DodgerBlue") {
  return `<${colorA}>$[${name}.desc] level $[${name}.stats.level]</${colorA}>${printStats(
    name,
    colorB
  )}`;
}

function snowBrackets(text) {
  return mergeParts(snow("["), text, snow("]"));
}

export default {
  attack: mergeParts(
    printPlayer("att"),
    yellow(" attaque "),
    printPlayer("deff"),
    yellow(" avec $[weapon.desc].")
  ),
  attackSuccess: mergeParts(
    yellow("Attaque "),
    springGreen("succès"),
    yellow(" dégâts "),
    snowBrackets(red("$[damages]")),
    yellow(" vie restante "),
    snowBrackets(springGreen("$[deff.stats.life]"))
  ),
  attackFailure: mergeParts(yellow("Attaque "), red("échec")),
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
  itsNotAChest: yellow("Ce n'est pas un coffre."),
  itsNotAGoodChest: yellow("Cette clef n'ouvre pas ce type de coffre."),
  chestOpened: mergeParts(
    yellow("vous ouvrez "),
    chartreuse("$[chest.desc]"),
    yellow(". Il est vide.")
  ),
  xpProgress: mergeParts(
    yellow("Vous atteignez le niveau "),
    snowBrackets(springGreen("$[stats.level]")),
    yellow(" !")
  ),
  nothingAppended: yellow("Il ne se passe rien."),
  deadEnemy: mergeParts(printPlayer("att"), yellow(" est mort.")),
  consumeXpPoint: mergeParts(yellow("vous utilisez un point d'expérience.")),
};

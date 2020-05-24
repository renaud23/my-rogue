function put(objects, object, level) {
  return objects.map(function (ol, i) {
    if (i === level) {
      return [...ol, { ...object, level }];
    }
    return ol;
  });
}

export default put;

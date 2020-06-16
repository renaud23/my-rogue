module.exports = {
  extends: ["airbnb", "prettier"],
  plugins: ["prettier"],
  rules: {},
  env: {
    jest: true,
  },
  settings: {
    "import/resolver": {
      node: {
        paths: ["src"],
      },
    },
  },
  globals: {
    fetch: true,
    window: true,
    document: true,
  },
};

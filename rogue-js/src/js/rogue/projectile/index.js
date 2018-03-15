import createProjectile from "./projectile";

export const createFireBall = createProjectile({
  speed: 2,
  depht: 10,
  damage: 10,
  message: { message: "Un projectile vous percute.", color: "red" }
});

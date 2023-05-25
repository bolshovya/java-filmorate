CREATE TABLE "user" (
  "user_id" int,
  "email" varchar,
  "login" varchar,
  "name" varchar,
  "birthday" date
);

CREATE TABLE "friendship" (
  "user_id" int,
  "friend_id" int,
  "confirmation" boolean,
  "confirmation_date" timestamp
);

CREATE TABLE "film" (
  "film_id" int,
  "title" varchar,
  "description" varchar,
  "release_date" date,
  "duration" int
);

CREATE TABLE "genre" (
  "film_id" int,
  "genre" varchar
);

CREATE TABLE "like" (
  "film_id" int,
  "user_id" int
);

CREATE TABLE "rating" (
  "film_id" int,
  "rating" varchar
);

ALTER TABLE "user" ADD FOREIGN KEY ("user_id") REFERENCES "friendship" ("user_id");

ALTER TABLE "user" ADD FOREIGN KEY ("user_id") REFERENCES "friendship" ("friend_id");

ALTER TABLE "film" ADD FOREIGN KEY ("film_id") REFERENCES "genre" ("film_id");

ALTER TABLE "film" ADD FOREIGN KEY ("film_id") REFERENCES "like" ("film_id");

ALTER TABLE "film" ADD FOREIGN KEY ("film_id") REFERENCES "rating" ("film_id");

ALTER TABLE "user" ADD FOREIGN KEY ("user_id") REFERENCES "like" ("user_id");

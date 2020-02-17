BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "product" (
	"id"	INTEGER,
	"name"	TEXT,
	"amount"	INTEGER,
	"import_price"	INTEGER,
	"export_price"	INTEGER,
	"code"	TEXT,
	"warehouse"	INTEGER,
	"location"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("warehouse") REFERENCES "warehouse"("id"),
	FOREIGN KEY("location") REFERENCES "location"("id")
);
CREATE TABLE IF NOT EXISTS "location" (
	"id"	INTEGER,
	"section"	INTEGER,
	"position"	INTEGER,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "warehouse" (
	"id"	INTEGER,
	"manager"	TEXT,
	"address"	TEXT,
	"mark"	TEXT,
	"image"  TEXT,
	PRIMARY KEY("id")
);
INSERT INTO "product" VALUES (1,'keyboard',15,20,30,'#123',1,1);
INSERT INTO "product" VALUES (2,'mouse',10,15,25,'#124',1,2);
INSERT INTO "product" VALUES (3,'monitor',12,200,300,'#125',1,3);
INSERT INTO "product" VALUES (4,'headphones',20,17,31,'#126',1,4);
INSERT INTO "product" VALUES (5,'keyboard',10,20,30,'#123',2,1);
INSERT INTO "product" VALUES (6,'monitor',8,200,300,'#125',2,2);
INSERT INTO "product" VALUES (7,'mouse',20,15,25,'#124',2,3);
INSERT INTO "product" VALUES (8,'CD',50,5,10,'#127',2,4);
INSERT INTO "location" VALUES (1,10,1);
INSERT INTO "location" VALUES (2,20,1);
INSERT INTO "location" VALUES (3,30,1);
INSERT INTO "location" VALUES (4,40,1);
INSERT INTO "warehouse" VALUES (1,'John Smith','Alipasina 1, Sarajevo','A', 'C:\Users\HP\IdeaProjects\rpr-projekat\resources\img\warhouseA.jpg');
INSERT INTO "warehouse" VALUES (2,'Will Smith','Ferhadija 5, Sarajevo','B', 'C:\Users\HP\IdeaProjects\rpr-projekat\resources\img\warehouseB.jpg');
COMMIT;

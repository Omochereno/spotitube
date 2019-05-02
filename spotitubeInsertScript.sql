

INSERT INTO `spotitube`.`user` (`user`,`pass`)
VALUES ('user','pass'),
	('Jaha', 'councel');

INSERT INTO `spotitube`.`track`
VALUES (1, "Ocean and a rock", "Lisa Hannigan", 335, "Sea sew", null, null, null, false),
	(2, "So long, Marianne", "Leonard Cohen", 543, "Songs of Leonard Cohen", null, null, null, false),
    (5, "One", "Metillica", 423, null, 37, "2001-11-01", "Long version", true),
    (3, "The cost", "The frames", 423, null, 30, "2005-01-10", "Title song from the Album The Cost", true);
    
INSERT INTO `spotitube`.`playlist`
VALUES (1, "Heavy Metal", "Jaha"),
	(2, "Pop", "Jaha"),
    (3, "Test", "user");
    
INSERT INTO `spotitube`.`trackinplaylist`
VALUES (1, 1),(1,3),(2,2),(5,1),(3,3);
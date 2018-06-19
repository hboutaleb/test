
 
INSERT INTO GROUPE3.GENRESPECTACLE(GENRE_ID, TYPEGENRE)
        VALUES (1,'Humour');
INSERT INTO GROUPE3.GENRESPECTACLE(GENRE_ID, TYPEGENRE)
        VALUES (2,'Drame');
INSERT INTO GROUPE3.GENRESPECTACLE(GENRE_ID, TYPEGENRE)
        VALUES (3,'Concert Musical');
INSERT INTO GROUPE3.GENRESPECTACLE(GENRE_ID, TYPEGENRE)
        VALUES (4,'Ballet');


INSERT INTO GROUPE3.CATAGE(CATAGEID, NOM)
        VALUES (1,'tout public');
INSERT INTO GROUPE3.CATAGE(CATAGEID, NOM)
        VALUES (2,'enfant 0 à 12 ans');
INSERT INTO GROUPE3.CATAGE(CATAGEID, NOM)
        VALUES (3,'enfant 12 à 14 ans');
INSERT INTO GROUPE3.CATAGE(CATAGEID, NOM)
        VALUES (4,'enfant 14 à 16 ans');
INSERT INTO GROUPE3.CATAGE(CATAGEID, NOM)
        VALUES (5,'enfant 16 à 18 ans');
INSERT INTO GROUPE3.CATAGE(CATAGEID, NOM)
        VALUES (6,'adulte +18 ans');


INSERT INTO GROUPE3.LESSPECTACLES (NOSPEC, NOMS, DUREE, RESUME,GENRE_ID,CATAGEID  ) 
 	VALUES (1, 'La flûte enchantée', 180,'La Reine de la nuit donne pour mission au prince Tamino de sauver sa fille Pamina, prisonnière de Sarastro, le grand-prêtre d''Isis et Osiris. S''il y parvient, il pourra l''épouser. Accompagné de l''oiseleur Papageno, Tamino se met en quête du palais de Sarastro. Pour les aider dans leur combat contre les forces maléfiques, ils reçoivent l''un une flûte enchantée, l''autre des clochettes magiques. Mais la Reine de la nuit se révèle être l''incarnation du mal et Sarastro, un gardien du bien... Tamino devra affronter maintes épreuves - garder le silence, traverser l''eau et le feu - avant d''être initié au culte sacré et de retrouver Pamina. Cette Flûte enchantée est le fruit d''une collaboration entre les chanteurs de la prestigieuse académie de la Scala de Milan, parmi lesquels figurent sans doute des étoiles lyriques de demain, et l''un des plus grands metteurs en scène de notre époque, familier des opéras mozartiens. L''académie est un héritage unique que la Scala cherche à développer davantage. Le but de ce projet est de monter chaque année une production réunissant un chef et un metteur en scène de renom avec les artistes de l''académie pour donner à ces jeunes talents l''opportunité de se faire connaître du grand public. Peter Stein a accepté d''enseigner à l''académie et d''être le metteur en scène de cette production. Reconnu lui aussi comme un spécialiste de Mozart, le chef d''orchestre hongrois Ádám Fischer revient au pupitre du prestigieux théâtre pour la première fois depuis 1998.',1,1);


 INSERT INTO GROUPE3.LESSPECTACLES (NOSPEC, NOMS, DUREE, RESUME,GENRE_ID,CATAGEID )  
 	VALUES (2, 'Coldplay', 120,'Le quatuor composé de Chris Martin (chant), Jon Buckland (guitare), Will Champion (batterie) et Guy Berryman (basse) se forme en 1998 à l''université de Londres avec comme références Travis Jeff Buckley ou l''Oasis des débuts. Leurs chansons doucement mélancoliques intéressent le label Parlophone qui les signent. Après une série de singles, leur album "Parachutes" sort fin 2000 se plaçant directement à la première place des charts british en s''écoulant à plus de 100 000 exemplaires en quelques jours. La hype continue à enfler autour de ces charmants garçons jusqu''à ce que des ennuis de santé compromettent leur première tournée US. Mais vite rétablis, ils commencent 2001 en beauté en étant doublement couronnés aux Brit Award des récompenses du "Meilleur Groupe" et "Album de l''année" une façon de reconnaître que le groupe possède un réel sens de la mélodie teintée de mélancolie contemplative. Bien sûr, l''album n''est pas d''une originalité fracassante, mais il y a là comme une synthèse de tout ce que la pop a produit de meilleur ces dix dernières années de Jeff Buckley à Deus en passant par Radiohead. On y retrouve de douces ballades à la guitare acoustique sur accords de piano romantiques qui prouvent que Coldplay pourrait peut-être devenir le grand groupe qu''il rêve encore d''être',2,3);

INSERT INTO GROUPE3.LESSPECTACLES (NOSPEC, NOMS, DUREE, RESUME, GENRE_ID,CATAGEID ) 
 	VALUES (3, 'Lac des cygnes', 130,'Le Lac des cygnes Ballet magique en 3 actes (4 tableaux) Musique: Piotr Tchaïkovski, Chorégraphie Marius Petipa et Lev Ivanov. Le Lac des cygnes du Saint-Pétersbourg Ballet Théâtre revient en triomphe en 2017! C''est l''école russe de danse classique, l''école de rigueur et d''excellence que vous pouvez admirer dans toute sa splendeur. Les danseurs sont issus des meilleures écoles de ballet de Russie comme l''Académie Vaganova de Saint-Pétersbourg. Le Lac des cygnes est un des ballets les plus célèbres au monde. C''est un des joyaux du répertoire de la musique classique avec des mélodies sublimes de Piotr Tchaïkovski. Interprété par le Saint-Pétersbourg Ballet Théâtre, une des meilleures troupes de Russie qui impressionne et séduit par sa jeunesse et son dynamisme. Les danses sont exécutées avec un style raffiné associé à une énergie stimulante. L''élégance, la discipline des alignements et l''harmonie d''ensemble caractérisent la compagnie du Saint-Pétersbourg Ballet Théâtre (SPBT) qui nous fait vibrer d''émotions en regardant la superbe légende de la danseuse-cygne. Les décors somptueux nous font voyager: un lac romantique perdu dans les forêts avec des délicates danseuses en tutu défilant gracieusement sur une musique enchanteresse,  un palais magnifique avec des danses de différents pays qui vous entrainent dans un tourbillon de mélodies. Une magnifique représentation à voir et à revoir pour toute la famille !',4,1);

 INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (1, TO_DATE ('2017-02-21 08:30:00', 'YYYY-MM-DD HH24:MI:SS'));
 INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (1, TO_DATE ('2017-02-22 20:30:00', 'YYYY-MM-DD HH24:MI:SS'));
 INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (1, TO_DATE ('2017-02-23 20:30:00', 'YYYY-MM-DD HH24:MI:SS'));
 INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (1, TO_DATE ('2017-02-24 20:30:00', 'YYYY-MM-DD HH24:MI:SS'));
 INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (2, TO_DATE ('2017-03-01 20:30:00', 'YYYY-MM-DD HH24:MI:SS'));
 INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (2, TO_DATE ('2017-03-02 20:30:00', 'YYYY-MM-DD HH24:MI:SS'));
 INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (2, TO_DATE ('2017-03-03 18:30:00', 'YYYY-MM-DD HH24:MI:SS'));
 INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (2, TO_DATE ('2017-03-04 20:30:00', 'YYYY-MM-DD HH24:MI:SS'));
 INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (2, TO_DATE ('2017-03-06 20:30:00', 'YYYY-MM-DD HH24:MI:SS'));
 INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (2, TO_DATE ('2017-03-07 20:30:00', 'YYYY-MM-DD HH24:MI:SS'));
 INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (1, TO_DATE ('2017-03-09 08:30:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (3, TO_DATE ('2017-02-21 08:30:00', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (1, TO_DATE ('2017-03-21 08:30:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (1, TO_DATE ('2017-03-23 21:30:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (2, TO_DATE ('2017-03-17 18:30:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (3, TO_DATE ('2017-03-22 20:30:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (1, TO_DATE ('2017-04-21 08:30:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (1, TO_DATE ('2017-03-18 20:30:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (2, TO_DATE ('2017-04-01 19:30:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (2, TO_DATE ('2017-03-25 20:00:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (2, TO_DATE ('2017-03-29 21:30:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (3, TO_DATE ('2017-04-22 08:30:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (3, TO_DATE ('2017-05-02 22:30:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (1, TO_DATE ('2017-05-01 18:30:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO GROUPE3.LESREPRESENTATIONS (NOSPEC, DATEREP) 
 	VALUES (3, TO_DATE ('2017-04-08 09:30:00', 'YYYY-MM-DD HH24:MI:SS'));

 
 INSERT INTO GROUPE3.LESRESERVATIONS (NODOSSIER, BINAIRE) 
 	VALUES (7, 'non');
 INSERT INTO GROUPE3.LESRESERVATIONS (NODOSSIER, BINAIRE) 
 	VALUES (8, 'oui');
 

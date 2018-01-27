--Destruction de toutes les tables
drop table lesphotos;
drop table lesphotosspectacle;
drop table LESTICKETS;
drop table LesRepresentations;
drop table LesSpectacles;
drop table LesSieges;
drop table LesZones;
drop table LESRESERVATIONS;
drop table GENRESPECTACLE;
drop table LESCATEGORIES;
drop table LESCLIENTS;
drop table LESDOSSIERS;
drop table CATAGE;

-- gestion des sequences 
drop sequence NOSERIE_SEQ;
CREATE sequence NOSERIE_SEQ;

-- Creation de toutes les tables
CREATE TABLE LESPHOTOS (NOPHOTO number(4), CONTENU VARCHAR2(20),
        constraint lph_c0 primary key (NOPHOTO));

CREATE TABLE LESPHOTOSSPECTACLE (NOPHOTO number(4), NOSPEC NUMBER(4),
        constraint lps_c0 primary key (NOPHOTO,NOSPEC ));

CREATE TABLE LESCATEGORIES (NOMC VARCHAR2(20), PRIX NUMBER(8, 2),
        constraint lcg_c0 primary key (NOMC),
        constraint lcg_c1 check (NOMC in('orchestre','1er balcon','2nd balcon','poulailler')));

CREATE TABLE LESZONES (NOZONE NUMBER(4), NOMC VARCHAR2(20),
        constraint lzn_c0 primary key (NOZONE),
	constraint lzn_c1 foreign key (NOMC) references LESCATEGORIES(NOMC));

CREATE TABLE LESSIEGES (NOPLACE NUMBER(4), NORANG NUMBER(4), NOZONE NUMBER(4),
        constraint lsg_c0 primary key (NOPLACE,NORANG),
	constraint lsg_c1 foreign key (NOZONE) references LESZONES(NOZONE));

CREATE TABLE GENRESPECTACLE (GENRE_ID NUMBER(4), TYPEGENRE VARCHAR2(40),
        constraint lgs_c0 primary key (GENRE_ID),
        constraint lgs_c1 check(TYPEGENRE in('Humour','Drame','Concert Musical', 'Ballet')));

CREATE TABLE CATAGE (CATAGEID NUMBER(4), NOM VARCHAR2(40),
        constraint Lag_c0 primary key (CATAGEID),
        constraint lag_c1 check(NOM in('tout public','enfant 0 à 12 ans','enfant 12 à 14 ans','enfant 14 à 16 ans','enfant 16 à 18 ans','adulte +18 ans')));

CREATE TABLE LESSPECTACLES (NOSPEC NUMBER(4), NOMS VARCHAR2(40), DUREE NUMBER(4), RESUME VARCHAR2 (4000), GENRE_ID NUMBER(4) , CATAGEID NUMBER(4),
        constraint lsp_c0 primary key (NOSPEC),
        constraint lsp_c1 foreign key (GENRE_ID) references GENRESPECTACLE(GENRE_ID),
        constraint lsp_c2 foreign key (CATAGEID) references CATAGE(CATAGEID));
        
CREATE TABLE LESREPRESENTATIONS (NOSPEC NUMBER(4), DATEREP DATE,
        constraint lrp_c0 primary key (NOSPEC,DATEREP),
	constraint lrp_c1 foreign key (NOSPEC) references LESSPECTACLES(NOSPEC));

CREATE TABLE LESRESERVATIONS (NODOSSIER NUMBER(4), BINAIRE VARCHAR2(10),
        constraint lrs_c0 primary key (NODOSSIER));

CREATE TABLE LESDOSSIERS (NODOSSIER NUMBER(4),MONTANT NUMBER(4),EMAIL VARCHAR2(40), 
constraint lds_c0 primary key (NODOSSIER),
constraint lds_c1 foreign key (EMAIL) references LESCLIENTS(EMAIL));

CREATE TABLE LESTICKETS (NOSERIE NUMBER(4), NOSPEC NUMBER(4), DATEREP DATE, NOPLACE NUMBER(4), NORANG NUMBER(4), DATEEMISSION DATE, NODOSSIER NUMBER(4),
        constraint ltk_c0 primary key (NOSERIE),	
	constraint ltk_c2 foreign key (NOSPEC,DATEREP) references LESREPRESENTATIONS(NOSPEC,DATEREP),
	constraint ltk_c3 foreign key (NOPLACE, NORANG) references LESSIEGES(NOPLACE, NORANG),
        constraint ltk_c1 unique (NOSPEC,DATEREP,NOPLACE,NORANG));

CREATE TABLE LESCLIENTS (EMAIL VARCHAR2(40),NOM VARCHAR2(40),PRENOM VARCHAR2(40),
        constraint lus_c0 primary key (EMAIL));
CREATE TABLE JUGES (
    NO_JUGE NUMBER(38) NOT NULL,
    NOM VARCHAR2(32) NOT NULL,
    PRENOM VARCHAR2(32) NOT NULL,
    PAYS VARCHAR2(3) NOT NULL,
    PASSWORD VARCHAR2(16) NOT NULL,
    PRIMARY KEY (NO_JUGE)
);

INSERT INTO JUGES (NO_JUGE, NOM, PRENOM, PAYS, PASSWORD) 
     VALUES (1, 'FORE', 'Liselotte', 'USA', 'juge1');
INSERT INTO JUGES (NO_JUGE, NOM, PRENOM, PAYS, PASSWORD) 
     VALUES (2, 'CLARKE', 'Stephen', 'GBR', 'juge2');
INSERT INTO JUGES (NO_JUGE, NOM, PRENOM, PAYS, PASSWORD) 
     VALUES (3, 'JUDET', 'Isabelle', 'FR', 'juge3');
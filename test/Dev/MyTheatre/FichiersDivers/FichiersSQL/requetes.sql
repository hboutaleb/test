-- Requete de recherche de spectacles sur une plage de date uniquement
select nomS
from LesRepresentations R
join LesSpectacles S on (R.nospec = S.nospec)
where dateRep >= to_date('03/03/2017', 'DD/MM/YYYY')
AND dateRep < to_date('04/03/2017', 'DD/MM/YYYY');

-- Requete de recherche de spectacles sur une plage de date completees par les heures
-- resultat vide attendu
select nomS
from LesRepresentations R
join LesSpectacles S on (R.nospec = S.nospec)
where dateRep >= to_date('03/03/2017 08:00:00', 'DD/MM/YYYY HH24:MI:SS')
AND dateRep < to_date('03/03/2017 18:00:00', 'DD/MM/YYYY HH24:MI:SS');

-- Requete de recherche de spectacles sur une plage de date completees par les heures
-- 1 spectacle attendu
select nomS
from LesRepresentations R
join LesSpectacles S on (R.nospec = S.nospec)
join CATAGE C on (C.CATAGEID = S.CATAGEID)
where dateRep >= to_date('03/03/2017 08:00:00', 'DD/MM/YYYY HH24:MI:SS')
AND dateRep < to_date('03/03/2017 19:00:00', 'DD/MM/YYYY HH24:MI:SS')
AND NOM= 'enfant12A14ans';

select nomS, dateRep, TYPEGENRE,NOM 
                    from LesRepresentations R                   
                    join LesSpectacles S on (R.nospec = S.nospec)  
                    JOIN GENRESPECTACLE G on (S.GENRE_ID=G.GENRE_ID)  
                    JOIN CATAGE C on (S.CATAGEID=C.CATAGEID) 
                    where dateRep >=  to_date('01/01/2017 08:00:00', 'DD/MM/YYYY HH24:MI:SS')
                    AND dateRep < to_date('03/03/2017 19:00:00', 'DD/MM/YYYY HH24:MI:SS')
                    AND NOM= 'enfant12A14ans';

select * 
from LesSpectacles
where nomS='Coldplay'; 

SELECT * from LESCATEGORIES;

select R.NOSPEC,S.NOMS,S.RESUME,R.DATEREP,G.TYPEGENRE,C.NOM ,count(T.NOPLACE) as nbplacepispo
                    FROM LesRepresentations R 
                    join LesSpectacles S on (R.NOSPEC = S.NOSPEC) 
                    join GENRESPECTACLE G on (G.GENRE_ID = S.GENRE_ID) 
                    join CATAGE C on (S.CATAGEID=C.CATAGEID) 
                    join lestickets T on (T.NOSPEC = R.NOSPEC and T.DATEREP = R.DATEREP)
                    where R.DATEREP = to_date('21/02/2017 8:30:00', 'DD/MM/YYYY HH24:MI:SS')
                    and R.NOSPEC=1
                    group by R.NOSPEC,R.DATEREP;

select 1000-count(NOPLACE) as nbplacereserve 
 FROM (select  R.NOSPEC,S.NOMS,S.RESUME,R.DATEREP,G.TYPEGENRE,C.NOM, T.NOPLACE
                    FROM LesRepresentations R 
                    join LesSpectacles S on (R.NOSPEC = S.NOSPEC) 
                    join GENRESPECTACLE G on (G.GENRE_ID = S.GENRE_ID) 
                    join CATAGE C on (S.CATAGEID=C.CATAGEID) 
                    join lestickets T on (T.NOSPEC = R.NOSPEC and T.DATEREP = R.DATEREP)
                    where R.DATEREP = to_date('21/02/2017 8:30:00', 'DD/MM/YYYY HH24:MI:SS')
                    and R.NOSPEC=1
                    ORDER BY T.NOPLACE)
group by R.NOSPEC

-- requete DAOplaceDisponible
select nospec,daterep,1000-count(noplace) as nbplacepispo
from lestickets
group by nospec,daterep;

--nombre de place de chaque zone 
select NOMC, count(*) as nbsiege
from lessieges S
join leszones Z ON (S.NOZONE = Z.NOZONE)
group by NOMC
;
--nombre de place resirvé par zone
select NOMC,count(*) as NBplacesres
from lestickets T
join lessieges S on(T.NOPLACE=S.NOPLACE AND T.NORANG=S.NORANG)
join leszones Z ON (S.NOZONE = Z.NOZONE)
 where T.DATEREP = to_date('01/03/2017 20:30:00', 'DD/MM/YYYY HH24:MI:SS')
  and T.NOSPEC=2
group by nomc;


SELECT NOMC,COUNT(*) AS nbPlacedispo
from(
select NOMC,NOPLACE,NORANG
from lessieges S
join leszones Z ON (S.NOZONE = Z.NOZONE)
MINUS
select NOMC,T.NOPLACE,T.NORANG
from lestickets T
join lessieges S on(T.NOPLACE=S.NOPLACE AND T.NORANG=S.NORANG)
join leszones Z ON (S.NOZONE = Z.NOZONE)
 where T.DATEREP = to_date('09/03/2017 08:30:00', 'DD/MM/YYYY HH24:MI:SS')
  and T.NOSPEC=1
order by NORANG)
group by NOMC;

SELECT NOMC,COUNT(*) AS nbPlacedispo
from(
select NOMC,NOPLACE,NORANG
from lessieges S
join leszones Z ON (S.NOZONE = Z.NOZONE)
where Z.nomc = 'poulailler'
MINUS
select NOMC,T.NOPLACE,T.NORANG
from lestickets T
join lessieges S on(T.NOPLACE=S.NOPLACE AND T.NORANG=S.NORANG)
join leszones Z ON (S.NOZONE = Z.NOZONE)
 where T.DATEREP = to_date('09/03/2017 08:30:00', 'DD/MM/YYYY HH24:MI:SS')
  and T.NOSPEC=1 and Z.nomc = 'poulailler'
order by NORANG
)
group by NOMC;




select count(*) as nb
from lesclients
where EMAIL='hoc@yahoo.fr';


 select  max(NODOSSIER)as maxND
 from LESDOSSIERS

select P.nophoto, contenu 
from lesphotosspectacle PS
join lesphotos P on (PS.nophoto = P.NOPHOTO)
where nospec = 1;
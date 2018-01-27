
create or replace view LesMontantsDossiers (montant, nodossier) as
select sum (prix), nodossier
from LesCategories
natural join LesZones
natural join LesCategories
natural join LesTickets
group by nodossier;

select * from LesMontantsDossiers where nodossier=61;


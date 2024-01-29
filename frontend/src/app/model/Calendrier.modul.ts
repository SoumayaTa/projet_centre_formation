import { Entreprise } from './Entreprise.modul';
import { Formation } from './formation.model';
import { Groupe } from './Group.model';
import { User } from './user.model';

export interface Calendrier {
  id?: number;
  datedebut: Date;
  datefin: Date;
  formation: Formation;
  formateur: User;
  entreprise: Entreprise;
  groupe: Groupe;
}

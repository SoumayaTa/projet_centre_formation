export interface Formation {
    id?: number;  // Le "?" indique que le champ est facultatif
    nom: string;
    nombreHeur: number;
    cout: number;
    objectifs: string;
    programme: string;
    categorie: string;
    ville: string;
    date: Date;
  }
  
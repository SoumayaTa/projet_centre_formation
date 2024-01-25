export interface Formation {
  id?: number; 
  nom: string;
  nombreHeur: number;
  cout: number;
  objectifs: string;
  programme: string;
  categorie: string;
  ville: string;
  date: Date;
  groupe_seuil: number;
  photos?: string;  
}
  
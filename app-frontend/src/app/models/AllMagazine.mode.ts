export interface AllMagazineResponse {
    id: number;
    name: string;
    author: AuthorResponse;
    description: string;
    canComment: boolean;
    canLike: boolean;
    canSubscribe: boolean;
    type: MagazineType;
    price: number;
    isEnabled: boolean;
    dateCreated: string;
    documents: DocumentResponse[];
    labels: LabelDTO[];
    categories: CategoryResponse[];
  }
  
  export type MagazineType = 'FREE' | 'PAID';
  
  export interface DocumentResponse {
    id: number;
    name: string;
    path: string;
  }
  
  export interface LabelDTO {
    id: number;
    name: string;
  }
  
  export interface CategoryResponse {
    id: number;
    name: string;
  }

  export interface AuthorResponse{
    id: number;
    username: string;
    email: string;
  }
  
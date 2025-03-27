export interface Magazine{
    id: number;
    name: string;
    FK_User: number;
    description: string;
    canComment: boolean;
    canLike: boolean;
    canSubscribe: boolean;
    type: MagazineType;
    price: number;
    isEnabled: boolean;
    path:string;
}

enum MagazineType{
    FREE = 'FREE',
    PAID = 'PAID'
}
import { User } from "./auth.model";

export interface Board {
    id: string;
    name: string;
    description: string;
    image: string;
    users: User[];
    columns: Column[];
    creationDate: Date;
    updateDate: Date;
}

export interface Column {
    id: string;
    name: string;
    order: number;
}
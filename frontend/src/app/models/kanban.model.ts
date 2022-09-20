import { User } from './auth.model';

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

export interface CreateBoard {
  name: string;
  description: string;
  image: string;
}

export interface UpdateBoardDto {
  name: string;
  description: string;
  image: string;
  users: string[]
}

export interface Column {
  id: string;
  name: string;
  order: number;
  cards: Card[];
  creationDate: Date;
  updateDate: Date;
}

export interface CreateColumn {
  name: string;
  boardId: string;
}

export interface Card {
  id: string;
  name: string;
  description: string;
  order: number;
  users: User[];
  priority: Priority;
  columnId: string;
  creationDate: Date;
  updateDate: Date;
}

export interface CreateCard {
  name: string;
  columnId: string;
}

export interface UpdateCard {
  name: string;
  description: string;
  users: User[];
  priorityName: string;
}

export interface Priority {
  id: string;
  name: string;
  columnId: string;
}

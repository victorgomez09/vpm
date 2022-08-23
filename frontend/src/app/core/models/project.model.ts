import { IUser } from "./auth.model";

export interface IProject {
  id: string;
  name: string;
  description: string;
  tag: string;
  users: IUser[];
}
